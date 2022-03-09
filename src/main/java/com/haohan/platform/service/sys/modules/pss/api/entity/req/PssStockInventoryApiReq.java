package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/29
 */
public class PssStockInventoryApiReq extends PssPageApiReq {
    @NotBlank(message = "warehouseStockId不能为空")
    private String warehouseStockId;

    public String getWarehouseStockId() {
        return warehouseStockId;
    }

    public void setWarehouseStockId(String warehouseStockId) {
        this.warehouseStockId = warehouseStockId;
    }
}
