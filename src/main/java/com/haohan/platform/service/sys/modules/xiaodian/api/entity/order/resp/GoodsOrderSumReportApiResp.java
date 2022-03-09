package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2019/1/17
 */
public class GoodsOrderSumReportApiResp implements Serializable {
    private Integer orderNum;      //订单总数
    private BigDecimal saleAmount;      //销售总额
    private BigDecimal RefundAmount;    //退款总额
    private Integer freshUserNum;       //新增用户
    private Integer totalUserNum;       //总用户数

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getRefundAmount() {
        return RefundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        RefundAmount = refundAmount;
    }

    public Integer getFreshUserNum() {
        return freshUserNum;
    }

    public void setFreshUserNum(Integer freshUserNum) {
        this.freshUserNum = freshUserNum;
    }

    public Integer getTotalUserNum() {
        return totalUserNum;
    }

    public void setTotalUserNum(Integer totalUserNum) {
        this.totalUserNum = totalUserNum;
    }
}
