package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsShipOrderApiReq extends PdsDateSeqApiReq {
    private String driverId;
    private String buyerId;
    private String uid;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
