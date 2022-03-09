package com.haohan.platform.service.sys.modules.pds.web.cost;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.cost.CostControl;
import com.haohan.platform.service.sys.modules.pds.service.cost.CostControlService;
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
 * 成本管控Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/cost/costControl")
public class CostControlController extends BaseController {

    @Autowired
    private CostControlService costControlService;

    @ModelAttribute
    public CostControl get(@RequestParam(required = false) String id) {
        CostControl entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = costControlService.get(id);
        }
        if (entity == null) {
            entity = new CostControl();
        }
        return entity;
    }

    @RequiresPermissions("pds:cost:costControl:view")
    @RequestMapping(value = {"list", ""})
    public String list(CostControl costControl, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<CostControl> page = costControlService.findPage(new Page<CostControl>(request, response), costControl);
        model.addAttribute("page", page);
        return "modules/pds/cost/costControlList";
    }

    @RequiresPermissions("pds:cost:costControl:view")
    @RequestMapping(value = "form")
    public String form(CostControl costControl, Model model) {
        model.addAttribute("costControl", costControl);
        return "modules/pds/cost/costControlForm";
    }

    @RequiresPermissions("pds:cost:costControl:edit")
    @RequestMapping(value = "save")
    public String save(CostControl costControl, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, costControl)) {
            return form(costControl, model);
        }
        costControlService.save(costControl);
        addMessage(redirectAttributes, "保存成本管控成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/costControl/?repage";
    }

    @RequiresPermissions("pds:cost:costControl:edit")
    @RequestMapping(value = "delete")
    public String delete(CostControl costControl, RedirectAttributes redirectAttributes) {
        costControlService.delete(costControl);
        addMessage(redirectAttributes, "删除成本管控成功");
        return "redirect:" + Global.getAdminPath() + "/pds/cost/costControl/?repage";
    }

}