package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSumOrderDetail;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiOrderAnalyzeReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisCurveReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisSaleTopReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.*;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.resp.BuyOrderDetailResp;
import com.haohan.platform.service.sys.modules.pds.entity.resp.BuyOrderSummaryResp;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购单详情DAO接口
 *
 * @author haohan
 * @version 2018-10-24
 */
@MyBatisDao
public interface BuyOrderDetailDao extends CrudDao<BuyOrderDetail> {
    List<BuyOrderSummaryResp> findGroupByGoodsId(BuyOrderDetail buyOrderDetail);

    BigDecimal countDealNum(BuyOrderDetail buyOrderDetail);

    BigDecimal countTotalAmount(BuyOrderDetail buyOrderDetail);

    List<PdsAdminSumOrderDetail> findBySummaryOrder(@Param(value = "summaryBuyId") String summaryBuyId,
                                                    @Param(value = "reqTime") Date reqTime);

    // 采购单明细列表 带交易单的状态/分拣数量
    List<BuyOrderDetailResp> findListWithTrade(BuyOrderDetail buyOrderDetail);

    // 按批次/送货时间/状态  修改对应采购单及采购明细的状态  批量
    int updateStatusBatch(BuyOrderDetail buyOrderDetail);

    BigDecimal countBuyAvgPrice(@Param(value = "summaryOrderId") String summaryOrderId);

    // 按采购编号/状态  修改对应采购单及采购明细的状态
    int updateStatusByBuyId(BuyOrderDetail buyOrderDetail);

    List<BuyOrderDetail> findJoinList(BuyOrderDetail buyOrderDetail);

    List<PdsApiCategoryPercentResp> categoryPercent(PdsApiStatisCurveReq overViewReq);

    List<PdsApiTopNSaleResp> goodsTopN(PdsApiStatisSaleTopReq overViewReq);

    BigDecimal sumBuyOrderProfit(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    List<PdsApiStatisCurveResp> orderAmountCurve(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    List<PdsApiStatisCurveResp> costCurve(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    List<PdsApiBuyerSaleTopResp> buyerSaleTop(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    PdsApiOrderAnalyzeResp orderAnalyze(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    List<BuyOrderDetail> findListGroupByMerchant(BuyOrderDetail buyOrderDetail);

    List<PdsPreSumOrderApiResp> selectPreSummaryOrders(@Param(value = "reqDate") Date reqDate);

    BuyOrderDetail fetchLastDeal(@Param(value = "pmId") String pmId, @Param(value = "goodsModelId") String goodsModelId, @Param(value = "buyerId") String buyerId);
}