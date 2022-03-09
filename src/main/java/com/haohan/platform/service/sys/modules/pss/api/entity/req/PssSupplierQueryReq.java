package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;

/**
 * @author dy
 * @date 2019/7/19
 */
public class PssSupplierQueryReq extends PssPageApiReq {

    private String supplierName;
    private String shortName;
    private String contact;
    private String telephone;

    public Supplier transToSupplier(Supplier supplier){
        supplier.setMerchantId(this.getMerchantId());
        supplier.setSupplierName(this.supplierName);
        supplier.setShortName(this.shortName);
        supplier.setContact(this.contact);
        supplier.setTelephone(this.telephone);
        return supplier;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
}
