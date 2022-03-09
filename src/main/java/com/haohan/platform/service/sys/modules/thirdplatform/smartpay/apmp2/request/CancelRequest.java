package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class CancelRequest extends BaseRequest {
	private String orgReqId;
	private String orgTransId;
	private String custId;


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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}




}
