package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.listener;

import java.util.Date;

/**
 * Created by james on 2017/7/17 0017.
 */
public class Record {
	private int id;
	private String record;
	private String sign;
	private String body;
	private Date visitDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
