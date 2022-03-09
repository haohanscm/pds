package com.haohan.platform.service.sys.modules.pds.entity.cost;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购商货款统计Entity
 *
 * @author haohan
 * @version 2018-10-20
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyerPayment extends DataEntity<BuyerPayment> {

    private static final long serialVersionUID = 1L;
    private String pmId;        //平台商家ID
    private String buyerPaymentId;  // 采购商货款编号
    private String buyId;        // 采购编号
    private String buyerId;        // 采购商
    private Date buyDate;        // 原采购日期  修改为采购单的送货日期
    private String goodsNum;        // 商品数量
    private BigDecimal buyerPayment;        // 采购货款
    private BigDecimal afterSalePayment;        // 售后货款
    private String status;  // 状态:已结/未结
    private BigDecimal shipFee;        //运费
    private String serviceId;  // 售后单编号列表
    private String billType;  // 账单类型: 1订单应收 2退款应收

    private Date beginBuyDate;        // 开始 采购日期
    private Date endBuyDate;        // 结束 采购日期

    private String buyerName;  // 采购商名称
    private String finalStatus; // 修改后状态

    private String merchantId; // 商家
    private String merchantName;

    private String pmName;        // 平台商家名称

    public BuyerPayment() {
        super();
    }

    public BuyerPayment(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "采购编号长度必须介于 0 和 64 之间")
    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    @Length(min = 0, max = 64, message = "采购商长度必须介于 0 和 64 之间")
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    @Length(min = 0, max = 64, message = "商品数量长度必须介于 0 和 64 之间")
    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
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

    public Date getBeginBuyDate() {
        return beginBuyDate;
    }

    public void setBeginBuyDate(Date beginBuyDate) {
        this.beginBuyDate = beginBuyDate;
    }

    public Date getEndBuyDate() {
        return endBuyDate;
    }

    public void setEndBuyDate(Date endBuyDate) {
        this.endBuyDate = endBuyDate;
    }

    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
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

    @Length(min = 0, max = 128, message = "售后单编号必须介于 0 和 128 之间")
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Length(min = 0, max = 64, message = "采购商货款编号长度必须介于 0 和 64 之间")
    public String getBuyerPaymentId() {
        return buyerPaymentId;
    }

    public void setBuyerPaymentId(String buyerPaymentId) {
        this.buyerPaymentId = buyerPaymentId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}