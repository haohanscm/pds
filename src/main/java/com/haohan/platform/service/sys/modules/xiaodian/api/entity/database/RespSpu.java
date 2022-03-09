package com.haohan.platform.service.sys.modules.xiaodian.api.entity.database;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StandardProductUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;

import java.io.Serializable;
import java.util.List;

/**
 * 公共商品库 标准商品spu 返回值
 * Created by dy on 2018/10/22.
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespSpu extends StandardProductUnit implements Serializable {

    private List<StockKeepingUnit> skuList;

    public List<StockKeepingUnit> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<StockKeepingUnit> skuList) {
        this.skuList = skuList;
    }
}
