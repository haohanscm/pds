package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class RefundRequest extends BaseRequest {

	private String refundOrderId;
	private String custId;
	private String orgReqId;
	private String orgTransId;

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}


	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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
}
