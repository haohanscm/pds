package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsTruckApiReq;

/**
 * @author shenyu
 * @create 2019/2/14
 */
public interface IPdsAdminDeliveryService {
    BaseResp truckList(String pmId, BaseResp baseResp);

    BaseResp insertTruck(PdsTruckApiReq apiReq, BaseResp baseResp);

    BaseResp updateTruck(PdsTruckApiReq apiReq, BaseResp baseResp);

    BaseResp delTruck(String id, BaseResp baseResp);
}
