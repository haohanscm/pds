package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.constant.IYiCloudConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.inf.IYiCloudCommonService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.OpenPlatformConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 开放平台管理Controller
 * @author haohan
 * @version 2017-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/openPlatformConfig")
public class OpenPlatformConfigController extends BaseController {

	@Autowired
	private OpenPlatformConfigService openPlatformConfigService;
    @Resource
	private IYiCloudCommonService yiCloudCommonService;
	
	@ModelAttribute
	public OpenPlatformConfig get(@RequestParam(required=false) String id) {
		OpenPlatformConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = openPlatformConfigService.get(id);
		}
		if (entity == null){
			entity = new OpenPlatformConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:openPlatformConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpenPlatformConfig openPlatformConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpenPlatformConfig> page = openPlatformConfigService.findPage(new Page<OpenPlatformConfig>(request, response), openPlatformConfig); 
		model.addAttribute("page", page);
		return "modules/xiaodian/common/openPlatformConfigList";
	}

	@RequiresPermissions("xiaodian:common:openPlatformConfig:view")
	@RequestMapping(value = "form")
	public String form(OpenPlatformConfig openPlatformConfig, Model model) {
		model.addAttribute("openPlatformConfig", openPlatformConfig);
		return "modules/xiaodian/common/openPlatformConfigForm";
	}

	@RequiresPermissions("xiaodian:common:openPlatformConfig:edit")
	@RequestMapping(value = "save")
	public String save(OpenPlatformConfig openPlatformConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, openPlatformConfig)){
			return form(openPlatformConfig, model);
		}
		openPlatformConfigService.save(openPlatformConfig);
		addMessage(redirectAttributes, "保存开放平台成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/openPlatformConfig/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:openPlatformConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(OpenPlatformConfig openPlatformConfig, RedirectAttributes redirectAttributes) {
		openPlatformConfigService.delete(openPlatformConfig);
		addMessage(redirectAttributes, "删除开放平台成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/openPlatformConfig/?repage";
	}


	@RequiresPermissions("weixin:open:authApp:edit")
	@RequestMapping("ycloud/flushToken")
	@ResponseBody
	public BaseResp ycloudFlushToken(String id) {
		BaseResp resp = BaseResp.newError();
		try {
			OpenPlatformConfig openPlatformConfig =	openPlatformConfigService.get(id);
			resp =	yiCloudCommonService.fetchAccessToken(openPlatformConfig.getAppId());
		}catch (Exception e){
			e.printStackTrace();
			resp.setMsg("系统错误");
		}finally {
			return resp;
		}

	}

	@RequiresPermissions("weixin:open:authApp:edit")
	@RequestMapping("ycloud/refreshToken")
	@ResponseBody
	public BaseResp refreshToken(String id) {
		BaseResp resp = BaseResp.newError();
		try {
			OpenPlatformConfig openPlatformConfig =	openPlatformConfigService.get(id);
			resp =	yiCloudCommonService.refreshToken(openPlatformConfig.getAppId());
		}catch (Exception e){
			e.printStackTrace();
			resp.setMsg("系统错误");
		}finally {
			return resp;
		}

	}

	@RequiresPermissions("weixin:open:authApp:edit")
	@RequestMapping("ycloud/setCallBackUrl")
	@ResponseBody
	public BaseResp setCallBackUrl(String id) {
		BaseResp resp = BaseResp.newError();
		try {
			OpenPlatformConfig openPlatformConfig =	openPlatformConfigService.get(id);
			String clientId = openPlatformConfig.getAppId();
			String callBackUrl = openPlatformConfig.getCallbackUrl();
			if (StringUtils.isAnyEmpty(clientId,callBackUrl)){
				resp.setMsg("应用和回调地址不能为空,请先填写并保存");
				return resp;
			}
			resp =	yiCloudCommonService.setCallBackUrl(clientId,IYiCloudConstant.cmd_oauth_finish,callBackUrl,"open");
		}catch (Exception e){
			e.printStackTrace();
			resp.setMsg("系统错误");
		}finally {
			return resp;
		}

	}

	@RequiresPermissions("weixin:open:authApp:edit")
	@RequestMapping("ycloud/cancelCallBackUrl")
	@ResponseBody
	public BaseResp cancelCallBackUrl(String id) {
		BaseResp resp = BaseResp.newError();
		try {
			OpenPlatformConfig openPlatformConfig =	openPlatformConfigService.get(id);
			if (null == openPlatformConfig){
				resp.setMsg("应用不存在");
				return resp;
			}
			resp =	yiCloudCommonService.setCallBackUrl(openPlatformConfig.getAppId(),IYiCloudConstant.cmd_oauth_finish,openPlatformConfig.getCallbackUrl(),"close");
		}catch (Exception e){
			e.printStackTrace();
			resp.setMsg("系统错误");
		}finally {
			return resp;
		}

	}


}