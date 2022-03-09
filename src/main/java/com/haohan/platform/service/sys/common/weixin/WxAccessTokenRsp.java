package com.haohan.platform.service.sys.common.weixin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 生成用户的授权token
 *
 * @author lenovo
 */
public class WxAccessTokenRsp extends WeixinBaseResp implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4410368685171647124L;

    private String userId;

    public WxAccessTokenRsp() {
    }

    public WxAccessTokenRsp(String accessToken, Integer expiresIn, String refreshToken, String openid, String scope, String unionid) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.openid = openid;
        this.scope = scope;
        this.unionid = unionid;
    }

    /*** 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 ***/
    @JsonProperty(value = "access_token")
    private String accessToken;

    /*** access_token接口调用凭证超时时间，单位（秒） ***/
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    /*** 用户刷新access_token ***/
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    /*** 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID ***/
    @JsonProperty(value = "openid")
    private String openid;

    /*** 用户授权的作用域，使用逗号（,）分隔 ***/
    @JsonProperty(value = "scope")
    private String scope;

    /*** 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段 ***/
    @JsonProperty(value = "unionid")
    private String unionid;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

}
