package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/9/27
 */
public class MercShopServiceAreaResp implements Serializable {
    private String id;
    private String shopName;
    private String shopTel;
    private String shopAddress;
    private String serviceDistricts;
    private String serviceCommunitys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getServiceDistricts() {
        return serviceDistricts;
    }

    public void setServiceDistricts(String serviceDistricts) {
        this.serviceDistricts = serviceDistricts;
    }

    public String getServiceCommunitys() {
        return serviceCommunitys;
    }

    public void setServiceCommunitys(String serviceCommunitys) {
        this.serviceCommunitys = serviceCommunitys;
    }
}
