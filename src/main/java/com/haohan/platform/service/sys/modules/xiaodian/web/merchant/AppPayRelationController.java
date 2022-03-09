package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AppPayRelation;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.AppPayRelationService;

import java.util.List;

/**
 * app支付账户Controller
 * @author yu.shen
 * @version 2019-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant/appPayRelation")
public class AppPayRelationController extends BaseController {

	@Autowired
	private AppPayRelationService appPayRelationService;
	
	@ModelAttribute
	public AppPayRelation get(@RequestParam(required=false) String id) {
		AppPayRelation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appPayRelationService.get(id);
		}
		if (entity == null){
			entity = new AppPayRelation();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:merchant:appPayRelation:view")
	@RequestMapping(value = {"list", ""})
	public String list(AppPayRelation appPayRelation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppPayRelation> page = new Page<AppPayRelation>(request, response);
		appPayRelation.setPage(page);
        page.setList(appPayRelationService.findJoinList(appPayRelation));
		model.addAttribute("page", page);
		return "modules/xiaodian/merchant/appPayRelationList";
	}

	@RequiresPermissions("xiaodian:merchant:appPayRelation:view")
	@RequestMapping(value = "form")
	public String form(AppPayRelation appPayRelation, Model model) {
		model.addAttribute("appPayRelation", appPayRelation);
		return "modules/xiaodian/merchant/appPayRelationForm";
	}

	@RequiresPermissions("xiaodian:merchant:appPayRelation:edit")
	@RequestMapping(value = "save")
	public String save(AppPayRelation appPayRelation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, appPayRelation)){
			return form(appPayRelation, model);
		}
		appPayRelationService.save(appPayRelation);
		addMessage(redirectAttributes, "保存app支付账户成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/appPayRelation/?repage";
	}
	
	@RequiresPermissions("xiaodian:merchant:appPayRelation:edit")
	@RequestMapping(value = "delete")
	public String delete(AppPayRelation appPayRelation, RedirectAttributes redirectAttributes) {
		appPayRelationService.delete(appPayRelation);
		addMessage(redirectAttributes, "删除app支付账户成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/appPayRelation/?repage";
	}

}