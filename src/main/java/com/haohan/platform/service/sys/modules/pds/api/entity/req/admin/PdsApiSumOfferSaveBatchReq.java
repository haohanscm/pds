package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsOfferOrderSaveParams;

import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/26
 */
public class PdsApiSumOfferSaveBatchReq {
    private String pmId;
    private List<PdsOfferOrderSaveParams> offerOrderList;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public List<PdsOfferOrderSaveParams> getOfferOrderList() {
        return offerOrderList;
    }

    public void setOfferOrderList(List<PdsOfferOrderSaveParams> offerOrderList) {
        this.offerOrderList = offerOrderList;
    }
}
