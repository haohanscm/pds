package com.haohan.platform.service.sys.modules.pss.api.entity.req;

/**
 * @author shenyu
 * @create 2018/11/27
 */
@Deprecated
public class ApiGoodsStorageReq extends ApiPssBasePageReq {
    private String warehouseId;
    private String queryParams;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }
}
