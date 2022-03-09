package com.haohan.platform.service.sys.modules.xiaodian.entity.printer;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 飞鹅打印机 打印订单
 */
public class PrintProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;		// 产品名称
	private BigDecimal price;		// 单价
	private BigDecimal number;		// 数量
	private String unit;			//单位
	private BigDecimal amount;		// 金额
	private String opTag;			//操作标签
	private String remarks;  // 备注信息

	private String[] paramList;

	public PrintProduct() {
	}

	public PrintProduct(String[] paramList) {
		this.paramList = paramList;
	}

	public PrintProduct(String name, BigDecimal price, BigDecimal number, String unit) {
		this.name = name;
		this.price = price;
		this.number = number;
		this.unit = unit;
	}

	public PrintProduct(String name, BigDecimal price, BigDecimal number, String unit, String opTag) {
		this.name = name;
		this.price = price;
		this.number = number;
		this.unit = unit;
		this.opTag = opTag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOpTag() {
		return opTag;
	}

	public void setOpTag(String opTag) {
		this.opTag = opTag;
	}

	public String[] getParamList() {
		return paramList;
	}

	public void setParamList(String[] paramList) {
		this.paramList = paramList;
	}
}