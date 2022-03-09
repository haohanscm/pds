package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDataResetApiReq;
import com.haohan.platform.service.sys.modules.pds.exception.PdsSummaryOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.PdsOnekeyOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/5
 */
public interface IPdsAdminShortcutService {

    // 平台确认报价-采购商确认报价-交易匹配-运营揽货
    BaseResp confirm(String pmId, String buySeq, Date deliveryTime) throws PdsOnekeyOperationException, PdsSummaryOperationException, StorageOperationException;

    // 装车- 送达
    BaseResp loadAndArrived(String pmId, String buySeq, Date deliveryTime) throws PdsOnekeyOperationException;

    // 重置数据 至汇总采购单前
    BaseResp resetSummary(PdsDataResetApiReq dataResetReq);

    // 平台下 统一批次 全收货
    BaseResp goodsReceived(String pmId, String buySeq, Date deliveryTime);

}
