package com.haohan.platform.service.sys.modules.xiaodian.api.entity.database;


import java.io.Serializable;

/**
 * 公共商品库 标准商品spu 请求参数
 * Created by dy on 2018/10/17.
 */
public class ReqStandardProduct implements Serializable {

    private int pageNo;
    private int pageSize;
    private String goodsId;		// 商品id  spuId
    private String goodsName;		// 商品名称
    private String goodsCategoryId;		// 商品分类id
    private String generalSn;		// 商品通用编号
    private String industry;		// 行业
    private String brand;		// 品牌
    private String manufacturer;		// 厂家/制造商

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setManufacturer(String manufacturer) {

        this.manufacturer = manufacturer;
    }

    public String getGeneralSn() {
        return generalSn;
    }

    public void setGeneralSn(String generalSn) {
        this.generalSn = generalSn;
    }
}
