package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/11/8
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsTradeOrderCommonParamsResp {
    private String shipId;              //送货单号
    private String buyId;
    private String goodsName;
    private String goodsModel;
    private String goodsUnit;
    private BigDecimal shipNum;        //送货单-送货数量
    private BigDecimal buyPrice;
    private String goodsCategory;
    private String tradeId;
    private String goodsImg;
    private BigDecimal sortOutNum;         //自提单-分拣数量
    private String goodsModelId;
    private BigDecimal orderGoodsNum; // 下单数量


    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public BigDecimal getShipNum() {
        return shipNum;
    }

    public void setShipNum(BigDecimal shipNum) {
        this.shipNum = shipNum;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public BigDecimal getSortOutNum() {
        return sortOutNum;
    }

    public void setSortOutNum(BigDecimal sortOutNum) {
        this.sortOutNum = sortOutNum;
    }

    public String getGoodsModelId() {
        return goodsModelId;
    }

    public void setGoodsModelId(String goodsModelId) {
        this.goodsModelId = goodsModelId;
    }

    public BigDecimal getOrderGoodsNum() {
        return orderGoodsNum;
    }

    public void setOrderGoodsNum(BigDecimal orderGoodsNum) {
        this.orderGoodsNum = orderGoodsNum;
    }
}
