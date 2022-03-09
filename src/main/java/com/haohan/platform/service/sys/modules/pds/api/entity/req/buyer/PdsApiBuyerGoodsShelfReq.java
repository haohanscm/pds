package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsReq;

import java.io.Serializable;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/12
 */
public class PdsApiBuyerGoodsShelfReq implements Serializable {
    private List<PdsBuyerGoodsReq> goodsReqList;

    public List<PdsBuyerGoodsReq> getGoodsReqList() {
        return goodsReqList;
    }

    public void setGoodsReqList(List<PdsBuyerGoodsReq> goodsReqList) {
        this.goodsReqList = goodsReqList;
    }
}
