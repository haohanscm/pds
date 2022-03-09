package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

/**
 * @author shenyu
 * @create 2018/12/25
 */
public class PdsApiStatisOrderDealReq {
    private String pmId;
    private String status;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
