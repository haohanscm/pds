package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/11/16
 */
public class ApiProcurementReq {
    private Integer pageNo;
    private Integer pageSize;

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

    //Join字段
    private String warehouseName;	//仓库名称
    private String supplierName;	//供应商名称

    //查询字段
    private Date beginTime;		//开始 查询时间
    private Date endTime;		//结束 查询时间
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProcurementDetail> detailList;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProcureNum() {
        return procureNum;
    }

    public void setProcureNum(String procureNum) {
        this.procureNum = procureNum;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

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

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public List<ProcurementDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ProcurementDetail> detailList) {
        this.detailList = detailList;
    }
}
