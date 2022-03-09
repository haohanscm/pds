package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求 供应商编辑
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminSupplierEditReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    @NotBlank(message = "供应商全称不能为空")
    @Length(min = 0, max = 64, message = "供应商全称长度必须介于 0 和 64 之间")
    private String supplierName;
    @NotBlank(message = "联系人不能为空")
    @Length(min = 0, max = 64, message = "联系人长度必须介于 0 和 64 之间")
    private String contact;
    @NotBlank(message = "电话不能为空")
    @Length(min = 0, max = 15, message = "电话长度必须介于 0 和 15 之间")
    private String telephone;
    @NotBlank(message = "供应商地址不能为空")
    @Length(min = 0, max = 64, message = "供应商地址长度必须介于 0 和 64 之间")
    private String address;

    @Length(min = 0, max = 64, message = "供应商ID长度必须介于 0 和 64 之间")
    private String id;
    @Length(min = 0, max = 64, message = "通行证ID长度必须介于 0 和 64 之间")
    private String uid;
    @Length(min = 0, max = 64, message = "供应商简称长度必须介于 0 和 64 之间")
    private String shortName;        // 供应商名称
    @Length(min = 0, max = 64, message = "微信长度必须介于 0 和 64 之间")
    private String wechatId;        // 微信
    @Length(min = 0, max = 64, message = "操作员长度必须介于 0 和 64 之间")
    private String operator;        // 操作员
    @Length(min = 0, max = 64, message = "账期长度必须介于 0 和 64 之间")
    private String payPeriod;        // 账期
    @Length(min = 0, max = 255, message = "标签长度必须介于 0 和 255 之间")
    private String tags;        //标签
    @Length(min = 0, max = 5, message = "是否启用长度必须介于 0 和 5 之间")
    private String status;        // 是否启用 字典 yes_no
    @Length(min = 0, max = 5, message = "是否开启消息推送长度必须介于 0 和 5 之间")
    private String needPush;        // 是否开启消息推送
    // 默认项

    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 5, message = "供应商类型长度必须介于 0 和 5 之间")
    private String supplierType; // 供应商类型0:普通 1:库存
    private String merchantName;

    /**
     * 参数转换
     *
     * @param supplier
     */
    public void transToSupplier(PdsSupplier supplier) {
        supplier.setId(this.id);
        supplier.setPmId(this.pmId);
        if (null != this.uid) {
            supplier.setPassportId(this.uid);
        }
        if (null != this.merchantId) {
            supplier.setMerchantId(this.merchantId);
        }
        if (null != this.merchantName) {
            supplier.setMerchantName(this.merchantName);
        }
        if (null != this.supplierName) {
            supplier.setSupplierName(this.supplierName);
        }
        if (null != this.shortName) {
            supplier.setShortName(this.shortName);
        }
        if (null != this.contact) {
            supplier.setContact(this.contact);
        }
        if (null != this.telephone) {
            supplier.setTelephone(this.telephone);
        }
        if (null != this.wechatId) {
            supplier.setWechatId(this.wechatId);
        }
        if (null != this.operator) {
            supplier.setOperator(this.operator);
        }
        if (null != this.payPeriod) {
            supplier.setPayPeriod(this.payPeriod);
        }
        if (null != this.address) {
            supplier.setAddress(this.address);
        }
        if (null != this.tags) {
            supplier.setTags(this.tags);
        }
        if (null != this.status) {
            supplier.setStatus(this.status);
        }
        if (null != this.supplierType) {
            supplier.setSupplierType(this.supplierType);
        }
        if (null != this.needPush) {
            supplier.setNeedPush(this.needPush);
        }
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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
}
