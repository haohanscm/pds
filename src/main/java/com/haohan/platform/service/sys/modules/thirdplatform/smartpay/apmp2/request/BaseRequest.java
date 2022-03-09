package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class BaseRequest {

	private String action;
	private String version;

	private String merBnkId;
	private String reqTime;
	private String orderId;
	private String reqId;
	private String orderSubject;
	private String orderDesc;
	private String totalAmount;
	private String transTimeOut;
	private String currency;
	private String bankCardLimit;
	private String notifyUrl;
	private String acquirerType;
	private String operatorId;
	private String deviceId;


	public String getMerBnkId() {
		return merBnkId;
	}

	public void setMerBnkId(String merBnkId) {
		this.merBnkId = merBnkId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getAcquirerType() {
		return acquirerType;
	}

	public void setAcquirerType(String acquirerType) {
		this.acquirerType = acquirerType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getOrderSubject() {
		return orderSubject;
	}

	public void setOrderSubject(String orderSubject) {
		this.orderSubject = orderSubject;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTransTimeOut() {
		return transTimeOut;
	}

	public void setTransTimeOut(String transTimeOut) {
		this.transTimeOut = transTimeOut;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBankCardLimit() {
		return bankCardLimit;
	}

	public void setBankCardLimit(String bankCardLimit) {
		this.bankCardLimit = bankCardLimit;
	}


	public String getAction() {
		return action;
	}

	public String getVersion() {
		return version == null ? "2.0" : version;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
