package com.haohan.platform.service.sys.modules.xiaodian.api.impl;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.service.BaseService;
import com.haohan.platform.service.sys.common.utils.HttpClientUtils;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.entity.WxUserInfo;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.open.WechatAuthResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOpenPlatformUserService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author dy
 * @date 2019/9/6
 */
@Service
public class OpenPlatformUserServiceImpl extends BaseService implements IOpenPlatformUserService {

    @Autowired
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private UPassportService uPassportService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;
    @Autowired
    private WxOpenApiService wxOpenApiService;

    @Override
    @Transactional(readOnly = false)
    public BaseResp openUserAdd(String appId, String code, String userInfo, String encryptedData, String signature, String iv, String regIp) {
        BaseResp resp = BaseResp.newError();
        UserOpenPlatform userOpen = new UserOpenPlatform();
        String avatar = "";
        WxUserInfo wxUserInfo = JacksonUtils.readValue(userInfo, WxUserInfo.class);
        if (null != wxUserInfo) {
            wxUserToPlatformUser(userOpen, wxUserInfo, userInfo);
            avatar = wxUserInfo.getAvatarUrl();
        }
        // ??????????????????????????????
        BaseResp wechatResp = wechatCheck(appId, code);
        if (!wechatResp.isSuccess()) {
            return wechatResp;
        }
        WechatAuthResp authResp = (WechatAuthResp) wechatResp.getExt();
        AuthApp authApp = authResp.getAuthApp();
        String sessionKey = authResp.getSessionKey();
        String unionId = authResp.getUnionId();
        String openId = authResp.getOpenId();
        MerchantAppManage merchantAppManage = merchantAppManageService.fetchByAppId(appId);
        // ?????? ????????????
        if (StringUtils.isNoneEmpty(encryptedData, signature, iv)) {
            try {
                WxOpenApiService apiService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId());
                WxMaUserService wxMaUserService = apiService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getUserService();
                WxMaUserInfo wxMaUserInfo = wxMaUserService.getUserInfo(sessionKey, encryptedData, iv);
                unionId = wxMaUserInfo.getUnionId();
                openId = wxMaUserInfo.getOpenId();
            } catch (Exception e) {
                logger.debug("add??????,????????????");
            }
        }
        userOpen.setAppId(appId);
        userOpen.setCreateTime(new Date());
        userOpen.setAppType("0");
        userOpen.setOpenId(openId);
        userOpen.setUnionId(unionId);
        userOpen.setAccessToken(sessionKey);

        // ???????????????
        UPassport upassport = null;
        // unionId ???????????????unionId ???????????????openId
        if (StringUtils.isNotEmpty(unionId)) {
            upassport = uPassportService.fetchByUnionID(unionId);
        } else {
            upassport = uPassportService.fetchByUnionID(openId);
        }

        String uid;
        if (null == upassport) {
            //???????????????
            upassport = new UPassport();
            if (null != merchantAppManage) {
                upassport.setMerchantId(merchantAppManage.getMerchantId());
            }
            upassport.setRegIp(regIp);
            upassport.setRegType("5");//??????
            upassport.setRegTime(new Date());
            upassport.setRegFrom("3");//???????????????
            upassport.setStatus(IMerchantConstant.available);//????????????
            upassport.setId(IdGen.genByT(UPassport.class));
            upassport.setIsNewRecord(true);
            upassport.setServiceId(appId);
        }
        upassport.setAvatar(avatar);
        upassport.setLoginName(userOpen.getNickName());
        // unionid ???????????????unionId ???????????????openId
        upassport.setUnionId(openId);
        if (StringUtils.isNotEmpty(unionId)) {
            upassport.setUnionId(unionId);
        }
        uid = upassport.getId();
        uPassportService.save(upassport);
        // ????????????????????????
        if (StringUtils.isNotEmpty(openId)) {
            UserOpenPlatform userOpenPlatform = userOpenPlatformService.fetchByAppIdAndOpenId(appId, openId);
            // ???????????????????????????????????????
            if (null != userOpenPlatform) {
                resp.setExt(userOpenPlatform.getUid());
                resp.putStatus(RespStatus.SUCCESS);
                //??????????????????
                if (null != wxUserInfo) {
                    wxUserToPlatformUser(userOpenPlatform, wxUserInfo, userInfo);
                }
                // sessionkey????????????,???????????????????????????
                userOpenPlatform.setAccessToken(sessionKey);
                userOpenPlatformService.save(userOpenPlatform);
                return resp;
            }
        }
        //?????? ??????????????????
        userOpen.setUpdateDate(new Date());
        userOpen.setUid(uid);
        userOpenPlatformService.save(userOpen);
        resp.setExt(uid);
        resp.putStatus(RespStatus.SUCCESS);
        return resp;
    }

    private void wxUserToPlatformUser(UserOpenPlatform userOpenPlatform, WxUserInfo wxUserInfo, String userInfoStr) {
        userOpenPlatform.setNickName(wxUserInfo.getNickName());
        userOpenPlatform.setAlbumUrl(wxUserInfo.getAvatarUrl());
        userOpenPlatform.setSex(wxUserInfo.getGender());
        userOpenPlatform.setProvince(wxUserInfo.getProvince());
        userOpenPlatform.setCity(wxUserInfo.getCity());
        userOpenPlatform.setPersonalInfo(userInfoStr);
        userOpenPlatform.setAccessToken(wxUserInfo.getUserToken());
    }

    /**
     * ????????????????????????
     *
     * @param appId
     * @param code
     * @return
     */
    @Override
    public BaseResp wechatCheck(String appId, String code) {
        BaseResp resp = BaseResp.newSuccess();
        AuthApp authApp = authAppService.fetchByAppId(appId);
        if (null == authApp) {
            resp.putStatus(RespStatus.NOT_FOUND_ERROR);
            return resp;
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        String result = HttpClientUtils.getInstance().httpGet(String.format(url, appId, authApp.getAppSecret(), code));

        WechatAuthResp authResp = JacksonUtils.readValue(result, WechatAuthResp.class);
        // ??????????????????
        int errCode = (authResp == null || authResp.getErrCode() == null) ? 0 : authResp.getErrCode();
        if (errCode != 0) {
            resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
            logger.debug("??????????????????????????????:" + result);
            return resp;
        }
        authResp.setAuthApp(authApp);
        resp.setExt(authResp);
        return resp;
    }

}
