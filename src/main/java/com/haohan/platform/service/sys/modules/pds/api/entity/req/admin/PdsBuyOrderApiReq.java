package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsBuyOrderApiReq extends PdsDateSeqApiReq {
    private String status;
    private String buyerId;
    private String buyId;
    private String buyerName;

    public void copyToBuyOrder(BuyOrder buyOrder) {
        buyOrder.setPmId(this.getPmId());
        buyOrder.setDeliveryTime(this.getDeliveryDate());
        buyOrder.setBuySeq(this.getBuySeq());
        buyOrder.setStatus(this.getStatus());
        buyOrder.setBuyerId(this.getBuyerId());
        buyOrder.setBuyId(this.getBuyId());
        buyOrder.setBuyerName(this.getBuyerName());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
