package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class PayQueryRequest extends BaseRequest {

	private String refundOrderId;
	private String transId;
	private String walletOrderId;
	private String custId;

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getWalletOrderId() {
		return walletOrderId;
	}

	public void setWalletOrderId(String walletOrderId) {
		this.walletOrderId = walletOrderId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
}
