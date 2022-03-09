package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/25
 */
public class PdsApiSaleTrendResp implements Serializable {
    private Date buyDate;           //日期
    private BigDecimal orderAmount; //下单金额
    private BigDecimal saleAmount;  //销售金额
    private BigDecimal cost;        //实际成本
    private BigDecimal grossProfit; //毛利润
    private double grossProfitRate; //销售毛利率

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public double getGrossProfitRate() {
        return grossProfitRate;
    }

    public void setGrossProfitRate(double grossProfitRate) {
        this.grossProfitRate = grossProfitRate;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }
}
