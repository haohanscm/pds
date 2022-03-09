package com.haohan.platform.service.sys.modules.pds.api.entity.params;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/10/29
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsOfferOrderSaveParams {
    private String pmId;            //平台商家ID
    private String offerId;         //报价单主键
    private String offerOrderId;    //报价单编号
    private String askOrderId;  //询价单号即汇总单号
    private String supplierId;  //供应商ID
    private BigDecimal buyNum;     //平台采购数量
    private BigDecimal supplyNum;  //供应数量
    private Integer minSaleNum; //起售数量
    private BigDecimal supplyPrice; //供应商报价
    private String supplyDesc;  //描述
    private String supplierName;    //供应商名称
    private BigDecimal otherAmount; // 其他费用

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getAskOrderId() {
        return askOrderId;
    }

    public void setAskOrderId(String askOrderId) {
        this.askOrderId = askOrderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(BigDecimal supplyNum) {
        this.supplyNum = supplyNum;
    }

    public Integer getMinSaleNum() {
        return minSaleNum;
    }

    public void setMinSaleNum(Integer minSaleNum) {
        this.minSaleNum = minSaleNum;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public String getSupplyDesc() {
        return supplyDesc;
    }

    public void setSupplyDesc(String supplyDesc) {
        this.supplyDesc = supplyDesc;
    }

    public String getOfferOrderId() {
        return offerOrderId;
    }

    public void setOfferOrderId(String offerOrderId) {
        this.offerOrderId = offerOrderId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(BigDecimal buyNum) {
        this.buyNum = buyNum;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }
}
