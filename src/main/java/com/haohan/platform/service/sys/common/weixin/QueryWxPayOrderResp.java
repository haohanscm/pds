package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/****
 * 微信订单查询接口返回参数
 *
 * @author lcw
 *
 */
@XStreamAlias("xml")
public class QueryWxPayOrderResp {

    @XStreamAlias("return_code")
    private String returnCode;

    @XStreamAlias("return_msg")
    private String returnMsg;

    /******* 在return_code为SUCCESS的时候有返回 ********/
    @XStreamAlias("appid")
    private String appId;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("sign")
    private String sign;

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode;

    @XStreamAlias("err_code_des")
    private String errCodeDes;

    /******* 在return_code 和result_code都为SUCCESS的时候有返回 ********/
    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("openid")
    private String openId;

    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    @XStreamAlias("trade_type")
    private String tradeType;

    @XStreamAlias("trade_state")
    private String tradeState;

    @XStreamAlias("bank_type")
    private String bank_type;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("cash_fee")
    private String cashFee;

    @XStreamAlias("cash_fee")
    private String cashFeeType;

    @XStreamAlias("coupon_fee")
    private String couponFee;

    @XStreamAlias("coupon_fee_0")
    private String couponFee0;

    @XStreamAlias("coupon_count")
    private String couponCount;

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    @XStreamAlias("attach")
    private String attach;

    @XStreamAlias("time_end")
    private String timeEnd;

    @XStreamAlias("trade_state_desc")
    private String tradeStateDesc;

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public String getAppId() {
        return appId;
    }

    public String getMchId() {
        return mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getOpenId() {
        return openId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public String getBank_type() {
        return bank_type;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public String getCouponFee0() {
        return couponFee0;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public void setCouponFee0(String couponFee0) {
        this.couponFee0 = couponFee0;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

}
