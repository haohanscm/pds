package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


/**
 * @author cx
 * @date 2019/8/15
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantInfoResp implements Serializable {

    private String pdsUrl;

    private String pmId ;

    private String shopId;

    private String pmName;

    private Boolean isSelfPm;

    private Boolean isPds;

    public String getPdsUrl() {
        return pdsUrl;
    }

    public void setPdsUrl(String pdsUrl) {
        this.pdsUrl = pdsUrl;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public Boolean getSelfPm() {
        return isSelfPm;
    }

    public void setSelfPm(Boolean selfPm) {
        isSelfPm = selfPm;
    }

    public Boolean getPds() {
        return isPds;
    }

    public void setPds(Boolean pds) {
        isPds = pds;
    }
}
