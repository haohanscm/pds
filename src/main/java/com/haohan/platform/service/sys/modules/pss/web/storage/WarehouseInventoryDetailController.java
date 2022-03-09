package com.haohan.platform.service.sys.modules.pss.web.storage;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseInventoryDetail;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseInventoryDetailService;
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
 * 盘点记录Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/storage/warehouseInventoryDetail")
public class WarehouseInventoryDetailController extends BaseController {

	@Autowired
	private WarehouseInventoryDetailService warehouseInventoryDetailService;
	
	@ModelAttribute
	public WarehouseInventoryDetail get(@RequestParam(required=false) String id) {
		WarehouseInventoryDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warehouseInventoryDetailService.get(id);
		}
		if (entity == null){
			entity = new WarehouseInventoryDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:storage:warehouseInventoryDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(WarehouseInventoryDetail warehouseInventoryDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WarehouseInventoryDetail> page = warehouseInventoryDetailService.findPage(new Page<WarehouseInventoryDetail>(request, response), warehouseInventoryDetail); 
		model.addAttribute("page", page);
		return "modules/pss/storage/warehouseInventoryDetailList";
	}

	@RequiresPermissions("pss:storage:warehouseInventoryDetail:view")
	@RequestMapping(value = "form")
	public String form(WarehouseInventoryDetail warehouseInventoryDetail, Model model) {
		model.addAttribute("warehouseInventoryDetail", warehouseInventoryDetail);
		return "modules/pss/storage/warehouseInventoryDetailForm";
	}

	@RequiresPermissions("pss:storage:warehouseInventoryDetail:edit")
	@RequestMapping(value = "save")
	public String save(WarehouseInventoryDetail warehouseInventoryDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, warehouseInventoryDetail)){
			return form(warehouseInventoryDetail, model);
		}
		warehouseInventoryDetailService.save(warehouseInventoryDetail);
		addMessage(redirectAttributes, "保存盘点记录成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/warehouseInventoryDetail/?repage";
	}
	
	@RequiresPermissions("pss:storage:warehouseInventoryDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(WarehouseInventoryDetail warehouseInventoryDetail, RedirectAttributes redirectAttributes) {
		warehouseInventoryDetailService.delete(warehouseInventoryDetail);
		addMessage(redirectAttributes, "删除盘点记录成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/warehouseInventoryDetail/?repage";
	}

}