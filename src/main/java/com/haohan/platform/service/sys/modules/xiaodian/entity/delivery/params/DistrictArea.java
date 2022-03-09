package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;

/**
 * 配送计划数量统计
 * Created by zgw on 2018/9/3.
 */
public class DistrictArea implements Serializable{

    private String districtName; //片区名称
    private String districtId;//ID
    private int deliveryPlanNum;// 配送计划数量
    private int productNum;//商品数量
    private int giftNum;//赠品数量


    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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
