package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.AddGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import com.haohan.platform.service.sys.common.beanvalidator.EditGroup;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsBaseApiReq {
    @NotBlank(groups = {AddGroup.class, EditGroup.class, DefaultGroup.class, DeleteGroup.class}, message = "missing param pmId")
    private String pmId;
    private Integer pageNo;
    private Integer pageSize;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
