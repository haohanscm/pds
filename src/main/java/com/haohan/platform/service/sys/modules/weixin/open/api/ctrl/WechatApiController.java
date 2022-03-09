package com.haohan.platform.service.sys.modules.weixin.open.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.Encodes;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.weixin.constant.IWxConstant;
import com.haohan.platform.service.sys.modules.weixin.miniapp.entity.QrCodeReqParams;
import com.haohan.platform.service.sys.modules.weixin.miniapp.service.WxAppBaseService;
import com.haohan.platform.service.sys.modules.weixin.miniapp.service.WxAppMsgService;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IUserConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenplatformManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.OpenplatformManageService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import me.chanjar.weixin.open.bean.auth.WxOpenAuthorizerInfo;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("${frontPath}/open/api")
public class WechatApiController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxOpenApiService wxOpenApiService;

    @Autowired
    private AuthAppService authAppService;

    @Autowired
    private OpenplatformManageService openplatformManageService;

    @Autowired
    private WxAppMsgService wxAppMsgService;

    @Autowired
    private WxAppBaseService wxAppBaseService;

    @Autowired
    private UPassportService passportService;

    @Autowired
    private UserOpenPlatformService userOpenPlatformService;

    // 微信应用授权
    @RequestMapping("auth_app")
    public void gotoPreAuthUrl(@RequestParam(required = true) String appId, @RequestParam(required = false) String url, @RequestParam(required = false) String authType, HttpServletRequest request, HttpServletResponse response) {
//        String host = request.getHeader("host");
        if (StringUtils.isBlank(url)) {
            url = "http://" + request.getServerName().concat(request.getRequestURI().replace("auth_app", "auth/jump/".concat(appId)));
        }
        try {
            url = wxOpenApiService.fetchByAppId(appId).getWxOpenComponentService().getPreAuthUrl(url);
            if (StringUtils.isNotBlank(authType)) {
                url = url.concat("&auth_type=" + authType);
            }
            response.sendRedirect(url);
        } catch (WxErrorException | IOException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 授权成功返回 商户信息页面
     *
     * @param authorizationCode
     * @return
     */
    @RequestMapping("auth/jump/{authAppId}")
    public String jump(@PathVariable("authAppId") String authAppId, @RequestParam("auth_code") String authorizationCode, Model model) {
        try {
            AuthApp app = authAppHandle(authAppId, authorizationCode, "0", null);

            addMessage(model, "授权应用成功,请添加应用秘钥");
            model.addAttribute("authApp", app);

            //将应用增加到wxAppService中
            WxAppBaseService.addWxappService(app);

            return "/modules/weixin/open/authAppForm";

        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }

    private AuthApp authAppHandle(String authAppId, String authorizationCode ,String merchantAppStyle, Integer tenantId) throws WxErrorException {
        // 设置租户id
        HttpSession session =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.setAttribute(IPdsConstant.TENANT_KEY, tenantId);

        WxOpenQueryAuthResult queryAuthResult = wxOpenApiService.fetchByAppId(authAppId).getWxOpenComponentService().getQueryAuth(authorizationCode);

        if (null == queryAuthResult || null == queryAuthResult.getAuthorizationInfo()) {
            logger.error("getQueryAuth", "没有获取到值,auth_code=" + authorizationCode);
        }
        WxOpenAuthorizationInfo authorizationInfo = queryAuthResult.getAuthorizationInfo();
        AuthApp app = new AuthApp();
        app.setAuthAppId(authAppId);
        String appId = authorizationInfo.getAuthorizerAppid();
        app.setAppId(appId);
        app.setAccessToken(authorizationInfo.getAuthorizerAccessToken());
        app.setFlushToken(authorizationInfo.getAuthorizerRefreshToken());
        app.setExpiresin(String.valueOf(authorizationInfo.getExpiresIn()));
        app.setAuthCode(authorizationInfo.getFuncInfo().toString());
        if (StringUtils.isNotBlank(appId)) {

            OpenplatformManage openplatformManage = openplatformManageService.findByAppId(appId);
            if (null != openplatformManage) {
                app.setAppSecret(openplatformManage.getAppSecrect());
                openplatformManage.setStatus(IMerchantConstant.available);
                openplatformManage.setTenantId(tenantId);
                openplatformManageService.save(openplatformManage);
            }

            WxOpenAuthorizerInfoResult authorizerInfo = wxOpenApiService.fetchByAppId(authAppId).getWxOpenComponentService().getAuthorizerInfo(appId);
            app.setAuthInfo(JacksonUtils.toJson(authorizerInfo));
            WxOpenAuthorizerInfo authInfo = authorizerInfo.getAuthorizerInfo();
            app.setAppName(authInfo.getNickName());
            app.setOriginalAppid(authInfo.getUserName());
            app.setAppIcon(authInfo.getHeadImg());
            app.setServiceType(authInfo.getServiceTypeInfo().toString());
            app.setVerifyType(authInfo.getVerifyTypeInfo().toString());
            app.setPrincipalName(authInfo.getPrincipalName());
            app.setAuthTime(new Date());
            app.setWeixinId(authInfo.getAlias());
            app.setQrcode(authInfo.getQrcodeUrl());
            app.setStatus("0");
        }
        app.setRemarks(merchantAppStyle);
        app.setTenantId(tenantId);

        AuthApp appOld = authAppService.fetchByAppId(appId);
        if (null != appOld) {
            authAppService.delete(appOld);
        }

        //获取小程序二维码
        if (IWxConstant.AppServiceType.wxapp.getCode().equals(app.getServiceType())) {
            QrCodeReqParams params = new QrCodeReqParams();
            params.setScene("auth");
            BaseResp resp = wxAppMsgService.fetchQrCodeByAppId(appId, params);
            if (resp.isSuccess()) {
                app.setQrcode(resp.getExt().toString());
            }
        }
        authAppService.save(app);

        logger.info("getQueryAuth", queryAuthResult);
        return app;
    }


    /**
     * 微信应用授权 （scm中使用）
     *
     * @param appId 授权平台appId
     * @param url
     * @return
     */
    @RequestMapping("wechat/auth")
    @ResponseBody
    public String gotoPreAuthUrl(@RequestParam String appId, @RequestParam String merchantAppStyle, @RequestParam Integer tenantId, @RequestParam String url, @RequestParam String redirectPage) {
        BaseResp resp = BaseResp.newError();
        url = url.concat("/").concat(appId).concat("/").concat(merchantAppStyle).concat("/").concat(tenantId.toString()).concat("/").concat(redirectPage);
        try {
            url = wxOpenApiService.fetchByAppId(appId).getWxOpenComponentService().getPreAuthUrl(url);
        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
            resp.setMsg(e.getMessage());
            return resp.toJson();
        }
        resp.setExt(url);
        resp.putStatus(RespStatus.SUCCESS);
        return resp.toJson();
    }

    /**
     * 授权成功返回 商户信息页面 （scm中使用）
     *
     * @param authorizationCode
     * @return
     */
    @RequestMapping("wechat/jump/{authAppId}/{merchantAppStyle}/{tenantId}/{redirectPage}")
    @ResponseBody
    public void jump(@PathVariable("authAppId") String authAppId, @PathVariable("merchantAppStyle") String merchantAppStyle, @PathVariable("tenantId") Integer tenantId, @PathVariable("redirectPage") String redirectPage,
                     @RequestParam("auth_code") String authorizationCode, HttpServletRequest request, HttpServletResponse response) {
        try {
            AuthApp app = authAppHandle(authAppId, authorizationCode, merchantAppStyle, tenantId);
            //将应用增加到wxAppService中
            WxAppBaseService.addWxappService(app);
            redirectPage = Encodes.decodeBase64String(redirectPage);
            String url = "http://" + request.getServerName().concat(redirectPage);
            logger.debug("===url:{}==", url);
            response.sendRedirect(url);
        } catch (WxErrorException | IOException e) {
            logger.error("gotoPreAuthUrl", e);
        }
    }


    @RequestMapping("get_authorizer_info")
    @ResponseBody
    public WxOpenAuthorizerInfoResult getAuthorizerInfo(@RequestParam String appId) {
        try {
            AuthApp app = authAppService.fetchByAppId(appId);
            return wxOpenApiService.fetchByAppId(app.getAuthAppId()).getWxOpenComponentService().getAuthorizerInfo(appId);
        } catch (WxErrorException e) {
            logger.error("getAuthorizerInfo", e);
            throw new RuntimeException(e);
        }
    }



    @RequestMapping("mp/auth")
    public String mpAuth(@RequestParam String appId, HttpServletRequest request, HttpServletResponse response){

//        String redirectUrl = "https://wxapp.haohanshop.com/".concat(request.getRequestURI()+"/callback/"+appId);
        String redirectUrl = "http://"+request.getServerName().concat(request.getRequestURI()+"/callback/"+appId);
        AuthApp authApp = authAppService.fetchByAppId(appId);
        WxMpService wxMpService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId()).getWxOpenComponentService().getWxMpServiceByAppid(appId);

        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo("oU6t6s9HbMYLyCs2_WhAgrjOrTPQ");
             if(null != wxMpUser){
                  logger.debug(JacksonUtils.toJson(wxMpUser));
             }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        String reqScope = request.getParameter("scope");
        String scope="snsapi_userinfo";
        if(StringUtils.isNotEmpty(reqScope)){
            scope=reqScope;
        }
//        String baseUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=${Redirect_Url}&response_type=code&scope=snsapi_userinfo&state=haohanshop#wechat_redirect";
//      String url= baseUrl.replace("${appId}",appId).replace("${Redirect_Url}",redirectUrl);
        String url = wxMpService.oauth2buildAuthorizationUrl(redirectUrl,scope,"haohanshop");
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }


    @RequestMapping("mp/auth/callback/{appId}")
    @ResponseBody
    public String mpAuthRedirect(@PathVariable("appId")String appId, HttpServletRequest request, HttpServletResponse response){
        BaseResp baseResp = BaseResp.newError();
        String code = request.getParameter("code");
        if(StringUtils.isEmpty(code)){
            baseResp.setMsg("授权失败");
            return baseResp.toJson();
        }

        try {
            AuthApp authApp = authAppService.fetchByAppId(appId);
         WxMpService wxMpService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId()).getWxOpenComponentService().getWxMpServiceByAppid(appId);
            WxMpOAuth2AccessToken auth2AccessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(auth2AccessToken,"zh_CN");
            if(null != auth2AccessToken  && null != wxMpUser && StringUtils.isNotEmpty(auth2AccessToken.getOpenId())){
                UPassport passport = new UPassport();
                passport.setRegIp(IpUtils.getRemoteIpAddr(request));
                passport.setRegType(IUserConstant.RegType.wechat.getCode());
                passport.setRegFrom(IUserConstant.RegFrom.app.getCode());
                passportService.save(passport);
                UserOpenPlatform userOpen = new UserOpenPlatform();
                userOpen.setUid(passport.getId());
                userOpen.setAccessToken(auth2AccessToken.getAccessToken());
                userOpen.setAppId(appId);
                userOpen.setAppType("1");//微信公众号
                userOpen.setAlbumUrl(wxMpUser.getHeadImgUrl());
                userOpen.setNickName(wxMpUser.getNickname());
                userOpen.setCity(wxMpUser.getCity());
                userOpen.setProvince(wxMpUser.getProvince());
                userOpen.setSex(wxMpUser.getSex()+"");
                userOpen.setUnionId(wxMpUser.getUnionId());
                userOpen.setOpenId(wxMpUser.getOpenId());
                userOpen.setMemo(wxMpUser.getRemark());
                userOpen.setPersonalInfo(JacksonUtils.toJson(wxMpUser));
                userOpenPlatformService.save(userOpen);
            }

        } catch (WxErrorException e) {
            e.printStackTrace();
            baseResp.setMsg("获取Token失败");
            return baseResp.toJson();
        }


        return baseResp.toJson();
    }




}
