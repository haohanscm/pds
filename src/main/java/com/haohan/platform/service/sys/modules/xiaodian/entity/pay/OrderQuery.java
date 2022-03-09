package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 交易状态查询Entity
 * @author haohan
 * @version 2017-12-12
 */
public class OrderQuery extends DataEntity<OrderQuery> {

	private static final long serialVersionUID = 1L;
	private String merchantId;   //商户ID
	private String partnerId;		// 商户编号
	private String requestId;		// 流水号
	private String orderId;		// 订单号
	private String transType;		// 交易类型
	private String respCode;		// 返回状态码
	private String respMsg;		// 返回描述
	private Date orderTime;		// 下单时间
	private String payAmount;		// 支付金额
	private Date payTime;		// 支付时间
	private String thirdOrdNo;		// 第三方平台订单号
	private String payResult;		// 支付结果
	private Date beginOrderTime;		// 开始 下单时间
	private Date endOrderTime;		// 结束 下单时间
	
	public OrderQuery() {
		super();
	}

	public OrderQuery(String id){
		super(id);
	}
	@Length(min=0, max=32, message="主商户ID长度必须介于 0 和 32 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@Length(min=0, max=32, message="主商户编号长度必须介于 0 和 32 之间")
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	@Length(min=0, max=32, message="流水号长度必须介于 0 和 32 之间")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Length(min=0, max=32, message="订单号长度必须介于 0 和 32 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=5, message="交易类型长度必须介于 0 和 5 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=32, message="返回状态码长度必须介于 0 和 32 之间")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	@Length(min=0, max=64, message="返回描述长度必须介于 0 和 64 之间")
	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=0, max=32, message="第三方平台订单号长度必须介于 0 和 32 之间")
	public String getThirdOrdNo() {
		return thirdOrdNo;
	}

	public void setThirdOrdNo(String thirdOrdNo) {
		this.thirdOrdNo = thirdOrdNo;
	}
	
	@Length(min=0, max=32, message="支付结果长度必须介于 0 和 32 之间")
	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
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
		
}