package com.haohan.platform.service.sys.modules.pds.api.entity.req.supplier;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 供应商商品 关联映射 请求
 *
 * @author dy
 * @date 2019/01/04
 */
public class SupplierGoodsRelationReq implements Serializable {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    @NotBlank(message = "supplierId不能为空")
    private String supplierId;
    private String supplierMerchantId;
    private String pmShopId;
    private String supplierShopId;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierMerchantId() {
        return supplierMerchantId;
    }

    public void setSupplierMerchantId(String supplierMerchantId) {
        this.supplierMerchantId = supplierMerchantId;
    }

    public String getPmShopId() {
        return pmShopId;
    }

    public void setPmShopId(String pmShopId) {
        this.pmShopId = pmShopId;
    }

    public String getSupplierShopId() {
        return supplierShopId;
    }

    public void setSupplierShopId(String supplierShopId) {
        this.supplierShopId = supplierShopId;
    }
}
