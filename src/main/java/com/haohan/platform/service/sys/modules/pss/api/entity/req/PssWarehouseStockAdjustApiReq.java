package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssWarehouseStockAdjustApiReq extends PssBaseApiReq {
    @NotBlank(message = "库存ID不能为空")
    private String warehouseStockId;
    @NotNull(message = "请输入数量")
    private BigDecimal stockNum;

    public String getWarehouseStockId() {
        return warehouseStockId;
    }

    public void setWarehouseStockId(String warehouseStockId) {
        this.warehouseStockId = warehouseStockId;
    }

    public BigDecimal getStockNum() {
        return stockNum;
    }

    public void setStockNum(BigDecimal stockNum) {
        this.stockNum = stockNum;
    }
}
