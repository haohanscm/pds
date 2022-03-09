package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/26
 */
public class PdsApiSumBuyDetailBatchReq {
    private String pmId;
    private List<PdsAdminSumOrderDetail> detailList;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public List<PdsAdminSumOrderDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<PdsAdminSumOrderDetail> detailList) {
        this.detailList = detailList;
    }
}
