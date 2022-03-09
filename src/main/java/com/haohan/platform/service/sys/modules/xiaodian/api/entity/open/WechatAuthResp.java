package com.haohan.platform.service.sys.modules.xiaodian.api.entity.open;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;

import java.io.Serializable;

/**
 * @author dy
 * @date 2019/9/6
 */
public class WechatAuthResp implements Serializable {

    @JsonProperty("openid")
    private String openId;
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("unionid")
    private String unionId;
    @JsonProperty("errcode")
    private Integer errCode;
    @JsonProperty("errmsg")
    private String errMsg;

    private AuthApp authApp;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public AuthApp getAuthApp() {
        return authApp;
    }

    public void setAuthApp(AuthApp authApp) {
        this.authApp = authApp;
    }
}
