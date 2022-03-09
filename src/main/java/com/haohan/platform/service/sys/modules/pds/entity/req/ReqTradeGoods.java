package com.haohan.platform.service.sys.modules.pds.entity.req;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购商/供货商  交易商品 请求参数
 * Created by dy on 2018/10/19.
 */
public class ReqTradeGoods implements Serializable {

    private String goodsId;        // 商品ID sku
    private String goodsImg;        // 商品图片
    private String goodsName;        // 商品名称
    private String goodsModel;        // 商品规格
    private Integer goodsNum;        // 商品数量
    private String unit;        // 商品单位
    private BigDecimal price;  // 价格

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

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
