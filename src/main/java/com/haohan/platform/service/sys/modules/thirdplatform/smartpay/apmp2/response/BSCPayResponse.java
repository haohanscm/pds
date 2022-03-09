package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

public class BSCPayResponse extends BaseResponse {

	private String orderId;
	private String transId;
	private String reqId;
	private String totalAmount;
	private String transAmount;
	private String transTime;
	private String currency;
	private String transResult;
	private String resultMsg;
	private String acquirerType;
	private String walletTransId;
	private String walletOrderId;
	private String bankType;
	private String buyerId;
	private String uuid;

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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTransResult() {
		return transResult;
	}

	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getAcquirerType() {
		return acquirerType;
	}

	public void setAcquirerType(String acquirerType) {
		this.acquirerType = acquirerType;
	}

	public String getWalletTransId() {
		return walletTransId;
	}

	public void setWalletTransId(String walletTransId) {
		this.walletTransId = walletTransId;
	}

	public String getWalletOrderId() {
		return walletOrderId;
	}

	public void setWalletOrderId(String walletOrderId) {
		this.walletOrderId = walletOrderId;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
