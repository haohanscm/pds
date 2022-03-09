package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public interface IPdsAdminStatisticalService {
    BaseResp overview(PdsApiStatisOverViewReq statisticalReq, Date queryDate);

    BaseResp statisCurve(PdsApiStatisCurveReq statisCurveReq);

    BaseResp categoryPercent(PdsApiStatisCurveReq statisCurveReq);

    BaseResp goodsTopN(PdsApiStatisSaleTopReq saleTopReq);

    BaseResp orderDeal(PdsApiStatisOrderDealReq orderDealReq);

    BaseResp orderAnalyze(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    BaseResp buyerSaleTop(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    BaseResp saleTrend(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    BaseResp briefReport(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    BaseResp rangeAmount(PdsRangeAmountReq rangeAmountReq);
}
