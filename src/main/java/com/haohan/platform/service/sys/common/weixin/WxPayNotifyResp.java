package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 微信成功支付后返回的响应
 *
 * @author lcw
 *
 */
@XStreamAlias("xml")
public class WxPayNotifyResp {

    @XStreamAlias("appid")
    private String appId;

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("bank_type")
    private String bankType;

    @XStreamAlias("cash_fee")
    private String cashFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("return_code")
    private String returnCode;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("time_end")
    private String timeEnd;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("transaction_id")
    private String transactionId;

    public String getAppId() {
        return appId;
    }

    public String getAttach() {
        return attach;
    }

    public String getBankType() {
        return bankType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public String getMchId() {
        return mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getOpenId() {
        return openId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getSign() {
        return sign;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
