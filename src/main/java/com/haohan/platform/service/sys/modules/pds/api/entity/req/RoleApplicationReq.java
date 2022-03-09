package com.haohan.platform.service.sys.modules.pds.api.entity.req;

import java.io.Serializable;

/**
 * 采购商/供应商 入驻申请
 * Created by dy on 2018/11/12.
 */
public class RoleApplicationReq implements Serializable {

    private String roleType; //  角色类型:采购商/供应商  pds_company_type
    private String merchantName; //
    private String contact; //
    private String telephone; //
    private String address; //

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
