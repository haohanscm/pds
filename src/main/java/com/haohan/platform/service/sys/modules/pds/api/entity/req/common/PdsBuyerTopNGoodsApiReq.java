package com.haohan.platform.service.sys.modules.pds.api.entity.req.common;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/13
 */
public class PdsBuyerTopNGoodsApiReq extends PdsBaseApiReq implements Serializable {
    @NotBlank(message = "采购商ID不能为空")
    private String buyerId;
    private int limitNum;
    private Date queryDate;
    /**
     * 商品上下架状态 yes_no
     */
    private String queryStatus;
    private String buyerMerchantId;

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    public String getBuyerMerchantId() {
        return buyerMerchantId;
    }

    public void setBuyerMerchantId(String buyerMerchantId) {
        this.buyerMerchantId = buyerMerchantId;
    }
}
