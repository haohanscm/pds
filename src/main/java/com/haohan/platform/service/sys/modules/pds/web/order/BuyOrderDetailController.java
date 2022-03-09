package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 采购单详情Controller
 *
 * @author haohan
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/buyOrderDetail")
public class BuyOrderDetailController extends BaseController {

    @Autowired
    private BuyOrderDetailService buyOrderDetailService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public BuyOrderDetail get(@RequestParam(required = false) String id) {
        BuyOrderDetail entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = buyOrderDetailService.get(id);
        }
        if (entity == null) {
            entity = new BuyOrderDetail();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:buyOrderDetail:view")
    @RequestMapping(value = {"list", ""})
    public String list(BuyOrderDetail buyOrderDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BuyOrderDetail> page = new Page<BuyOrderDetail>(request, response);
        buyOrderDetail.setPage(page);
        page.setList(buyOrderDetailService.findJoinList(buyOrderDetail));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(new PdsBuyer());
        model.addAttribute("buyerList", buyerList);
        return "modules/pds/order/buyOrderDetailList";
    }

    @RequiresPermissions("pds:order:buyOrderDetail:view")
    @RequestMapping(value = "form")
    public String form(BuyOrderDetail buyOrderDetail, Model model) {
        model.addAttribute("buyOrderDetail", buyOrderDetail);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/order/buyOrderDetailForm";
    }

    @RequiresPermissions("pds:order:buyOrderDetail:edit")
    @RequestMapping(value = "save")
    public String save(BuyOrderDetail buyOrderDetail, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, buyOrderDetail)) {
            return form(buyOrderDetail, model);
        }
        buyOrderDetailService.save(buyOrderDetail);
        addMessage(redirectAttributes, "保存采购单详情成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrderDetail/?repage";
    }

    @RequiresPermissions("pds:order:buyOrderDetail:edit")
    @RequestMapping(value = "delete")
    public String delete(BuyOrderDetail buyOrderDetail, RedirectAttributes redirectAttributes) {
        buyOrderDetailService.delete(buyOrderDetail);
        addMessage(redirectAttributes, "删除采购单详情成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/buyOrderDetail/?repage";
    }

}