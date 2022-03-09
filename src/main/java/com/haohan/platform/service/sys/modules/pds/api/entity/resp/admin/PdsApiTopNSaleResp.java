package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/18
 */
public class PdsApiTopNSaleResp implements Serializable {
    private String goodsName;
    private BigDecimal saleNum;
    private BigDecimal saleAmount;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(BigDecimal saleNum) {
        this.saleNum = saleNum;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }
}
