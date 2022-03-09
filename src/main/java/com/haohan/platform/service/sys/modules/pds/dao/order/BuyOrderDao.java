package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiOrderAnalyzeReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisCurveReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisOverViewReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.*;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 采购单DAO接口
 *
 * @author haohan
 * @version 2018-10-22
 */
@MyBatisDao
public interface BuyOrderDao extends CrudDao<BuyOrder> {

    PdsApiSumBuyOrderResp findAdmSumList(String buyOrderId);

    List<BuyOrder> findOfferedList(BuyOrder buyOrder);

    List<BuyOrder> findJoinList(BuyOrder buyOrder);

    int countOrderNum(PdsApiStatisOverViewReq apiStatisticalReq);

    BigDecimal summarySaleAmount(PdsApiStatisOverViewReq apiStatisticalReq);

    List<PdsApiStatisCurveResp> statisCurvePast(PdsApiStatisCurveReq statisticalReq);

    Integer countPayOrderNum(@Param(value = "pmId") String pmId, @Param("payStatus") String payStatus);

    PdsApiOrderAnalyzeResp orderAnalyze(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    List<PdsApiBuyerSaleTopResp> buyerSaleTop(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    PdsApiBriefReportResp briefReport(PdsApiOrderAnalyzeReq orderAnalyzeReq);

    List<BuyOrder> findListGroupByMerchant(BuyOrder buyOrder);

    Integer updatePart(BuyOrder buyOrder);
}