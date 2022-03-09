package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgw on 2018/6/12.
 */
public class TAppInfoResp implements Serializable{

    private String sid;

    private String appKey;

    private String appSecret;

    private List<TShopDto> shops;

    private String partnerId;

    private String merchantId;

    private String merchantName;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public List<TShopDto> getShops() {
        return shops;
    }

    public void setShops(List<TShopDto> shops) {
        this.shops = shops;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
