package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/9/29
 */
public class MercOrderDeliveryResp implements Serializable {

    private String[] goodsNames;   //no
    private String memberName;      //no
    private String deliveryType;
    private String deliveryManName;
    private String deliveryManTel;
    private String receiverMobile;
    private String address;
    private Integer finishTimes;    //no
    private Integer totalTimes;     //no
    private String deliveryStatus;
    private String planGenStatus;
    private String planGenDesc;
    private Date startDeliveryDate;
    private String orderMark;
    private String orderId;

    public String[] getGoodsNames() {
        return goodsNames;
    }

    public void setGoodsNames(String[] goodsNames) {
        this.goodsNames = goodsNames;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryManName() {
        return deliveryManName;
    }

    public void setDeliveryManName(String deliveryManName) {
        this.deliveryManName = deliveryManName;
    }

    public String getDeliveryManTel() {
        return deliveryManTel;
    }

    public void setDeliveryManTel(String deliveryManTel) {
        this.deliveryManTel = deliveryManTel;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFinishTimes() {
        return finishTimes;
    }

    public void setFinishTimes(Integer finishTimes) {
        this.finishTimes = finishTimes;
    }

    public Integer getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPlanGenStatus() {
        return planGenStatus;
    }

    public void setPlanGenStatus(String planGenStatus) {
        this.planGenStatus = planGenStatus;
    }

    public String getPlanGenDesc() {
        return planGenDesc;
    }

    public void setPlanGenDesc(String planGenDesc) {
        this.planGenDesc = planGenDesc;
    }

    public Date getStartDeliveryDate() {
        return startDeliveryDate;
    }

    public void setStartDeliveryDate(Date startDeliveryDate) {
        this.startDeliveryDate = startDeliveryDate;
    }

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
