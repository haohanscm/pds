package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 订单撤销Entity
 * @author haohan
 * @version 2017-12-30
 */
public class OrderCancel extends DataEntity<OrderCancel> {
	
	private static final long serialVersionUID = 1L;
	private String requestId;		// 流水号
	private String merchantId;		// 商家ID
	private String merchantName;		// 商家名称
	private String partnerId;		// 商户账号
	private String orderId;		// 商户订单号
	private Date reTime;		// 撤销时间
	private String respCode;		// 返回码
	private String respDesc;		// 返回信息描述
	private Date respTime;		// 返回时间
	private Date beginReTime;		// 开始 撤销时间
	private Date endReTime;		// 结束 撤销时间
	private String orgReqId;   //原商户订单流水号
	private String orgTransId;  //平台交易流水号

	private String status;		//状态

	
	public OrderCancel() {
		super();
	}

	public OrderCancel(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流水号长度必须介于 0 和 64 之间")
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商家名称长度必须介于 0 和 64 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=64, message="商户账号长度必须介于 0 和 64 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReTime() {
		return reTime;
	}

	public void setReTime(Date reTime) {
		this.reTime = reTime;
	}
	
	@Length(min=0, max=64, message="返回码长度必须介于 0 和 64 之间")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	@Length(min=0, max=100, message="返回信息描述长度必须介于 0 和 100 之间")
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
	
	public Date getBeginReTime() {
		return beginReTime;
	}

	public void setBeginReTime(Date beginReTime) {
		this.beginReTime = beginReTime;
	}
	
	public Date getEndReTime() {
		return endReTime;
	}

	public void setEndReTime(Date endReTime) {
		this.endReTime = endReTime;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}