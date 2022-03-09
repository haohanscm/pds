package com.haohan.platform.service.sys.modules.pds.core.operation.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerOrderService;
import com.haohan.platform.service.sys.modules.pds.core.operation.IPlatformOperationService;
import com.haohan.platform.service.sys.modules.pds.core.order.IBuyOrderService;
import com.haohan.platform.service.sys.modules.pds.entity.operation.PdsTradeProcess;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import com.haohan.platform.service.sys.modules.pds.service.operation.PdsTradeProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlatformOperationServiceImpl implements IPlatformOperationService {

    @Autowired
    private PdsTradeProcessService tradeProcessService;
    @Autowired
    private IBuyerOrderService buyerOrderService;
    @Autowired
    private IBuyOrderService buyOrderService;


    // 单个采购商修改采购单
    @Override
    public BaseResp modifyBuyOrder(BuyOrder buyOrder) {
        // 平台交易流程阶段判断
        PdsTradeProcess process = tradeProcessService.fetchProcessByBuyId(buyOrder.getBuyId());
        if (null == process || StringUtils.equals(process.getStatus(), IPdsConstant.TradeProcessStatus.submit.getCode())) {
            BaseResp baseResp = BaseResp.newError();
            baseResp.setMsg("当前阶段不能修改采购单");
            return baseResp;
        }
        return buyerOrderService.modifyBuyOrder(buyOrder);
    }

    @Override
    public BaseResp triggerSummary(String buySeq, Date deliveryTime) {
        return null;
    }

    @Override
    public BaseResp triggerOfferOrderInit(List<String> supplierIdList, String buySeq, Date deliveryTime) {
        return null;
    }

    @Override
    public BaseResp goodsValuation(List<SummaryOrder> list, String buySeq, Date deliveryTime) {
        return null;
    }

    /**
     * 修改采购单状态为待确认 并向采购商推送信息
     *
     * @param buySeq
     * @param deliveryTime
     * @return
     */
    @Override
    public BaseResp updateBuyOrderStatus(String buySeq, Date deliveryTime) {
        BaseResp baseResp = BaseResp.newError();
        // 平台交易流程阶段判断
        PdsTradeProcess process = tradeProcessService.fetchProcess(buySeq, deliveryTime);
        if (null == process || StringUtils.equals(process.getStatus(), IPdsConstant.TradeProcessStatus.valuation.getCode())) {
            baseResp.setMsg("当前阶段不能修改采购单状态为待确认");
            return baseResp;
        }
        // 修改采购单状态
        baseResp = buyOrderService.updateStatusWait(buySeq, deliveryTime);
        if (!baseResp.isSuccess()) {
            return baseResp;
        }
        // 修改平台交易流程阶段
        process.setStatus(IPdsConstant.TradeProcessStatus.confirm.getCode());
        // todo 向采购商推送信息
        return baseResp;
    }

    /**
     * 采购商确认报价
     *
     * @param buyOrder
     * @return
     */
    @Override
    public BaseResp confirmPrice(BuyOrder buyOrder) {
        // 平台交易流程阶段判断
        PdsTradeProcess process = tradeProcessService.fetchProcessByBuyId(buyOrder.getBuyId());
        if (null == process || StringUtils.equals(process.getStatus(), IPdsConstant.TradeProcessStatus.confirm.getCode())) {
            BaseResp baseResp = BaseResp.newError();
            baseResp.setMsg("当前阶段不能确认报价");
            return baseResp;
        }
        return buyerOrderService.confirmBuyOrder(buyOrder);
    }

    @Override
    public BaseResp triggerTradeMatch(String buySeq, Date deliveryTime) {
        return null;
    }

    @Override
    public BaseResp triggerTradeOrder(String buySeq, Date deliveryTime) {
        return null;
    }

    @Override
    public BaseResp tradeFinish(String buySeq, Date deliveryTime) {
        return null;
    }
}
