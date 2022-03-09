package com.haohan.platform.service.sys.modules.pds.web.business;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.entity.business.BusinessProcess;
import com.haohan.platform.service.sys.modules.pds.service.business.BusinessProcessService;
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
 * 业务流程Controller
 *
 * @author haohan
 * @version 2018-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/business/businessProcess")
public class BusinessProcessController extends BaseController {

    @Autowired
    private BusinessProcessService businessProcessService;

    @ModelAttribute
    public BusinessProcess get(@RequestParam(required = false) String id) {
        BusinessProcess entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = businessProcessService.get(id);
        }
        if (entity == null) {
            entity = new BusinessProcess();
        }
        return entity;
    }

    @RequiresPermissions("pds:business:businessProcess:view")
    @RequestMapping(value = {"list", ""})
    public String list(BusinessProcess businessProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<BusinessProcess> page = businessProcessService.findPage(new Page<BusinessProcess>(request, response), businessProcess);
        model.addAttribute("page", page);
        return "modules/pds/business/businessProcessList";
    }

    @RequiresPermissions("pds:business:businessProcess:view")
    @RequestMapping(value = "form")
    public String form(BusinessProcess businessProcess, Model model) {
        model.addAttribute("businessProcess", businessProcess);
        return "modules/pds/business/businessProcessForm";
    }

    @RequiresPermissions("pds:business:businessProcess:edit")
    @RequestMapping(value = "save")
    public String save(BusinessProcess businessProcess, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, businessProcess)) {
            return form(businessProcess, model);
        }
        businessProcessService.save(businessProcess);
        addMessage(redirectAttributes, "保存业务流程成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/businessProcess/?repage";
    }

    @RequiresPermissions("pds:business:businessProcess:edit")
    @RequestMapping(value = "delete")
    public String delete(BusinessProcess businessProcess, RedirectAttributes redirectAttributes) {
        businessProcessService.delete(businessProcess);
        addMessage(redirectAttributes, "删除业务流程成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/businessProcess/?repage";
    }

}