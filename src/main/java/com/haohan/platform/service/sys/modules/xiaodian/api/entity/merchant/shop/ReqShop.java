package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.shop;

import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;

import java.io.Serializable;

/**
 * 商户后台 店铺管理  请求参数
 * Created by dy on 2018/10/9.
 */
public class ReqShop implements Serializable {

    private String merchantId; //  商家id
    private String shopId; //  店铺id
    private String name;		// 店铺名称
    private String serviceType; // 店铺服务模式

    // 店铺可修改属性
    private String shopLogo;    //店铺logo
    private String payCode;		// 店铺收款码
    private String qrcode;		// 店铺二维码

    private String shopDesc;		// 店铺介绍
    private String address;		// 店铺地址
    private String shopLocation; //店铺位置
    private String mapLongitude;		// 经度
    private String mapLatitude;		// 纬度
    private String manager;		// 店铺负责人
    private String telephone;		// 店铺电话
    private String onlineTime;		// 营业时间
    private String shopService;		// 店铺服务
    private String deliverDistence;	//配送距离
    private String industry;		// 行业名称  仅展示

    // 图片 图片资源库id
    private String logoGalleryId;
    private String payCodeGalleryId;
    private String qrcodeGalleryId;


    // 复制需要修改的属性到shop
    public void transfModifyProperties(Shop shop){
        shop.setShopLogo(this.shopLogo);
        shop.setShopDesc(this.shopDesc);
        shop.setAddress(this.address);
        shop.setShopLocation(this.shopLocation);
        shop.setMapLongitude(this.mapLongitude);
        shop.setMapLatitude(this.mapLatitude);
        shop.setManager(this.manager);
        shop.setTelephone(this.telephone);
        shop.setOnlineTime(this.onlineTime);
        shop.setShopService(this.shopService);
        shop.setDeliverDistence(this.deliverDistence);
        shop.setPayCode(this.payCode);
        shop.setQrcode(this.qrcode);
        shop.setIndustry(this.industry);
    }

    // 新增店铺时复制属性
    public void transfShop(Shop shop){
        transfModifyProperties(shop);
        shop.setMerchantId(this.merchantId);
        shop.setName(this.name);
        shop.setServiceType(this.serviceType);

    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getMapLongitude() {
        return mapLongitude;
    }

    public void setMapLongitude(String mapLongitude) {
        this.mapLongitude = mapLongitude;
    }

    public String getMapLatitude() {
        return mapLatitude;
    }

    public void setMapLatitude(String mapLatitude) {
        this.mapLatitude = mapLatitude;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getShopService() {
        return shopService;
    }

    public void setShopService(String shopService) {
        this.shopService = shopService;
    }

    public String getDeliverDistence() {
        return deliverDistence;
    }

    public void setDeliverDistence(String deliverDistence) {
        this.deliverDistence = deliverDistence;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getLogoGalleryId() {
        return logoGalleryId;
    }

    public void setLogoGalleryId(String logoGalleryId) {
        this.logoGalleryId = logoGalleryId;
    }

    public String getPayCodeGalleryId() {
        return payCodeGalleryId;
    }

    public void setPayCodeGalleryId(String payCodeGalleryId) {
        this.payCodeGalleryId = payCodeGalleryId;
    }

    public String getQrcodeGalleryId() {
        return qrcodeGalleryId;
    }

    public void setQrcodeGalleryId(String qrcodeGalleryId) {
        this.qrcodeGalleryId = qrcodeGalleryId;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
