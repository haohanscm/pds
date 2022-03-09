package com.haohan.platform.service.sys.modules.pds.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dy
 * @date 2019/9/10
 * 上次采购价及时间
 */
public class LastDealDTO {

    /**
     * 上次采购价
     */
    private BigDecimal lastPrice;
    /**
     * 上次采购配送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastDate;

    public LastDealDTO(BuyOrderDetail buyOrderDetail) {
        if (null != buyOrderDetail) {
            this.lastPrice = buyOrderDetail.getLastPrice();
            this.lastDate = buyOrderDetail.getLastDate();
        }
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
    public String toString() {
        return "LastPurchaseDTO{" +
                "lastPrice=" + lastPrice +
                ", lastPurchaseDate=" + lastDate +
                '}';
    }
}
