package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class EnterQueryRequest extends BaseRequest {

	private String coopMchtId;
	private String custId;


	public String getCoopMchtId() {
		return coopMchtId;
	}

	public void setCoopMchtId(String coopMchtId) {
		this.coopMchtId = coopMchtId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

}
