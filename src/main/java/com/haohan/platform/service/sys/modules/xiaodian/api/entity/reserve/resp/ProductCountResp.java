package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve.resp;

import java.io.Serializable;
import java.util.List;

/**
 *  商品、赠品 配送 数量统计
 */
public class ProductCountResp implements Serializable {

    private List<GoodsCountResp> goodsList; // 商品列表
    private List<GiftCountResp> giftList; // 赠品列表

    public List<GoodsCountResp> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsCountResp> goodsList) {
        this.goodsList = goodsList;
    }

    public List<GiftCountResp> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftCountResp> giftList) {
        this.giftList = giftList;
    }
}
