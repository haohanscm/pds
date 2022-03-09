package com.haohan.platform.service.sys.modules.pss.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssPageApiReq;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import org.springframework.stereotype.Service;

/**
 * 仓库管理
 * @author shenyu
 * @create 2018/11/27
 */
@Service
public interface IPssWarehouseService {
    //仓库列表(分页)
    BaseResp findWarehousePage(PssPageApiReq pageApiReq,Page page);

    //保存仓库
    BaseResp saveWarehouse(PssWarehouse warehouse);

    //删除仓库
    BaseResp delWarehouse(String id);


}
