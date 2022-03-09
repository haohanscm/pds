package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ServiceSelection;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.ServiceSelectionService;
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
 * 服务选项管理Controller
 * @author haohan
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/serviceSelection")
public class ServiceSelectionController extends BaseController {

	@Autowired
	private ServiceSelectionService serviceSelectionService;
	
	@ModelAttribute
	public ServiceSelection get(@RequestParam(required=false) String id) {
		ServiceSelection entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serviceSelectionService.get(id);
		}
		if (entity == null){
			entity = new ServiceSelection();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:serviceSelection:view")
	@RequestMapping(value = {"list", ""})
	public String list(ServiceSelection serviceSelection, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ServiceSelection> page = serviceSelectionService.findPage(new Page<ServiceSelection>(request, response), serviceSelection); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/serviceSelectionList";
	}

	@RequiresPermissions("xiaodian:delivery:serviceSelection:view")
	@RequestMapping(value = "form")
	public String form(ServiceSelection serviceSelection, Model model) {
		model.addAttribute("serviceSelection", serviceSelection);
		return "modules/xiaodian/delivery/serviceSelectionForm";
	}

	@RequiresPermissions("xiaodian:delivery:serviceSelection:edit")
	@RequestMapping(value = "save")
	public String save(ServiceSelection serviceSelection, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, serviceSelection)){
			return form(serviceSelection, model);
		}
		serviceSelectionService.save(serviceSelection);
		addMessage(redirectAttributes, "保存服务选项成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/serviceSelection/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:serviceSelection:edit")
	@RequestMapping(value = "delete")
	public String delete(ServiceSelection serviceSelection, RedirectAttributes redirectAttributes) {
		serviceSelectionService.delete(serviceSelection);
		addMessage(redirectAttributes, "删除服务选项成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/serviceSelection/?repage";
	}

}