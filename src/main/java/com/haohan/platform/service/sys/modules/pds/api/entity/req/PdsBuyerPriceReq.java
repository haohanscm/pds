package com.haohan.platform.service.sys.modules.pds.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zgw on 2018/12/8.
 */
public class PdsBuyerPriceReq implements Serializable {

    private Date startDate;//取最大值
    private Date endDate;//取最小值
    @NotNull(message = "queryDate不能为空")
    private Date queryDate;     //查询日期

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    private String shopId;
    private String buyerId;//采购商ID
    private String buyerMerchantId;//采购商商家ID

    private String targetBuyerId;//需要复制的目标采购商ID
    private BigDecimal rate; // 上浮比例
    private String destBuyerId; //copy目标buyerId

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getTargetBuyerId() {
        return targetBuyerId;
    }

    public void setTargetBuyerId(String targetBuyerId) {
        this.targetBuyerId = targetBuyerId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getDestBuyerId() {
        return destBuyerId;
    }

    public void setDestBuyerId(String destBuyerId) {
        this.destBuyerId = destBuyerId;
    }

    public String getBuyerMerchantId() {
        return buyerMerchantId;
    }

    public void setBuyerMerchantId(String buyerMerchantId) {
        this.buyerMerchantId = buyerMerchantId;
    }
}
