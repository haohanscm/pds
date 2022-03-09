package com.haohan.platform.service.sys.modules.pss.web.info;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.info.SupplierPayrecord;
import com.haohan.platform.service.sys.modules.pss.service.info.SupplierPayrecordService;
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
 * 供应商结款记录Controller
 * @author yu
 * @version 2018-10-11
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/info/supplierPayrecord")
public class SupplierPayrecordController extends BaseController {

	@Autowired
	private SupplierPayrecordService supplierPayrecordService;
	
	@ModelAttribute
	public SupplierPayrecord get(@RequestParam(required=false) String id) {
		SupplierPayrecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = supplierPayrecordService.get(id);
		}
		if (entity == null){
			entity = new SupplierPayrecord();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:info:supplierPayrecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SupplierPayrecord supplierPayrecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SupplierPayrecord> page = supplierPayrecordService.findPage(new Page<SupplierPayrecord>(request, response), supplierPayrecord); 
		model.addAttribute("page", page);
		return "modules/pss/info/supplierPayrecordList";
	}

	@RequiresPermissions("pss:info:supplierPayrecord:view")
	@RequestMapping(value = "form")
	public String form(SupplierPayrecord supplierPayrecord, Model model) {
		model.addAttribute("supplierPayrecord", supplierPayrecord);
		return "modules/pss/info/supplierPayrecordForm";
	}

	@RequiresPermissions("pss:info:supplierPayrecord:edit")
	@RequestMapping(value = "save")
	public String save(SupplierPayrecord supplierPayrecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supplierPayrecord)){
			return form(supplierPayrecord, model);
		}
		supplierPayrecordService.save(supplierPayrecord);
		addMessage(redirectAttributes, "保存供应商结款记录成功");
		return "redirect:"+Global.getAdminPath()+"/pss/info/supplierPayrecord/?repage";
	}
	
	@RequiresPermissions("pss:info:supplierPayrecord:edit")
	@RequestMapping(value = "delete")
	public String delete(SupplierPayrecord supplierPayrecord, RedirectAttributes redirectAttributes) {
		supplierPayrecordService.delete(supplierPayrecord);
		addMessage(redirectAttributes, "删除供应商结款记录成功");
		return "redirect:"+Global.getAdminPath()+"/pss/info/supplierPayrecord/?repage";
	}

}