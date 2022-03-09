package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.util.HtmlUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单支付Entity
 * @author haohan
 * @version 2017-12-10
 */
public class OrderPayRecord extends DataEntity<OrderPayRecord> {

	private static final long serialVersionUID = 1L;
	private String partnerNum;		//渠道编号
	private String merchantId;		// 商户ID
	private String merchantName;		// 商户名称
	private String shopId;		// 店铺ID
	private String shopName;		// 店铺名称
	private String orderId;		// 订单号
	private String orderType;		// 订单类型
	private String clientIp;		// 用户请求IP
	private String requestId;		// 支付流水号
	private String transId;		//第三方平台订单id
	private String partnerId;		// 主商户编号
	private String goodsName;		// 商品名称
	private Date orderTime;		// 订单提交日期
	private BigDecimal orderAmount;		// 订单金额 单位元
	private String authCode;		// 授权码
	private String payChannel;		// 支付渠道
	private String payType;		// 支付方式
	private String limitPay;		// 是否支持信用卡
	private String respCode;		// 返回码
	private String respDesc;		// 返回码信息描述
	private Date respTime;		// 返回时间
	private String orderQrcode;		// 商户订单二维码
	private String openid;		// 用户标示openid
	private String buyerId;		// 交易用户id
	private String appid;		// 微信公众帐号ID
	private String prepayId;		// 预支付订单号
	private String deviceId;	//设备号
	private String paySign;		// 签名
	private String timeStamp;		// 时间戳
	private String noncestr;		// 随机字符串
	private String payStatus;		// 支付状态
	private String orderInfo; 		//订单信息
	private String orderDetail;     //订单明细
	private String payInfo;   		//支付信息
	private Date beginOrderTime;		// 开始 订单提交日期
	private Date endOrderTime;		// 结束 订单提交日期
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private String notifyUrl;       //银行回调地址
	private String partnerNotifyUrl; 	//渠道商回调地址

	private BigDecimal fee;  // 手续费
	private BigDecimal rate;   // 费率

	/**
	 * 支付网关渠道
	 */
	private String bankChannel;

	//查询补充字段
	private String partnerAppName;
	private Date startTime;
	private Date endTime;
	private String jsAppId;



	public OrderPayRecord() {
		super();
	}

	public OrderPayRecord(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商户ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@Length(min=0, max=64, message="商户名称长度必须介于 0 和 64 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	@Length(min=0, max=64, message="店铺名称长度必须介于 0 和 64 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Length(min=0, max=64, message="订单号长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Length(min=0, max=16, message="订单类型长度必须介于 0 和 16 之间")
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Length(min=0, max=32, message="用户请求IP长度必须介于 0 和 32 之间")
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Length(min=0, max=32, message="支付流水号长度必须介于 0 和 32 之间")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Length(min=0, max=32, message="主商户编号长度必须介于 0 和 32 之间")
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Length(min=0, max=32, message="授权码长度必须介于 0 和 32 之间")
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Length(min=0, max=32, message="支付渠道长度必须介于 0 和 32 之间")
	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	@Length(min=0, max=32, message="支付方式长度必须介于 0 和 32 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Length(min=0, max=1, message="是否支持信用卡长度必须介于 0 和 1 之间")
	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	@Length(min=0, max=32, message="返回码长度必须介于 0 和 32 之间")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	@Length(min=0, max=1000, message="返回码信息描述长度必须介于 0 和 64 之间")
	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRespTime() {
		return respTime;
	}

	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}

	@Length(min=0, max=500, message="商户订单二维码长度必须介于 0 和 500 之间")
	public String getOrderQrcode() {
		return orderQrcode;
	}

	public void setOrderQrcode(String orderQrcode) {
		this.orderQrcode = orderQrcode;
	}

	@Length(min=0, max=32, message="用户标示openid长度必须介于 0 和 32 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Length(min=0, max=32, message="微信公众帐号ID长度必须介于 0 和 32 之间")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Length(min=0, max=64, message="预支付订单号长度必须介于 0 和 64 之间")
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	@Length(min=0, max=500, message="签名长度必须介于 0 和 500 之间")
	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	@Length(min=0, max=64, message="时间戳长度必须介于 0 和 64 之间")
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Length(min=0, max=64, message="随机字符串长度必须介于 0 和 64 之间")
	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	@Length(min=0, max=5, message="支付状态长度必须介于 0 和 5 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Date getBeginOrderTime() {
		return beginOrderTime;
	}

	public void setBeginOrderTime(Date beginOrderTime) {
		this.beginOrderTime = beginOrderTime;
	}

	public Date getEndOrderTime() {
		return endOrderTime;
	}

	public void setEndOrderTime(Date endOrderTime) {
		this.endOrderTime = endOrderTime;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}


	public OrderPayParamsExt fromOrderInfo(){

		if(StringUtils.isNotEmpty(this.orderInfo) && this.orderInfo.contains("&quot;")){
			this.orderInfo= HtmlUtils.htmlUnescape(this.orderInfo);
		}
		return JacksonUtils.readValue(this.orderInfo,OrderPayParamsExt.class);
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getJsAppId() {
		return jsAppId;
	}

	public void setJsAppId(String jsAppId) {
		this.jsAppId = jsAppId;
	}

	public String getPartnerAppName() {
		return partnerAppName;
	}

	public void setPartnerAppName(String partnerAppName) {
		this.partnerAppName = partnerAppName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPartnerNum() {
		return partnerNum;
	}

	public void setPartnerNum(String partnerNum) {
		this.partnerNum = partnerNum;
	}

	public String getPartnerNotifyUrl() {
		return partnerNotifyUrl;
	}

	public void setPartnerNotifyUrl(String partnerNotifyUrl) {
		this.partnerNotifyUrl = partnerNotifyUrl;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getBankChannel() {
		return bankChannel;
	}

	public void setBankChannel(String bankChannel) {
		this.bankChannel = bankChannel;
	}
}