package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;

/**
 * 小区信息
 */
public class CommunityResp implements Serializable{

    private String communityNo; // 服务小区编号
    private String communityName; // 服务小区名称

    public String getCommunityNo() {
        return communityNo;
    }

    public void setCommunityNo(String communityNo) {
        this.communityNo = communityNo;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
