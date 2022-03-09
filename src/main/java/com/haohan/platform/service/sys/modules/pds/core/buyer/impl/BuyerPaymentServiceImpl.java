package com.haohan.platform.service.sys.modules.pds.core.buyer.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.cost.BuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.ServiceOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.pds.utils.ComputeSumPrice;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2018/10/30.
 */
@Service
public class BuyerPaymentServiceImpl implements IBuyerPaymentService {

    @Autowired
    private BuyerPaymentService buyerPaymentService;
    @Autowired
    @Lazy(true)
    private TradeOrderService tradeOrderService;
    @Autowired
    @Lazy(true)
    private BuyOrderService buyOrderService;
    @Autowired
    @Lazy(true)
    private ServiceOrderService serviceOrderService;

    @Override
    public BaseResp queryPaymentList(BuyerPayment buyerPayment, Page page) {
        BaseResp baseResp = BaseResp.newError();
        Page<BuyerPayment> result = buyerPaymentService.findPage(page, buyerPayment);
        ApiRespPage respPage = new ApiRespPage(result);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp totalPayment(BuyerPayment buyerPayment) {
        BaseResp baseResp = BaseResp.newError();
        BuyerPayment result = buyerPaymentService.countPayment(buyerPayment);
        if (null == result) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            baseResp.setExt(result);
        }
        return baseResp;
    }

    /**
     * 生成采购商货款账单  buyerId/buyId
     * 并修改采购单中总价
     *
     * @param buyOrder
     * @return
     */
    @Override
    public BaseResp paymentRecord(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        String buyId = buyOrder.getBuyId();
        String buyerId = buyOrder.getBuyerId();
        // 采购单查询
        BuyOrder oldBuyOrder = buyOrderService.fetchByBuyId(buyId);
        if (null == oldBuyOrder || !StringUtils.equals(oldBuyOrder.getStatus(), IPdsConstant.BuyOrderStatus.success.getCode())) {
            baseResp.setMsg("采购单未成交");
            return baseResp;
        }
        if (!StringUtils.equals(buyerId, oldBuyOrder.getBuyerId())) {
            baseResp.setMsg("采购商有误");
            return baseResp;
        }
        // 采购商货款账单
        BuyerPayment buyerPayment = buyerPaymentService.fetchByBuyId(buyId);
        if (null == buyerPayment) {
            buyerPayment = new BuyerPayment();
            buyerPayment.setBuyId(buyId);
            buyerPayment.setBuyerId(buyerId);
        } else if (StringUtils.equals(buyerPayment.getStatus(), ICommonConstant.YesNoType.yes.getCode())) {
            baseResp.setMsg("该采购单已结算");
            return baseResp;
        }
        buyerPayment.setPmId(oldBuyOrder.getPmId());
        // 货款时间修改为使用采购单的送货时间
        buyerPayment.setBuyDate(oldBuyOrder.getDeliveryTime());
        buyerPayment.setStatus(ICommonConstant.YesNoType.no.getCode());
        // 查询交易单列表  已收货
        TradeOrder trade = new TradeOrder();
        trade.setBuyerId(buyerId);
        trade.setBuyId(buyId);
        trade.setBuyerStatus(IPdsConstant.BuyerDealStatus.received.getCode());
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(trade);
        if (Collections3.isEmpty(tradeOrderList)) {
            baseResp.setMsg("该采购单还未收货");
            return baseResp;
        }
        // 按分拣数量/采购价格 计算金额
        List<ComputeSumPrice> sumPriceList = new ArrayList<>();
        for (TradeOrder order : tradeOrderList) {
            sumPriceList.add(new ComputeSumPrice(order.getSortOutNum(), order.getBuyPrice()));
        }
        BigDecimal sumPrice = ComputeSumPrice.sum(sumPriceList);
        // 运费
        BigDecimal shipFee = oldBuyOrder.getShipFee();
        shipFee = (null == shipFee) ? BigDecimal.ZERO : shipFee;
        buyerPayment.setBuyerPayment(sumPrice.add(shipFee));
        // 商品数量
        buyerPayment.setGoodsNum("" + tradeOrderList.size());
        // 售后单货款  状态为已解决
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setBuyerId(buyerId);
        serviceOrder.setBuyId(buyId);
        serviceOrder.setStatus(IPdsConstant.ServiceStatus.finish.getCode());
        List<ServiceOrder> serviceOrderList = serviceOrderService.findJoinList(serviceOrder);
        BigDecimal refundAmount = BigDecimal.ZERO;
        if (!Collections3.isEmpty(serviceOrderList)) {
            List<String> serviceIdList = new ArrayList<>(serviceOrderList.size());
            for (ServiceOrder s : serviceOrderList) {
                if (null != s.getRefundAmount()) {
                    refundAmount = refundAmount.add(s.getRefundAmount());
                    serviceIdList.add(s.getServiceId());
                }
            }
            if (!Collections3.isEmpty(serviceIdList)) {
                buyerPayment.setServiceId(StringUtils.join(serviceIdList, ","));
            }
        }
        buyerPayment.setAfterSalePayment(refundAmount);
        buyerPaymentService.save(buyerPayment);
        // 只修改采购单中总价
        oldBuyOrder.setTotalPrice(sumPrice);
        oldBuyOrder.setStatus(null);
        buyOrderService.updatePart(oldBuyOrder);
        baseResp.success();
        baseResp.setExt(buyerPayment);
        return baseResp;
    }

    @Override
    public BaseResp paymentRecordBatch(List<BuyOrder> buyOrderList, StringBuilder successMsg, StringBuilder errorMsg) {
        BaseResp baseResp = BaseResp.newError();
        BaseResp resp;
        int successNum = 0;
        int failedNum = 0;
        for (BuyOrder order : buyOrderList) {
            resp = paymentRecord(order);
            if (resp.isSuccess()) {
                successNum++;
                successMsg.append(order.getBuyId()).append(";");
            } else {
                failedNum++;
                errorMsg.append(order.getBuyId()).append(resp.getMsg()).append(";");
            }
        }
        successMsg.insert(0, "共" + successNum + "个采购单成功生成货款记录：");
        if (failedNum > 0) {
            errorMsg.insert(0, "共" + failedNum + "个采购单生成货款记录失败：");
        }
        if (successNum > 0) {
            baseResp.success();
        }
        return baseResp;
    }
}
