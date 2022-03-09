package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 *  商品 配送 数量统计
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsCountResp implements Serializable {

    private String goodsName; // 商品名称
    private int goodsNum; // 商品数量
    private String goodsUnit; // 商品单位
    private String goodsInfo; // 商品规格信息

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
}
