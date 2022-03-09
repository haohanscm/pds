package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/11/12
 */
public class PdsApiDeliveryBuyerResp {
    private String buyerName;        //采购商名称
    private String buyerType;            //采购商类型
    private String address;             //地址
    private String contact;             //联系人
    private String telephone;           //联系电话
    private String buyerId;              //采购商ID
    private Integer goodsCount;         //商品种类数
    private String shipId;              //送货单号
    private String status;              //送货状态

    private BigDecimal totalAmount;     //总金额
    private String selfOrderAddress;    //自提地址

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getSelfOrderAddress() {
        return selfOrderAddress;
    }

    public void setSelfOrderAddress(String selfOrderAddress) {
        this.selfOrderAddress = selfOrderAddress;
    }
}
