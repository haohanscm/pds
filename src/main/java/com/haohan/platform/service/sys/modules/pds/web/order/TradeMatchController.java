package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeMatch;
import com.haohan.platform.service.sys.modules.pds.exception.PdsSummaryOperationException;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.SummaryOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeMatchService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
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
 * 交易匹配单Controller
 *
 * @author haohan
 * @version 2018-10-17
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/tradeMatch")
public class TradeMatchController extends BaseController {

    @Autowired
    private TradeMatchService tradeMatchService;
    @Resource
    private IPdsSummaryService iPdsSummaryServiceImpl;
    @Resource
    private SummaryOrderService summaryOrderService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public TradeMatch get(@RequestParam(required = false) String id) {
        TradeMatch entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = tradeMatchService.get(id);
        }
        if (entity == null) {
            entity = new TradeMatch();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:tradeMatch:view")
    @RequestMapping(value = {"list", ""})
    public String list(TradeMatch tradeMatch, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TradeMatch> page = new Page<TradeMatch>(request, response);
        tradeMatch.setPage(page);
        page.setList(tradeMatchService.findJoinList(tradeMatch));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/tradeMatchList";
    }

    @RequiresPermissions("pds:order:tradeMatch:view")
    @RequestMapping(value = "form")
    public String form(TradeMatch tradeMatch, Model model) {
        model.addAttribute("tradeMatch", tradeMatch);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/tradeMatchForm";
    }

    @RequiresPermissions("pds:order:tradeMatch:edit")
    @RequestMapping(value = "save")
    public String save(TradeMatch tradeMatch, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, tradeMatch)) {
            return form(tradeMatch, model);
        }
        tradeMatchService.save(tradeMatch);
        addMessage(redirectAttributes, "保存交易匹配单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeMatch/?repage";
    }

    @RequiresPermissions("pds:order:tradeMatch:edit")
    @RequestMapping(value = "delete")
    public String delete(TradeMatch tradeMatch, RedirectAttributes redirectAttributes) {
        tradeMatchService.delete(tradeMatch);
        addMessage(redirectAttributes, "删除交易匹配单成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeMatch/?repage";
    }

    @RequiresPermissions("pds:order:tradeMatch:edit")
    @RequestMapping(value = "match")
    public String match(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String deliveryDate = request.getParameter("deliveryDate");
        String type = request.getParameter("typeChoose");
        String buySeq = request.getParameter("buySeq");
        String pmId = request.getParameter("pmId");

        Date date;
        if ("1".equals(type)) {
            if (StringUtils.isAnyEmpty(buySeq, deliveryDate)) {
                redirectAttributes.addFlashAttribute("tip_message", "必要参数不能为空");
                return "redirect:" + Global.getAdminPath() + "/pds/order/tradeMatch/?repage";
            }
            //自定义时间
            date = DateUtils.parseDate(deliveryDate);
        } else {
            //默认当天
            date = new Date();
        }

        BaseResp baseResp = BaseResp.newError();

        try {
            baseResp = iPdsSummaryServiceImpl.tradeMatch(pmId, buySeq, date);
            if (baseResp.isSuccess()) {
                baseResp = iPdsSummaryServiceImpl.createTradeOrder(pmId, buySeq, date);
            }
        } catch (PdsSummaryOperationException e) {
            baseResp.setMsg(e.getMessage());
        }
        redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
        return "redirect:" + Global.getAdminPath() + "/pds/order/tradeMatch/?repage";
    }

}