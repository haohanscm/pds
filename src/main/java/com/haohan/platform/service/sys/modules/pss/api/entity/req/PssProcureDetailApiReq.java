package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssProcureDetailApiReq extends PssPageApiReq {
    @NotBlank(message = "missing param procureNum")
    private String procureNum;		// 采购编号
    private String goodsName;		// 商品名称
    private String goodsModelId;		// 商品SKU ID
    private BigDecimal price;		// 单价
    private Integer goodsNum;		// 数量
    private String modelName;		// 规格
    private BigDecimal sumAmount;		// 合计金额
    private String unit;		// 单位
    private String categrory;		// 分类
    private String stockStatus;		// 入库状态

    public String getProcureNum() {
        return procureNum;
    }

    public void setProcureNum(String procureNum) {
        this.procureNum = procureNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsModelId() {
        return goodsModelId;
    }

    public void setGoodsModelId(String goodsModelId) {
        this.goodsModelId = goodsModelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategrory() {
        return categrory;
    }

    public void setCategrory(String categrory) {
        this.categrory = categrory;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }
}
