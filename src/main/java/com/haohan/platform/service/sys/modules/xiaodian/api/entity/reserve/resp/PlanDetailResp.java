package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 *  配送计划 商品详情
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanDetailResp implements Serializable {

    private String deliveryPlanId; // 配送计划编号
    private String orderId; //      订单号
    private String status; //      配送状态
    private String goodsUrl; //    商品图片地址
    private String goodsName; // 商品名称
    private int goodsNum; // 商品数量
    private String goodsUnit; // 商品单位
    private String goodsInfo; // 商品规格信息
    private String giftUrl; //   赠品图片地址
    private String giftName; //   赠品名称
    private int giftNum; //     赠品数量
    private String giftUnit; //   赠品单位
    private String giftInfo; //  赠品规格信息
    private String remarks; //   配送计划备注
    private String theDay; // 配送日期
    private String orderRemark; // 订单备注

    public String getDeliveryPlanId() {
        return deliveryPlanId;
    }

    public void setDeliveryPlanId(String deliveryPlanId) {
        this.deliveryPlanId = deliveryPlanId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public String getGiftUnit() {
        return giftUnit;
    }

    public void setGiftUnit(String giftUnit) {
        this.giftUnit = giftUnit;
    }

    public String getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(String giftInfo) {
        this.giftInfo = giftInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTheDay() {
        return theDay;
    }

    public void setTheDay(String theDay) {
        this.theDay = theDay;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
}
