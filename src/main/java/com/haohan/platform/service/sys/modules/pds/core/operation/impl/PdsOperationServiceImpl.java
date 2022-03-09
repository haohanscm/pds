package com.haohan.platform.service.sys.modules.pds.core.operation.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.operate.PdsAfterSaleOrderReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.operation.IPdsOperationService;
import com.haohan.platform.service.sys.modules.pds.core.pss.IPdsGoodsStorageOpService;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.entity.req.PdsOfferOrderReq;
import com.haohan.platform.service.sys.modules.pds.entity.resp.PdsGoodsListParams;
import com.haohan.platform.service.sys.modules.pds.entity.resp.PdsSupListParams;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.ServiceOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/10/29
 */
@Service
public class PdsOperationServiceImpl implements IPdsOperationService {
    @Resource
    private OfferOrderService offerOrderService;
    @Resource
    private TradeOrderService tradeOrderService;
    @Resource
    private ServiceOrderService serviceOrderService;
    @Autowired
    private IPdsGoodsStorageOpService pdsGoodsStorageOpService;

    @Override
    public BaseResp findDealSupList(PdsOfferOrderReq pdsOfferOrderReq) {
        BaseResp baseResp = BaseResp.newError();
        String shipStatus = pdsOfferOrderReq.getShipStatus();

        String pCode = IPdsConstant.SupplierDealStatus.prepare.getCode();
        String tCode = IPdsConstant.SupplierDealStatus.take.getCode();
        if (StringUtils.isEmpty(shipStatus)) {
            String[] shipStatusArray = new String[]{pCode, tCode};
            pdsOfferOrderReq.setShipStatusArry(shipStatusArray);
        }
        // 已成交报价单
        pdsOfferOrderReq.setStatus(IPdsConstant.OfferOrderStatus.success.getCode());

        List<PdsSupListParams> list = offerOrderService.findSupList(pdsOfferOrderReq);

        if (CollectionUtils.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        for (PdsSupListParams pdsSupListParams : list) {
            pdsOfferOrderReq.setSupplierId(pdsSupListParams.getSupplierId());
            Integer categoryNum = offerOrderService.countCategoryNum(pdsOfferOrderReq);
            Integer gNum = offerOrderService.countGoodsNum(pdsOfferOrderReq);
            pdsSupListParams.setCategoryNum(categoryNum);
            pdsSupListParams.setGoodsCount(gNum);
        }

        baseResp.success();
        baseResp.setExt(list.stream().collect(Collectors.toCollection(ArrayList::new)));
        return baseResp;
    }


    @Override
    public BaseResp findSupGoodsList(Date deliveryDate, String buySeq, String supplierId, String shipStatus) {
        BaseResp baseResp = BaseResp.newError();

        List<PdsGoodsListParams> goodsList = offerOrderService.findSupGoodsList(deliveryDate, buySeq, supplierId, shipStatus);
        if (CollectionUtils.isEmpty(goodsList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(goodsList.stream().collect(Collectors.toCollection(ArrayList::new)));
        return baseResp;
    }

    @Override
    public BaseResp freightConfirm(String operatorUid, String offerOrderId, String goodsId) {
        BaseResp baseResp = BaseResp.newError();

        OfferOrder offerOrder = offerOrderService.fetchByOrderId(offerOrderId);
        if (null == offerOrder) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOfferId(offerOrderId);
        tradeOrder.setGoodsId(goodsId);
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
        if (CollectionUtils.isEmpty(tradeOrderList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        for (TradeOrder order : tradeOrderList) {
            order.setSupplierStatus(IPdsConstant.SupplierDealStatus.shipped.getCode());
            order.setOpStatus(IPdsConstant.OperatorViewStatus.waitSortOut.getCode());
            order.setBuyOperator(operatorUid);
            tradeOrderService.save(order);
        }

        offerOrder.setShipStatus(IPdsConstant.SupplierDealStatus.shipped.getCode());
        offerOrderService.save(offerOrder);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp countDealNum(Date date, String pmId) {
        BaseResp baseResp = BaseResp.newError();
        String tStatus = IPdsConstant.OperatorViewStatus.feight.getCode();
        Integer num = tradeOrderService.countFreightNum(date, tStatus, pmId);

        baseResp.success();
        baseResp.setExt(num);
        return baseResp;
    }

    @Override
    public BaseResp afterSale(PdsAfterSaleOrderReq pdsAfterSaleOrderReq) {
        BaseResp baseResp = BaseResp.newError();
        String[] photos = pdsAfterSaleOrderReq.getPhotos();
        String offerOrderId = pdsAfterSaleOrderReq.getOfferOrderId();
        String goodsId = pdsAfterSaleOrderReq.getGoodsId();
        String pmId = pdsAfterSaleOrderReq.getPmId();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < photos.length; i++) {
            sb.append(photos[i]);
            if (i != photos.length) {
                sb.append(",");
            }
        }

        //修改报价单为未成交
        OfferOrder offerOrder = offerOrderService.fetchByOrderId(offerOrderId);
        offerOrder.setStatus(IPdsConstant.OfferOrderStatus.cancel.getCode());
        offerOrderService.save(offerOrder);

        //修改交易单状态为待售后
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOfferId(offerOrderId);
        tradeOrder.setGoodsId(goodsId);
        tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
        for (TradeOrder order : tradeOrderList) {
            order.setTransStatus(IPdsConstant.TradeOrderStatus.after_sale.getCode());
            tradeOrderService.save(order);
            //新增售后单
            ServiceOrder serviceOrder = new ServiceOrder();
            serviceOrder.setPmId(pmId);
            serviceOrder.setServiceId(PdsCommonUtil.incrIdByClass(ServiceOrder.class, IPdsConstant.SERVICEORDER_PREFIX));
            serviceOrder.setTradeId(order.getTradeId());
            serviceOrder.setFeedbackInfo(sb.toString() + "@_@" + pdsAfterSaleOrderReq.getNote());
            serviceOrder.setLinkMan(pdsAfterSaleOrderReq.getUid());
            serviceOrder.setServiceCategory(pdsAfterSaleOrderReq.getServiceCategory());
            serviceOrder.setStatus(IPdsConstant.ServiceStatus.wait.getCode());
            serviceOrder.setBuyerId(order.getBuyerId());
            serviceOrder.setSupplierId(order.getSupplierId());
            serviceOrderService.save(serviceOrder);
        }
        tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.after_sale.getCode());
        baseResp.success();
        return baseResp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp supplierFreightConfirm(String pmId, String supplierId, String buySeq, Date deliveryDate) throws StorageOperationException {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(pmId, supplierId, buySeq) || null == deliveryDate) {
            baseResp.setMsg("缺少参数pmId/supplier/buySeq/deliveryDate");
            return baseResp;
        }
        // 修改交易单状态
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setPmId(pmId);
        tradeOrder.setSupplierId(supplierId);
        tradeOrder.setBuySeq(buySeq);
        tradeOrder.setDeliveryTime(deliveryDate);
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
        if (Collections3.isEmpty(tradeOrderList)) {
            baseResp.setMsg("找不到交易单");
            return baseResp;
        }
        // 修改的报价单
        Set<String> offerIdSet = new HashSet<>();
        for (TradeOrder order : tradeOrderList) {
            if (!StringUtils.equals(order.getSupplierStatus(), IPdsConstant.SupplierDealStatus.shipped.getCode())) {
                order.setSupplierStatus(IPdsConstant.SupplierDealStatus.shipped.getCode());
                order.setOpStatus(IPdsConstant.OperatorViewStatus.waitSortOut.getCode());
                tradeOrderService.updateStatus(order);
                offerIdSet.add(order.getOfferId());
            }
        }
        if (Collections3.isEmpty(offerIdSet)) {
            baseResp.setMsg("没有需要确认的交易单");
            return baseResp;
        }
        // 修改报价单状态
        OfferOrder offerOrder;
        for (String offerId : offerIdSet) {
            offerOrder = offerOrderService.fetchByOrderId(offerId);
            if (null != offerOrder) {
                offerOrder.setShipStatus(IPdsConstant.OfferShipStatus.receiveCargo.getCode());
                offerOrderService.save(offerOrder);
            }
        }
        String[] offerIds = offerIdSet.toArray(new String[offerIdSet.size()]);
        if (offerIds.length > 0) {
            pdsGoodsStorageOpService.freightGoodsEnterStock(offerIds, pmId);
        }

        baseResp.success();
        return baseResp;
    }


}
