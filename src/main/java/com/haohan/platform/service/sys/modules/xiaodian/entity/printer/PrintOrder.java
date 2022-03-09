package com.haohan.platform.service.sys.modules.xiaodian.entity.printer;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 飞鹅打印机 打印订单
 */
public class PrintOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;		// 小票头名称
	private List<PrintProduct> productList;		// 需打印产品
	private String orderId;		// 订单号
	private Date orderTime;		// 订单提交时间
	private BigDecimal totalAmount;		// 合计金额
	private BigDecimal discountAmount;		// 优惠金额
	private BigDecimal payAmount;		// 应付金额
	private String remarks;  // 备注信息

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PrintProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<PrintProduct> productList) {
        this.productList = productList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}