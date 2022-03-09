package com.haohan.platform.service.sys.modules.pds.api.entity.req;

import java.util.List;

/**
 * Created by dy on 2018/12/14.
 * 采购商商品(sku)请求  用于获取商品规格当前信息
 */
public class PdsBuyerGoodsModelListReq extends PdsBuyerGoodsReq {

    List<String> goodsIdList; // 商品规格id 列表

    public List<String> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<String> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }
}
