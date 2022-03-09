package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求结算记录列表
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminSettlementRecordListReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    private int pageNo;
    private int pageSize;

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
}
