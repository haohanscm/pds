package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付结果通知Entity
 * @author haohan
 * @version 2017-12-07
 */
public class PayNotify extends DataEntity<PayNotify> {
	
	private static final long serialVersionUID = 1L;
	private String requestId;		// 流水号
	private String orderId;		// 商户订单号
	private Date orderTime;		// 订单时间
	private BigDecimal payAmount;		// 支付金额
	private Date payTime;		// 支付时间
	private String acDate;		// 会计日期
	private BigDecimal fee;		// 费用
	private String result;		// 商户处理结果
	private String status;		// 支付结果
	private String respCode;		// 返回码
	private String respDesc;		// 返回码描述
	private Date beginOrderTime;		// 开始 订单时间
	private Date endOrderTime;		// 结束 订单时间
	private String transId; 		//交易ID

	private String isNotifyJsapp;	//订单是否同步即速应用

	public String getIsNotifyJsapp() {
		return isNotifyJsapp;
	}

	public void setIsNotifyJsapp(String isNotifyJsapp) {
		this.isNotifyJsapp = isNotifyJsapp;
	}

	public PayNotify() {
		super();
	}

	public PayNotify(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流水号长度必须介于 0 和 64 之间")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=0, max=32, message="会计日期长度必须介于 0 和 32 之间")
	public String getAcDate() {
		return acDate;
	}

	public void setAcDate(String acDate) {
		this.acDate = acDate;
	}
	
	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	@Length(min=0, max=64, message="商户处理结果长度必须介于 0 和 64 之间")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Length(min=0, max=32, message="支付结果长度必须介于 0 和 32 之间")
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
	
	@Length(min=0, max=64, message="返回码描述长度必须介于 0 和 64 之间")
	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
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

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}
}