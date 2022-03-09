package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/11/1
 */
public interface IPdsAdminSortOutService {
    //分拣记录列表
    BaseResp findList(TradeOrder tradeOrder, Page reqPage);

    //修改分拣数量
    BaseResp editSortNum(String tradeOrderId, BigDecimal sortOutNum);

    //确定分拣
    BaseResp confirm(String tradeOrderId, BigDecimal sortOutNum);

    //分拣进度(整体)
    BaseResp allProcess(Date deliveryDate, String buySeq, String pmId);

    //分拣进度(按采购单分组)
    BaseResp buyOrderProcess(Date deliveryDate, String buySeq, String pmId);

    //一键分拣
    BaseResp fastSortOut(Date deliveryDate, String buySeq, String pmId);


}
