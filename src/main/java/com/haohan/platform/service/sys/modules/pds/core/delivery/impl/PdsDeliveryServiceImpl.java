package com.haohan.platform.service.sys.modules.pds.core.delivery.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsShipOrderApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery.PdsShipBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiDeliveryBuyerResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.core.delivery.IPdsDeliveryService;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.delivery.DeliveryFlowService;
import com.haohan.platform.service.sys.modules.pds.service.delivery.TruckManageService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.PdsShipOrderDetailService;
import com.haohan.platform.service.sys.modules.pds.service.order.PdsShipOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.weixin.mp.message.WxMpMessageService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PdsDeliveryServiceImpl implements IPdsDeliveryService {

    @Autowired
    private TradeOrderService tradeOrderService;
    @Resource
    private DeliveryFlowService deliveryFlowService;
    @Resource
    private MerchantEmployeeService merchantEmployeeService;
    @Resource
    private TruckManageService truckManageService;
    @Resource
    private BuyOrderService buyOrderService;
    @Resource
    private WxMpMessageService wxMpMessageService;
    @Resource
    private IPdsCommonService pdsCommonServiceImpl;
    @Resource
    private PdsShipOrderService pdsShipOrderService;
    @Resource
    private PdsShipOrderDetailService pdsShipOrderDetailService;
    @Resource
    private IPssGoodsStorageService mercGoodsStorageService;


    // TODO  sql需修改 未使用
    @Override
    public BaseResp goodsArrived(DeliveryFlow deliveryFlow) {
        BaseResp baseResp = BaseResp.newError();
        // 修改前状态为 配送中 改为 已送达
        deliveryFlow.setStatus(IPdsConstant.DeliveryStatus.delivering.getCode());
        deliveryFlow.setFinalStatus(IPdsConstant.DeliveryStatus.arrived.getCode());
        int i = tradeOrderService.updateStatusByDelivery(deliveryFlow);
        if (i > 0) {
            baseResp.success();
            baseResp.setExt(i);
        } else {
            baseResp.setMsg("没有可修改的数据");
        }
        return baseResp;
    }

    @Override
    public BaseResp findDeliveryBuyerList(PdsShipBuyerApiReq shipBuyerReq, Page reqPage) {
        BaseResp baseResp = BaseResp.newError();
        Date date = shipBuyerReq.getDeliveryDate();
        String uid = shipBuyerReq.getUid();
        String buySeq = shipBuyerReq.getBuySeq();
        String pmId = shipBuyerReq.getPmId();

        MerchantEmployee merchantEmployee = merchantEmployeeService.fetchByUidAndRole(uid, IPdsConstant.EmployeeRole.driver);
        if (null == merchantEmployee) {
            baseResp.setMsg("未找到司机信息或无查询权限");
            return baseResp;
        }

        DeliveryFlow deliveryFlow = new DeliveryFlow();
        deliveryFlow.setDeliveryDate(date);
        deliveryFlow.setDeliverySeq(buySeq);
        deliveryFlow.setDriver(merchantEmployee.getId());
        deliveryFlow.setPmId(pmId);
        List<DeliveryFlow> deliveryFlowList = deliveryFlowService.findList(deliveryFlow);
        if (CollectionUtils.isEmpty(deliveryFlowList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        Page<PdsApiDeliveryBuyerResp> respPage = pdsShipOrderService.findShipOrderInfoList(deliveryFlow, reqPage);
        List<PdsApiDeliveryBuyerResp> respList = respPage.getList();
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        for (PdsApiDeliveryBuyerResp entity : respList) {
            entity.setGoodsCount(pdsShipOrderDetailService.countDriverShipGoods(date, buySeq, merchantEmployee.getId(), entity.getBuyerId()));
        }

        BaseList<PdsApiDeliveryBuyerResp> baseList = new BaseList<>();
        baseList.setCurPage(reqPage.getPageNo());
        baseList.setPageSize(reqPage.getPageSize());
        baseList.setList(respList);
        baseList.setTotalRows(new Long(reqPage.getCount()).intValue());
        baseList.setTotalPage(reqPage.getTotalPage());
        baseList.success();
        return baseList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp truckLoad(TradeOrder tradeOrder) throws Exception {
        BaseResp baseResp = BaseResp.newError();

        String pmId = tradeOrder.getPmId();
        tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
        tradeOrder.setDeliveryStatus(IPdsConstant.DeliveryStatus.wait_delivery.getCode());
        tradeOrder.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
        tradeOrder.setDeliveryType(IPdsConstant.DeliveryType.home.getCode());
        // 查询是否有已分拣待装车的商品
        List<TradeOrder> waitList = tradeOrderService.findList(tradeOrder);
        if (Collections3.isEmpty(waitList)) {
            baseResp.setMsg("无可装车商品");
            return baseResp;
        }

        //获取空闲车辆列表
        Date now = new Date();
        TruckManage truckManage = new TruckManage();
        truckManage.setPmId(pmId);
        List<TruckManage> truckList = truckManageService.findEmptyEnableList(truckManage);
        if (CollectionUtils.isEmpty(truckList)) {
            baseResp.setMsg("无可用车辆");
            return baseResp;
        }
        // 默认使用车辆
        TruckManage truck = truckList.get(0);
//        for (TruckManage truck : truckList){
        PdsShipOrderApiReq shipOrderReq = new PdsShipOrderApiReq();
        shipOrderReq.setPmId(pmId);
        shipOrderReq.setBuySeq(tradeOrder.getBuySeq());
        shipOrderReq.setDeliveryDate(tradeOrder.getDeliveryTime());
        shipOrderReq.setDriverId(truck.getDriver());
        DeliveryFlow deliveryFlow = deliveryFlowService.fetchByDateSeqDriver(shipOrderReq, null);
        if (null == deliveryFlow) {
            deliveryFlow = new DeliveryFlow();
            deliveryFlow.setDeliveryDate(tradeOrder.getDeliveryTime());
            deliveryFlow.setTruckNo(truck.getTruckNo());
            deliveryFlow.setDriver(truck.getDriver());
            deliveryFlow.setDeliverySeq(tradeOrder.getBuySeq());
            deliveryFlow.setLoadTruckTime(now);
            deliveryFlow.setStatus(IPdsConstant.DeliveryStatus.wait_delivery.getCode());
            deliveryFlow.setPmId(pmId);
            deliveryFlowService.save(deliveryFlow);
        }


        //获取需要配送的所有商家
        List<String> buyerIdList = tradeOrderService.findBuyerIdList(tradeOrder.getDeliveryTime(), tradeOrder.getBuySeq(), pmId);

        for (String buyerId : buyerIdList) {
            PdsShipOrder pdsShipOrder = new PdsShipOrder();
            pdsShipOrder.setPmId(pmId);
            pdsShipOrder.setBuyerId(buyerId);
            pdsShipOrder.setDeliveryId(deliveryFlow.getDeliveryId());
            pdsShipOrder.setStatus(IPdsConstant.DeliveryStatus.wait_delivery.getCode());
            pdsShipOrderService.save(pdsShipOrder);

            //获取该商家的所有交易单
            tradeOrder.setBuyerId(buyerId);
            List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
            for (TradeOrder order : tradeOrderList) {
                BigDecimal sortOutNum = order.getSortOutNum();
                PdsShipOrderDetail pdsShipOrderDetail = new PdsShipOrderDetail();
                pdsShipOrderDetail.setShipId(pdsShipOrder.getShipId());
                pdsShipOrderDetail.setTradeId(order.getTradeId());
                pdsShipOrderDetail.setGoodsName(order.getGoodsName());
                pdsShipOrderDetail.setGoodsNum(sortOutNum);
                pdsShipOrderDetail.setPmId(pmId);

                //销售出库
                mercGoodsStorageService.outStock("", order.getGoodsId(), sortOutNum);

                pdsShipOrderDetailService.save(pdsShipOrderDetail);

                //变更交易单运营状态为已装车
                order.setOpStatus(IPdsConstant.OperatorViewStatus.truckLoad.getCode());
                tradeOrderService.save(order);
            }
        }
//        }
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp depart(PdsShipOrderApiReq apiShipOrderReq) {
        BaseResp baseResp = BaseResp.newError();
//        Date deliveryDate = pdsAdmBaseReq.getDeliveryDate();
//        String buySeq = pdsAdmBaseReq.getBuySeq();
        String pmId = apiShipOrderReq.getPmId();
        String uid = apiShipOrderReq.getUid();

        MerchantEmployee merchantEmployee = merchantEmployeeService.fetchByUidAndRole(uid, IPdsConstant.EmployeeRole.driver);
        if (null != merchantEmployee) {
            String employeeId = merchantEmployee.getId();
            apiShipOrderReq.setDriverId(employeeId);
            DeliveryFlow deliveryFlow = deliveryFlowService.fetchByDateSeqDriver(apiShipOrderReq, IPdsConstant.DeliveryStatus.wait_delivery);
            if (null == deliveryFlow) {
                baseResp.setMsg("未找到配送单");
                return baseResp;
            }
            deliveryFlow.setStatus(IPdsConstant.DeliveryStatus.delivering.getCode());
            TruckManage truckManage = truckManageService.fetchByDriver(deliveryFlow.getDriver());
            PdsShipOrder pdsShipOrder = new PdsShipOrder();
            pdsShipOrder.setDeliveryId(deliveryFlow.getDeliveryId());
            pdsShipOrder.setStatus(IPdsConstant.DeliveryStatus.wait_delivery.getCode());
            pdsShipOrder.setPmId(pmId);
            List<PdsShipOrder> shipOrderList = pdsShipOrderService.findList(pdsShipOrder);
            for (PdsShipOrder shipOrder : shipOrderList) {
                //送货单变更状态为配送中
                shipOrder.setStatus(IPdsConstant.DeliveryStatus.delivering.getCode());
                List<PdsShipOrderDetail> shipOrderDetailList = pdsShipOrderDetailService.findByShipIdList(shipOrder.getShipId());
                for (PdsShipOrderDetail detail : shipOrderDetailList) {
                    //交易单采购商状态为待验收,配送状态为配送中
                    TradeOrder tradeOrder = tradeOrderService.fetchByTradeOrderId(detail.getTradeId());
                    tradeOrder.setBuyerStatus(IPdsConstant.BuyerDealStatus.wait_check.getCode());
                    tradeOrder.setDeliveryStatus(IPdsConstant.DeliveryStatus.delivering.getCode());
                    tradeOrderService.save(tradeOrder);
                    //发送发货通知
                    sendShipMessage(tradeOrder.getBuyId(), truckManage);
                }
                pdsShipOrderService.save(shipOrder);
            }
            deliveryFlowService.save(deliveryFlow);
        }

        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp selfOrderArrived(Date deliveryDate, String buySeq, String pmId) {
        BaseResp baseResp = BaseResp.newError();

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setDeliveryTime(deliveryDate);
        tradeOrder.setBuySeq(buySeq);
        tradeOrder.setDeliveryType(IPdsConstant.DeliveryType.self.getCode());
        tradeOrder.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
        tradeOrder.setDeliveryStatus(IPdsConstant.DeliveryStatus.wait_delivery.getCode());
        tradeOrder.setPmId(pmId);
        List<TradeOrder> tradeOrders = tradeOrderService.findList(tradeOrder);
        if (CollectionUtils.isEmpty(tradeOrders)) {
            baseResp.setMsg("未找到新的自提订单");
            return baseResp;
        }
        for (TradeOrder order : tradeOrders) {
            order.setBuyerStatus(IPdsConstant.BuyerDealStatus.wait_check.getCode());
            order.setDeliveryStatus(IPdsConstant.DeliveryStatus.delivering.getCode());
            tradeOrderService.save(order);
            //发送自提通知
            sendSelfTakeMessage(order.getBuyId());
        }

        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp shipOrderArrived(String shipId) {
        BaseResp baseResp = BaseResp.newError();

        PdsShipOrder pdsShipOrder = pdsShipOrderService.fetchByShipId(shipId);
        pdsShipOrder.setArriveTime(new Date());
        pdsShipOrder.setStatus(IPdsConstant.DeliveryStatus.arrived.getCode());
        pdsShipOrderService.save(pdsShipOrder);
        List<PdsShipOrderDetail> detailList = pdsShipOrderDetailService.findByShipIdList(shipId);
        for (PdsShipOrderDetail detail : detailList) {
            TradeOrder tradeOrder = tradeOrderService.fetchByTradeOrderId(detail.getTradeId());
            tradeOrder.setDeliveryStatus(IPdsConstant.DeliveryStatus.arrived.getCode());
            tradeOrderService.save(tradeOrder);
        }

        //TODO 发送送达消息

        baseResp.success();
        return baseResp;
    }

    private void sendShipMessage(String buyId, TruckManage truckManage) {
        if (!JedisUtils.exists(buyId.concat("-shipNotify"))) {
            BuyOrder buyOrder = buyOrderService.fetchByBuyId(buyId);
            if (null != buyOrder) {
                UserOpenPlatform userOpenPlatform = pdsCommonServiceImpl.fetchOpenUserByUid(buyOrder.getBuyerUid(), IPdsConstant.WX_MP_APPID, buyOrder.getBuyerId(), IPdsConstant.CompanyType.buyer);
                if (null != userOpenPlatform) {
                    if (wxMpMessageService.orderDeliveryNotify(userOpenPlatform, buyOrder, truckManage)) {
                        JedisUtils.set(buyOrder.getBuyId().concat("-shipNotify"), "success", 120);
                    }
                }
            }
        }
    }

    private void sendSelfTakeMessage(String buyId) {
        if (!JedisUtils.exists(buyId.concat("-selfTakeNotify"))) {
            BuyOrder buyOrder = buyOrderService.fetchByBuyId(buyId);
            if (null != buyOrder) {
                UserOpenPlatform userOpenPlatform = pdsCommonServiceImpl.fetchOpenUserByUid(buyOrder.getBuyerUid(), IPdsConstant.WX_MP_APPID, buyOrder.getBuyerId(), IPdsConstant.CompanyType.buyer);
                if (null != userOpenPlatform) {
                    if (wxMpMessageService.orderSelfTakeNotify(userOpenPlatform, buyOrder)) {
                        JedisUtils.set(buyOrder.getBuyId().concat("-selfTakeNotify"), "success", 120);
                    }
                }
            }
        }
    }
}
