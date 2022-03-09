package com.haohan.platform.service.sys.modules.pds.entity.cost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商货款统计Entity
 *
 * @author haohan
 * @version 2018-11-06
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierPayment extends DataEntity<SupplierPayment> {

    private static final long serialVersionUID = 1L;
    private String supplierPaymentId; // 供应商货款编号
    private String askOrderId;        // 询价单号
    private String supplierId;        // 供应商
    private String pmId;        // 平台商家ID
    private Date supplyDate;        // 供应日期
    private String goodsNum;        // 商品数量
    private BigDecimal supplierPayment;        // 供应货款
    private BigDecimal afterSalePayment;        // 售后货款
    private String status;        // 状态
    private Date beginSupplyDate;        // 开始 供应日期
    private Date endSupplyDate;        // 结束 供应日期
    private String serviceId;  // 售后单编号列表

    private String supplierName;        // 供应商名称
    private String finalStatus; // 修改后状态
    private String merchantId; // 商家
    private String merchantName;

    private String pmName;        // 平台商家名称

    public SupplierPayment() {
        super();
    }

    public SupplierPayment(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "询价单号长度必须介于 0 和 64 之间")
    public String getAskOrderId() {
        return askOrderId;
    }

    public void setAskOrderId(String askOrderId) {
        this.askOrderId = askOrderId;
    }

    @Length(min = 0, max = 64, message = "供应商长度必须介于 0 和 64 之间")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Length(min = 0, max = 64, message = "平台商家ID长度必须介于 0 和 64 之间")
    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    @Length(min = 0, max = 64, message = "商品数量长度必须介于 0 和 64 之间")
    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getSupplierPayment() {
        return supplierPayment;
    }

    public void setSupplierPayment(BigDecimal supplierPayment) {
        this.supplierPayment = supplierPayment;
    }

    public BigDecimal getAfterSalePayment() {
        return afterSalePayment;
    }

    public void setAfterSalePayment(BigDecimal afterSalePayment) {
        this.afterSalePayment = afterSalePayment;
    }

    @Length(min = 0, max = 2, message = "状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBeginSupplyDate() {
        return beginSupplyDate;
    }

    public void setBeginSupplyDate(Date beginSupplyDate) {
        this.beginSupplyDate = beginSupplyDate;
    }

    public Date getEndSupplyDate() {
        return endSupplyDate;
    }

    public void setEndSupplyDate(Date endSupplyDate) {
        this.endSupplyDate = endSupplyDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSupplierPaymentId() {
        return supplierPaymentId;
    }

    public void setSupplierPaymentId(String supplierPaymentId) {
        this.supplierPaymentId = supplierPaymentId;
    }
}