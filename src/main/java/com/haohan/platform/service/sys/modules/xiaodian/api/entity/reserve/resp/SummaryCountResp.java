package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 配送情况 汇总 数量统计
 * Created by zgw on 2018/9/3.
 */
public class SummaryCountResp implements Serializable {

    private int communityNum; // 配送小区数量
    private int memberNum; // 会员数量
    private int goodsNum; // 商品数量
    private int giftNum; // 赠品数量
    private int addressNum; // 地址数量
    private int totalNum; // 配送总数
    private int successNum; // 已配送数量
    private int waitNum; // 未配送数量
    private int failedNum; //  配送失败数量

    @JsonIgnore
    private int changeNum;
    @JsonIgnore
    private int cancelNum;

    public int getCommunityNum() {
        return communityNum;
    }

    public void setCommunityNum(int communityNum) {
        this.communityNum = communityNum;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
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

    public int getAddressNum() {
        return addressNum;
    }

    public void setAddressNum(int addressNum) {
        this.addressNum = addressNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }

    public int getWaitNum() {
        return waitNum;
    }

    public void setWaitNum(int waitNum) {
        this.waitNum = waitNum;
    }

    public int getFailedNum() {
        return failedNum;
    }

    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }

    public int getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(int changeNum) {
        this.changeNum = changeNum;
    }

    public int getCancelNum() {
        return cancelNum;
    }

    public void setCancelNum(int cancelNum) {
        this.cancelNum = cancelNum;
    }
}
