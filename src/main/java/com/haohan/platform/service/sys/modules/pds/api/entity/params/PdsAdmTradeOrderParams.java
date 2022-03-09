package com.haohan.platform.service.sys.modules.pds.api.entity.params;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author shenyu
 * @create 2018/10/31
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsAdmTradeOrderParams {
    private String offerId;
    private String supplierName;
    private String supplyNum;
    private String tradeId;
    private String opStatus;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(String supplyNum) {
        this.supplyNum = supplyNum;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
    }
}
