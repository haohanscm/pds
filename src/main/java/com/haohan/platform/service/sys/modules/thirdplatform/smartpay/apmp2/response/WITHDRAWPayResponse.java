package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

public class WITHDRAWPayResponse extends BaseResponse {
	private String reqId;
	private String transId;
	private String bankMchtId;
	private String withdrawResult;
	private String resultMsg;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getBankMchtId() {
		return bankMchtId;
	}

	public void setBankMchtId(String bankMchtId) {
		this.bankMchtId = bankMchtId;
	}

	public String getWithdrawResult() {
		return withdrawResult;
	}

	public void setWithdrawResult(String withdrawResult) {
		this.withdrawResult = withdrawResult;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
