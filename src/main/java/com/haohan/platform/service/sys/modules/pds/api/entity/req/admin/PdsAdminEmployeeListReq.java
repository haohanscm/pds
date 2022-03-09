package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求 员工列表
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminEmployeeListReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    private int pageNo;
    private int pageSize;
    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 64, message = "商家名称长度必须介于 0 和 64 之间")
    private String merchantName;
    @Length(min = 0, max = 64, message = "用户ID长度必须介于 0 和 64 之间")
    private String uid;
    @Length(min = 0, max = 5, message = "角色长度必须介于 0 和 5 之间")
    private String role;
    @Length(min = 0, max = 15, message = "电话长度必须介于 0 和 15 之间")
    private String telephone;
    @Length(min = 0, max = 64, message = "姓名长度必须介于 0 和 64 之间")
    private String name;
    @Length(min = 0, max = 10, message = "来源长度必须介于 0 和 10 之间")
    private String origin;
    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    private String status;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
