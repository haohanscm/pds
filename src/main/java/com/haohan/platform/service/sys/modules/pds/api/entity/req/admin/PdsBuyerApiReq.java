package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

/**
 * @author shenyu
 * @create 2019/1/4
 */
public class PdsBuyerApiReq extends PdsBaseApiReq {
    private String buyerMerchantId;

    public String getBuyerMerchantId() {
        return buyerMerchantId;
    }

    public void setBuyerMerchantId(String buyerMerchantId) {
        this.buyerMerchantId = buyerMerchantId;
    }
}
