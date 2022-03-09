package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zgw on 2017/12/29.
 */
public class MerchantAppExtInfo implements Serializable{

    private String aladId;

    private String shopId;

    private String shopName;

    private String partnerId;

    private String versionNo;

    private String versionDesc;


    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getAladId() {
        return aladId;
    }

    public void setAladId(String aladId) {
        this.aladId = aladId;
    }


    public Map<String, Object> toMap(){

        return CommonUtils.beanToMap(this);
    }

    public String toJson(){
        return JsonMapper.toJsonString(this);
    }
}
