package com.haohan.platform.service.sys.modules.pds.entity.req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/10/27
 */
public class PdsOfferOrderReq {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    private String buySeq;
    private String shipStatus;
    private String[] shipStatusArry;
    private String supplierId;
    private String status;
    private String pmId;

    private String excludeShipStatus;  // 排除的发货状态


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public String[] getShipStatusArry() {
        return shipStatusArry;
    }

    public void setShipStatusArry(String[] shipStatusArry) {
        this.shipStatusArry = shipStatusArry;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getExcludeShipStatus() {
        return excludeShipStatus;
    }

    public void setExcludeShipStatus(String excludeShipStatus) {
        this.excludeShipStatus = excludeShipStatus;
    }
}
