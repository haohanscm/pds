package com.haohan.platform.service.sys.modules.pds.api.entity.resp.supplier;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商商品 返回值
 *
 * @author dy
 * @date 2019/01/04
 */
public class SupplyPriceResp implements Serializable {

    private String supplierId;
    private String supplierName;
    private String goodsId;
    private String goodsModelId;
    private String goodsName;
    private String modelName;
    private BigDecimal supplyPrice;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsModelId() {
        return goodsModelId;
    }

    public void setGoodsModelId(String goodsModelId) {
        this.goodsModelId = goodsModelId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }
}
