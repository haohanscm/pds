package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request;

import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/7/19
 */
public class MBaseRequest implements Serializable {

    private String org_no;      //渠道编号
    private String merchantId;      //合作方平台商户编号
    private String outTradeNo;      //合作方平台订单号
    private String channel;         //支付渠道 WECHAT ALIPAY
    private String version;         //版本号
    private String random_str;      //唯一随机数
    private String sign;            //签名字符串

    public Map<String,Object> toMap(){
        return CommonUtils.beanToMap(this);
    }

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String toJson() {
        return JacksonUtils.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
