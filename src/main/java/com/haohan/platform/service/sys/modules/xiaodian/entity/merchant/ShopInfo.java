package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;

import java.io.Serializable;

/**
 * Created by zgw on 2018/1/11.
 */
public class ShopInfo implements Serializable{

    private Shop shop;

    private ShopLocation shopLocation;

    private PhotoGroupManage shopLogos;

    private PhotoGroupManage shopQrCodes;

    private PhotoGroupManage shopPayCodes;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public ShopLocation getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(ShopLocation shopLocation) {
        this.shopLocation = shopLocation;
    }

    public PhotoGroupManage getShopLogos() {
        return shopLogos;
    }

    public void setShopLogos(PhotoGroupManage shopLogos) {
        this.shopLogos = shopLogos;
    }

    public PhotoGroupManage getShopQrCodes() {
        return shopQrCodes;
    }

    public void setShopQrCodes(PhotoGroupManage shopQrCodes) {
        this.shopQrCodes = shopQrCodes;
    }

    public PhotoGroupManage getShopPayCodes() {
        return shopPayCodes;
    }

    public void setShopPayCodes(PhotoGroupManage shopPayCodes) {
        this.shopPayCodes = shopPayCodes;
    }
}
