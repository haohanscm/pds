package com.haohan.platform.service.sys.modules.pds.web.business;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.GoodsCollections;
import com.haohan.platform.service.sys.modules.pds.service.business.GoodsCollectionsService;
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
 * 商品收藏Controller
 *
 * @author yu
 * @version 2018-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/business/goodsCollections")
public class GoodsCollectionsController extends BaseController {

    @Autowired
    private GoodsCollectionsService goodsCollectionsService;

    @ModelAttribute
    public GoodsCollections get(@RequestParam(required = false) String id) {
        GoodsCollections entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = goodsCollectionsService.get(id);
        }
        if (entity == null) {
            entity = new GoodsCollections();
        }
        return entity;
    }

    @RequiresPermissions("pds:business:goodsCollections:view")
    @RequestMapping(value = {"list", ""})
    public String list(GoodsCollections goodsCollections, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<GoodsCollections> page = goodsCollectionsService.findPage(new Page<GoodsCollections>(request, response), goodsCollections);
        model.addAttribute("page", page);
        return "modules/pds/business/goodsCollectionsList";
    }

    @RequiresPermissions("pds:business:goodsCollections:view")
    @RequestMapping(value = "form")
    public String form(GoodsCollections goodsCollections, Model model) {
        model.addAttribute("goodsCollections", goodsCollections);
        return "modules/pds/business/goodsCollectionsForm";
    }

    @RequiresPermissions("pds:business:goodsCollections:edit")
    @RequestMapping(value = "save")
    public String save(GoodsCollections goodsCollections, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, goodsCollections)) {
            return form(goodsCollections, model);
        }
        goodsCollectionsService.save(goodsCollections);
        addMessage(redirectAttributes, "保存商品收藏成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/goodsCollections/?repage";
    }

    @RequiresPermissions("pds:business:goodsCollections:edit")
    @RequestMapping(value = "delete")
    public String delete(GoodsCollections goodsCollections, RedirectAttributes redirectAttributes) {
        goodsCollectionsService.delete(goodsCollections);
        addMessage(redirectAttributes, "删除商品收藏成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/goodsCollections/?repage";
    }

}