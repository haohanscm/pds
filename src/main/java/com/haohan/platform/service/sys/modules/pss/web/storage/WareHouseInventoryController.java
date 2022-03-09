package com.haohan.platform.service.sys.modules.pss.web.storage;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WareHouseInventory;
import com.haohan.platform.service.sys.modules.pss.service.storage.WareHouseInventoryService;
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
 * 库存盘点Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/storage/wareHouseInventory")
public class WareHouseInventoryController extends BaseController {

	@Autowired
	private WareHouseInventoryService wareHouseInventoryService;
	
	@ModelAttribute
	public WareHouseInventory get(@RequestParam(required=false) String id) {
		WareHouseInventory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wareHouseInventoryService.get(id);
		}
		if (entity == null){
			entity = new WareHouseInventory();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:storage:wareHouseInventory:view")
	@RequestMapping(value = {"list", ""})
	public String list(WareHouseInventory wareHouseInventory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WareHouseInventory> page = wareHouseInventoryService.findPage(new Page<WareHouseInventory>(request, response), wareHouseInventory); 
		model.addAttribute("page", page);
		return "modules/pss/storage/wareHouseInventoryList";
	}

	@RequiresPermissions("pss:storage:wareHouseInventory:view")
	@RequestMapping(value = "form")
	public String form(WareHouseInventory wareHouseInventory, Model model) {
		model.addAttribute("wareHouseInventory", wareHouseInventory);
		return "modules/pss/storage/wareHouseInventoryForm";
	}

	@RequiresPermissions("pss:storage:wareHouseInventory:edit")
	@RequestMapping(value = "save")
	public String save(WareHouseInventory wareHouseInventory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wareHouseInventory)){
			return form(wareHouseInventory, model);
		}
		wareHouseInventoryService.save(wareHouseInventory);
		addMessage(redirectAttributes, "保存库存盘点成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/wareHouseInventory/?repage";
	}
	
	@RequiresPermissions("pss:storage:wareHouseInventory:edit")
	@RequestMapping(value = "delete")
	public String delete(WareHouseInventory wareHouseInventory, RedirectAttributes redirectAttributes) {
		wareHouseInventoryService.delete(wareHouseInventory);
		addMessage(redirectAttributes, "删除库存盘点成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/wareHouseInventory/?repage";
	}

}