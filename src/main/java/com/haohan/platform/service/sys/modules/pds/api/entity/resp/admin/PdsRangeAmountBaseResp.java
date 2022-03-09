package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dy
 * @create 2018/12/26
 */
public class PdsRangeAmountBaseResp implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date; //货款日期
    private BigDecimal buyAmount; //采购金额
    private BigDecimal afterSaleAmount; //售后金额
    private BigDecimal payAmount; //总计金额
    private String status; // 结算状态
    private String buyerId;
    private String merchantId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    public BigDecimal getAfterSaleAmount() {
        return afterSaleAmount;
    }

    public void setAfterSaleAmount(BigDecimal afterSaleAmount) {
        this.afterSaleAmount = afterSaleAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
