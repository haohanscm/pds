/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TSaleVolumeResp
 * Author:   Lenovo
 * Date:     2018/6/16 23:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/6/16
 * @since 1.0.0
 */
public class TSaleVolumeResp implements Serializable {
    private String goodsId;

    private String goodsName;

    private int saleNum;

    private BigDecimal SaleAmount;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public BigDecimal getSaleAmount() {
        return SaleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        SaleAmount = saleAmount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
