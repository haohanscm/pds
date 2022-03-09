package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

/**
 * @author dy
 * @date 2019/1/7
 */
public class PdsSupplierListApiReq extends PdsBaseApiReq {

    private String goodsId;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
