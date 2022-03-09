package com.haohan.platform.service.sys.modules.xiaodian.api.entity.database;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;

import java.io.Serializable;

/**
 * 公共商品库 库存商品sku 返回值
 * Created by dy on 2018/10/23.
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespSku extends StockKeepingUnit implements Serializable {

    private String goodsCategoryId;		// 商品分类id
    private String detailDesc;		// 商品描述
    private String generalSn;		// 商品通用编号
    private String categoryName; // 分类名称
    private String industry;		// 行业
    private String brand;		// 品牌
    private String manufacturer;		// 厂家/制造商

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getGeneralSn() {
        return generalSn;
    }

    public void setGeneralSn(String generalSn) {
        this.generalSn = generalSn;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
