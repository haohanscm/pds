package com.haohan.platform.service.sys.modules.xiaodian.api.service.order;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req.GoodsOrderReportApiReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderCategoryStatisApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderSaleCurveApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderSaleVolRankApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderSumReportApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2019/1/17
 */
@Service
public class GoodsOrderReportApiService {
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    private UPassportService uPassportService;

    public BaseResp reportSummary(GoodsOrderReportApiReq apiReq, BaseResp baseResp) {
        String merchantId = apiReq.getMerchantId();
        String shopId = apiReq.getShopId();
        Date now = new Date();
        Date startTime = apiReq.getStartTime();
        Date endTime = apiReq.getEndTime();
        Date todayMorning = DateUtils.getDayStartTime(now);
        Date todayNight = DateUtils.getDayEndTime(now);

        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setMerchantId(merchantId);
        goodsOrder.setShopId(shopId);
        goodsOrder.setStartTime(startTime);
        goodsOrder.setEndTime(endTime);
        Integer orderNum = goodsOrderService.countOrderNum(goodsOrder);
        BigDecimal saleAmount = goodsOrderService.sumSaleAmount(goodsOrder);
        Integer freshUser = 0;
        Integer totalUser = 0;
        if (StringUtils.isBlank(merchantId)){
            freshUser = uPassportService.countUserNum(todayMorning,todayNight);
            totalUser = uPassportService.countUserNum(null,null);
        }else {
            freshUser = userOpenPlatformService.countUserNum(merchantId,shopId,todayMorning,todayNight);
            totalUser = userOpenPlatformService.countUserNum(merchantId,shopId,null,null);
        }
        goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.REFUNDING.getCode());
        BigDecimal refundAmount = goodsOrderService.sumRefundAmount(goodsOrder);
        GoodsOrderSumReportApiResp apiResp = new GoodsOrderSumReportApiResp();
        apiResp.setOrderNum(orderNum);
        apiResp.setSaleAmount(saleAmount);
        apiResp.setFreshUserNum(freshUser);
        apiResp.setTotalUserNum(totalUser);
        apiResp.setRefundAmount(refundAmount);
        baseResp.setExt(apiResp);
        baseResp.success();
        return baseResp;
    }

    public BaseResp saleCurve(GoodsOrderReportApiReq apiReq, BaseResp baseResp) {
        String merchantId = apiReq.getMerchantId();
        String shopId = apiReq.getShopId();
        Date startTime = apiReq.getStartTime();
        Date endTime = apiReq.getEndTime();

        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setMerchantId(merchantId);
        goodsOrder.setShopId(shopId);
        goodsOrder.setStartTime(startTime);
        goodsOrder.setEndTime(endTime);
        goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
        List<GoodsOrderSaleCurveApiResp> respList = goodsOrderService.saleCurve(goodsOrder);
        if (CollectionUtils.isEmpty(respList)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    public BaseResp realTimeSummary(GoodsOrderReportApiReq apiReq, BaseResp baseResp) {
        Date now = new Date();
        Date todayMorning = DateUtils.getDayStartTime(now);
        Date todayNight = DateUtils.getDayEndTime(now);
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setMerchantId(apiReq.getMerchantId());
        goodsOrder.setShopId(apiReq.getShopId());
        goodsOrder.setStartTime(todayMorning);
        goodsOrder.setEndTime(todayNight);
        HashMap<String,Object> respMap = new HashMap<>();
        BigDecimal saleAmount = goodsOrderService.sumSaleAmount(goodsOrder);
        Integer orderNum = goodsOrderService.countOrderNum(goodsOrder);
        Integer buyerNum = goodsOrderService.countBuyerNum(goodsOrder);
        BigDecimal userAvgPrice = buyerNum == 0 ? BigDecimal.ZERO : saleAmount.divide(new BigDecimal(buyerNum).setScale(2,RoundingMode.HALF_DOWN));
        goodsOrder.setStartTime(DateUtils.getStartOfYesterday(now));
        goodsOrder.setEndTime(DateUtils.getEndOfYesterday(now));
        BigDecimal lastSaleAmount = goodsOrderService.sumSaleAmount(goodsOrder);
        Integer lastBuyerNum = goodsOrderService.countBuyerNum(goodsOrder);
        BigDecimal lastUserAvgPrice = lastBuyerNum == 0 ? BigDecimal.ZERO : saleAmount.divide(new BigDecimal(lastBuyerNum).setScale(2,RoundingMode.HALF_DOWN));

        respMap.put("saleAmount",saleAmount);
        respMap.put("orderNum",orderNum);
        respMap.put("userAvgPrice",userAvgPrice);
        respMap.put("lastSaleAmount",lastSaleAmount);
        respMap.put("lastUserAvgPrice",lastUserAvgPrice);

        baseResp.setExt(respMap);
        baseResp.success();
        return baseResp;
    }

    public BaseResp categorySaleStatis(GoodsOrderReportApiReq apiReq, BaseResp baseResp) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setMerchantId(apiReq.getMerchantId());
        goodsOrder.setShopId(apiReq.getShopId());
        goodsOrder.setStartTime(apiReq.getStartTime());
        goodsOrder.setEndTime(apiReq.getEndTime());
        List<GoodsOrderCategoryStatisApiResp> respList = goodsOrderDetailService.categorySaleStatis(goodsOrder);
        if (CollectionUtils.isEmpty(respList)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    public BaseResp saleVolumeRank(GoodsOrderReportApiReq apiReq, BaseResp baseResp) {
        GoodsOrder goodsOrder = new GoodsOrder();
        String orderBy = apiReq.getOrderBy();
        Integer limit = apiReq.getLimit();
        if ("1".equals(orderBy)){
            orderBy = "saleAmount";
        }else {
            orderBy = "saleVolume";
        }
        if (null == limit || limit < 3 || limit > 20) limit = 3 ;
        List<GoodsOrderSaleVolRankApiResp> respList = goodsOrderDetailService.saleVolumeRank(goodsOrder,orderBy,limit);
        if (CollectionUtils.isEmpty(respList)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(respList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }
}
