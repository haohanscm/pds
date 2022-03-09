package com.haohan.platform.service.sys.modules.pss.entity.procure;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品采购Entity
 * @author haohan
 * @version 2018-09-07
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Procurement extends DataEntity<Procurement> {
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "商家不能为空")
	private String merchantId;		// 商家ID
	private String procureNum;		// 采购编号
	private String supplierId;		// 供应商ID
	private String stockStatus;		// 入库状态
	private Integer num;		// 采购数量
	private BigDecimal totalAmount;		// 总计金额
	private BigDecimal payAmount;		// 实付金额
	private BigDecimal sumAmount;		// 合计金额
	private BigDecimal otherAmount;		// 其他费用
	private String payType;		// 结账方式
	private String bizNote;		// 业务备注
	private String operator;		// 操作员
	private Date opTime;		// 操作时间
	private String warehouseId;		//仓库/门店id
	private Date procureDate;		// 采购时间

	//Join字段
	private String warehouseName;	//仓库名称
	private String supplierName;	//供应商名称

	//查询字段
	private Date beginTime;		//开始 查询时间
	private Date endTime;		//结束 查询时间
	@Valid
	@NotEmpty(message = "采购商品列表不能为空")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<ProcurementDetail> detailList;
	
	public Procurement() {
		super();
	}

	public Procurement(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="采购编号长度必须介于 0 和 64 之间")
	public String getProcureNum() {
		return procureNum;
	}

	public void setProcureNum(String procureNum) {
		this.procureNum = procureNum;
	}
	
	@Length(min=0, max=64, message="供应商ID长度必须介于 0 和 64 之间")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@Length(min=0, max=5, message="入库状态长度必须介于 0 和 5 之间")
	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
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
	
	@Length(min=0, max=5, message="结账方式长度必须介于 0 和 5 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=200, message="业务备注长度必须介于 0 和 200 之间")
	public String getBizNote() {
		return bizNote;
	}

	public void setBizNote(String bizNote) {
		this.bizNote = bizNote;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<ProcurementDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ProcurementDetail> detailList) {
		this.detailList = detailList;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getProcureDate() {
        return procureDate;
    }

    public void setProcureDate(Date procureDate) {
        this.procureDate = procureDate;
    }

    public BigDecimal fetchTotalAmount(){
		BigDecimal sumAmount = null == this.sumAmount ? BigDecimal.ZERO : this.getSumAmount();
		BigDecimal otherAmount = null == this.otherAmount ? BigDecimal.ZERO : this.getOtherAmount();
		return sumAmount.add(otherAmount);
	}
}