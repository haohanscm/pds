package com.haohan.platform.service.sys.modules.pds.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.RouteManage;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.delivery.RouteManageService;
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
 * 路线管理Controller
 *
 * @author haohan
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/delivery/routeManage")
public class RouteManageController extends BaseController {

    @Autowired
    private RouteManageService routeManageService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    @ModelAttribute
    public RouteManage get(@RequestParam(required = false) String id) {
        RouteManage entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = routeManageService.get(id);
        }
        if (entity == null) {
            entity = new RouteManage();
        }
        return entity;
    }

    @RequiresPermissions("pds:delivery:routeManage:view")
    @RequestMapping(value = {"list", ""})
    public String list(RouteManage routeManage, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RouteManage> page = new Page<RouteManage>(request, response);
        routeManage.setPage(page);
        page.setList(routeManageService.findJoinList(routeManage));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/delivery/routeManageList";
    }

    @RequiresPermissions("pds:delivery:routeManage:view")
    @RequestMapping(value = "form")
    public String form(RouteManage routeManage, Model model) {
        model.addAttribute("routeManage", routeManage);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/delivery/routeManageForm";
    }

    @RequiresPermissions("pds:delivery:routeManage:edit")
    @RequestMapping(value = "save")
    public String save(RouteManage routeManage, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, routeManage)) {
            return form(routeManage, model);
        }
        routeManageService.save(routeManage);
        addMessage(redirectAttributes, "保存路线管理成功");
        return "redirect:" + Global.getAdminPath() + "/pds/delivery/routeManage/?repage";
    }

    @RequiresPermissions("pds:delivery:routeManage:edit")
    @RequestMapping(value = "delete")
    public String delete(RouteManage routeManage, RedirectAttributes redirectAttributes) {
        routeManageService.delete(routeManage);
        addMessage(redirectAttributes, "删除路线管理成功");
        return "redirect:" + Global.getAdminPath() + "/pds/delivery/routeManage/?repage";
    }

}