package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class WechatGzhRequest extends BaseRequest {

//	private String reqTime;
	private String appId;
	private String uuid;
//	private String orderId;
//	private String reqId;
//	private String orderSubject;
//	private String orderDesc;
//	private String totalAmount;
//	private String transTimeOut;
//	private String bankCardLimit;
//	private String currency;
//	private String notifyUrl;
//	private String acquirerType;
	private String custId;
//	private String deviceId;
//	private String operatorId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
}
