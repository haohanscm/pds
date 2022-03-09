package com.haohan.platform.service.sys.modules.pds.web.operation;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
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

/**
 * 运营平台操作  供应商报价  Controller
 *
 * @author haohan
 * @version 2018-10-25
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/action/offer")
public class PlatformOfferController extends BaseController {

    @Autowired
    private BuyOrderService buyOrderService;
    @Resource
    private IPdsSummaryService iPdsSummaryServiceImpl;

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
        Page<BuyOrder> page = buyOrderService.findPage(new Page<BuyOrder>(request, response), buyOrder);
        model.addAttribute("page", page);
        return "modules/pds/order/buyOrderList";
    }

    @RequiresPermissions("pds:order:buyOrder:view")
    @RequestMapping(value = "form")
    public String form(BuyOrder buyOrder, Model model) {
        model.addAttribute("buyOrder", buyOrder);
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

    @RequiresPermissions("pds:order:buyOrder:view")
    @RequestMapping(value = {"summary"})
    public String summary(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String buySeq = request.getParameter("buySeqSelection");
        String deliveryDate = request.getParameter("_deliveryDate");
        Date date = DateUtils.parseDate(deliveryDate);
        BaseResp baseResp = BaseResp.newError();
//		BaseResp baseResp = iPdsSummaryServiceImpl.summaryBuyOrders(date,buySeq,true);
        addMessage(redirectAttributes, baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrder/?repage";
    }

}