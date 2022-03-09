package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *  小区 配送 数量统计
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "communityNum", "addressNum" })
public class CommunityCountResp extends SummaryCountResp {

    private String communityNo; // 小区编号
    private String communityName; // 小区名称
    private String areaDetail; // 地址详情

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

    public String getAreaDetail() {
        return areaDetail;
    }

    public void setAreaDetail(String areaDetail) {
        this.areaDetail = areaDetail;
    }
}
