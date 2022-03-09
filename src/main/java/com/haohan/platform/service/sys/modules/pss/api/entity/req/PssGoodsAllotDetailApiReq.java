package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssGoodsAllotDetailApiReq extends PssPageApiReq {
//    private String merchantId;		// 商家ID
    private String supplierId;		//供应商ID
    private String allotId;		// 调拨ID
    @JsonProperty("goodsModelId")
    private String goodsCode;		// 商品编码
    private String modelName;		//商品规格
    private String goodsCategory;	//商品分类
    private String goodsName;		// 商品名称
    private String unit;			//商品单位
    private BigDecimal price;		//采购价格
    private Integer goodsNum;		// 调拨数量
    private Integer outorigStock;		// 调出原库存
    private Integer outStock;		// 调出后库存
    private Integer inorigStock;		// 调入原库存
    private Integer inStock;		// 调入后库存
    private String operator;		// 操作人
    private Date oprateTime;		// 操作时间

//    @Override
//    public String getMerchantId() {
//        return merchantId;
//    }
//
//    @Override
//    public void setMerchantId(String merchantId) {
//        this.merchantId = merchantId;
//    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getAllotId() {
        return allotId;
    }

    public void setAllotId(String allotId) {
        this.allotId = allotId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getOutorigStock() {
        return outorigStock;
    }

    public void setOutorigStock(Integer outorigStock) {
        this.outorigStock = outorigStock;
    }

    public Integer getOutStock() {
        return outStock;
    }

    public void setOutStock(Integer outStock) {
        this.outStock = outStock;
    }

    public Integer getInorigStock() {
        return inorigStock;
    }

    public void setInorigStock(Integer inorigStock) {
        this.inorigStock = inorigStock;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOprateTime() {
        return oprateTime;
    }

    public void setOprateTime(Date oprateTime) {
        this.oprateTime = oprateTime;
    }
}
