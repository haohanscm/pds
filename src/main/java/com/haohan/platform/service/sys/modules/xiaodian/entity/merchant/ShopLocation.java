package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.haohan.framework.utils.JacksonUtils;

import java.io.Serializable;

/**
 * Created by zgw on 2018/1/11.
 */
public class ShopLocation implements Serializable{

    private String province; //省
    private String city; //城市
    private String district; //区县
    private String street; //街道
    private String lnglat;//经纬度

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }

    public String toJson(){
        return JacksonUtils.toJson(this);
    }
}
