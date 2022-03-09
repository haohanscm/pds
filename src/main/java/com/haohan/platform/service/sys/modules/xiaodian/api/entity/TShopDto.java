package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2019/3/5
 */
public class TShopDto implements Serializable {
    private String id;
    private String name;		// 店铺名称
    private String address;		// 店铺地址
    private String manager;		// 店铺负责人
    private String telephone;		// 店铺电话
    private String onlineTime;		// 营业时间
    private String shopService;		// 店铺经营范围  仅展示
    private String shopLogo;    //店铺logo     保存图片组编号 只一张
    private String shopType;    //店铺类型: 0 餐饮, 1零售  终端使用
    private String shopLevel;    //店铺级别   0 总店, 1 分店

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }
}
