package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDataResetApiReq;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerOrderService;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 采购单Controller
 *
 * @author haohan
 * @version 2018-10-22
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/buyOrder")
public class BuyOrderController extends BaseController {

    @Autowired
    private BuyOrderService buyOrderService;
    @Autowired
    @Lazy(true)
    private IPdsSummaryService pdsSummaryService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService pdsBuyerService;
    @Autowired
    @Lazy(true)
    private IBuyerOrderService buyerOrderService;
    @Autowired
    @Lazy(true)
    private IBuyerPaymentService buyerPaymentService;

    @ModelAttribute
    public BuyOrder get(@RequestParam(required = false) String id) {
        BuyOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = buyOrderService.get(id);
        }
        if (entity == null) {
            entity = new BuyOrder();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:buyOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(BuyOrder buyOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BuyOrder> page = new Page<BuyOrder>(request, response);
        buyOrder.setPage(page);
        page.setList(buyOrderService.findJoinList(buyOrder));
        model.addAttribute("page", page);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(new PdsBuyer());
        model.addAttribute("buyerList", buyerList);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/buyOrderList";
    }

    @RequiresPermissions("pds:order:buyOrder:view")
    @RequestMapping(value = "form")
    public String form(BuyOrder buyOrder, Model model) {
        model.addAttribute("buyOrder", buyOrder);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(new PdsBuyer());
        model.addAttribute("buyerList", buyerList);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/buyOrderForm";
    }

    @RequiresPermissions("pds:order:buyOrder:edit")
    @RequestMapping(value = "save")
    public String save(BuyOrder buyOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, buyOrder)) {
            return form(buyOrder, model);
        }
        buyOrderService.save(buyOrder);
        addMessage(redirectAttributes, "保存采购单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrder/?repage";
    }

    @RequiresPermissions("pds:order:buyOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(BuyOrder buyOrder, RedirectAttributes redirectAttributes) {
        buyOrderService.delete(buyOrder);
        addMessage(redirectAttributes, "删除采购单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrder/?repage";
    }

    @RequiresPermissions("pds:order:buyOrder:edit")
    @RequestMapping(value = "confirmBuyOrder")
    public String confirmBuyOrder(BuyOrder buyOrder, RedirectAttributes redirectAttributes) {
        BaseResp baseResp = buyerOrderService.confirmBuyOrder(buyOrder);
        if (baseResp.isSuccess()) {
            addMessage(redirectAttributes, "采购单报价确认成功");
        } else {
            addMessage(redirectAttributes, baseResp.getMsg());
        }
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrder/?repage";
    }

    // 确认收货
    @RequiresPermissions("pds:order:buyOrder:edit")
    @RequestMapping(value = "confirmAllGoods")
    @ResponseBody
    public BaseResp confirmAllGoods(BuyOrder buyOrder, RedirectAttributes redirectAttributes) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.setMsg("参数有误");
            return baseResp;
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setBuyId(buyOrder.getBuyId());
        tradeOrder.setBuyerId(buyOrder.getBuyerId());
        baseResp = buyerOrderService.confirmAllGoods(tradeOrder);
        return baseResp;
    }

    // 生成货款记录
    @RequiresPermissions("pds:order:buyOrder:edit")
    @RequestMapping(value = "paymentRecord")
    @ResponseBody
    public BaseResp paymentRecord(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.setMsg("参数有误");
            return baseResp;
        }
        baseResp = buyerPaymentService.paymentRecord(buyOrder);
        return baseResp;
    }

    @RequiresPermissions("pds:order:buyOrder:edit")
    @RequestMapping(value = "summary")
    public String summary(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String deliveryDate = request.getParameter("deliveryDate");
        String selected = request.getParameter("typeChoose");
        String buySeq = request.getParameter("buySeq");
        String pmId = request.getParameter("pmId");

        Date date;
        if ("1".equals(selected)) {
            if (StringUtils.isAnyEmpty(deliveryDate, buySeq, pmId)) {
                redirectAttributes.addFlashAttribute("tip_message", "必要参数不能为空");
                return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrder/?repage";
            }
            //自定义时间
            date = DateUtils.parseDate(deliveryDate);
        } else {
            //默认当天
            date = new Date();
        }
        BaseResp baseResp = pdsSummaryService.summaryBuyOrders(pmId, date, buySeq);
        redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrder/?repage";
    }

    // 一键生成货款记录 同一批次
    @RequiresPermissions("pds:order:buyOrder:edit")
    @RequestMapping(value = "confirmAllGoodsBatch")
    public String confirmAllGoodsBatch(@Validated PdsDataResetApiReq dataResetReq, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        BaseResp baseResp = BaseResp.newError();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        if (validBindingResult(bindingResult, baseResp)) {
            BuyOrder buyOrder = new BuyOrder();
            buyOrder.setPmId(dataResetReq.getPmId());
            buyOrder.setBuySeq(dataResetReq.getBuySeq());
            buyOrder.setDeliveryTime(dataResetReq.getDeliveryDate());
            List<BuyOrder> list = buyOrderService.findList(buyOrder);
            if (Collections3.isEmpty(list)) {
                baseResp.setMsg("当前时间批次无采购单");
            } else {
                baseResp = buyerPaymentService.paymentRecordBatch(list, successMsg, errorMsg);
            }
        }
        addMessage(redirectAttributes, baseResp.getMsg(), successMsg.toString(), errorMsg.toString());
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrder/?repage";
    }

}