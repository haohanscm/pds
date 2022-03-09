package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDataResetApiReq;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminShortcutService;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import com.haohan.platform.service.sys.modules.pds.exception.PdsSummaryOperationException;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.SummaryOrderService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 采购单汇总Controller
 *
 * @author haohan
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/summaryOrder")
public class SummaryOrderController extends BaseController {

    @Autowired
    private SummaryOrderService summaryOrderService;
    @Resource
    private IPdsSummaryService iPdsSummaryServiceImpl;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    @Lazy(true)
    private IPdsAdminShortcutService adminShortcutService;

    @ModelAttribute
    public SummaryOrder get(@RequestParam(required = false) String id) {
        SummaryOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = summaryOrderService.get(id);
        }
        if (entity == null) {
            entity = new SummaryOrder();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:summaryOrder:view")
    @RequestMapping(value = {"list", ""})
    public String list(SummaryOrder summaryOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SummaryOrder> page = new Page<SummaryOrder>(request, response);
        summaryOrder.setPage(page);
        page.setList(summaryOrderService.findJoinList(summaryOrder));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/summaryOrderList";
    }

    @RequiresPermissions("pds:order:summaryOrder:view")
    @RequestMapping(value = "form")
    public String form(SummaryOrder summaryOrder, Model model) {
        model.addAttribute("summaryOrder", summaryOrder);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/summaryOrderForm";
    }

    @RequiresPermissions("pds:order:summaryOrder:edit")
    @RequestMapping(value = "save")
    public String save(SummaryOrder summaryOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, summaryOrder)) {
            return form(summaryOrder, model);
        }
        summaryOrderService.save(summaryOrder);
        addMessage(redirectAttributes, "保存采购单汇总成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/summaryOrder/?repage";
    }

    @RequiresPermissions("pds:order:summaryOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(SummaryOrder summaryOrder, RedirectAttributes redirectAttributes) {
        summaryOrderService.delete(summaryOrder);
        addMessage(redirectAttributes, "删除采购单汇总成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/summaryOrder/?repage";
    }

    @RequiresPermissions("pds:order:summaryOrder:edit")
    @RequestMapping(value = "confirmOffer")
    public String confirmOffer(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        BaseResp baseResp = BaseResp.newError();
        String buySeq = request.getParameter("buySeq");
        String deliveryDateStr = request.getParameter("deliveryDate");
        String selected = request.getParameter("typeChoose");
        String pmId = request.getParameter("pmId");

        Date date;
        if ("1".equals(selected)) {
            if (StringUtils.isAnyEmpty(buySeq, deliveryDateStr)) {
                redirectAttributes.addFlashAttribute("tip_message", "必要参数不能为空");
                return "redirect:" + Global.getAdminPath() + "/pds/order/summaryOrder/?repage";
            }
            //自定义时间
            date = DateUtils.parseDate(deliveryDateStr);
        } else {
            //默认当天
            date = new Date();
        }

        try {
            baseResp = iPdsSummaryServiceImpl.confirmOffer(pmId, buySeq, date);
        } catch (PdsSummaryOperationException e) {
            baseResp.setMsg(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/summaryOrder/?repage";
    }

    // 一键重置 同一批次 至汇总前
    @RequiresPermissions("pds:order:summaryOrder:edit")
    @RequestMapping(value = "resetSummary")
    public String resetSummary(@Validated PdsDataResetApiReq dataResetReq, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        BaseResp baseResp = BaseResp.newError();
        if (validBindingResult(bindingResult, baseResp)) {
            baseResp = adminShortcutService.resetSummary(dataResetReq);
        }
        addMessage(redirectAttributes, baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/summaryOrder/?repage";
    }

}