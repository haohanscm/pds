package com.haohan.platform.service.sys.modules.pds.entity.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/10/20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyOrderSummaryResp implements Serializable {
    private String goodsId;        // 商品ID
    private String goodsImg;        // 商品图片
    private String goodsName;        // 商品名称
    private String goodsModel;        // 商品规格
    private String goodsUnit;       //商品单位
    private BigDecimal goodsNum;        // 采购数量
    private BigDecimal marketPrice;        // 市场价格
    private String buySeq;          //采购批次

    public void copyToSummaryOrder(SummaryOrder summaryOrder) {
        summaryOrder.setGoodsId(this.goodsId);        // 商品ID
        summaryOrder.setGoodsImg(this.goodsImg);        // 商品图片
        summaryOrder.setGoodsName(this.goodsName);        // 商品名称
        summaryOrder.setGoodsModel(this.goodsModel);        // 商品规格
        summaryOrder.setUnit(this.goodsUnit);       //商品单位
//        summaryOrder.setGoodsNum(this.goodsNum);		// 采购数量
        summaryOrder.setMarketPrice(this.marketPrice);        // 市场价格
        summaryOrder.setBuySeq(this.buySeq);          //采购批次
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
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

    public BigDecimal getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(BigDecimal goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }
}
