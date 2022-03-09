package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2019/1/19
 */
public class GoodsOrderSaleVolRankApiResp implements Serializable {
    private String goodsId;
    private String goodsName;
    private BigDecimal saleVolume;
    private BigDecimal saleAmount;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getSaleVolume() {
        return saleVolume;
    }

    public void setSaleVolume(BigDecimal saleVolume) {
        this.saleVolume = saleVolume;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }
}
