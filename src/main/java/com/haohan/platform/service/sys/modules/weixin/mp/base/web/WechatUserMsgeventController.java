package com.haohan.platform.service.sys.modules.weixin.mp.base.web;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.mp.base.entity.WechatUserMsgevent;
import com.haohan.platform.service.sys.modules.weixin.mp.base.service.WechatUserMsgeventService;
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
 * 微信用户消息事件Controller
 * @author haohan
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/mp/wechatUserMsgevent")
public class WechatUserMsgeventController extends BaseController {

	@Autowired
	private WechatUserMsgeventService wechatUserMsgeventService;
	
	@ModelAttribute
	public WechatUserMsgevent get(@RequestParam(required=false) String id) {
		WechatUserMsgevent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wechatUserMsgeventService.get(id);
		}
		if (entity == null){
			entity = new WechatUserMsgevent();
		}
		return entity;
	}
	
	@RequiresPermissions("mp:wechatUserMsgevent:view")
	@RequestMapping(value = {"list", ""})
	public String list(WechatUserMsgevent wechatUserMsgevent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WechatUserMsgevent> page = wechatUserMsgeventService.findPage(new Page<WechatUserMsgevent>(request, response), wechatUserMsgevent); 
		model.addAttribute("page", page);
		return "/modules/weixin/mp/wechatUserMsgeventList";
	}

	@RequiresPermissions("mp:wechatUserMsgevent:view")
	@RequestMapping(value = "form")
	public String form(WechatUserMsgevent wechatUserMsgevent, Model model) {
		model.addAttribute("wechatUserMsgevent", wechatUserMsgevent);
		return "/modules/weixin/mp/wechatUserMsgeventForm";
	}

	@RequiresPermissions("mp:wechatUserMsgevent:edit")
	@RequestMapping(value = "save")
	public String save(WechatUserMsgevent wechatUserMsgevent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wechatUserMsgevent)){
			return form(wechatUserMsgevent, model);
		}
		wechatUserMsgeventService.save(wechatUserMsgevent);
		addMessage(redirectAttributes, "保存微信消息事件成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/mp/wechatUserMsgevent/?repage";
	}
	
	@RequiresPermissions("mp:wechatUserMsgevent:edit")
	@RequestMapping(value = "delete")
	public String delete(WechatUserMsgevent wechatUserMsgevent, RedirectAttributes redirectAttributes) {
		wechatUserMsgeventService.delete(wechatUserMsgevent);
		addMessage(redirectAttributes, "删除微信消息事件成功");
		return "redirect:"+Global.getAdminPath()+"/weixin/mp/wechatUserMsgevent/?repage";
	}

}