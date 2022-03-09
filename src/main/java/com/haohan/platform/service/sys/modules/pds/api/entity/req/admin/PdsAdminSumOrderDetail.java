package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/10/29
 */
public class PdsAdminSumOrderDetail {
    private String buyerId;
    private String goodsId;
    private String detailId;
    private String buyOrderId;
    private String buyerName;
    private BigDecimal goodsNum;
    private BigDecimal sortOutNum;
    private BigDecimal buyPrice;
    private BigDecimal marketPrice;
    private String detailStatus;
    /**
     * 上次采购价
     */
    private BigDecimal lastPrice;
    /**
     * 上次采购时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastDate;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(String buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public BigDecimal getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(BigDecimal goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getSortOutNum() {
        return sortOutNum;
    }

    public void setSortOutNum(BigDecimal sortOutNum) {
        this.sortOutNum = sortOutNum;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(String detailStatus) {
        this.detailStatus = detailStatus;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        final PdsAdminSumOrderDetail detail = (PdsAdminSumOrderDetail) obj;
        if (this == detail) {
            return true;
        } else {
            if (null == this.detailId) {
                return false;
            }
            return this.detailId.equals(detail.getDetailId());
        }
    }

    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13 * hashno + (detailId == null ? 0 : detailId.hashCode());
        return hashno;
    }
}
