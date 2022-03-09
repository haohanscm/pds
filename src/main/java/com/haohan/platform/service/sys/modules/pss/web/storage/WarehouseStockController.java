package com.haohan.platform.service.sys.modules.pss.web.storage;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseStockService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 库存管理Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/storage/warehouseStock")
public class WarehouseStockController extends BaseController {

	@Autowired
	private WarehouseStockService warehouseStockService;
	@Resource
	private MerchantService merchantService;
	
	@ModelAttribute
	public WarehouseStock get(@RequestParam(required=false) String id) {
		WarehouseStock entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = warehouseStockService.get(id);
		}
		if (entity == null){
			entity = new WarehouseStock();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:storage:warehouseStock:view")
	@RequestMapping(value = {"list", ""})
	public String list(WarehouseStock warehouseStock, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WarehouseStock> page = warehouseStockService.findPage(new Page<WarehouseStock>(request, response), warehouseStock);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("page", page);
		return "modules/pss/storage/warehouseStockList";
	}

	@RequiresPermissions("pss:storage:warehouseStock:view")
	@RequestMapping(value = "form")
	public String form(WarehouseStock warehouseStock, Model model) {
		model.addAttribute("warehouseStock", warehouseStock);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		return "modules/pss/storage/warehouseStockForm";
	}

	@RequiresPermissions("pss:storage:warehouseStock:edit")
	@RequestMapping(value = "save")
	public String save(WarehouseStock warehouseStock, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, warehouseStock)){
			return form(warehouseStock, model);
		}
		warehouseStockService.save(warehouseStock);
		addMessage(redirectAttributes, "保存库存管理成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/warehouseStock/?repage";
	}
	
	@RequiresPermissions("pss:storage:warehouseStock:edit")
	@RequestMapping(value = "delete")
	public String delete(WarehouseStock warehouseStock, RedirectAttributes redirectAttributes) {
		warehouseStockService.delete(warehouseStock);
		addMessage(redirectAttributes, "删除库存管理成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/warehouseStock/?repage";
	}

}