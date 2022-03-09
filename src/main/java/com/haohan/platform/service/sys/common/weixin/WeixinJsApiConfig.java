package com.haohan.platform.service.sys.common.weixin;

import java.io.Serializable;

/**
 * @author zhaokuner
 * @ClassName: WeixinJsApiConfig
 * @Description: 微信公众号JSAPI
 * @date 2016年8月27日 下午6:58:06
 */
public class WeixinJsApiConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    // 必填，公众号的唯一标识
    private String appId;
    // 必填，生成签名的时间戳
    private Long timestamp;
    // 必填，生成签名的随机串
    private String nonceStr;
    // 必填，签名
    private String signature;
    // 必填，需要使用的JS接口列表
    private String[] jsApiList;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String[] getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(String[] jsApiList) {
        this.jsApiList = jsApiList;
    }
}
