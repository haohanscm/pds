package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户后台 店铺管理  返回参数
 * Created by dy on 2018/10/9.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespShopInfo implements Serializable {

    private String shopId; //  店铺id
    private String name; // 店铺名称
    private String shopLogo;    //店铺logo
    private String shopDesc;		// 店铺介绍
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;   // 创建时间
    private String shopLevel;    //店铺级别   0 总店, 1 分店
    private String status;		// 状态
    private String serviceType;		// 店铺服务模式
    private String industry;		// 行业名称

    private String address;		// 店铺地址
    private String shopLocation; //店铺位置
    private String mapLongitude;		// 经度
    private String mapLatitude;		// 纬度

    private String manager;		// 店铺负责人
    private String telephone;		// 店铺电话
    private String onlineTime;		// 营业时间
    private String shopService;		// 店铺服务
    private String deliverDistence;	//配送距离
    private String payCode;		// 店铺收款码
    private String qrcode;		// 店铺二维码

    public void copyFromShop(Shop shop){
        this.shopId = shop.getId();
        this.name = shop.getName();
        this.shopLogo = shop.getShopLogo();
        this.shopDesc = shop.getShopDesc();
        this.createDate = shop.getCreateDate();
        this.shopLevel = shop.getShopLevel();
        this.status = shop.getStatus();
        this.serviceType = shop.getServiceType();
        this.industry = shop.getIndustry();

        this.address = shop.getAddress();
        this.shopLocation = shop.getShopLocation();
        this.mapLongitude = shop.getMapLongitude();
        this.mapLatitude = shop.getMapLatitude();

        this.manager = shop.getManager();
        this.telephone = shop.getTelephone();
        this.onlineTime = shop.getOnlineTime();
        this.shopService = shop.getShopService();
        this.deliverDistence = shop.getDeliverDistence();
        this.payCode = shop.getPayCode();
        this.qrcode = shop.getQrcode();
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
