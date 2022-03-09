package com.haohan.platform.service.sys.modules.pds.web.cost;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.cost.BuyerPaymentService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 采购商货款统计Controller
 *
 * @author haohan
 * @version 2018-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/cost/buyerPayment")
public class BuyerPaymentController extends BaseController {

    @Autowired
    private BuyerPaymentService buyerPaymentService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService pdsBuyerService;


    @ModelAttribute
    public BuyerPayment get(@RequestParam(required = false) String id) {
        BuyerPayment entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = buyerPaymentService.get(id);
        }
        if (entity == null) {
            entity = new BuyerPayment();
        }
        return entity;
    }

    @RequiresPermissions("pds:cost:buyerPayment:view")
    @RequestMapping(value = {"list", ""})
    public String list(BuyerPayment buyerPayment, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BuyerPayment> page = new Page<BuyerPayment>(request, response);
        buyerPayment.setPage(page);
        page.setList(buyerPaymentService.findJoinList(buyerPayment));
        model.addAttribute("page", page);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(new PdsBuyer());
        model.addAttribute("buyerList", buyerList);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/buyerPaymentList";
    }

    @RequiresPermissions("pds:cost:buyerPayment:view")
    @RequestMapping(value = "form")
    public String form(BuyerPayment buyerPayment, Model model) {
        model.addAttribute("buyerPayment", buyerPayment);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(new PdsBuyer());
        model.addAttribute("buyerList", buyerList);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/cost/buyerPaymentForm";
    }

    @RequiresPermissions("pds:cost:buyerPayment:edit")
    @RequestMapping(value = "save")
    public String save(BuyerPayment buyerPayment, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, buyerPayment)) {
            return form(buyerPayment, model);
        }
        buyerPaymentService.save(buyerPayment);
        addMessage(redirectAttributes, "保存采购商货款成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/buyerPayment/?repage";
    }

    @RequiresPermissions("pds:cost:buyerPayment:edit")
    @RequestMapping(value = "delete")
    public String delete(BuyerPayment buyerPayment, RedirectAttributes redirectAttributes) {
        buyerPaymentService.delete(buyerPayment);
        addMessage(redirectAttributes, "删除采购商货款成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/buyerPayment/?repage";
    }

}