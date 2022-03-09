package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 配送 产品信息
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryProduct implements Serializable{

    private String goodsName;   // 商品名称
    private Integer goodsNum;   // 商品数量
    private String goodsUrl;   // 商品图片地址
    private String giftName;   // 赠品名称
    private Integer giftNum;   // 赠品数量
    private String giftUrl;   //  赠品图片地址
    private String deliveryPlanId;   // 配送计划id

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public String getDeliveryPlanId() {
        return deliveryPlanId;
    }

    public void setDeliveryPlanId(String deliveryPlanId) {
        this.deliveryPlanId = deliveryPlanId;
    }
}
