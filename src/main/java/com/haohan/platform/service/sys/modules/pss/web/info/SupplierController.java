package com.haohan.platform.service.sys.modules.pss.web.info;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;
import com.haohan.platform.service.sys.modules.pss.service.info.SupplierService;
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
 * 供应商管理Controller
 * @author haohan
 * @version 2018-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/info/supplier")
public class SupplierController extends BaseController {

	@Autowired
	private SupplierService supplierService;
	@Resource
	private MerchantService merchantService;
	
	@ModelAttribute
	public Supplier get(@RequestParam(required=false) String id) {
		Supplier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = supplierService.get(id);
		}
		if (entity == null){
			entity = new Supplier();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:info:supplier:view")
	@RequestMapping(value = {"list", ""})
	public String list(Supplier supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Supplier> page = supplierService.findPage(new Page<Supplier>(request, response), supplier);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("page", page);
		return "modules/pss/info/supplierList";
	}

	@RequiresPermissions("pss:info:supplier:view")
	@RequestMapping(value = "form")
	public String form(Supplier supplier, Model model) {
		model.addAttribute("supplier", supplier);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		return "modules/pss/info/supplierForm";
	}

	@RequiresPermissions("pss:info:supplier:edit")
	@RequestMapping(value = "save")
	public String save(Supplier supplier, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supplier)){
			return form(supplier, model);
		}
		supplierService.save(supplier);
		addMessage(redirectAttributes, "保存供应商成功");
		return "redirect:"+Global.getAdminPath()+"/pss/info/supplier/?repage";
	}
	
	@RequiresPermissions("pss:info:supplier:edit")
	@RequestMapping(value = "delete")
	public String delete(Supplier supplier, RedirectAttributes redirectAttributes) {
		supplierService.delete(supplier);
		addMessage(redirectAttributes, "删除供应商成功");
		return "redirect:"+Global.getAdminPath()+"/pss/info/supplier/?repage";
	}

}