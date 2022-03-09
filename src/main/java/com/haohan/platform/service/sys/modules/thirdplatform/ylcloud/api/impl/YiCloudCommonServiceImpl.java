package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req.*;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp.YiCloudBaseResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp.YiCloudFetchTokenResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp.YiCloudRefreshTokenResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp.YiCloudTerminalAuthResp;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.inf.IYiCloudCommonService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.OpenPlatformConfigService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shenyu
 * @create 2018/11/22
 */
@Service
public class YiCloudCommonServiceImpl extends YiCloudAbstractService  implements IYiCloudCommonService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private OpenPlatformConfigService openPlatformConfigService;

    @Override
    public BaseResp fetchAccessToken(String clientId) {
        BaseResp baseResp = BaseResp.newError();

        OpenPlatformConfig openPlatformConfig = openPlatformConfigService.fetchByAppIdAndType(clientId,ICommonConstant.AppType.yilCloud);
        if (null == openPlatformConfig){
            baseResp.setMsg("应用不存在");
            return baseResp;
        }
        YiCloudFetchTokenReq fetchTokenReq = fillBaseInfo(clientId,YiCloudFetchTokenReq.class,baseResp,openPlatformConfig);
        fetchTokenReq.setScope("all");
        fetchTokenReq.setGrant_type("client_credentials");
        YiCloudFetchTokenResp fetchTokenResp = sendReq(accessTokenUrl,CommonUtils.beanToStrMap(fetchTokenReq),YiCloudFetchTokenResp.class,baseResp);

        if (null == fetchTokenResp){
            return baseResp;
        }else {
            if (baseResp.isSuccess()){
                //保存token
                openPlatformConfig.setAccessToken(fetchTokenResp.getAccess_token());
                openPlatformConfig.setFlushToken(fetchTokenResp.getRefresh_token());
                openPlatformConfigService.save(openPlatformConfig);
            }
        }
        return baseResp;
    }

    @Override
    public BaseResp refreshToken(String clientId) {
        BaseResp baseResp = BaseResp.newError();

        OpenPlatformConfig openPlatformConfig = openPlatformConfigService.fetchByAppIdAndType(clientId,ICommonConstant.AppType.yilCloud);
        if (null == openPlatformConfig){
            baseResp.setMsg("应用不存在");
            return baseResp;
        }
        YiCloudRefreshTokenReq refreshTokenReq = fillBaseInfo(clientId,YiCloudRefreshTokenReq.class,baseResp,openPlatformConfig);
        refreshTokenReq.setScope("all");
        refreshTokenReq.setGrant_type("refresh_token");
        refreshTokenReq.setRefresh_token(openPlatformConfig.getFlushToken());
        YiCloudRefreshTokenResp refreshTokenResp = sendReq(accessTokenUrl,CommonUtils.beanToStrMap(refreshTokenReq),YiCloudRefreshTokenResp.class,baseResp);

        if (null == refreshTokenResp){
            return baseResp;
        }else {
            if (baseResp.isSuccess()){
                //保存token
                openPlatformConfig.setAccessToken(refreshTokenResp.getAccess_token());
                openPlatformConfig.setFlushToken(refreshTokenResp.getRefresh_token());
                openPlatformConfigService.save(openPlatformConfig);
            }
        }
        return baseResp;
    }

    @Override
    public BaseResp addPrinter(String clientId,String machineCode,String msign,String printerName) {
        BaseResp baseResp = BaseResp.newError();

        OpenPlatformConfig openPlatformConfig = openPlatformConfigService.fetchByAppIdAndType(clientId,ICommonConstant.AppType.yilCloud);
        if (null == openPlatformConfig){
            baseResp.setMsg("应用不存在");
            return baseResp;
        }
        YiCloudTerminalAuthReq terminalAuthReq = fillBaseInfo(clientId,YiCloudTerminalAuthReq.class,baseResp,openPlatformConfig);
        terminalAuthReq.setMachine_code(machineCode);
        terminalAuthReq.setMsign(msign);
        terminalAuthReq.setPrint_name(printerName);
        terminalAuthReq.setAccess_token(openPlatformConfig.getAccessToken());

        YiCloudTerminalAuthResp terminalAuthResp = sendReq(terminalAuthUrl,CommonUtils.beanToStrMap(terminalAuthReq),YiCloudTerminalAuthResp.class,baseResp);

        return baseResp;
    }

    @Override
    public BaseResp setLogo(String clientId, String machineCode, String imgUrl) {
        BaseResp baseResp = BaseResp.newError();
        OpenPlatformConfig openPlatformConfig = openPlatformConfigService.fetchByAppIdAndType(clientId,ICommonConstant.AppType.yilCloud);
        if (null == openPlatformConfig){
            baseResp.setMsg("应用不存在");
            return baseResp;
        }
        YiCloudLogoSettingReq logoSettingReq = fillBaseInfo(clientId,YiCloudLogoSettingReq.class,baseResp,openPlatformConfig);
        logoSettingReq.setMachine_code(machineCode);
        logoSettingReq.setImg_url(imgUrl);
        logoSettingReq.setAccess_token(openPlatformConfig.getAccessToken());

        YiCloudBaseResp yiCloudBaseResp = sendReq(logoSettingsUrl,CommonUtils.beanToStrMap(logoSettingReq),YiCloudBaseResp.class,baseResp);
        return baseResp;
    }

    @Override
    public BaseResp cancelLogo(String clientId, String machineCode) {
        BaseResp baseResp = BaseResp.newError();
        OpenPlatformConfig openPlatformConfig = openPlatformConfigService.fetchByAppIdAndType(clientId,ICommonConstant.AppType.yilCloud);
        if (null == openPlatformConfig){
            baseResp.setMsg("应用不存在");
            return baseResp;
        }
        YiCloudLogoSettingReq logoSettingReq = fillBaseInfo(clientId,YiCloudLogoSettingReq.class,baseResp,openPlatformConfig);
        logoSettingReq.setMachine_code(machineCode);
        logoSettingReq.setAccess_token(openPlatformConfig.getAccessToken());

        YiCloudBaseResp yiCloudBaseResp = sendReq(cancelLogoUrl,CommonUtils.beanToStrMap(logoSettingReq),YiCloudBaseResp.class,baseResp);
        return baseResp;
    }

    @Override
    public BaseResp setCallBackUrl(String clientId, String cmd, String url, String status) {
        BaseResp baseResp = BaseResp.newError();
        OpenPlatformConfig openPlatformConfig = openPlatformConfigService.fetchByAppIdAndType(clientId,ICommonConstant.AppType.yilCloud);
        if (null == openPlatformConfig){
            baseResp.setMsg("应用不存在");
            return baseResp;
        }
        YiCloudPushUrlSettingsReq urlSettingsReq = fillBaseInfo(clientId,YiCloudPushUrlSettingsReq.class,baseResp,openPlatformConfig);
        urlSettingsReq.setCmd(cmd);
        urlSettingsReq.setUrl(url);
        urlSettingsReq.setStatus(status);
        urlSettingsReq.setAccess_token(openPlatformConfig.getAccessToken());

        YiCloudBaseResp yiCloudBaseResp = sendReq(pushSettingsUrl,CommonUtils.beanToStrMap(urlSettingsReq),YiCloudBaseResp.class,baseResp);
        return baseResp;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }



}
