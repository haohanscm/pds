package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage;

import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;

import java.io.Serializable;

/**
 * 商户后台 商家的店铺 返回参数
 * Created by dy on 2018/10/9.
 */
public class RespShop implements Serializable {

    private String shopId; //  店铺id
    private String name;		// 店铺名称

    public void copyFromShop(Shop shop){
        this.shopId = shop.getId();
        this.name = shop.getName();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
