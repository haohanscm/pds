package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.*;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminStatisticalService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.cost.BuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderDetailService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.SummaryOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/12/17
 */
@Service
public class AdminStatisticalServiceImpl implements IPdsAdminStatisticalService {
    @Autowired
    private BuyOrderService buyOrderService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    private BuyOrderDetailService buyOrderDetailService;
    @Autowired
    private SummaryOrderService summaryOrderService;
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private BuyerPaymentService buyerPaymentService;

    @Override
    public BaseResp overview(PdsApiStatisOverViewReq overViewReq, Date queryDate) {
        BaseResp baseResp = BaseResp.newError();
        PdsApiOverViewResp overViewResp = new PdsApiOverViewResp();
        Date startTime = DateUtils.getDayStartTime(queryDate);
        Date endTime = DateUtils.getDayEndTime(queryDate);

        //查询总订单数量
        int orderNum = buyOrderService.countOrderNum(overViewReq);
        overViewReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
        overViewResp.setOrderNum(orderNum);
        //查询销售总额
        BigDecimal saleAmount = buyOrderService.summarySaleAmount(overViewReq);
        overViewResp.setSaleAmount(saleAmount);
        //查询总用户
        overViewResp.setBuyerNum(pdsBuyerService.countUserNum(overViewReq));
        //查询今日新增用户
        overViewReq.setStartTime(startTime);
        overViewReq.setEndTime(endTime);
        int newUser = pdsBuyerService.countUserNum(overViewReq);
        overViewResp.setNewUser(newUser);
        //TODO 查询退货总额
        overViewResp.setRefundAmount(BigDecimal.ZERO);
//        overViewResp.setBuyerNum();

        baseResp.setExt(overViewResp);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp statisCurve(PdsApiStatisCurveReq statisCurveReq) {
        BaseResp baseResp = BaseResp.newError();

        statisCurveReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
        List<PdsApiStatisCurveResp> respList = buyOrderService.statisCurvePast(statisCurveReq);
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }


    @Override
    public BaseResp categoryPercent(PdsApiStatisCurveReq statisCurveReq) {
        BaseResp baseResp = BaseResp.newError();

        statisCurveReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
        List<PdsApiCategoryPercentResp> respList = buyOrderDetailService.categoryPercent(statisCurveReq);
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }


    @Override
    public BaseResp goodsTopN(PdsApiStatisSaleTopReq saleTopReq) {
        BaseResp baseResp = BaseResp.newError();
        int limitNum = saleTopReq.getLimitNum();
        if (limitNum < 3) {
            limitNum = 3;
        } else if (limitNum > 20) {
            limitNum = 20;
        }
        saleTopReq.setLimitNum(limitNum);
        saleTopReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
        String opt = saleTopReq.getOpt();
        if ("1".equals(opt)) {
            saleTopReq.setOpt("saleNum");
        } else {
            saleTopReq.setOpt("saleAmount");
        }
        List<PdsApiTopNSaleResp> respList = buyOrderDetailService.goodsTopN(saleTopReq);
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp orderDeal(PdsApiStatisOrderDealReq orderDealReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsApiStatisOverViewReq overViewReq = new PdsApiStatisOverViewReq();
        PdsApiOrderDataResp orderDataResp = new PdsApiOrderDataResp();
        //待结账
        overViewReq.setPmId(orderDealReq.getPmId());
        overViewReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
        orderDataResp.setNotPay(buyOrderService.countPayOrderNum(orderDealReq.getPmId(), ICommonConstant.YesNoType.no));  //TODO

        //待汇总订单数
        overViewReq.setStatus(IPdsConstant.BuyOrderStatus.submit.getCode());
        orderDataResp.setWaitSummary(buyOrderService.countOrderNum(overViewReq));

        //待报价
        overViewReq.setStatus(IPdsConstant.SummaryOrderStatus.wait.getCode());
        orderDataResp.setOfferPrice(summaryOrderService.countNotOffer(overViewReq));

        //待确认
        overViewReq.setStatus(IPdsConstant.BuyOrderStatus.wait.getCode());
        orderDataResp.setConfirm(buyOrderService.countOrderNum(overViewReq));

        //配送中
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setPmId(overViewReq.getPmId());
        tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
        tradeOrder.setBuyerStatus(IPdsConstant.BuyerDealStatus.wait_check.getCode());
        orderDataResp.setDelivering(tradeOrderService.countBuyOrderNum(tradeOrder));

        //待分拣
        tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.waitSortOut.getCode());
        tradeOrder.setDeliveryStatus("");
        orderDataResp.setSortout(tradeOrderService.countTradeOrderNum(tradeOrder));

        baseResp.setExt(orderDataResp);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp orderAnalyze(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
        BaseResp baseResp = BaseResp.newError();
        double rate = IPdsConstant.sale_rate;

        if (null != orderAnalyzeReq) {
            Date startDate = orderAnalyzeReq.getStartDate();
            Date endDate = orderAnalyzeReq.getEndDate();
            if (null != startDate && null != endDate) {
                orderAnalyzeReq.setStartDate(DateUtils.getDayStartTime(startDate));
                orderAnalyzeReq.setEndDate(DateUtils.getDayEndTime(endDate));
            }
        }

        orderAnalyzeReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
//        PdsApiOrderAnalyzeResp orderAnalyzeResp = buyOrderService.orderAnalyze(orderAnalyzeReq);
        PdsApiOrderAnalyzeResp orderAnalyzeResp = buyOrderDetailService.orderAnalyze(orderAnalyzeReq);
        BigDecimal grossProfitAmount = buyOrderDetailService.sumBuyOrderProfit(orderAnalyzeReq);    //毛利润,不含运费,不含税

        PdsApiOrderSaleAnalyzeResp orderSaleAnalyzeResp = new PdsApiOrderSaleAnalyzeResp();
        BigDecimal decRate = new BigDecimal(1 - rate);
        BigDecimal saleAmount = orderAnalyzeResp.getOrderAmount();                  //不含税,不含运费
        BigDecimal saleAmounted = saleAmount.multiply(decRate).setScale(2, BigDecimal.ROUND_HALF_DOWN);         //销售额,税后,不含运费,保留两位小数
        BigDecimal profitRated = grossProfitAmount.multiply(decRate).setScale(2, BigDecimal.ROUND_HALF_DOWN);               //毛利润,税后,不含运费,保留两位小数

        BigDecimal shipFee = orderAnalyzeResp.getShipFee() == null ? BigDecimal.ZERO : orderAnalyzeResp.getShipFee();
        orderSaleAnalyzeResp.setSaleAmount(saleAmount);
        orderSaleAnalyzeResp.setSaleAmountWSF(saleAmounted.add(shipFee));
        orderSaleAnalyzeResp.setGrossProfit(grossProfitAmount);
        orderSaleAnalyzeResp.setGrossProfitWSF(profitRated.add(shipFee));
        if (BigDecimal.ZERO.compareTo(orderAnalyzeResp.getOrderAmount()) != 0) {
            orderSaleAnalyzeResp.setProfitRate(grossProfitAmount.divide(saleAmount, 5).doubleValue());
            orderSaleAnalyzeResp.setProfitRateWSF(profitRated.add(shipFee).divide(saleAmounted.add(shipFee), 5).doubleValue());
        } else {
            orderSaleAnalyzeResp.setProfitRate(0);
            orderSaleAnalyzeResp.setProfitRateWSF(0);
        }

        baseResp.setExt(orderSaleAnalyzeResp);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp buyerSaleTop(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
        BaseResp baseResp = BaseResp.newError();
        if (null != orderAnalyzeReq) {
            Date startDate = orderAnalyzeReq.getStartDate();
            Date endDate = orderAnalyzeReq.getEndDate();
            if (null != orderAnalyzeReq && null != startDate && null != endDate) {
                orderAnalyzeReq.setStartDate(DateUtils.getDayStartTime(startDate));
                orderAnalyzeReq.setEndDate(DateUtils.getDayEndTime(endDate));
            }
        }
        Integer limit = orderAnalyzeReq.getLimit();
        if (null == limit || limit == 0) {
            orderAnalyzeReq.setLimit(5);
        }
        String opt = orderAnalyzeReq.getOpt();
        if ("1".equals(opt)) {
            orderAnalyzeReq.setOpt("orderNum");
        } else {
            orderAnalyzeReq.setOpt("amount");
        }

        orderAnalyzeReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
//        List<PdsApiBuyerSaleTopResp> respList = buyOrderService.buyerSaleTop(orderAnalyzeReq);
        List<PdsApiBuyerSaleTopResp> respList = buyOrderDetailService.buyerSaleTop(orderAnalyzeReq);
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp saleTrend(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
        BaseResp baseResp = BaseResp.newError();
        if (null != orderAnalyzeReq) {
            Date startDate = orderAnalyzeReq.getStartDate();
            Date endDate = orderAnalyzeReq.getEndDate();
            if (null != orderAnalyzeReq && null != startDate && null != endDate) {
                orderAnalyzeReq.setStartDate(DateUtils.getDayStartTime(startDate));
                orderAnalyzeReq.setEndDate(DateUtils.getDayEndTime(endDate));
            }
        }

        //下单金额
        List<PdsApiStatisCurveResp> orderAmountList = buyOrderDetailService.orderAmountCurve(orderAnalyzeReq);
        //销售金额
        orderAnalyzeReq.setStatus(IPdsConstant.BuyOrderStatus.success.getCode());
        List<PdsApiStatisCurveResp> saleAmountList = buyOrderDetailService.orderAmountCurve(orderAnalyzeReq);
        Map<Date, BigDecimal> saleMap = saleAmountList.stream().collect(Collectors.toMap(PdsApiStatisCurveResp::getBuyDate, PdsApiStatisCurveResp::getAmount));
        //实际成本
        List<PdsApiStatisCurveResp> costList = buyOrderDetailService.costCurve(orderAnalyzeReq);
        Map<Date, BigDecimal> costMap = costList.stream().collect(Collectors.toMap(PdsApiStatisCurveResp::getBuyDate, PdsApiStatisCurveResp::getAmount));
        //返回参数
        ArrayList<PdsApiSaleTrendResp> respList = orderAmountList.stream().map(item -> {
            PdsApiSaleTrendResp trendResp = new PdsApiSaleTrendResp();
            trendResp.setBuyDate(item.getBuyDate());
            BigDecimal sale = saleMap.get(item.getBuyDate());
            BigDecimal cost = costMap.get(item.getBuyDate());
            if (null == sale) {
                sale = BigDecimal.ZERO;
            }
            if (null == cost) {
                cost = BigDecimal.ZERO;
            }
            trendResp.setSaleAmount(sale);
            trendResp.setCost(cost);
            trendResp.setOrderAmount(item.getAmount());
            if (BigDecimal.ZERO.compareTo(sale) == 0) {
                trendResp.setGrossProfitRate(0);
                trendResp.setGrossProfit(BigDecimal.ZERO);
            } else {
                trendResp.setGrossProfitRate(sale.subtract(cost).divide(sale, 5).doubleValue());
                trendResp.setGrossProfit(sale.subtract(cost));
            }
            return trendResp;
        }).collect(Collectors.toCollection(ArrayList::new));
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        baseResp.setExt(respList);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp briefReport(PdsApiOrderAnalyzeReq orderAnalyzeReq) {
        BaseResp baseResp = BaseResp.newError();
        if (null != orderAnalyzeReq) {
            Date startDate = orderAnalyzeReq.getStartDate();
            Date endDate = orderAnalyzeReq.getEndDate();
            if (null != orderAnalyzeReq && null != startDate && null != endDate) {
                orderAnalyzeReq.setStartDate(DateUtils.getDayStartTime(startDate));
                orderAnalyzeReq.setEndDate(DateUtils.getDayEndTime(endDate));
            }
        }

        PdsApiBriefReportResp briefReportResp = buyOrderService.briefReport(orderAnalyzeReq);

        baseResp.setExt(briefReportResp);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp rangeAmount(PdsRangeAmountReq rangeAmountReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = rangeAmountReq.getPmId();
        // 采购商所属商家 可选参数
        String merchantId = rangeAmountReq.getMerchantId();
        PdsBuyer buyer = new PdsBuyer();
        buyer.setPmId(pmId);
        buyer.setMerchantId(merchantId);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(buyer);
        if (Collections3.isEmpty(buyerList)) {
            baseResp.setMsg("无采购商");
            return baseResp;
        }
        BuyerPayment buyerPayment = new BuyerPayment();
        buyerPayment.setBeginBuyDate(rangeAmountReq.getStartDate());
        buyerPayment.setEndBuyDate(rangeAmountReq.getEndDate());
        buyerPayment.setPmId(pmId);
        buyerPayment.setMerchantId(merchantId);
        HashMap<String, Object> resultMap = new HashMap<>(buyerList.size());
        // 采购商列表 按 采购商的商家查询
        List<PdsRangeAmountResultResp> list = buyerPaymentService.rangeAmount(buyerPayment);
        BuyerPayment payment;
        if (!Collections3.isEmpty(list)) {
            for (PdsRangeAmountResultResp resultResp : list) {
                buyerPayment.setMerchantId(resultResp.getMerchantId());
                payment = buyerPaymentService.countPayment(buyerPayment);
                if (null != payment) {
                    resultResp.setTotalBuyAmount(payment.getBuyerPayment());
                    resultResp.setTotalAfterSaleAmount(payment.getAfterSalePayment());
                    resultResp.setTotalPayAmount(payment.getBuyerPayment().add(payment.getAfterSalePayment()));
                }
            }
            resultMap.put("list", list);
            resultMap.put("count", list.size());
            baseResp.success();
            baseResp.setExt(resultMap);
        } else {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        }
        return baseResp;
    }

}
