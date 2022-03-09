package com.haohan.platform.service.sys.modules.xiaodian.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayParamsExt;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.util.HtmlUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单Entity
 * @author haohan
 * @version 2017-12-12
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsOrder extends DataEntity<GoodsOrder> {
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户ID
	private String merchantName;		// 商户名称
	private String partnerNum;            //渠道编号
	private String orderId;		// 订单编号
	private String payId;		// 支付流水号
	private String shopId;		// 店铺ID
	private String shopName;		// 店铺名称
	private String orderStatus;		// 订单状态
	private String orderFrom;		// 订单来源
	private String orderType;	//订单类型
	private String orderDesc;		//订单描述
	private String orderMarks;		// 订单备注
	private String uid;		// 用户ID
	private String userName;		// 用户名称
	private String shippingId;		// 配送ID
	private BigDecimal insureFee;		// 保价费用
	private BigDecimal shippingFee;		// 配送费用
	private String shippingOntime;		// 预约配送时间
	private Date shippingTime;		// 发货时间
	private String payStatus;		// 支付状态
	private Date payTime;		// 支付时间
	private String payType;     //支付类型
	private String orderInfo;
	private BigDecimal orderAmount;		//订单金额
	private Date orderTime;			//下单时间
	private String appid;		//微信appid
	private String addressId;			//地址id
	private String deliveryType;		//配送方式


	//查询补充字段
	private Date startTime;		//查询开始时间
	private Date endTime;		//查询结束时间
	private String partnerAppName;      //渠道名称


	public GoodsOrder() {
		super();
	}

	public GoodsOrder(String id){
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

	@Length(min=0, max=64, message="订单编号长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Length(min=0, max=64, message="支付流水号长度必须介于 0 和 64 之间")
	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	@Length(min=0, max=100, message="店铺名称长度必须介于 0 和 100 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	@Length(min=0, max=64, message="用户ID长度必须介于 0 和 64 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Length(min=0, max=50, message="用户名称长度必须介于 0 和 50 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Length(min=0, max=64, message="配送ID长度必须介于 0 和 64 之间")
	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public BigDecimal getInsureFee() {
		return insureFee;
	}

	public void setInsureFee(BigDecimal insureFee) {
		this.insureFee = insureFee;
	}

	public BigDecimal getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}

	@Length(min=0, max=100, message="预约配送时间长度必须介于 0 和 100 之间")
	public String getShippingOntime() {
		return shippingOntime;
	}

	public void setShippingOntime(String shippingOntime) {
		this.shippingOntime = shippingOntime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(Date shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPartnerNum() {
		return partnerNum;
	}

	public void setPartnerNum(String partnerNum) {
		this.partnerNum = partnerNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public String getPartnerAppName() {
		return partnerAppName;
	}

	public void setPartnerAppName(String partnerAppName) {
		this.partnerAppName = partnerAppName;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderMarks() {
		return orderMarks;
	}

	public void setOrderMarks(String orderMarks) {
		this.orderMarks = orderMarks;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public OrderPayParamsExt fromOrderInfo(){
		if(StringUtils.isNotEmpty(this.orderInfo) && this.orderInfo.contains("&quot;")){
			this.orderInfo= HtmlUtils.htmlUnescape(this.orderInfo);
		}
		return JacksonUtils.readValue(this.orderInfo,OrderPayParamsExt.class);
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
}