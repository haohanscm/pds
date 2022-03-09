package com.haohan.platform.service.sys.modules.xiaodian.api.entity.database;

import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dy on 2018/9/29.
 */
public class ReqGoodsCategory implements Serializable {

    private List<String> categoryIds;  // 分类编号列表

    private String shopId;		// 店铺ID
    private String merchantId;  // 商家Id
	private String name;		// 名称
    private String categoryType;		// 分类类型  修改为是否展示 0否 1是

    private String categoryId;  // 分类唯一编号
    private String parentId;   // 父级编号 主键

    private String logo;        // logo图片地址
	private Integer sort;		// 排序
    private String goodsCount; // 分类下的商品数量

//    private String shopName;
//    private String merchantName;
//    private String description;		// 描述
//    private String industry;		// 行业名称
//    private String keywords;		// 关键词
    //	private GoodsCategory parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//    private String categoryType;		// 分类类型
//    private String rootNodeNum; // 根节点数


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    // 复制属性到商品分类
    public GoodsCategory transfGoodsCategory(GoodsCategory category) {
        category.setShopId(this.shopId);
        category.setMerchantId(this.merchantId);
        category.setName(this.name);
        category.setId(this.categoryId);
        category.setParent(new GoodsCategory(this.parentId));
        category.setLogo(this.logo);
        category.setSort(this.sort);
        category.setCategoryType(this.categoryType);
        return category;
    }
}
