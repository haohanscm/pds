package com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/13
 */
public class PdsTopNGoodsModelResp implements Serializable {
    private String id;
    private String modelName;
    private BigDecimal modelPrice;
    private String modelUnit;
    private String modelUrl;
    private BigDecimal purchasePrice;
    private BigDecimal virtualPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public BigDecimal getModelPrice() {
        return modelPrice;
    }

    public void setModelPrice(BigDecimal modelPrice) {
        this.modelPrice = modelPrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getModelUnit() {
        return modelUnit;
    }

    public void setModelUnit(String modelUnit) {
        this.modelUnit = modelUnit;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public BigDecimal getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(BigDecimal virtualPrice) {
        this.virtualPrice = virtualPrice;
    }
}
