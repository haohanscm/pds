package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author laijie.zhang
 * @ClassName: WxRefundOrderResp
 * @Description: 微信退款接口返回参数
 * @date 2016年7月7日 下午9:35:23
 */
@XStreamAlias("xml")
public class WxRefundOrderResp {

    @XStreamAlias("return_code")
    private String returnCode;

    @XStreamAlias("return_msg")
    private String returnMsg;

    /******* 在return_code为SUCCESS的时候有返回 ********/

    @XStreamAlias("result_code")
    private String resultCode;

    @XStreamAlias("err_code")
    private String errCode;

    @XStreamAlias("err_code_des")
    private String errCodeDes;

    @XStreamAlias("appid")
    private String appId;

    @XStreamAlias("mch_id")
    private String mchId;

    @XStreamAlias("device_info")
    private String deviceInfo;

    @XStreamAlias("nonce_str")
    private String nonceStr;

    @XStreamAlias("sign")
    private String sign;

    /******* 在return_code 和result_code都为SUCCESS的时候有返回 ********/

    @XStreamAlias("transaction_id")
    private String transactionId;

    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    // 商户退款单号
    @XStreamAlias("out_refund_no")
    private String outRefundNo;

    // 微信退款单号
    @XStreamAlias("refund_id")
    private String refundId;
    // 退款渠道
    @XStreamAlias("refund_channel")
    private String refundChannel;

    // 申请退款金额
    @XStreamAlias("refund_fee")
    private Integer refundFree;

    @XStreamAlias("total_fee")
    private String totalFee;

    @XStreamAlias("fee_type")
    private String feeType;

    @XStreamAlias("cash_fee")
    private String cashFee;

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

    public String getTotalFee() {
        return totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
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

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getRefundChannel() {
        return refundChannel;
    }

    public void setRefundChannel(String refundChannel) {
        this.refundChannel = refundChannel;
    }

    public Integer getRefundFree() {
        return refundFree;
    }

    public void setRefundFree(Integer refundFree) {
        this.refundFree = refundFree;
    }

}
