package com.haohan.platform.service.sys.modules.pds.entity.business;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;

/**
 * 供应商商品 扩展 零售商品信息
 * Created by dy on 2018/11/14.
 */
@JsonIgnoreProperties({"createDate", "isNewRecord", "updateDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierGoodsExt extends SupplierGoods {

    private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
