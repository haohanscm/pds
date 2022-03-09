package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsApiStatisCurveResp implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyDate;
    private BigDecimal amount;
    private Integer orderNum;

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
