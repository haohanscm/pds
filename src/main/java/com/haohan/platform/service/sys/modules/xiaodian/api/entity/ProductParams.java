package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;

public class ProductParams extends BaseParams implements Serializable {

    private String shopId;  // 店铺ID
    private String shopType;  // 店铺类型
    private String categoryId;  // 分类ID
    private String productId;  // 产品ID
    private String categoryType; // 分类类型


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
