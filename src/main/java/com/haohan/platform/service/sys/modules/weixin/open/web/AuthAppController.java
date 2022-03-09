package com.haohan.platform.service.sys.modules.weixin.open.web;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.miniapp.entity.QrCodeReqParams;
import com.haohan.platform.service.sys.modules.weixin.miniapp.service.WxAppMsgService;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 授权应用管理Controller
 *
 * @author haohan
 * @version 2018-01-05
 */
@Controller
@RequestMapping(value = "${adminPath}/weixin/open/authApp")
public class AuthAppController extends BaseController {

    @Autowired
    private AuthAppService authAppService;

    @Autowired
    private WxOpenApiService wxOpenApiService;

    @Autowired
    private WxAppMsgService wxAppMsgService;


    @ModelAttribute
    public AuthApp get(@RequestParam(required = false) String id) {
        AuthApp entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = authAppService.get(id);
        }
        if (entity == null) {
            entity = new AuthApp();
        }
        return entity;
    }

    @RequiresPermissions("weixin:open:authApp:view")
    @RequestMapping(value = {"list", ""})
    public String list(AuthApp authApp, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AuthApp> page = authAppService.findPage(new Page<AuthApp>(request, response), authApp);
        model.addAttribute("page", page);
        return "/modules/weixin/open/authAppList";
    }

    @RequiresPermissions("weixin:open:authApp:view")
    @RequestMapping(value = "form")
    public String form(AuthApp authApp, Model model) {
        model.addAttribute("authApp", authApp);
        return "/modules/weixin/open/authAppForm";
    }

    @RequiresPermissions("weixin:open:authApp:edit")
    @RequestMapping(value = "save")
    public String save(AuthApp authApp, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, authApp)) {
            return form(authApp, model);
        }
        authAppService.save(authApp);
        addMessage(redirectAttributes, "保存应用成功");
        return "redirect:" + Global.getAdminPath() + "/weixin/open/authApp/?repage";
    }

    @RequiresPermissions("weixin:open:authApp:edit")
    @RequestMapping(value = "delete")
    public String delete(AuthApp authApp, RedirectAttributes redirectAttributes) {
        authAppService.delete(authApp);
        addMessage(redirectAttributes, "删除应用成功");
        return "redirect:" + Global.getAdminPath() + "/weixin/open/authApp/?repage";
    }


    @RequiresPermissions("weixin:open:authApp:edit")
    @RequestMapping("flushToken")
    @ResponseBody
    public String flushToken(String id) {
        BaseResp resp = BaseResp.newError();
        try {
            AuthApp app = authAppService.get(id);
            String appId = app.getAppId();

            String accessToken = wxOpenApiService.fetchByAppId(app.getAuthAppId()).getWxOpenComponentService().getAuthorizerAccessToken(appId, true);
            if (StringUtils.isNotEmpty(accessToken)) {
                app.setAccessToken(accessToken);
                app.setUpdateDate(new Date());
            }
            /**
             WxOpenConfigStorage configStorage = wxOpenApiService.fetchByAppId(app.getAuthAppId()).getWxOpenConfigStorage();
             if (null != app) {
             configStorage.setAuthorizerRefreshToken(appId, app.getFlushToken());
             if (configStorage.isAuthorizerAccessTokenExpired(appId)) {
             JsonObject jsonObject = new JsonObject();
             jsonObject.addProperty("component_appid", configStorage.getComponentAppId());
             jsonObject.addProperty("authorizer_appid", appId);
             jsonObject.addProperty("authorizer_refresh_token", configStorage.getAuthorizerRefreshToken(appId));
             String componentAccessToken =configStorage.getComponentAccessToken();
             if(StringUtils.isEmpty(componentAccessToken)){
             componentAccessToken=wxOpenApiService.fetchByAppId(app.getAuthAppId()).getWxOpenComponentService().getComponentAccessToken(true);
             }


             WxOpenAuthorizerInfoResult authorizerInfoResult  =wxOpenApiService.getWxOpenComponentService().getAuthorizerInfo(appId);

             //                    String responseContent = wxOpenApiService.post(WxOpenComponentService.API_AUTHORIZER_TOKEN_URL.concat("?component_access_token=").concat(componentAccessToken), jsonObject.toString());
             //                    WxOpenAuthorizerAccessToken wxOpenAuthorizerAccessToken = WxOpenAuthorizerAccessToken.fromJson(responseContent);
             //                    if(null != authorizerInfoResult) {
             WxOpenAuthorizationInfo authInfo =  authorizerInfoResult.getAuthorizationInfo();
             if(null != authInfo) {
             configStorage.updateAuthorizerAccessToken(appId, );
             }
             String accessToken = configStorage.getAuthorizerAccessToken(appId);
             if (StringUtils.isNotEmpty(accessToken)) {
             app.setAccessToken(accessToken);
             app.setUpdateDate(new Date());
             }
             //                    }

             }
             **/
            authAppService.save(app);
            resp.putStatus(RespStatus.SUCCESS);
            resp.setExt(app.toJson());
            return resp.toJson();
        } catch (WxErrorException e) {
            logger.error("flushToken", e);
            return resp.toJson();
        }
    }


    // 获取小程序码
    @RequiresPermissions("xiaodian:merchant:merchantAppManage:edit")
    @RequestMapping(value = "fetchQrcode/{appId}")
    @ResponseBody
    public String fetchQrcode(@PathVariable("appId") String appId, QrCodeReqParams reqParams, HttpServletRequest request) {

        BaseResp resp = new BaseResp();

        try {

            resp = wxAppMsgService.fetchQrCodeByAppId(appId, reqParams);
            if (resp.isSuccess()) {
                resp.putStatus(RespStatus.SUCCESS);
//                不需要保存,页面自动保存
//                AuthApp authApp  = authAppService.fetchByAppId(appId);
//                authApp.setQrcode(resp.getExt().toString());
//                authAppService.save(authApp);
                return resp.toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resp.toJson();
        }


    }


}