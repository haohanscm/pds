package com.haohan.platform.service.sys.modules.pds.entity.cost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 成本核算单Entity
 *
 * @author haohan
 * @version 2018-12-03
 */
public class CostOrder extends DataEntity<CostOrder> {

    private static final long serialVersionUID = 1L;
    private Date dealDate;        // 交易日
    private String pmId;        // 平台商家ID
    private String totalOrderNum;        // 总订单数
    private String spuNum;        // 商品SPU数
    private String skuNum;        // 商品SKU数
    private BigDecimal supplierPayment;        // 供应商货款
    private BigDecimal buyerPayment;        // 采购商货款
    private BigDecimal afterSalePayment;        // 售后货款
    private BigDecimal costTotal;        // 成本合计
    private BigDecimal grossProfit;        // 毛利润
    private String grossRate;        // 利润率

    public CostOrder() {
        super();
    }

    public CostOrder(String id) {
        super(id);
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    @Length(min = 0, max = 255, message = "平台商家ID长度必须介于 0 和 255 之间")
    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    @Length(min = 0, max = 64, message = "总订单数长度必须介于 0 和 64 之间")
    public String getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(String totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    @Length(min = 0, max = 64, message = "商品SPU数长度必须介于 0 和 64 之间")
    public String getSpuNum() {
        return spuNum;
    }

    public void setSpuNum(String spuNum) {
        this.spuNum = spuNum;
    }

    @Length(min = 0, max = 64, message = "商品SKU数长度必须介于 0 和 64 之间")
    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public BigDecimal getSupplierPayment() {
        return supplierPayment;
    }

    public void setSupplierPayment(BigDecimal supplierPayment) {
        this.supplierPayment = supplierPayment;
    }

    public BigDecimal getBuyerPayment() {
        return buyerPayment;
    }

    public void setBuyerPayment(BigDecimal buyerPayment) {
        this.buyerPayment = buyerPayment;
    }

    public BigDecimal getAfterSalePayment() {
        return afterSalePayment;
    }

    public void setAfterSalePayment(BigDecimal afterSalePayment) {
        this.afterSalePayment = afterSalePayment;
    }

    public BigDecimal getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(BigDecimal costTotal) {
        this.costTotal = costTotal;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    @Length(min = 0, max = 64, message = "利润率长度必须介于 0 和 64 之间")
    public String getGrossRate() {
        return grossRate;
    }

    public void setGrossRate(String grossRate) {
        this.grossRate = grossRate;
    }

}