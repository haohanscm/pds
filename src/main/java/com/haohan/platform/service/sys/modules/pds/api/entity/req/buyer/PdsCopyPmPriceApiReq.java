package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsCopyPmPriceApiReq extends PdsBaseApiReq {
    @NotBlank(message = "目标采购商不能为空")
    private String destBuyerId;
    @NotBlank(message = "buyerId不能为空")
    private String buyerId;
    private Date queryDate;
    private BigDecimal rate;

    public String getDestBuyerId() {
        return destBuyerId;
    }

    public void setDestBuyerId(String destBuyerId) {
        this.destBuyerId = destBuyerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
