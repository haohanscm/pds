package com.haohan.platform.service.sys.modules.pds.entity.req;

import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 供应商 报价单提交 请求参数
 * Created by dy on 2018/10/26.
 */
public class ReqOfferOrders implements Serializable {

    private String supplierId;        // 供应商
    private String buySeq;        //采购批次
    private Date deliveryTime;        // 送货日期
    private List<OfferOrder> offerOrderList;  // 供应商的所有商品报价单

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<OfferOrder> getOfferOrderList() {
        return offerOrderList;
    }

    public void setOfferOrderList(List<OfferOrder> offerOrderList) {
        this.offerOrderList = offerOrderList;
    }
}
