package com.haohan.platform.service.sys.modules.pds.api.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购商商品定价 返回值
 * Created by zgw on 2018/12/8.
 */
public class PdsBuyerGoodsResp implements Serializable {

    private String id;
    private BigDecimal marketPrice; // 市场价
    private BigDecimal purchasePrice;  // 采购商采购价
    private String goodsName;  // 商品名称
    private String modelName;  // 商品规格名称
    private String unit;  // 单位
    private String status; // 商品上下架状态
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;        // 起始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;        // 截止时间

    private String categoryId;
    private String categoryName;
    private String goodsModelSn;
    private String modelId;

    // 从平台商品定价复制属性
    public void copyFromPlatformGoods(PdsPlatformGoodsPrice goods) {
        this.id = goods.getId();
        this.marketPrice = goods.getMarketPrice();
        this.purchasePrice = goods.getPrice();
        this.goodsName = goods.getGoodsName();
        this.modelName = goods.getModelName();
        this.unit = goods.getUnit();
        this.status = goods.getStatus();
        this.startDate = goods.getStartDate();
        this.endDate = goods.getEndDate();
        this.categoryName = goods.getCategoryName();
        this.categoryId = goods.getCategoryId();
        this.goodsModelSn = goods.getGoodsModelSn();
        this.modelId = goods.getModelId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsModelSn() {
        return goodsModelSn;
    }

    public void setGoodsModelSn(String goodsModelSn) {
        this.goodsModelSn = goodsModelSn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
