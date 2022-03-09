package com.haohan.platform.service.sys.modules.pds.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.order.Evaluate;
import com.haohan.platform.service.sys.modules.pds.service.order.EvaluateService;
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
 * 评价管理Controller
 *
 * @author haohan
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/order/evaluate")
public class EvaluateController extends BaseController {

    @Autowired
    private EvaluateService evaluateService;

    @ModelAttribute
    public Evaluate get(@RequestParam(required = false) String id) {
        Evaluate entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = evaluateService.get(id);
        }
        if (entity == null) {
            entity = new Evaluate();
        }
        return entity;
    }

    @RequiresPermissions("pds:order:evaluate:view")
    @RequestMapping(value = {"list", ""})
    public String list(Evaluate evaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Evaluate> page = evaluateService.findPage(new Page<Evaluate>(request, response), evaluate);
        model.addAttribute("page", page);
        return "modules/pds/order/evaluateList";
    }

    @RequiresPermissions("pds:order:evaluate:view")
    @RequestMapping(value = "form")
    public String form(Evaluate evaluate, Model model) {
        model.addAttribute("evaluate", evaluate);
        return "modules/pds/order/evaluateForm";
    }

    @RequiresPermissions("pds:order:evaluate:edit")
    @RequestMapping(value = "save")
    public String save(Evaluate evaluate, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, evaluate)) {
            return form(evaluate, model);
        }
        evaluateService.save(evaluate);
        addMessage(redirectAttributes, "保存评价管理成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/evaluate/?repage";
    }

    @RequiresPermissions("pds:order:evaluate:edit")
    @RequestMapping(value = "delete")
    public String delete(Evaluate evaluate, RedirectAttributes redirectAttributes) {
        evaluateService.delete(evaluate);
        addMessage(redirectAttributes, "删除评价管理成功");
        return "redirect:" + Global.getAdminPath() + "/pds/order/evaluate/?repage";
    }

}