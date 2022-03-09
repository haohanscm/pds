package com.haohan.platform.service.sys.modules.xiaodian.web.retail;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsPriceRule;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsPriceRuleService;
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
 * 定价规则管理Controller
 * @author haohan
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/retail/goodsPriceRule")
public class GoodsPriceRuleController extends BaseController {

	@Autowired
	private GoodsPriceRuleService goodsPriceRuleService;

	@ModelAttribute
	public GoodsPriceRule get(@RequestParam(required=false) String id) {
		GoodsPriceRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsPriceRuleService.get(id);
		}
		if (entity == null){
			entity = new GoodsPriceRule();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:retail:goodsPriceRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsPriceRule goodsPriceRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsPriceRule> page = goodsPriceRuleService.findPage(new Page<GoodsPriceRule>(request, response), goodsPriceRule);
		model.addAttribute("page", page);
		return "modules/xiaodian/retail/goodsPriceRuleList";
	}

	@RequiresPermissions("xiaodian:retail:goodsPriceRule:view")
	@RequestMapping(value = "form")
	public String form(GoodsPriceRule goodsPriceRule, Model model) {
		model.addAttribute("goodsPriceRule", goodsPriceRule);
		return "modules/xiaodian/retail/goodsPriceRuleForm";
	}

	@RequiresPermissions("xiaodian:retail:goodsPriceRule:edit")
	@RequestMapping(value = "save")
	public String save(GoodsPriceRule goodsPriceRule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsPriceRule)){
			return form(goodsPriceRule, model);
		}
		goodsPriceRuleService.save(goodsPriceRule);
		addMessage(redirectAttributes, "保存定价规则成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsPriceRule/?shopId="+goodsPriceRule.getShopId();
	}
	
	@RequiresPermissions("xiaodian:retail:goodsPriceRule:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsPriceRule goodsPriceRule, RedirectAttributes redirectAttributes) {
		goodsPriceRuleService.delete(goodsPriceRule);
		addMessage(redirectAttributes, "删除定价规则成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsPriceRule/?shopId="+goodsPriceRule.getShopId();
	}

	@RequiresPermissions("xiaodian:retail:goodsPriceRule:edit")
	@RequestMapping(value = "copy")
	public String copy(GoodsPriceRule goodsPriceRule, Model model) {
	    goodsPriceRule.setId("");
		model.addAttribute("goodsPriceRule", goodsPriceRule);
		return "modules/xiaodian/retail/goodsPriceRuleForm";
	}

}