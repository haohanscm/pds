package com.haohan.platform.service.sys.modules.xiaodian.web.message;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.message.WechatMessageTemplate;
import com.haohan.platform.service.sys.modules.xiaodian.service.message.WechatMessageTemplateService;
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
 * 消息模板管理Controller
 * @author haohan
 * @version 2018-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/message/wechatMessageTemplate")
public class WechatMessageTemplateController extends BaseController {

	@Autowired
	private WechatMessageTemplateService wechatMessageTemplateService;
	
	@ModelAttribute
	public WechatMessageTemplate get(@RequestParam(required=false) String id) {
		WechatMessageTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wechatMessageTemplateService.get(id);
		}
		if (entity == null){
			entity = new WechatMessageTemplate();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:message:wechatMessageTemplate:view")
	@RequestMapping(value = {"list", ""})
	public String list(WechatMessageTemplate wechatMessageTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WechatMessageTemplate> page = wechatMessageTemplateService.findPage(new Page<WechatMessageTemplate>(request, response), wechatMessageTemplate); 
		model.addAttribute("page", page);
		return "modules/xiaodian/message/wechatMessageTemplateList";
	}

	@RequiresPermissions("xiaodian:message:wechatMessageTemplate:view")
	@RequestMapping(value = "form")
	public String form(WechatMessageTemplate wechatMessageTemplate, Model model) {
		model.addAttribute("wechatMessageTemplate", wechatMessageTemplate);
		return "modules/xiaodian/message/wechatMessageTemplateForm";
	}

	@RequiresPermissions("xiaodian:message:wechatMessageTemplate:edit")
	@RequestMapping(value = "save")
	public String save(WechatMessageTemplate wechatMessageTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wechatMessageTemplate)){
			return form(wechatMessageTemplate, model);
		}
		wechatMessageTemplateService.save(wechatMessageTemplate);
		addMessage(redirectAttributes, "保存消息模板管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/message/wechatMessageTemplate/?repage";
	}
	
	@RequiresPermissions("xiaodian:message:wechatMessageTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(WechatMessageTemplate wechatMessageTemplate, RedirectAttributes redirectAttributes) {
		wechatMessageTemplateService.delete(wechatMessageTemplate);
		addMessage(redirectAttributes, "删除消息模板管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/message/wechatMessageTemplate/?repage";
	}

}