package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *  会员配送 数量统计
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberCountResp extends MemberResp {

    private String communityName; // 小区名称
    private int goodsNum; // 商品数量
    private int giftNum; // 赠品数量
    private String status; // 配送状态

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
