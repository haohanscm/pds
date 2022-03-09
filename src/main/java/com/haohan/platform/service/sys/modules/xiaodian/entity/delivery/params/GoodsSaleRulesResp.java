package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/9/5
 */
public class GoodsSaleRulesResp implements Serializable {
//    private String saleArriveType;  //售卖时效  not used
    private Integer limitBuyTimes;      //限制购买次数
    private String saleAreas;       //可售区域
    private Integer minSaleNum;         //起售数量
    private Date beginSaleDate;       //售卖开始时间
    private Date endSaleDate;         //售卖结束时间

//    public String getSaleArriveType() {
//        return saleArriveType;
//    }
//
//    public void setSaleArriveType(String saleArriveType) {
//        this.saleArriveType = saleArriveType;
//    }

    public Integer getLimitBuyTimes() {
        return limitBuyTimes;
    }

    public void setLimitBuyTimes(Integer limitBuyTimes) {
        this.limitBuyTimes = limitBuyTimes;
    }

    public String getSaleAreas() {
        return saleAreas;
    }

    public void setSaleAreas(String saleAreas) {
        this.saleAreas = saleAreas;
    }

    public Integer getMinSaleNum() {
        return minSaleNum;
    }

    public void setMinSaleNum(Integer minSaleNum) {
        this.minSaleNum = minSaleNum;
    }

    public Date getBeginSaleDate() {
        return beginSaleDate;
    }

    public void setBeginSaleDate(Date beginSaleDate) {
        this.beginSaleDate = beginSaleDate;
    }

    public Date getEndSaleDate() {
        return endSaleDate;
    }

    public void setEndSaleDate(Date endSaleDate) {
        this.endSaleDate = endSaleDate;
    }
}
