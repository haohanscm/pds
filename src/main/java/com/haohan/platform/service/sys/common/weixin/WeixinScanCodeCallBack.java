package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/*****
 * 微信扫码支付模式一 ，扫码完成后 ，接收到微信的响应
 *
 * @author lcw
 *
 */
@XStreamAlias("xml")
public class WeixinScanCodeCallBack extends WxPayBaseResp {

    /**
     *
     */
    private static final long serialVersionUID = 6078506507920288499L;

    @XStreamAlias("appid")
    private String appid;

    @XStreamAlias("openid")
    private String openid;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("product_id")
    private String productId;

    @XStreamAlias("sign")
    private String sign;

    public String getAppid() {
        return appid;
    }

    public String getOpenid() {
        return openid;
    }

    public String getMchId() {
        return mchId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getProductId() {
        return productId;
    }

    public String getSign() {
        return sign;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
