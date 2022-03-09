package com.haohan.platform.service.sys.common.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/****
 * 统一下单请求参数
 *
 * @author lenovo
 *
 */
@XStreamAlias("xml")
public class WxPayReq {
    /***** 公众账号ID *****/
    @XStreamAlias("appid")
    @XStreamCDATA
    private String appId;

    /***** 商户号 ****/
    @XStreamAlias("mch_id")
    @XStreamCDATA
    private String mchId;

    /***** 设备号 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB *****/
    @XStreamAlias("device_info")
    @XStreamCDATA
    private String deviceInfo;

    /***** 随机字符串 ****/
    @XStreamAlias("nonce_str")
    @XStreamCDATA
    private String nonceStr;

    /***** 签名 ****/
    @XStreamAlias("sign")
    @XStreamCDATA
    private String sign;

    /***** 商品描述 商品或支付单简要描述 ****/
    @XStreamAlias("body")
    @XStreamCDATA
    private String body;

    /***** 商品详情 商品名称明细列表 ****/
    @XStreamAlias("detail")
    @XStreamCDATA
    private String detail;

    /***** 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据 如 深圳分店 ****/
    @XStreamAlias("attach")
    @XStreamCDATA
    private String attach;

    /***** 商户订单号 ****/
    @XStreamAlias("out_trade_no")
    @XStreamCDATA
    private String outTradeNo;

    /***** 货币类型 ****/
    @XStreamAlias("fee_type")
    @XStreamCDATA
    private String feeType;

    /***** 总金额 ****/
    @XStreamAlias("total_fee")
    @XStreamCDATA
    private Integer totalFee;

    /***** 终端IP ****/
    @XStreamAlias("spbill_create_ip")
    @XStreamCDATA
    private String spbillCreateIp;

    /***** 交易起始时间 ****/
    @XStreamAlias("time_start")
    @XStreamCDATA
    private String timeStart;

    /***** 交易结束时间 ****/
    @XStreamAlias("time_expire")
    @XStreamCDATA
    private String time_expire;

    /***** 商品标记 ****/
    @XStreamAlias("goods_tag")
    @XStreamCDATA
    private String goodsTag;

    /***** 通知地址 ****/
    @XStreamAlias("notify_url")
    @XStreamCDATA
    private String notifyUrl;

    /***** 交易类型 JSAPI，NATIVE，APP ****/
    @XStreamAlias("trade_type")
    @XStreamCDATA
    private String tradeType;

    /***** 商品ID ****/
    @XStreamAlias("product_id")
    @XStreamCDATA
    private String productId;

    /***** 指定支付方式 ****/
    @XStreamAlias("limit_pay")
    @XStreamCDATA
    private String limit_pay;

    /***** 用户标识 ****/
    @XStreamAlias("openid")
    private String openid;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

}
