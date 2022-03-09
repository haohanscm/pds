package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;

/**
 * 小区信息
 */
public class ServerAreaResp extends CommunityResp implements Serializable{

    private String districtId; // 服务片区编号
    private String districtName; // 服务片区名称

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
