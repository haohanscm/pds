package com.haohan.platform.service.sys.modules.pds.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 供应商货物管理Entity
 *
 * @author haohan
 * @version 2018-11-14
 */
@JsonIgnoreProperties({"createDate", "isNewRecord", "updateDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierGoods extends DataEntity<SupplierGoods> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String supplierId;        // 供应商id
    private String supplierMerchantId; // 供应商商家id
    private String goodsId;        // 商品id;spu
    private String goodsModelId;  // 商品规格id;sku
    private String supplierModelId;  // 供应商商品规格id;sku
    private String status;        // 启用状态  是否关联为供应商商品yes_no

    // 查询条件
    private String shopId;        // 供应商关联店铺id
    private String parentId;        // 商品分类父级id
    private String categoryId;        // 商品分类id
    private String categoryName;  // 分类名称
    private Integer goodsNum;  // 数量

    private String supplierName;		// 供应商名称
    private String supplierMerchantName;		// 供应商商家名称
    private String pmName;		// 平台商家名称
    private String supplierShopId; // 供应商商家店铺id
    private String goodsName; // 商品名称
    private String goodsModelName; // 商品规格名称
    private String supplierGoodsId; // 供应商商品id
    private String supplierGoodsName; // 供应商商品名称
    private String supplierModelName; // 供应商商品规格名称

    public SupplierGoods() {
        super();
    }

    public SupplierGoods(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "供应商id长度必须介于 0 和 64 之间")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Length(min = 0, max = 64, message = "商品id;spu长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Length(min = 0, max = 2, message = "状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getGoodsModelId() {
        return goodsModelId;
    }

    public void setGoodsModelId(String goodsModelId) {
        this.goodsModelId = goodsModelId;
    }

    public String getSupplierMerchantId() {
        return supplierMerchantId;
    }

    public void setSupplierMerchantId(String supplierMerchantId) {
        this.supplierMerchantId = supplierMerchantId;
    }

    public String getSupplierModelId() {
        return supplierModelId;
    }

    public void setSupplierModelId(String supplierModelId) {
        this.supplierModelId = supplierModelId;
    }

    public String getSupplierShopId() {
        return supplierShopId;
    }

    public void setSupplierShopId(String supplierShopId) {
        this.supplierShopId = supplierShopId;
    }

    public String getSupplierMerchantName() {
        return supplierMerchantName;
    }

    public void setSupplierMerchantName(String supplierMerchantName) {
        this.supplierMerchantName = supplierMerchantName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsModelName() {
        return goodsModelName;
    }

    public void setGoodsModelName(String goodsModelName) {
        this.goodsModelName = goodsModelName;
    }

    public String getSupplierGoodsName() {
        return supplierGoodsName;
    }

    public void setSupplierGoodsName(String supplierGoodsName) {
        this.supplierGoodsName = supplierGoodsName;
    }

    public String getSupplierModelName() {
        return supplierModelName;
    }

    public void setSupplierModelName(String supplierModelName) {
        this.supplierModelName = supplierModelName;
    }

    public String getSupplierGoodsId() {
        return supplierGoodsId;
    }

    public void setSupplierGoodsId(String supplierGoodsId) {
        this.supplierGoodsId = supplierGoodsId;
    }
}