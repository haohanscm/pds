package com.haohan.platform.service.sys.modules.pds.web.business;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 采购商管理Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/business/pdsBuyer")
public class PdsBuyerController extends BaseController {

    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    @Lazy(true)
    private MerchantService merchantService;

    @ModelAttribute
    public PdsBuyer get(@RequestParam(required = false) String id) {
        PdsBuyer entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = pdsBuyerService.get(id);
        }
        if (entity == null) {
            entity = new PdsBuyer();
        }
        return entity;
    }

    @RequiresPermissions("pds:business:pdsBuyer:view")
    @RequestMapping(value = {"list", ""})
    public String list(PdsBuyer pdsBuyer, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PdsBuyer> page = new Page<PdsBuyer>(request, response);
        pdsBuyer.setPage(page);
        page.setList(pdsBuyerService.findJoinList(pdsBuyer));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        List<PdsBuyer> merchantList = pdsBuyerService.findMerchantList();
        model.addAttribute("merchantList", merchantList);
        return "modules/pds/business/pdsBuyerList";
    }

    @RequiresPermissions("pds:business:pdsBuyer:view")
    @RequestMapping(value = "form")
    public String form(PdsBuyer pdsBuyer, Model model) {
        model.addAttribute("pdsBuyer", pdsBuyer);
        // 查询已启用的商家
        List<Merchant> list = merchantService.findListEnabled(new Merchant());
        List<Merchant> pmList = new ArrayList<>();
        for (Merchant merchant : list) {
            if (StringUtils.equals(merchant.getPdsType(), IMerchantConstant.PdsType.platform.getType())) {
                pmList.add(merchant);
            }
        }
        model.addAttribute("merchantList", list);
        model.addAttribute("pmList", pmList);
        return "modules/pds/business/pdsBuyerForm";
    }

    @RequiresPermissions("pds:business:pdsBuyer:edit")
    @RequestMapping(value = "save")
    public String save(PdsBuyer pdsBuyer, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, pdsBuyer)) {
            return form(pdsBuyer, model);
        }
        pdsBuyerService.save(pdsBuyer);
        addMessage(redirectAttributes, "保存采购商成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/pdsBuyer/?repage";
    }

    @RequiresPermissions("pds:business:pdsBuyer:edit")
    @RequestMapping(value = "delete")
    public String delete(PdsBuyer pdsBuyer, RedirectAttributes redirectAttributes) {
        pdsBuyerService.delete(pdsBuyer);
        addMessage(redirectAttributes, "删除采购商成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/pdsBuyer/?repage";
    }

}