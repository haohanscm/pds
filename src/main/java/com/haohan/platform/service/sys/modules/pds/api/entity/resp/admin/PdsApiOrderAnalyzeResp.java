package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/25
 */
public class PdsApiOrderAnalyzeResp implements Serializable {
    private BigDecimal orderAmount;
    private Integer orderNum;
    private BigDecimal shipFee;

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }


    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }
}
