package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.core.delivery.IPdsDeliveryService;
import com.haohan.platform.service.sys.modules.pds.core.operation.IPdsOperationService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.weixin.mp.message.WxMpMessageService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 交易订单Controller
 *
 * @author haohan
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/tradeOrder")
public class TradeOrderController extends BaseController {

    @Autowired
    private TradeOrderService tradeOrderService;
    @Resource
    private IPdsDeliveryService pdsDeliveryServiceImpl;
    @Resource
    private OfferOrderService offerOrderService;
    @Resource
    private MerchantEmployeeService merchantEmployeeService;
    @Resource
    private WxMpMessageService wxMpMessageService;
    @Resource
    private IPdsCommonService pdsCommonService;
    @Resource
    private PdsSupplierService pdsSupplierService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    private IPdsOperationService pdsOperationService;

    @ModelAttribute
    public TradeOrder get(@RequestParam(required = false) String id) {
        TradeOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = tradeOrderService.get(id);
        }
        if (entity == null) {
            entity = new TradeOrder();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:tradeOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(TradeOrder tradeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeOrder> page = new Page<TradeOrder>(request, response);
        tradeOrder.setPage(page);
        page.setList(tradeOrderService.findJoinList(tradeOrder));
        model.addAttribute("page", page);
        List<PdsSupplier> supplierList = pdsSupplierService.findUsableList();
        model.addAttribute("supplierList", supplierList);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/tradeOrderList";
    }

    @RequiresPermissions("pds:order:tradeOrder:view")
    @RequestMapping(value = "form")
    public String form(TradeOrder tradeOrder, Model model) {
        model.addAttribute("tradeOrder", tradeOrder);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/tradeOrderForm";
    }

    @RequiresPermissions("pds:order:tradeOrder:edit")
    @RequestMapping(value = "save")
    public String save(TradeOrder tradeOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, tradeOrder)) {
            return form(tradeOrder, model);
        }
        tradeOrderService.save(tradeOrder);
        addMessage(redirectAttributes, "保存交易订单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
    }

    @RequiresPermissions("pds:order:tradeOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeOrder tradeOrder, RedirectAttributes redirectAttributes) {
        tradeOrderService.delete(tradeOrder);
        addMessage(redirectAttributes, "删除交易订单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
    }

    @RequiresPermissions("pds:order:tradeOrder:edit")
    @RequestMapping(value = "truckLoad")
    public String truckLoad(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String deliveryDate = request.getParameter("deliveryDate");
        String type = request.getParameter("typeChoose");
        String buySeq = request.getParameter("buySeq");
        String pmId = request.getParameter("pmId");

        TradeOrder tradeOrder = new TradeOrder();
        if ("1".equals(type)) {
            if (StringUtils.isAnyEmpty(pmId, buySeq, deliveryDate)) {
                redirectAttributes.addFlashAttribute("tip_message", "必要参数不能为空");
                return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
            }
            Date date = DateUtils.parseDate(deliveryDate);
            tradeOrder.setDeliveryTime(date);
            tradeOrder.setBuySeq(buySeq);
        } else {
            tradeOrder.setDeliveryTime(new Date());
        }
        tradeOrder.setPmId(pmId);
        BaseResp baseResp = BaseResp.newError();
        try {
            baseResp = pdsDeliveryServiceImpl.truckLoad(tradeOrder);
        } catch (Exception e) {
            baseResp.setMsg("系统错误");
        }
        redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
    }

    @RequiresPermissions("pds:order:tradeOrder:edit")
    @RequestMapping(value = "selfOrderArrived")
    public String selfOrderArrived(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String deliveryDate = request.getParameter("deliveryDate");
        String type = request.getParameter("typeChoose");
        String buySeq = request.getParameter("buySeq");
        String pmId = request.getParameter("pmId");

        Date date = new Date();
        if ("1".equals(type)) {
            if (StringUtils.isAnyEmpty(buySeq, deliveryDate)) {
                redirectAttributes.addFlashAttribute("tip_message", "必要参数不能为空");
                return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
            }
            date = DateUtils.parseDate(deliveryDate);
        }

        BaseResp baseResp = BaseResp.newError();
        try {
            baseResp = pdsDeliveryServiceImpl.selfOrderArrived(date, buySeq, pmId);
        } catch (Exception e) {
            baseResp.setMsg("系统错误");
        }
        redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
    }

    @RequiresPermissions("pds:order:tradeOrder:edit")
    @RequestMapping(value = "prepareNotify")
    public String prepareNotify(String supplier, Date deliveryDate, String buySeq, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(buySeq, supplier) || null == deliveryDate) {
            redirectAttributes.addFlashAttribute("tip_message", "必要参数不能为空");
            return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
        }

        PdsSupplier pdsSupplier = pdsSupplierService.get(supplier);
        OfferOrder offerOrder = new OfferOrder();
        offerOrder.setSupplierId(supplier);
        offerOrder.setPrepareDate(deliveryDate);
        offerOrder.setBuySeq(buySeq);

        List<OfferOrder> offerOrders = offerOrderService.findList(offerOrder);
        if (CollectionUtils.isEmpty(offerOrders)) {
            baseResp.setMsg("发送失败,未找到报价单");
            redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
            return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
        }

        String orderIdMsg = offerOrders.get(0).getOfferOrderId().concat("等");
        StringBuilder sb = new StringBuilder();
        int size = offerOrders.size();
        for (OfferOrder order : offerOrders) {
            int index = offerOrders.indexOf(order);
            if (index == 10) break;
            sb.append(order.getGoodsName());
            if (index != size - 1) {
                sb.append("、");
            }
        }
        sb.append("等，共" + size + "件商品");

        MerchantEmployee employee = new MerchantEmployee();
        employee.setRole(IPdsConstant.EmployeeRole.buyer.getCode());
        employee.setStatus(ICommonConstant.IsEnable.enable.getCode());
        MerchantEmployee merchantEmployee = merchantEmployeeService.findList(employee).get(0);

        UserOpenPlatform useropen = pdsCommonService.fetchOpenUserByUid(pdsSupplier.getPassportId(), IPdsConstant.WX_MP_APPID, pdsSupplier.getId(), IPdsConstant.CompanyType.supplier);
        if (null == useropen) {
            baseResp.setMsg("发送失败,供应商未绑定手机");
        } else {
            baseResp = wxMpMessageService.supPrepareNotify(useropen, orderIdMsg, sb.toString(), merchantEmployee, deliveryDate, buySeq);
        }

        redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
    }

    @RequiresPermissions("pds:order:tradeOrder:edit")
    @RequestMapping(value = "supplierFreight")
    public String supplierFreight(TradeOrder tradeOrder, RedirectAttributes redirectAttributes) {
        // 供应商揽货
        BaseResp baseResp = null;
        try {
            baseResp = pdsOperationService.supplierFreightConfirm(tradeOrder.getPmId(), tradeOrder.getSupplierId(), tradeOrder.getBuySeq(), tradeOrder.getDeliveryTime());
        } catch (StorageOperationException e) {
            e.printStackTrace();
            baseResp.setMsg(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeOrder/?repage";
    }

}