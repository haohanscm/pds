package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 交易匹配单Entity
 *
 * @author haohan
 * @version 2018-10-17
 */
public class TradeMatch extends DataEntity<TradeMatch> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String askOrderId;        // 询价单号
    private String offerOrderId;        // 报价单号
    private String supplierId;        // 供应商
    private BigDecimal supplyNum;        // 供应数量
    private String offerType;        // 报价类型
    private BigDecimal offerPrice;        // 报价
    private BigDecimal dealPrice;        // 成交价
    private BigDecimal buyNum;        // 采购数量
    private String status;        // 状态
    private String respDesc;    //匹配结果描述

    //查询
    private String supplierName;    //供应商名称
    private String pmName;        // 平台商家名称

    public TradeMatch() {
        super();
    }

    public TradeMatch(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "询价单号长度必须介于 0 和 64 之间")
    public String getAskOrderId() {
        return askOrderId;
    }

    public void setAskOrderId(String askOrderId) {
        this.askOrderId = askOrderId;
    }

    @Length(min = 0, max = 64, message = "报价单号长度必须介于 0 和 64 之间")
    public String getOfferOrderId() {
        return offerOrderId;
    }

    public void setOfferOrderId(String offerOrderId) {
        this.offerOrderId = offerOrderId;
    }

    @Length(min = 0, max = 64, message = "供应商长度必须介于 0 和 64 之间")
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

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(BigDecimal buyNum) {
        this.buyNum = buyNum;
    }

    @Length(min = 0, max = 5, message = "报价类型长度必须介于 0 和 5 之间")
    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }


    @Length(min = 0, max = 2, message = "状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
}