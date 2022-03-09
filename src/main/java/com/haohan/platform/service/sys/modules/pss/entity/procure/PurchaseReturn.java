package com.haohan.platform.service.sys.modules.pss.entity.procure;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购退货Entity
 * @author haohan
 * @version 2018-09-05
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseReturn extends DataEntity<PurchaseReturn> {
	
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "merchantId不能为空")
	private String merchantId;		// 商家ID
	private String returnNum;		// 退货编号
	private Integer goodsNum;		// 商品数量
	private BigDecimal totalAmount;		// 总计金额
	private BigDecimal sumAmount;		// 合计金额
	private BigDecimal otherAmount;		// 其他费用
	private BigDecimal payAmount;		// 实付金额
	private String payType;		// 结账方式
	private String supplierId;		// 供应商ID
	private String operator;		// 操作员
	private Date bizTime;		// 业务时间
	private String returnStatus;		// 退货状态
	private String returnNote;		// 退货备注

	private List<PurchaseReturnDetail> detailList;
	private String supplierName;

	//辅助查询字段
    @JsonIgnore
	private Date beginTime;
    @JsonIgnore
	private Date endTime;
	
	public PurchaseReturn() {
		super();
	}

	public PurchaseReturn(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="退货编号长度必须介于 0 和 64 之间")
	public String getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(String returnNum) {
		this.returnNum = returnNum;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public BigDecimal getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Length(min=0, max=64, message="结账方式长度必须介于 0 和 64 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=64, message="供应商ID长度必须介于 0 和 64 之间")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBizTime() {
		return bizTime;
	}

	public void setBizTime(Date bizTime) {
		this.bizTime = bizTime;
	}
	
	@Length(min=0, max=5, message="退货状态长度必须介于 0 和 5 之间")
	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	
	@Length(min=0, max=200, message="退货备注长度必须介于 0 和 200 之间")
	public String getReturnNote() {
		return returnNote;
	}

	public void setReturnNote(String returnNote) {
		this.returnNote = returnNote;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal fetchTotalAmount(){
        BigDecimal sumAmount = null == this.sumAmount ? BigDecimal.ZERO : this.getSumAmount();
        BigDecimal otherAmount = null == this.otherAmount ? BigDecimal.ZERO : this.getOtherAmount();
        return sumAmount.add(otherAmount);
    }

	public List<PurchaseReturnDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseReturnDetail> detailList) {
		this.detailList = detailList;
	}
}