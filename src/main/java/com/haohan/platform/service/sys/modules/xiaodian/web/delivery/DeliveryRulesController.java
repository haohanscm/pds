package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryRules;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryRulesService;
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
 * 配送规则管理Controller
 * @author haohan
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/deliveryRules")
public class DeliveryRulesController extends BaseController {

	@Autowired
	private DeliveryRulesService deliveryRulesService;
	
	@ModelAttribute
	public DeliveryRules get(@RequestParam(required=false) String id) {
		DeliveryRules entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deliveryRulesService.get(id);
		}
		if (entity == null){
			entity = new DeliveryRules();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:deliveryRules:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeliveryRules deliveryRules, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeliveryRules> page = deliveryRulesService.findPage(new Page<DeliveryRules>(request, response), deliveryRules); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/deliveryRulesList";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryRules:view")
	@RequestMapping(value = "form")
	public String form(DeliveryRules deliveryRules, Model model) {
		model.addAttribute("deliveryRules", deliveryRules);
		return "modules/xiaodian/delivery/deliveryRulesForm";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryRules:edit")
	@RequestMapping(value = "save")
	public String save(DeliveryRules deliveryRules, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deliveryRules)){
			return form(deliveryRules, model);
		}
		deliveryRulesService.save(deliveryRules);
		addMessage(redirectAttributes, "保存配送规则成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryRules/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:deliveryRules:edit")
	@RequestMapping(value = "delete")
	public String delete(DeliveryRules deliveryRules, RedirectAttributes redirectAttributes) {
		deliveryRulesService.delete(deliveryRules);
		addMessage(redirectAttributes, "删除配送规则成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryRules/?repage";
	}

}