package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/****
 * 微信订单查询接口请求参数
 *
 * @author lcw
 *
 */
@XStreamAlias("xml")
public class CheckWxPayOrderBillReq {

    @XStreamAlias("appid")
    @XStreamCDATA
    private String appId;

    @XStreamAlias("nonce_str")
    @XStreamCDATA
    private String nonceStr;

    @XStreamAlias("mch_id")
    @XStreamCDATA
    private String mchId;

    @XStreamAlias("bill_date")
    @XStreamCDATA
    private String billDate;

    @XStreamAlias("bill_type")
    @XStreamCDATA
    private String billType;

    @XStreamAlias("sign")
    @XStreamCDATA
    private String sign;

    public String getAppId() {
        return appId;
    }

    public String getMchId() {
        return mchId;
    }

    public String getSign() {
        return sign;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}
