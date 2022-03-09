package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;

/**
 * 配送计划数量统计
 * Created by zgw on 2018/9/3.
 */
public class DeliveryParams implements Serializable{

    private int communityNum;//配送小区数

    private int deliveryPlanNum;// 配送计划数

    private int productNum;//商品数量

    private int giftNum;//礼品数量


    public int getCommunityNum() {
        return communityNum;
    }

    public void setCommunityNum(int communityNum) {
        this.communityNum = communityNum;
    }

    public int getDeliveryPlanNum() {
        return deliveryPlanNum;
    }

    public void setDeliveryPlanNum(int deliveryPlanNum) {
        this.deliveryPlanNum = deliveryPlanNum;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }
}
