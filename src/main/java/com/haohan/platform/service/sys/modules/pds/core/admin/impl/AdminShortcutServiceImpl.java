package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsDataResetApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminShortcutService;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerOrderService;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.core.delivery.IPdsDeliveryService;
import com.haohan.platform.service.sys.modules.pds.core.operation.IPdsOperationService;
import com.haohan.platform.service.sys.modules.pds.core.pss.IPdsGoodsStorageOpService;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.*;
import com.haohan.platform.service.sys.modules.pds.entity.req.PdsOfferOrderReq;
import com.haohan.platform.service.sys.modules.pds.entity.resp.PdsSupListParams;
import com.haohan.platform.service.sys.modules.pds.exception.PdsSummaryOperationException;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.delivery.DeliveryFlowService;
import com.haohan.platform.service.sys.modules.pds.service.order.*;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import com.haohan.platform.service.sys.modules.pss.service.info.WarehouseService;
import com.haohan.platform.service.sys.modules.weixin.mp.message.WxMpMessageService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.exception.PdsOnekeyOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/5
 */
@Service
public class AdminShortcutServiceImpl implements IPdsAdminShortcutService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BuyOrderService buyOrderService;
    @Autowired
    private IBuyerOrderService buyerOrderService;
    @Autowired
    private IPdsOperationService pdsOperationService;
    @Autowired
    private OfferOrderService offerOrderService;
    @Autowired
    private IPdsDeliveryService pdsDeliveryService;
    @Resource
    private IPdsGoodsStorageOpService pdsGoodsStorageOpService;
    @Resource
    private IPdsSummaryService pdsSummaryService;
    @Autowired
    private BuyOrderDetailService buyOrderDetailService;
    @Autowired
    private SummaryOrderService summaryOrderService;
    @Autowired
    private DeliveryFlowService deliveryFlowService;
    @Autowired
    private PdsShipOrderService pdsShipOrderService;
    @Autowired
    private PdsShipOrderDetailService pdsShipOrderDetailService;
    @Autowired
    private TradeOrderService tradeOrderService;
    @Autowired
    private TradeMatchService tradeMatchService;
    @Autowired
    private PdsSupplierService pdsSupplierService;
    @Autowired
    private IPssGoodsStorageService pssGoodsStorageService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private IPdsCommonService pdsCommonServiceImpl;
    @Autowired
    private WxMpMessageService wxMpMessageService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp confirm(String pmId, String buySeq, Date deliveryTime) throws PdsOnekeyOperationException, PdsSummaryOperationException, StorageOperationException {
        BaseResp resp = BaseResp.newError();

        logger.debug("--????????????--??????????????????--begin--\npmId:{}\nbuySeq:{}\ndeliveryTime:{}", pmId, buySeq, deliveryTime);
        // ??????????????????  ????????????
        resp = pdsSummaryService.confirmOffer(pmId, buySeq, deliveryTime);
        logger.debug("--????????????--??????????????????--end--\n??????:{}", resp.getMsg());
        if (!resp.isSuccess()) {
            throw new PdsOnekeyOperationException("??????????????????:" + resp.getMsg());
        }

        // ?????????????????????  ???????????????
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setPmId(pmId);
        buyOrder.setBuySeq(buySeq);
        buyOrder.setDeliveryTime(deliveryTime);
        buyOrder.setStatus(IPdsConstant.BuyOrderStatus.wait.getCode());
        List<BuyOrder> buyOrderList = buyOrderService.findList(buyOrder);
        logger.debug("--????????????--?????????????????????--begin--\n???????????????:{}", buyOrderList.size());
        for (BuyOrder order : buyOrderList) {
            resp = buyerOrderService.confirmBuyOrder(order);
            if (!resp.isSuccess()) {
                throw new PdsOnekeyOperationException("??????????????????:" + resp.getMsg());
            }
        }
        logger.debug("--????????????--?????????????????????--end--\n");

        logger.debug("--????????????--????????????--begin--\n");
        // ????????????  ????????????
        resp = pdsSummaryService.tradeMatch(pmId, buySeq, deliveryTime);
        if (!resp.isSuccess()) {
            throw new PdsOnekeyOperationException("??????????????????:" + resp.getMsg());
        }
        logger.debug("--????????????--????????????--end--\n??????:{}", resp.getMsg());

        logger.debug("--????????????--???????????????--begin--\n");
        // ???????????????  ????????????
        resp = pdsSummaryService.createTradeOrder(pmId, buySeq, deliveryTime);
        if (!resp.isSuccess()) {
            throw new PdsOnekeyOperationException("?????????????????????:" + resp.getMsg());
        }
        logger.debug("--????????????--???????????????--end--\n??????:{}", resp.getMsg());

        logger.debug("--????????????--??????--begin--\n");
        // ????????????????????????  ???????????? ?????????????????????
        PdsOfferOrderReq pdsOfferOrderReq = new PdsOfferOrderReq();
        pdsOfferOrderReq.setPmId(pmId);
        pdsOfferOrderReq.setBuySeq(buySeq);
        pdsOfferOrderReq.setDeliveryDate(deliveryTime);
        // ????????????????????? ?????????  ?????????
        String pCode = IPdsConstant.OfferShipStatus.prepare.getCode();
        String tCode = IPdsConstant.OfferShipStatus.take.getCode();
        String[] shipStatusArray = new String[]{pCode, tCode};
        pdsOfferOrderReq.setShipStatusArry(shipStatusArray);
        // ??????????????????
        pdsOfferOrderReq.setStatus(IPdsConstant.OfferOrderStatus.success.getCode());
        List<PdsSupListParams> supplierList = offerOrderService.findSupList(pdsOfferOrderReq);
        if (Collections3.isEmpty(supplierList)) {
            throw new PdsOnekeyOperationException("????????????:???????????????????????????");
        }
        logger.debug("--????????????--??????--list--\n???????????????:{}", supplierList.size());
        for (PdsSupListParams params : supplierList) {
            // ????????????/????????????
            resp = pdsOperationService.supplierFreightConfirm(pmId, params.getSupplierId(), buySeq, deliveryTime);
            if (!resp.isSuccess()) {
                throw new PdsOnekeyOperationException("????????????:" + resp.getMsg());
            }
        }
        logger.debug("--????????????--??????--end--\n");

//        //????????????
//        resp = pdsAdminSortOutService.fastSortOut(deliveryTime,buySeq,pmId);
//        if (!resp.isSuccess()){
//            throw new PdsOnekeyOperationException("????????????:"+resp.getMsg());
//        }
//        //????????????,??????
//        resp = loadAndArrived(pmId,buySeq,deliveryTime);
//        if (!resp.isSuccess()){
//            throw new PdsOnekeyOperationException("??????????????????:"+resp.getMsg());
//        }

        return resp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp loadAndArrived(String pmId, String buySeq, Date deliveryTime) throws PdsOnekeyOperationException {
        BaseResp baseResp = BaseResp.newError();

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setPmId(pmId);
        tradeOrder.setBuySeq(buySeq);
        tradeOrder.setDeliveryTime(deliveryTime);
        try {
            baseResp = pdsDeliveryService.truckLoad(tradeOrder);
        } catch (Exception e) {
            throw new PdsOnekeyOperationException("????????????", e);
        }
        if (!baseResp.isSuccess()) {
            throw new PdsOnekeyOperationException("????????????:" + baseResp.getMsg());
        }
        // ?????????????????????  ??????????????? ????????????
        TradeOrder queryTrade = new TradeOrder();
        queryTrade.setPmId(pmId);
        queryTrade.setBuySeq(buySeq);
        queryTrade.setDeliveryTime(deliveryTime);
        queryTrade.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(queryTrade);
        for (TradeOrder t : tradeOrderList) {
            t.setBuyerStatus(IPdsConstant.BuyerDealStatus.wait_check.getCode());
            t.setDeliveryStatus(IPdsConstant.DeliveryStatus.arrived.getCode());
            tradeOrderService.save(t);
        }
        // ????????????
        DeliveryFlow deliveryFlow = new DeliveryFlow();
        deliveryFlow.setPmId(pmId);
        deliveryFlow.setDeliverySeq(buySeq);
        deliveryFlow.setDeliveryDate(deliveryTime);
        List<DeliveryFlow> deliveryFlowList = deliveryFlowService.findList(deliveryFlow);
        for (DeliveryFlow flow : deliveryFlowList) {
            flow.setStatus(IPdsConstant.DeliveryStatus.arrived.getCode());

            PdsShipOrder pdsShipOrder = new PdsShipOrder();
            pdsShipOrder.setDeliveryId(flow.getDeliveryId());
            List<PdsShipOrder> pdsShipOrderList = pdsShipOrderService.findList(pdsShipOrder);
            for (PdsShipOrder item : pdsShipOrderList) {
                item.setStatus(IPdsConstant.DeliveryStatus.arrived.getCode());
                pdsShipOrderService.save(item);
            }
            deliveryFlowService.save(flow);
        }

        // ??????BuyOrderStatus??? ?????????????????????????????????
        BuyOrderDetail update = new BuyOrderDetail();
        update.setPmId(pmId);
        update.setDeliveryDate(deliveryTime);
        update.setBuySeq(buySeq);
        update.setStatus(IPdsConstant.BuyOrderStatus.delivery.getCode());
        update.setFinalStatus(IPdsConstant.BuyOrderStatus.arrive.getCode());
        buyOrderDetailService.updateStatusBatch(update);

        return baseResp.success();
    }

    @Override
    public BaseResp resetSummary(PdsDataResetApiReq dataResetReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = dataResetReq.getPmId();
        Date deliveryDate = dataResetReq.getDeliveryDate();
        String buySeq = dataResetReq.getBuySeq();

        int affectRows = 0;    //?????????????????????(???????????????????????????)

        //???????????????
        DeliveryFlow deliveryFlow = new DeliveryFlow();
        deliveryFlow.setPmId(pmId);
        deliveryFlow.setDeliveryDate(deliveryDate);
        deliveryFlow.setDeliverySeq(buySeq);
        affectRows += deliveryFlowService.deleteByDateSeqPmId(deliveryFlow);

        //???????????????
        PdsShipOrder pdsShipOrder = new PdsShipOrder();
        pdsShipOrder.setPmId(pmId);
        pdsShipOrder.setDeliveryDate(deliveryDate);
        pdsShipOrder.setDeliverySeq(buySeq);
        List<PdsShipOrder> shipOrderList = pdsShipOrderService.findList(pdsShipOrder);
        affectRows += shipOrderList.size();
        for (PdsShipOrder shipOrder : shipOrderList) {
            affectRows += pdsShipOrderDetailService.deleteByShipOrderId(shipOrder.getShipId());
            pdsShipOrderService.delete(shipOrder);
        }

        //???????????????
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setPmId(pmId);
        tradeOrder.setDeliveryTime(deliveryDate);
        tradeOrder.setBuySeq(buySeq);
        affectRows += tradeOrderService.deleteByDateSeqPmId(tradeOrder);

        //???????????????
        SummaryOrder summaryOrder = new SummaryOrder();
        summaryOrder.setPmId(pmId);
        summaryOrder.setDeliveryTime(deliveryDate);
        summaryOrder.setBuySeq(buySeq);
        List<SummaryOrder> summaryOrderList = summaryOrderService.findList(summaryOrder);
        affectRows += summaryOrderList.size();
        BigDecimal addStockNum;
        for (SummaryOrder sumOrder : summaryOrderList) {
            addStockNum = BigDecimal.ZERO;
            List<TradeMatch> tradeMatchList = tradeMatchService.findByAskOrder(sumOrder.getSummaryOrderId());
            affectRows += tradeMatchList.size();
            //??????????????????
            for (TradeMatch tradeMatch : tradeMatchList) {
                tradeMatchService.delete(tradeMatch);
            }

            //???????????????
            List<OfferOrder> offerOrderList = offerOrderService.findByAskId(sumOrder.getSummaryOrderId());
            affectRows += offerOrderList.size();
            for (OfferOrder offerOrder : offerOrderList) {
                PdsSupplier pdsSupplier = pdsSupplierService.get(offerOrder.getSupplierId());
                tradeOrder.setOfferId(offerOrder.getOfferOrderId());
                List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
                if (CollectionUtils.isNotEmpty(tradeOrderList)) {
                    TradeOrder item = tradeOrderList.get(0);
                    boolean truckFlag = IPdsConstant.OperatorViewStatus.truckLoad.getCode().equals(item.getOpStatus());
                    boolean stockSupTypeFlag = IPdsConstant.SupplierType.stock.getCode().equals(pdsSupplier);
                    if (!truckFlag && !stockSupTypeFlag) {
                        addStockNum = addStockNum.add(offerOrder.getBuyNum());
                    }
                }
                offerOrderService.delete(offerOrder);
            }
            //????????????
            PssWarehouse warehouse = new PssWarehouse();
            warehouse.setMerchantId(pmId);
            warehouse.setStatus(ICommonConstant.IsEnable.enable.getCode());
            List<PssWarehouse> warehouseList = warehouseService.findList(warehouse);
            if (CollectionUtils.isNotEmpty(warehouseList)) {
                pssGoodsStorageService.outStock(warehouseList.get(0).getId(), sumOrder.getGoodsId(), addStockNum);
                affectRows += 1;
            }
            //??????PSS????????? TODO

            //???????????????
            summaryOrderService.delete(sumOrder);
        }

        //???????????????
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setPmId(pmId);
        buyOrder.setDeliveryTime(deliveryDate);
        buyOrder.setBuySeq(buySeq);
        List<BuyOrder> buyOrderList = buyOrderService.findList(buyOrder);
        if (CollectionUtils.isEmpty(buyOrderList)) {
            baseResp.setMsg("????????????,??????????????????");
            return baseResp;
        }
        affectRows += buyOrderList.size();
        String cancelStatus = IPdsConstant.BuyOrderStatus.cancel.getCode();
        String submitStatus = IPdsConstant.BuyOrderStatus.submit.getCode();
        for (BuyOrder order : buyOrderList) {
            // ??????????????????????????????
            if (StringUtils.equals(order.getStatus(), cancelStatus)) {
                affectRows--;
                continue;
            }
            order.setStatus(submitStatus);
            List<BuyOrderDetail> detailList = buyOrderDetailService.findListByBuyId(order.getBuyId());
            affectRows += detailList.size();
            for (BuyOrderDetail detail : detailList) {
                // ???????????? ??????
                detail.setSmmaryBuyId(null);
                detail.setSummaryFlag(IPdsConstant.DetailSummaryFlag.wait.getCode());
                // ????????????????????????????????????
                if (!StringUtils.equals(detail.getStatus(), cancelStatus)) {
                    detail.setStatus(submitStatus);
                }
                buyOrderDetailService.save(detail);
            }
            buyOrderService.save(order);
        }

        baseResp.success();
        HashMap<String, Integer> respMap = new HashMap<>(8);
        respMap.put("affectRows", affectRows);
        baseResp.setExt(respMap);
        return baseResp;
    }

    @Override
    public BaseResp goodsReceived(String pmId, String buySeq, Date deliveryTime) {
        BaseResp baseResp = BaseResp.newError();
        // ????????? ?????????
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setPmId(pmId);
        buyOrder.setDeliveryTime(deliveryTime);
        buyOrder.setBuySeq(buySeq);
        buyOrder.setStatus(IPdsConstant.BuyOrderStatus.arrive.getCode());
        List<BuyOrder> buyOrderList = buyOrderService.findList(buyOrder);
        if (Collections3.isEmpty(buyOrderList)) {
            baseResp.setMsg("????????????????????????");
            return baseResp;
        }
        TradeOrder tradeOrder = new TradeOrder();
        for (BuyOrder order : buyOrderList) {
            tradeOrder.setBuyId(order.getBuyId());
            tradeOrder.setBuyerId(order.getBuyerId());
            baseResp = buyerOrderService.confirmAllGoods(tradeOrder);
            // ???????????? ????????????
            if (baseResp.isSuccess()) {
                UserOpenPlatform userOpenPlatform = pdsCommonServiceImpl.fetchOpenUserByUid(order.getBuyerUid(), IPdsConstant.WX_MP_APPID, order.getBuyerId(), IPdsConstant.CompanyType.buyer);
                if (null != userOpenPlatform && null != order) {
                    wxMpMessageService.orderDealCloseNotify(userOpenPlatform, order);
                }
            }
            logger.debug("--??????---\n?????????{}\n ?????????:{}\n{}", order.getBuyerName(), order.getBuyId(), baseResp.getMsg());
        }
        return baseResp;
    }
}
