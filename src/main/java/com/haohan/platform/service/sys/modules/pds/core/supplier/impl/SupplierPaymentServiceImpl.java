package com.haohan.platform.service.sys.modules.pds.core.supplier.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierPaymentService;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;
import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.cost.SupplierPaymentService;
import com.haohan.platform.service.sys.modules.pds.service.order.ServiceOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.pds.utils.ComputeSumPrice;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SupplierPaymentServiceImpl implements ISupplierPaymentService {

    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private SupplierPaymentService supplierPaymentService;
    @Autowired
    private ServiceOrderService serviceOrderService;

    @Override
    public BaseResp queryPaymentList(SupplierPayment supplierPayment, Page page) {
        // TODO 查看账款列表
        return null;
    }

    @Override
    public BaseResp totalPayment(SupplierPayment supplierPayment) {
        // TODO 查看账款统计
        return null;
    }


    /**
     * 生成供应商货款记录  按日 一条记录
     */
    @Override
    public BaseResp paymentRecord(SupplierPayment payment) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = payment.getPmId();
        String supplierId = payment.getSupplierId();
        Date supplyDate = payment.getSupplyDate();
        if (StringUtils.isAnyEmpty(pmId, supplierId) || null == supplyDate) {
            baseResp.setMsg("缺少pmId/supplierId/supplyDate");
            return baseResp;
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setSupplierId(supplierId);
        tradeOrder.setDeliveryTime(supplyDate);
        tradeOrder.setPmId(pmId);
        // 供应商 已发货
        tradeOrder.setSupplierStatus(IPdsConstant.SupplierDealStatus.shipped.getCode());
        // 供应商 交易单
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
        if (Collections3.isEmpty(tradeOrderList)) {
            baseResp.setMsg("没有找到相应数据");
            return baseResp;
        }
        // 供应商货款账单  新增或修改
        SupplierPayment supplierPayment = supplierPaymentService.findByDateAndSupplier(payment);
        if (null == supplierPayment) {
            supplierPayment = new SupplierPayment();
            supplierPayment.setSupplierId(supplierId);
            supplierPayment.setSupplyDate(supplyDate);
        } else if (StringUtils.equals(supplierPayment.getStatus(), ICommonConstant.YesNoType.yes.getCode())) {
            baseResp.setMsg("当日货款已结算");
            return baseResp;
        }
        supplierPayment.setPmId(pmId);
        // 设置状态
        supplierPayment.setStatus(ICommonConstant.YesNoType.no.getCode());
        // 询价单列表(采购单汇总编号)
        Set<String> askOrderIdSet = new TreeSet<>();
        // 按分拣数量/供应单价 计算金额
        List<ComputeSumPrice> sumPriceList = new ArrayList<>();
        for (TradeOrder order : tradeOrderList) {
            sumPriceList.add(new ComputeSumPrice(order.getSortOutNum(), order.getSupplyPrice()));
            askOrderIdSet.add(order.getSummaryBuyId());
        }
        BigDecimal sumPrice = ComputeSumPrice.sum(sumPriceList);
        supplierPayment.setSupplierPayment(sumPrice);
        if (!Collections3.isEmpty(askOrderIdSet)) {
            supplierPayment.setAskOrderId(Collections3.convertToString(askOrderIdSet, ","));
        }
        // 商品数量
        supplierPayment.setGoodsNum("" + tradeOrderList.size());
        // 售后单货款  状态为已解决
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setSupplierId(supplierId);
        serviceOrder.setDeliveryTime(supplyDate);
        serviceOrder.setStatus(IPdsConstant.ServiceStatus.finish.getCode());
        serviceOrder.setPmId(pmId);
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
                supplierPayment.setServiceId(StringUtils.join(serviceIdList, ","));
            }
        }
        supplierPayment.setAfterSalePayment(refundAmount);
        supplierPaymentService.save(supplierPayment);
        baseResp.success();
        baseResp.setMsg("操作成功,货款编号:" + supplierPayment.getSupplierPaymentId());
        baseResp.setExt(supplierPayment);
        return baseResp;
    }
}
