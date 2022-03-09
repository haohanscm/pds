package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;

import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/26
 */
public class PdsApiSumOrderBatchReq {
    private String pmId;
    private List<SummaryOrder> summaryOrderList;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public List<SummaryOrder> getSummaryOrderList() {
        return summaryOrderList;
    }

    public void setSummaryOrderList(List<SummaryOrder> summaryOrderList) {
        this.summaryOrderList = summaryOrderList;
    }
}
