package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.req;

/**
 * @author shenyu
 * @create 2018/9/28
 */
public class MercCommitServiceItemsReq {
    private String districtId;
    private String communityId;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
}
