package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 请求 员工编辑
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminEmployeeEditReq {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "平台商家ID长度必须介于 0 和 64 之间")
    private String pmId;

    @Length(min = 0, max = 64, message = "员工ID长度必须介于 0 和 64 之间")
    private String id;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regDate;        // 注册日期

    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;

    /**
     * 参数转换
     *
     * @param employee
     */
    public void transToEmployee(MerchantEmployee employee) {
        employee.setPmId(this.pmId);
        employee.setId(this.id);
        if (null != this.uid) {
            employee.setPassportId(this.uid);
        }
        if (null != this.role) {
            employee.setRole(this.role);
        }
        if (null != this.telephone) {
            employee.setTelephone(this.telephone);
        }
        if (null != this.name) {
            employee.setName(this.name);
        }
        if (null != this.origin) {
            employee.setOrigin(this.origin);
        }
        if (null != this.status) {
            employee.setStatus(this.status);
        }
        if (null != this.regDate) {
            employee.setRegDate(this.regDate);
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

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
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
