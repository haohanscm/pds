package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsEmployeeApiReq;

/**
 * @author shenyu
 * @create 2019/2/14
 */
public interface IPdsAdminEmployeeService {
    BaseResp selectList(PdsBaseApiReq apiBaseReq, BaseResp baseResp);

    BaseResp add(PdsEmployeeApiReq apiReq, BaseResp baseResp);

    BaseResp update(PdsEmployeeApiReq apiReq, BaseResp baseResp);

    BaseResp delete(PdsEmployeeApiReq delReq, BaseResp baseResp);
}
