package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author laijie.zhang
 * @ClassName: WxRefundOrderReq
 * @Description: 微信退款接口请求参数
 * @date 2016年7月7日 下午9:20:17
 */
@XStreamAlias("xml")
public class WxRefundOrderReq {

    @XStreamAlias("appid")
    @XStreamCDATA
    private String appId;

    @XStreamAlias("mch_id")
    @XStreamCDATA
    private String mchId;

    /***** 设备号 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB *****/
    @XStreamAlias("device_info")
    @XStreamCDATA
    private String deviceInfo;

    @XStreamAlias("nonce_str")
    @XStreamCDATA
    private String nonceStr;

    @XStreamAlias("sign")
    @XStreamCDATA
    private String sign;

    @XStreamAlias("transaction_id")
    @XStreamCDATA
    private String transactionId;

    @XStreamAlias("out_trade_no")
    @XStreamCDATA
    private String outTradeNo;

    @XStreamAlias("out_refund_no")
    @XStreamCDATA
    private String outRefundNo;

    // 订单总金额
    @XStreamAlias("total_fee")
    @XStreamCDATA
    private Integer totalFee;

    @XStreamAlias("refund_fee")
    @XStreamCDATA
    private Integer refundFee;

    @XStreamAlias("op_user_id")
    @XStreamCDATA
    private String opUserId;

    public String getAppId() {
        return appId;
    }

    public String getMchId() {
        return mchId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
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

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

}
