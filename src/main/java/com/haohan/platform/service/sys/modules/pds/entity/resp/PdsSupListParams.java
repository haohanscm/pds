package com.haohan.platform.service.sys.modules.pds.entity.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/10/26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsSupListParams implements Serializable {
    private String supplierId;
    private String supplierName;
    private String[] tags;
    private String supLocation;
    private String contact;
    private String telephone;
    private Integer categoryNum;
    private Integer goodsCount;

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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getSupLocation() {
        return supLocation;
    }

    public void setSupLocation(String supLocation) {
        this.supLocation = supLocation;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(Integer categoryNum) {
        this.categoryNum = categoryNum;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }
}
