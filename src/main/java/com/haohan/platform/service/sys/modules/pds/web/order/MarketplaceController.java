package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.order.Marketplace;
import com.haohan.platform.service.sys.modules.pds.service.order.MarketplaceService;
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

/**
 * 市场报价Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/marketplace")
public class MarketplaceController extends BaseController {

    @Autowired
    private MarketplaceService marketplaceService;

    @ModelAttribute
    public Marketplace get(@RequestParam(required = false) String id) {
        Marketplace entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = marketplaceService.get(id);
        }
        if (entity == null) {
            entity = new Marketplace();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:marketplace:view")
    @RequestMapping(value = {"list", ""})
    public String list(Marketplace marketplace, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Marketplace> page = marketplaceService.findPage(new Page<Marketplace>(request, response), marketplace);
        model.addAttribute("page", page);
        return "modules/pds/order/marketplaceList";
    }

    @RequiresPermissions("pds:order:marketplace:view")
    @RequestMapping(value = "form")
    public String form(Marketplace marketplace, Model model) {
        model.addAttribute("marketplace", marketplace);
        return "modules/pds/order/marketplaceForm";
    }

    @RequiresPermissions("pds:order:marketplace:edit")
    @RequestMapping(value = "save")
    public String save(Marketplace marketplace, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, marketplace)) {
            return form(marketplace, model);
        }
        marketplaceService.save(marketplace);
        addMessage(redirectAttributes, "保存市场报价成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/marketplace/?repage";
    }

    @RequiresPermissions("pds:order:marketplace:edit")
    @RequestMapping(value = "delete")
    public String delete(Marketplace marketplace, RedirectAttributes redirectAttributes) {
        marketplaceService.delete(marketplace);
        addMessage(redirectAttributes, "删除市场报价成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/marketplace/?repage";
    }

}