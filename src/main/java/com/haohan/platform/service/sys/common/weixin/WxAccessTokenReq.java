package com.haohan.platform.service.sys.common.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 获取授权token 请求参数
 *
 * @author lenovo
 */
public class WxAccessTokenReq {

    /**** 弹出授权页面，直接跳转，只能获取用户openid ****/
    public static String BASESCOPE = "snsapi_base";

    /****
     * 弹出授权页面， 可通过openid拿到昵称、性别、所在地。 并且，即使在未关注的情况下，只要用户授权，也能获取其信息
     ****/
    public static String INFOSCOPE = "snsapi_userinfo";

    /**** 公众号的唯一标识 ****/
    private String appId;

    /**** 授权后重定向的回调链接地址，请使用urlencode对链接进行处理 ****/
    private String redirectUri;

    /**** 返回类型，请填写code ****/
    private String responseType = "code";

    /****
     * 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）， snsapi_userinfo （弹出授权页面， 可通过openid拿到昵称、性别、所在地。 并且，即使在未关注的情况下，只要用户授权，也能获取其信息)
     */
    private String scope = "snsapi_userinfo";

    /**** 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节 *****/
    private String state;

    public WxAccessTokenReq() {
    }

    public WxAccessTokenReq(String appId, String redirectUri, String state) {
        this.appId = appId;
        this.redirectUri = redirectUri;
        this.state = state;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /***
     * 拼接获取授权token的参数串
     */
    @Override
    public String toString() {

        if (null == this.getRedirectUri() || this.getRedirectUri().isEmpty()) {
            try {
                throw new Exception("未设置回调URI");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        StringBuffer s = new StringBuffer();
        s.append("appid=").append(this.getAppId());
        try {
            s.append("&redirect_uri=").append(URLEncoder.encode(this.getRedirectUri(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        s.append("&response_type=").append(this.getResponseType());
        s.append("&scope=").append(this.getScope());
        s.append("&state=").append(this.getState());
        s.append("&connect_redirect=1");
        s.append("#wechat_redirect");
        return s.toString();
    }

}
