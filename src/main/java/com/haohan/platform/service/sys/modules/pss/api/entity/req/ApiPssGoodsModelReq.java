package com.haohan.platform.service.sys.modules.pss.api.entity.req;


import java.io.Serializable;

/**
 * 商户后台 进销存 规格商品 请求参数
 * Created by dy on 2018/10/17.
 */
public class ApiPssGoodsModelReq implements Serializable {

    private int pageNo;
    private int pageSize;
    private String shopId;
    private String goodsCategoryId; // 分类id
    private String goodsName; // 商品名称
    private String modelCode;  // 扫码条码
    private String goodsModelSn; // 规格商品唯一编号
    private String merchantId;


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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getGoodsModelSn() {
        return goodsModelSn;
    }

    public void setGoodsModelSn(String goodsModelSn) {
        this.goodsModelSn = goodsModelSn;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
