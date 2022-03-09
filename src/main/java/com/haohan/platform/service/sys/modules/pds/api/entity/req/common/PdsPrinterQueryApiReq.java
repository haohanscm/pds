package com.haohan.platform.service.sys.modules.pds.api.entity.req.common;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsPrinterQueryApiReq extends PdsBaseApiReq {
    @NotBlank(message = "shopId不能为空")
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
