package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/7/19
 */
public class MJSPayResponse extends MBaseResponse implements Serializable {

    private String org_no;      //机构号
    private String merchantId;      //商户号
    private String outTradeNo;      //我方平台订单号
    private String hf_order_no;     //惠付平台订单号
    private String pay_url;         //支付请求URL
    private String channel;         //支付渠道      WECHAT  ALIPAY
    private String random_str;      //唯一随机数
    private String sign;            //签名

    public String getOrg_no() {
        return org_no;
    }

    public void setOrg_no(String org_no) {
        this.org_no = org_no;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getHf_order_no() {
        return hf_order_no;
    }

    public void setHf_order_no(String hf_order_no) {
        this.hf_order_no = hf_order_no;
    }

    public String getPay_url() {
        return pay_url;
    }

    public void setPay_url(String pay_url) {
        this.pay_url = pay_url;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRandom_str() {
        return random_str;
    }

    public void setRandom_str(String random_str) {
        this.random_str = random_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
