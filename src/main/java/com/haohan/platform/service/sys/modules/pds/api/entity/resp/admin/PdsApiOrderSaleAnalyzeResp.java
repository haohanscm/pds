package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/25
 */
public class PdsApiOrderSaleAnalyzeResp implements Serializable {
    private BigDecimal saleAmount;      //销售额,税后
    private BigDecimal saleAmountWSF;   //销售额,税后,含运费
    private BigDecimal grossProfit;     //毛利润,税后
    private BigDecimal grossProfitWSF;  //毛利润,税后,含运费
    private double profitRate;          //毛利率,税后
    private double profitRateWSF;       //毛利率,税后,含运费

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(BigDecimal grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(double profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getSaleAmountWSF() {
        return saleAmountWSF;
    }

    public void setSaleAmountWSF(BigDecimal saleAmountWSF) {
        this.saleAmountWSF = saleAmountWSF;
    }

    public BigDecimal getGrossProfitWSF() {
        return grossProfitWSF;
    }

    public void setGrossProfitWSF(BigDecimal grossProfitWSF) {
        this.grossProfitWSF = grossProfitWSF;
    }

    public double getProfitRateWSF() {
        return profitRateWSF;
    }

    public void setProfitRateWSF(double profitRateWSF) {
        this.profitRateWSF = profitRateWSF;
    }
}
