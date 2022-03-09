package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request;

public class BillDownLoadRequest extends BaseRequest {

	private String coopId;
	private String billDate;
	public String getCoopId() {
		return coopId;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setCoopId(String coopId) {
		this.coopId = coopId;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	
}
