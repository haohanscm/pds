package com.haohan.platform.service.sys.modules.pds.api.entity.req;

import java.io.Serializable;

/**
 * Created by zgw on 2018/10/22.
 */
public class BuyOrderReq implements Serializable {
    private String detailId;
    private String buyId;
    private int buyNum;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }
}
