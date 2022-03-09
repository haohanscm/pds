package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.order.PdsShipOrderDetailService;
import com.haohan.platform.service.sys.modules.pds.service.order.PdsShipOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.ServiceOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGallery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGalleryService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 售后单Controller
 *
 * @author haohan
 * @version 2018-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/serviceOrder")
public class ServiceOrderController extends BaseController {

    @Autowired
    private ServiceOrderService serviceOrderService;
    @Resource
    private PhotoGalleryService photoGalleryService;
    @Resource
    private PdsBuyerService pdsBuyerService;
    @Resource
    private PdsSupplierService pdsSupplierService;
    @Autowired
    @Lazy(true)
    private TradeOrderService tradeOrderService;
    @Autowired
    @Lazy(true)
    private PdsShipOrderDetailService shipOrderDetailService;
    @Autowired
    @Lazy(true)
    private PdsShipOrderService shipOrderService;


    @ModelAttribute
    public ServiceOrder get(@RequestParam(required = false) String id) {
        ServiceOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = serviceOrderService.get(id);
        }
        if (entity == null) {
            entity = new ServiceOrder();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:serviceOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(ServiceOrder serviceOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ServiceOrder> page = new Page<ServiceOrder>(request, response);
        serviceOrder.setPage(page);
        page.setList(serviceOrderService.findJoinList(serviceOrder));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/serviceOrderList";
    }

    @RequiresPermissions("pds:order:serviceOrder:view")
    @RequestMapping(value = "form")
    public String form(ServiceOrder serviceOrder, Model model) {
        serviceOrder.parseFeedbackInfo();
        String[] imgIds = serviceOrder.getFeedbackImgIds();
        if (ArrayUtils.isNotEmpty(imgIds)) {
            List<String> imgUrlList = new ArrayList<>();
            for (String id : imgIds) {
                PhotoGallery photoGallery = photoGalleryService.get(id);
                imgUrlList.add(photoGallery.getPicUrl());
            }
            serviceOrder.setFeedbackImgList(imgUrlList);
        }
        List<PdsBuyer> buyerList = pdsBuyerService.findList(new PdsBuyer());
        List<PdsSupplier> supplierList = pdsSupplierService.findUsableList();
        model.addAttribute("buyerList", buyerList);
        model.addAttribute("supplierList", supplierList);
        model.addAttribute("serviceOrder", serviceOrder);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/serviceOrderForm";
    }

    @RequiresPermissions("pds:order:serviceOrder:edit")
    @RequestMapping(value = "save")
    public String save(ServiceOrder serviceOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, serviceOrder)) {
            return form(serviceOrder, model);
        }
        serviceOrderService.save(serviceOrder);
        addMessage(redirectAttributes, "保存售后单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/serviceOrder/?repage";
    }

    @RequiresPermissions("pds:order:serviceOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(ServiceOrder serviceOrder, RedirectAttributes redirectAttributes) {
        serviceOrderService.delete(serviceOrder);
        addMessage(redirectAttributes, "删除售后单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/serviceOrder/?repage";
    }

    // 添加售后单
    @RequiresPermissions("pds:order:serviceOrder:edit")
    @RequestMapping(value = "addByTradeOrder")
    public String addByTradeOrder(TradeOrder tradeOrder, Model model) {
        TradeOrder order = tradeOrderService.get(tradeOrder.getId());
        if (null != order) {
            tradeOrder = order;
        }
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setPmId(tradeOrder.getPmId());
        serviceOrder.setTradeId(tradeOrder.getTradeId());
        serviceOrder.setBuyId(tradeOrder.getBuyId());
        serviceOrder.setBuyerId(tradeOrder.getBuyerId());
        serviceOrder.setSupplierId(tradeOrder.getSupplierId());
        serviceOrder.setDeliveryTime(tradeOrder.getDeliveryTime());
        serviceOrder.setStatus(IPdsConstant.ServiceStatus.wait.getCode());
        serviceOrder.setLinkMan(tradeOrder.getContact());
        serviceOrder.setLinkPhone(tradeOrder.getContactPhone());
        // 获取配送编号
        PdsShipOrderDetail detail = new PdsShipOrderDetail();
        detail.setTradeId(order.getTradeId());
        List<PdsShipOrderDetail> detailList = shipOrderDetailService.findList(detail);
        if (!Collections3.isEmpty(detailList)) {
            PdsShipOrder shipOrder = new PdsShipOrder();
            shipOrder.setShipId(detailList.get(0).getShipId());
            List<PdsShipOrder> shipOrderList = shipOrderService.findList(shipOrder);
            if (!Collections3.isEmpty(shipOrderList)) {
                serviceOrder.setDeliveryId(shipOrderList.get(0).getDeliveryId());
            }
        }
        return form(serviceOrder, model);
    }

}