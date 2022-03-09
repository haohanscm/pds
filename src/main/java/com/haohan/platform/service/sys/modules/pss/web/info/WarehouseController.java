package com.haohan.platform.service.sys.modules.pss.web.info;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import com.haohan.platform.service.sys.modules.pss.service.info.WarehouseService;
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
 * 仓库管理Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/info/warehouse")
public class WarehouseController extends BaseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@ModelAttribute
	public PssWarehouse get(@RequestParam(required=false) String id) {
		PssWarehouse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warehouseService.get(id);
		}
		if (entity == null){
			entity = new PssWarehouse();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:info:warehouse:view")
	@RequestMapping(value = {"list", ""})
	public String list(PssWarehouse warehouse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PssWarehouse> page = warehouseService.findPage(new Page<PssWarehouse>(request, response), warehouse);
		model.addAttribute("page", page);
		return "modules/pss/info/warehouseList";
	}

	@RequiresPermissions("pss:info:warehouse:view")
	@RequestMapping(value = "form")
	public String form(PssWarehouse warehouse, Model model) {
		model.addAttribute("warehouse", warehouse);
		return "modules/pss/info/warehouseForm";
	}

	@RequiresPermissions("pss:info:warehouse:edit")
	@RequestMapping(value = "save")
	public String save(PssWarehouse warehouse, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, warehouse)){
			return form(warehouse, model);
		}
		warehouseService.save(warehouse);
		addMessage(redirectAttributes, "保存仓库管理成功");
		return "redirect:"+Global.getAdminPath()+"/pss/info/warehouse/?repage";
	}
	
	@RequiresPermissions("pss:info:warehouse:edit")
	@RequestMapping(value = "delete")
	public String delete(PssWarehouse warehouse, RedirectAttributes redirectAttributes) {
		warehouseService.delete(warehouse);
		addMessage(redirectAttributes, "删除仓库管理成功");
		return "redirect:"+Global.getAdminPath()+"/pss/info/warehouse/?repage";
	}

}