package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopTemplate;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopTemplateExtInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopTemplateService;
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
 * 店铺模板管理Controller
 * @author haohan
 * @version 2017-12-25
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant/shopTemplate")
public class ShopTemplateController extends BaseController {

	@Autowired
	private ShopTemplateService shopTemplateService;
	@Autowired
	private PhotoGroupManageService photoGroupManageService;
	@Autowired
	private ShopTemplateExtInfoService shopTemplateExtInfoService;
	
	@ModelAttribute
	public ShopTemplate get(@RequestParam(required=false) String id) {
		ShopTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopTemplateService.get(id);
		}
		if (entity == null){
			entity = new ShopTemplate();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:merchant:shopTemplate:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopTemplate shopTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopTemplate> page = shopTemplateService.findPage(new Page<ShopTemplate>(request, response), shopTemplate); 
		model.addAttribute("page", page);
		return "modules/xiaodian/merchant/shopTemplateList";
	}

	@RequiresPermissions("xiaodian:merchant:shopTemplate:view")
	@RequestMapping(value = "form")
	public String form(ShopTemplate shopTemplate, Model model) {
		model.addAttribute("shopTemplate", shopTemplate);
		if(StringUtils.isNotEmpty(shopTemplate.getTemplatePic())){
			PhotoGroupManage groupManage = 	photoGroupManageService.fetchByGroupNum(shopTemplate.getTemplatePic());
			shopTemplate.setShopTemplatePhotos(groupManage);
			model.addAttribute("shopTemplatePhotos", groupManage);
		}
		return "modules/xiaodian/merchant/shopTemplateForm";
	}

	@RequiresPermissions("xiaodian:merchant:shopTemplate:edit")
	@RequestMapping(value = "save")
	public String save(ShopTemplate shopTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopTemplate)){
			return form(shopTemplate, model);
		}
		PhotoGroupManage groupManage = shopTemplate.getShopTemplatePhotos();
		groupManage.setMerchantId(shopTemplate.getId());
		groupManage.setGroupNum(shopTemplate.getTemplatePic());
		photoGroupManageService.save(groupManage);
		shopTemplateService.save(shopTemplate);
		//默认创建模板扩展参数
		shopTemplateExtInfoService.configShopTemplateExt(shopTemplate.getId(),shopTemplate.getTemplateName());
		addMessage(redirectAttributes, "保存模板成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shopTemplate/?repage";
	}
	
	@RequiresPermissions("xiaodian:merchant:shopTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopTemplate shopTemplate, RedirectAttributes redirectAttributes) {
		shopTemplateService.delete(shopTemplate);
		addMessage(redirectAttributes, "删除模板成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shopTemplate/?repage";
	}

}