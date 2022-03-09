package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款管理Entity
 * @author haohan
 * @version 2017-12-20
 */
public class RefundManage extends DataEntity<RefundManage> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String merchantName;	//商家名称
	private String requestId;		// 流水号
	private String partnerId;		// 商户编号
	private String orderId;		// 商户订单号
	private String orgReqId;	//原商户交易流水号
	private String orgTransId;	//原平台交易流水号
	private BigDecimal orderAmount;		// 订单金额
	private BigDecimal payAmount;		// 支付金额
	private BigDecimal refundAmount;		// 退款金额
	private String busType;		// 业务类型
	private Date refundApplyTime;		// 申请退款时间
	private String status;		// 退款结果
	private String respCode;		// 返回码
	private String respDesc;		// 返回信息描述
	private String tradeNo;		// 退款平台流水号
	private Date respTime;		// 退款返回时间
//	private String transId; //平台交易ID

	private String refundCause;		//退款原因


	public String getOrgReqId() {
		return orgReqId;
	}

	public void setOrgReqId(String orgReqId) {
		this.orgReqId = orgReqId;
	}

	public String getOrgTransId() {
		return orgTransId;
	}

	public void setOrgTransId(String orgTransId) {
		this.orgTransId = orgTransId;
	}

	public RefundManage() {
		super();
	}

	public RefundManage(String id){
		super(id);
	}

	@Length(min=0, max=32, message="商家ID长度必须介于 0 和 32 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="流水号长度必须介于 0 和 64 之间")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Length(min=0, max=64, message="商户编号长度必须介于 0 和 64 之间")
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	@Length(min=0, max=32, message="业务类型长度必须介于 0 和 32 之间")
	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRefundApplyTime() {
		return refundApplyTime;
	}

	public void setRefundApplyTime(Date refundApplyTime) {
		this.refundApplyTime = refundApplyTime;
	}
	
	@Length(min=0, max=32, message="退款结果长度必须介于 0 和 32 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="返回码长度必须介于 0 和 32 之间")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	@Length(min=0, max=128, message="返回信息描述长度必须介于 0 和 128 之间")
	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	@Length(min=0, max=32, message="退款平台流水号长度必须介于 0 和 32 之间")
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRespTime() {
		return respTime;
	}

	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}

//	public String getTransId() {
//		return transId;
//	}
//
//	public void setTransId(String transId) {
//		this.transId = transId;
//	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getRefundCause() {
		return refundCause;
	}

	public void setRefundCause(String refundCause) {
		this.refundCause = refundCause;
	}
}