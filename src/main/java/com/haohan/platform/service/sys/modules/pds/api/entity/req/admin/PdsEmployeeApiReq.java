package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2019/2/14
 */
public class PdsEmployeeApiReq extends PdsBaseApiReq {
    @NotBlank(groups = DeleteGroup.class, message = "missing param id")
    private String id;
    private String passportId;        // 通行证ID
    private String role;        // 角色
    private String telephone;    //手机号
    private String name;        //姓名
    private String status;        // 状态

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
