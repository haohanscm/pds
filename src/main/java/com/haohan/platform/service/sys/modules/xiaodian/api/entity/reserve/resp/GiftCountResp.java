package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 *  赠品 配送 数量统计
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCountResp implements Serializable {

    private String giftName; //赠品名称
    private int giftNum; // 赠品数量
    private String giftUnit; // 商品单位
    private String giftInfo; // 商品规格信息

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public String getGiftUnit() {
        return giftUnit;
    }

    public void setGiftUnit(String giftUnit) {
        this.giftUnit = giftUnit;
    }

    public String getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(String giftInfo) {
        this.giftInfo = giftInfo;
    }
}
