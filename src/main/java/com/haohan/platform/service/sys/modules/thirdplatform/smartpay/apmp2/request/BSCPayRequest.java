package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class BSCPayRequest extends BaseRequest {

//	private String reqTime;
//	private String orderId;
//	private String reqId;
//	private String orderSubject;
//	private String orderDesc;
//	private String totalAmount;
//	private String transTimeOut;
//	private String currency;
//	private String bankCardLimit;
	private String walletAuthCode;
//	private String acquirerType;
	private String custId;

//	private String operatorId;


	public String getWalletAuthCode() {
		return walletAuthCode;
	}

	public void setWalletAuthCode(String walletAuthCode) {
		this.walletAuthCode = walletAuthCode;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

}
