package com.haohan.platform.service.sys.modules.pds.api.entity.req.supplier;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 供应商商品 请求
 *
 * @author dy
 * @date 2019/01/03
 */
public class SupplyPriceApiReq extends PdsBaseApiReq {

    @NotBlank(message = "goodsModelId不能为空")
    private String goodsModelId;

    public String getGoodsModelId() {
        return goodsModelId;
    }

    public void setGoodsModelId(String goodsModelId) {
        this.goodsModelId = goodsModelId;
    }
}
