package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.SaleRules;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.SaleRulesService;
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
 * 售卖规则管理Controller
 * @author haohan
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/saleRules")
public class SaleRulesController extends BaseController {

	@Autowired
	private SaleRulesService saleRulesService;
	
	@ModelAttribute
	public SaleRules get(@RequestParam(required=false) String id) {
		SaleRules entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = saleRulesService.get(id);
		}
		if (entity == null){
			entity = new SaleRules();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:saleRules:view")
	@RequestMapping(value = {"list", ""})
	public String list(SaleRules saleRules, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SaleRules> page = saleRulesService.findPage(new Page<SaleRules>(request, response), saleRules); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/saleRulesList";
	}

	@RequiresPermissions("xiaodian:delivery:saleRules:view")
	@RequestMapping(value = "form")
	public String form(SaleRules saleRules, Model model) {
		model.addAttribute("saleRules", saleRules);
		return "modules/xiaodian/delivery/saleRulesForm";
	}

	@RequiresPermissions("xiaodian:delivery:saleRules:edit")
	@RequestMapping(value = "save")
	public String save(SaleRules saleRules, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, saleRules)){
			return form(saleRules, model);
		}
		saleRulesService.save(saleRules);
		addMessage(redirectAttributes, "保存售卖规则成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/saleRules/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:saleRules:edit")
	@RequestMapping(value = "delete")
	public String delete(SaleRules saleRules, RedirectAttributes redirectAttributes) {
		saleRulesService.delete(saleRules);
		addMessage(redirectAttributes, "删除售卖规则成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/saleRules/?repage";
	}

}