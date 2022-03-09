package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求供应商列表
 *
 * @author dy
 * @date 2019/2/14
 */
public class PdsAdminSupplierListReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    private int pageNo;
    private int pageSize;
    @Length(min = 0, max = 64, message = "通行证ID长度必须介于 0 和 64 之间")
    private String uid;
    @Length(min = 0, max = 64, message = "供应商简称长度必须介于 0 和 64 之间")
    private String shortName;        // 供应商名称
    @Length(min = 0, max = 64, message = "供应商名称长度必须介于 0 和 64 之间")
    private String supplierName;        // 供应商名称
    @Length(min = 0, max = 5, message = "是否启用长度必须介于 0 和 5 之间")
    private String status;        // 是否启用 字典 yes_no
    @Length(min = 0, max = 5, message = "是否开启消息推送长度必须介于 0 和 5 之间")
    private String needPush;        // 是否开启消息推送
    @Length(min = 0, max = 5, message = "供应商类型长度必须介于 0 和 5 之间")
    private String supplierType; // 供应商类型0:普通 1:库存
    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 64, message = "商家名称长度必须介于 0 和 64 之间")
    private String merchantName;
    @Length(min = 0, max = 15, message = "电话长度必须介于 0 和 15 之间")
    private String telephone;
    @Length(min = 0, max = 5, message = "是否绑定微信长度必须介于 0 和 5 之间")
    private String bindStatus;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNeedPush() {
        return needPush;
    }

    public void setNeedPush(String needPush) {
        this.needPush = needPush;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }
}
