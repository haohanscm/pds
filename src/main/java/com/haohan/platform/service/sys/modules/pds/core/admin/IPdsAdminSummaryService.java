package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsAdminSumBuyOrder;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsOfferOrderSaveParams;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiSumBuyDetailBatchReq;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/10/30
 */
public interface IPdsAdminSummaryService {
    //获取汇总单明细
    BaseResp findSummaryOrderPage(Page<PdsAdminSumBuyOrder> reqPage, PdsAdminSumBuyOrder pdsAdminSumBuyOrder);

    //查询采购单明细
    BaseResp findBuyDetailBySumId(String summaryOrderId, String pmId);

    //修改采购订单明细
    BaseResp editBuyOrderDetail(String detailId, BigDecimal buyNum, BigDecimal buyPrice);

    //修改采购订单明细 批量
    BaseResp editBuyOrderDetailBatch(PdsApiSumBuyDetailBatchReq buyDetailBatchReq);

    //保存报价单
    BaseResp saveOfferOrder(SummaryOrder summaryOrder, PdsOfferOrderSaveParams pdsOfferOrderSaveParams) throws Exception;

    //修改汇总单
    BaseResp editSumOrder(String sumOrderId, BigDecimal platformPrice, BigDecimal buyAvgPrice);

    //删除报价单
    BaseResp delOfferOrder(String offerOrderId);

}
