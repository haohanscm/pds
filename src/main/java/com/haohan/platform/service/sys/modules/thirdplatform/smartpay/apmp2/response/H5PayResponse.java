package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

/**
 * Created by james on 2017/7/17 0017.
 */
public class H5PayResponse extends BaseResponse {

	private String orderId;
	private String transId;
	private String reqId;
	private String mwUrl;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getMwUrl() {
		return mwUrl;
	}

	public void setMwUrl(String mwUrl) {
		this.mwUrl = mwUrl;
	}

}
