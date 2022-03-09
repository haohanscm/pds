package com.haohan.platform.service.sys.modules.pds.core.order;

import com.haohan.framework.entity.BaseResp;

import java.util.Date;

/**
 * Created by zgw on 2018/10/20.
 */
public interface IBuyOrderService {


    // 修改采购单及明细状态为待确认
    BaseResp updateStatusWait(String buySeq, Date deliveryTime);

}
