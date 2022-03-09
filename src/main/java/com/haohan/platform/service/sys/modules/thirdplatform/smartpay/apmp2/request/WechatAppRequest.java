package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class WechatAppRequest extends BaseRequest {

	private String reqTime;
	private String appId;
	private String orderId;
	private String reqId;
	private String orderSubject;
	private String orderDesc;
	private String totalAmount;
	private String transTimeOut;
	private String bankCardLimit;
	private String currency;
	private String notifyUrl;
	private String acquirerType;
	private String custId;
	private String deviceId;
	private String operatorId;

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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

	public String getBankCardLimit() {
		return bankCardLimit;
	}

	public void setBankCardLimit(String bankCardLimit) {
		this.bankCardLimit = bankCardLimit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

}
