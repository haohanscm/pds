package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class CSBPayRequest extends BaseRequest {

//	private String reqTime;
//	private String orderId;
//	private String reqId;
//	private String orderSubject;
//	private String orderDesc;
//	private String totalAmount;
//	private String transTimeOut;
//	private String currency;
//	private String bankCardLimit;
//	private String notifyUrl;
//	private String acquirerType;
	private String custId;
//	private String deviceId;
//	private String operatorId;


	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
}
