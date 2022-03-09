package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

public class WechatAppResponse extends BaseResponse {

	private String transId;
	private String reqId;
	private String payInfo;

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

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

}
