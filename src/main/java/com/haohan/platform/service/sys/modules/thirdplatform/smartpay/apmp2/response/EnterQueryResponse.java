package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

public class EnterQueryResponse extends BaseResponse {

	private String coopMchtId;
	private String custId;
	private String mchtName;
	private String mchtShortName;
	private String acquirerType;
	private String mchtAddr;
	private String mchtPhone;
	private String contactName;
	private String contactMobile;
	private String contactEmail;
	private String category;
	private String remark;
	private String checkStatus;
	private String checkMessag;

	public String getCheckMessag() {
		return checkMessag;
	}

	public void setCheckMessag(String checkMessag) {
		this.checkMessag = checkMessag;
	}

	public String getCoopMchtId() {
		return coopMchtId;
	}

	public void setCoopMchtId(String coopMchtId) {
		this.coopMchtId = coopMchtId;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getMchtName() {
		return mchtName;
	}

	public void setMchtName(String mchtName) {
		this.mchtName = mchtName;
	}

	public String getMchtShortName() {
		return mchtShortName;
	}

	public void setMchtShortName(String mchtShortName) {
		this.mchtShortName = mchtShortName;
	}

	public String getAcquirerType() {
		return acquirerType;
	}

	public void setAcquirerType(String acquirerType) {
		this.acquirerType = acquirerType;
	}

	public String getMchtAddr() {
		return mchtAddr;
	}

	public void setMchtAddr(String mchtAddr) {
		this.mchtAddr = mchtAddr;
	}

	public String getMchtPhone() {
		return mchtPhone;
	}

	public void setMchtPhone(String mchtPhone) {
		this.mchtPhone = mchtPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
