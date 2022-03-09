package com.haohan.platform.service.sys.modules.pds.core.summary.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.core.summary.IPdsSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.entity.order.*;
import com.haohan.platform.service.sys.modules.pds.entity.resp.BuyOrderSummaryResp;
import com.haohan.platform.service.sys.modules.pds.exception.PdsSummaryOperationException;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.business.SupplierGoodsService;
import com.haohan.platform.service.sys.modules.pds.service.cost.GoodsLossService;
import com.haohan.platform.service.sys.modules.pds.service.order.*;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.weixin.mp.message.WxMpMessageService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.exception.DataNotFoundException;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/10/20
 */
@Service
public class PdsSummaryServiceImpl implements IPdsSummaryService, IPdsConstant {

    @Resource
    private BuyOrderDetailService buyOrderDetailService;
    @Resource
    private SummaryOrderService summaryOrderService;
    @Resource
    private OfferOrderService offerOrderService;
    @Resource
    private TradeMatchService tradeMatchService;
    @Resource
    private TradeOrderService tradeOrderService;
    @Resource
    private BuyOrderService buyOrderService;
    @Resource
    private GoodsLossService goodsLossService;
    @Resource
    private WxMpMessageService wxMpMessageService;
    @Resource
    private IPdsCommonService pdsCommonServiceImpl;
    @Resource
    private GoodsModelService goodsModelService;
    @Resource
    private GoodsService goodsService;
    @Autowired
    private SupplierGoodsService supplierGoodsService;
    @Autowired
    private PdsSupplierService pdsSupplierService;
    @Autowired
    private PdsBuyerService pdsBuyerService;

    /**
     * 汇总订单(用户下单后,定时调用)
     */
    @Override
    public BaseResp summaryBuyOrders(String pmId, Date deliveryDate, String buySeq) {
        BaseResp baseResp = BaseResp.newError();
        try {
            //1 汇总采购商品(已下单)
            Date now = new Date();
            BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
            // 可查询 已下单/待确认 状态的未汇总 明细
            buyOrderDetail.setStatus(BuyOrderStatus.submit.getCode());
            buyOrderDetail.setFinalStatus(BuyOrderStatus.wait.getCode());
            deliveryDate = DateUtils.transTimeToDate(deliveryDate);
            buyOrderDetail.setDeliveryDate(deliveryDate);
            buyOrderDetail.setBuySeq(buySeq);
            buyOrderDetail.setSummaryFlag(DetailSummaryFlag.wait.getCode());
            buyOrderDetail.setPmId(pmId);
            List<BuyOrderSummaryResp> list = buyOrderDetailService.findGroupByGoodsId(buyOrderDetail);
            if (CollectionUtils.isEmpty(list)) {
                baseResp.setMsg("操作失败,未发现新采购单");
                return baseResp;
            }

            //有新采购单时,先检查是否有已报价但平台未确认的汇总单,有则先清除
            SummaryOrder sumOrder = new SummaryOrder();
            sumOrder.setStatus(SummaryOrderStatus.offered.getCode());
            sumOrder.setDeliveryTime(deliveryDate);
            sumOrder.setBuySeq(buySeq);
            sumOrder.setPmId(pmId);
            List<SummaryOrder> offeredSumList = summaryOrderService.findList(sumOrder);
            if (CollectionUtils.isNotEmpty(offeredSumList)) {
                for (SummaryOrder order : offeredSumList) {
                    summaryOrderService.delete(order);
                    OfferOrder offerOrder = new OfferOrder();
                    offerOrder.setAskOrderId(order.getSummaryOrderId());
                    List<OfferOrder> offerOrders = offerOrderService.findList(offerOrder);
                    for (OfferOrder offerOrder1 : offerOrders) {
                        offerOrderService.delete(offerOrder1);
                    }
                }
            }

            for (BuyOrderSummaryResp item : list) {
                //验证是否有相同送货日期,相同送货批次,相同商品,状态为待报价的汇总单,有的话直接增加需求数量,不新增汇总单
                SummaryOrder summar = new SummaryOrder();
                summar.setDeliveryTime(deliveryDate);
                summar.setBuySeq(item.getBuySeq());
                summar.setGoodsId(item.getGoodsId());
                summar.setStatus(SummaryOrderStatus.wait.getCode());
                summar.setPmId(pmId);
                List<SummaryOrder> sList = summaryOrderService.findList(summar);

                // 被汇总的 订单明细 (summaryFlag未汇总)
                buyOrderDetail.setGoodsId(item.getGoodsId());

                if (CollectionUtils.isNotEmpty(sList)) {
                    summar = sList.get(0);
                    summar.setNeedBuyNum(summar.getNeedBuyNum().add(item.getGoodsNum()));
                    summar.setPmId(pmId);
                    summaryOrderService.save(summar);
                    List<BuyOrderDetail> buyOrderDetailList = buyOrderDetailService.findList(buyOrderDetail);
                    if (CollectionUtils.isNotEmpty(buyOrderDetailList)) {
                        for (BuyOrderDetail entity : buyOrderDetailList) {
                            entity.setSmmaryBuyId(summar.getSummaryOrderId());
                            entity.setSummaryFlag(DetailSummaryFlag.wait.getCode());
                            buyOrderDetailService.save(entity);
                        }
                    }
                    continue;
                }

                BigDecimal needsBuyNum = item.getGoodsNum();
                String summaryOrderId = PdsCommonUtil.incrIdByClass(SummaryOrder.class, IPdsConstant.SUMMARY_ORDER_SN_PRE);
                SummaryOrder summaryOrder = new SummaryOrder();
                item.copyToSummaryOrder(summaryOrder);
                summaryOrder.setDeliveryTime(deliveryDate);
                summaryOrder.setSummaryOrderId(summaryOrderId);
                summaryOrder.setNeedBuyNum(needsBuyNum);
                summaryOrder.setBuyTime(now);
                summaryOrder.setStatus(SummaryOrderStatus.wait.getCode());
                summaryOrder.setIsGenTrade(ICommonConstant.YesNoType.no.getCode());
                summaryOrder.setLimitSupplyNum(needsBuyNum.intValue() / 3);
                summaryOrder.setPmId(pmId);

                //保存商品分类
                String modelId = item.getGoodsId();
                GoodsModel goodsModel = goodsModelService.get(modelId);
                if (null != goodsModel) {
                    Goods goods = goodsService.get(goodsModel.getGoodsId());
                    if (null != goods) {
                        // 分类多选时,只保留一个
                        summaryOrder.setGoodsCategoryId(StringUtils.substringBefore(goods.getGoodsCategoryId(), ","));
                    }
                }
                summaryOrderService.save(summaryOrder);

                List<BuyOrderDetail> buyOrderDetailList = buyOrderDetailService.findList(buyOrderDetail);
                if (CollectionUtils.isNotEmpty(buyOrderDetailList)) {
                    for (BuyOrderDetail entity : buyOrderDetailList) {
                        entity.setSmmaryBuyId(summaryOrderId);
                        entity.setSummaryFlag(DetailSummaryFlag.wait.getCode());
                        buyOrderDetailService.save(entity);
                    }
                }
            }

            //2 发送汇总单至供应商

            //3 供应商报价,计算平台报价

            //4 发送给采购商确认价格数量

            baseResp.success();
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("操作失败,系统错误");
        } finally {
            return baseResp;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp confirmOffer(String pmId, String buySeq, Date date) throws PdsSummaryOperationException {
        BaseResp baseResp = BaseResp.newError();

        date = DateUtils.transTimeToDate(date);

        //验证是否所有商品已报价
        SummaryOrder summaryOrder = new SummaryOrder();
        summaryOrder.setDeliveryTime(date);
        summaryOrder.setBuySeq(buySeq);
        summaryOrder.setStatus(SummaryOrderStatus.wait.getCode());
        summaryOrder.setPmId(pmId);
        List<SummaryOrder> summaryOrderList = summaryOrderService.findList(summaryOrder);

        if (CollectionUtils.isNotEmpty(summaryOrderList)) {
            String goodsName = summaryOrderList.get(0).getGoodsName();
            if (summaryOrderList.size() > 1) {
                goodsName = goodsName.concat("、" + summaryOrderList.get(1).getGoodsName());
            }
            baseResp.setMsg(goodsName + " 等 " + summaryOrderList.size() + " 件商品未完成报价！");
            return baseResp;
        } else {
            summaryOrder.setStatus(SummaryOrderStatus.offered.getCode());
            summaryOrderList = summaryOrderService.findList(summaryOrder);
            if (CollectionUtils.isEmpty(summaryOrderList)) {
                baseResp.setMsg("未找到已报价的汇总单");
                return baseResp;
            }
            // 成交的采购单
            Map<String, BuyOrder> dealOrderMap = new HashMap<>(16);
            String buyId;
            BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
            List<BuyOrderDetail> detailList;
            BuyOrder buyOrder;
            for (SummaryOrder sumOrder : summaryOrderList) {
                if (StringUtils.isNotEmpty(sumOrder.getSummaryOrderId())) {
                    sumOrder.setStatus(SummaryOrderStatus.confirm.getCode());
                    summaryOrderService.save(sumOrder);
                    // 查询汇总单对应的采购明细
                    buyOrderDetail.setPmId(pmId);
                    buyOrderDetail.setSmmaryBuyId(sumOrder.getSummaryOrderId());
                    buyOrderDetail.setStatus(BuyOrderStatus.submit.getCode());
                    // scm系统 可能状态为wait
                    buyOrderDetail.setFinalStatus(BuyOrderStatus.wait.getCode());
                    detailList = buyOrderDetailService.findList(buyOrderDetail);
                    if (CollectionUtils.isEmpty(detailList)) {
                        throw new PdsSummaryOperationException("采购单明细状态异常");
                    }
                    for (BuyOrderDetail orderDetail : detailList) {
                        buyId = orderDetail.getBuyId();
                        // 查询采购单
                        if (!dealOrderMap.containsKey(buyId)) {
                            //修改采购单状态为待确认
                            buyOrder = buyOrderService.fetchByBuyId(buyId);
                            if (null == buyOrder) {
                                throw new PdsSummaryOperationException("采购单明细buyId异常");
                            }
                            buyOrder.setStatus(BuyOrderStatus.wait.getCode());
                            buyOrderService.save(buyOrder);
                            dealOrderMap.put(buyId, buyOrder);
                        } else {
                            buyOrder = dealOrderMap.get(buyId);
                        }
                        if (null != buyOrder) {
                            //发送订单确认通知
                            if (!JedisUtils.exists(buyId.concat("-confirmNotify"))) {
                                UserOpenPlatform userOpenPlatform = pdsCommonServiceImpl.fetchOpenUserByUid(buyOrder.getBuyerUid(), IPdsConstant.WX_MP_APPID, buyOrder.getBuyerId(), IPdsConstant.CompanyType.buyer);
                                if (null != userOpenPlatform) {
                                    if (wxMpMessageService.confirmBuyOrderNotify(userOpenPlatform, buyOrder)) {
                                        JedisUtils.set(buyId.concat("-confirmNotify"), "success", 240);
                                    }
                                }
                            }
                        }
                        //修改采购明细为待确认
                        orderDetail.setStatus(BuyOrderStatus.wait.getCode());
                        buyOrderDetailService.save(orderDetail);
                    }

                }
            }
        }

        baseResp.success();
        return baseResp;
    }

    /**
     * 交易匹配(汇总单所有用户确认交易后调用)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp tradeMatch(String pmId, String buySeq, Date deliveryDate) throws PdsSummaryOperationException {
        BaseResp baseResp = BaseResp.newError();

        //获取汇总单(按送货时间,批次,已报价状态筛选)
        SummaryOrder summaryOrder = new SummaryOrder();
        deliveryDate = DateUtils.transTimeToDate(deliveryDate);
        summaryOrder.setDeliveryTime(deliveryDate);
        summaryOrder.setStatus(SummaryOrderStatus.confirm.getCode());
        if (StringUtils.isNotEmpty(buySeq)) {
            summaryOrder.setBuySeq(buySeq);
        }
        summaryOrder.setPmId(pmId);
        List<SummaryOrder> summaryOrderList = summaryOrderService.findList(summaryOrder);

        if (CollectionUtils.isEmpty(summaryOrderList)) {
            baseResp.setMsg("未找到已确认的汇总订单");
            return baseResp;
        }
//        BigDecimal lossPercent;
        BigDecimal dealNum;
        OfferOrder offerOrder;
        List<OfferOrder> offerOrderList;
        BigDecimal needsNum;
        BigDecimal totalRealBuyNum;
        BigDecimal minOfferNum;
//        BigDecimal offerOrderBuyNum;
        TradeMatch tradeMatch;
//        GoodsLoss goodsLoss;
        List<OfferOrder> offerOrders;
        for (SummaryOrder item : summaryOrderList) {
//            goodsLoss = goodsLossService.fetchByGoodsId(item.getGoodsId());
//            lossPercent = BigDecimal.ZERO;
//            if (null != goodsLoss && null != goodsLoss.getLossPercent()){
//                lossPercent = goodsLoss.getLossPercent();
//            }
            offerOrder = new OfferOrder();
            offerOrder.setStatus(OfferOrderStatus.submit.getCode());
            offerOrder.setPmId(pmId);

            //更新采购需求
            dealNum = buyOrderDetailService.countDealNum(item.getSummaryOrderId(), item.getGoodsId());
            if (BigDecimal.ZERO.compareTo(dealNum) >= 0) {
                //汇总单数量变为0
                item.setNeedBuyNum(dealNum);
                item.setRealBuyNum(dealNum);
                item.setStatus(SummaryOrderStatus.cancel.getCode());
                item.setIsGenTrade(ICommonConstant.YesNoType.no.getCode());
                summaryOrderService.save(item);
                //取消需求数量为0的报价单
                offerOrder.setAskOrderId(item.getSummaryOrderId());
                offerOrderList = offerOrderService.findList(offerOrder);
                if (CollectionUtils.isNotEmpty(offerOrderList)) {
                    for (OfferOrder order : offerOrderList) {
                        order.setStatus(OfferOrderStatus.cancel.getCode());
                        order.setShipStatus(OfferShipStatus.disabled.getCode());
                        order.setBuyNum(BigDecimal.ZERO);
                        offerOrderService.save(order);
                    }
                }
                continue;   //需求数量为0 进入下一次循环
            }
            item.setNeedBuyNum(dealNum);
            //获取报价单(按汇总单号,已报价状态筛选)
            offerOrder.setAskOrderId(item.getSummaryOrderId());
            offerOrderList = offerOrderService.findList(offerOrder);
            if (CollectionUtils.isEmpty(offerOrderList)) {
                throw new PdsSummaryOperationException("未找到" + item.getGoodsName() + "的报价信息");
            }
            //按价格增序排序
            offerOrders = offerOrderList.stream()
                    .sorted(Comparator.comparing(OfferOrder::getSupplyPrice)).collect(Collectors.toList());
            //加上损耗数量
//            needsNum = item.getNeedBuyNum().multiply(lossPercent).add(item.getNeedBuyNum());
            needsNum = item.getNeedBuyNum();
            //实际采购总数
            totalRealBuyNum = BigDecimal.ZERO;
            for (OfferOrder offerOrderItem : offerOrders) {
//                //报价单实际采购数
//                offerOrderBuyNum = BigDecimal.ZERO;
                if (null == offerOrderItem.getMinSaleNum()) {
                    minOfferNum = BigDecimal.ZERO;
                } else {
                    minOfferNum = new BigDecimal(offerOrderItem.getMinSaleNum());
                }
                //剩余需求小于供应商起售数量,则不生成交易匹配  需求数量需大于0
                if (needsNum.compareTo(minOfferNum) >= 0 && needsNum.compareTo(BigDecimal.ZERO) > 0) {
                    //创建交易匹配记录
                    tradeMatch = fetchNewTradeMatch(offerOrderItem, item);
                    if (needsNum.compareTo(offerOrderItem.getBuyNum()) <= 0) {
                        //供应量大于需求量,按需供应
                        tradeMatch.setBuyNum(needsNum);
                    } else {
                        //供应量小于需求量,全部供应
                        tradeMatch.setBuyNum(offerOrderItem.getBuyNum());
                    }
                    totalRealBuyNum = totalRealBuyNum.add(tradeMatch.getBuyNum());
                    offerOrderItem.setStatus(OfferOrderStatus.success.getCode());
                    offerOrderItem.setShipStatus(OfferShipStatus.prepare.getCode());
                    offerOrderItem.setDealTime(new Date());
                    offerOrderItem.setDealPrice(offerOrderItem.getSupplyPrice());
                    tradeMatchService.save(tradeMatch);
//                    offerOrderBuyNum = tradeMatch.getBuyNum();
                    needsNum = needsNum.subtract(tradeMatch.getBuyNum());
                } else {
                    //未匹配成功的报价单状态为未成交
                    offerOrderItem.setStatus(OfferOrderStatus.cancel.getCode());
                    offerOrderItem.setShipStatus(OfferShipStatus.disabled.getCode());
                }
                //设置实际采购数
//                offerOrderItem.setBuyNum(offerOrderBuyNum);
                offerOrderService.save(offerOrderItem);
            }
            //汇总单状态为成交
            item.setStatus(SummaryOrderStatus.deal.getCode());
            item.setRealBuyNum(totalRealBuyNum);
            summaryOrderService.save(item);
        }
        baseResp.success();
        return baseResp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp createTradeOrder(String pmId, String buySeq, Date deliveryDate) throws PdsSummaryOperationException {
        BaseResp baseResp = BaseResp.newError();

        SummaryOrder summaryOrder = new SummaryOrder();
        summaryOrder.setBuySeq(buySeq);
        summaryOrder.setDeliveryTime(deliveryDate);
        summaryOrder.setStatus(SummaryOrderStatus.deal.getCode());
        summaryOrder.setIsGenTrade(ICommonConstant.YesNoType.no.getCode());
        summaryOrder.setPmId(pmId);
        List<SummaryOrder> summaryOrderList = summaryOrderService.findList(summaryOrder);
        if (CollectionUtils.isEmpty(summaryOrderList)) {
            throw new PdsSummaryOperationException("未找到成交的汇总订单");
        }
        for (SummaryOrder order : summaryOrderList) {
            String summaryOrderId = order.getSummaryOrderId();

            //查询汇总单的所有采购明细
            BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
            buyOrderDetail.setSmmaryBuyId(summaryOrderId);
            buyOrderDetail.setStatus(BuyOrderStatus.delivery.getCode());
            buyOrderDetail.setPmId(pmId);
            List<BuyOrderDetail> buyOrderDetailList = buyOrderDetailService.findList(buyOrderDetail);
            if (CollectionUtils.isEmpty(buyOrderDetailList)) {
                throw new PdsSummaryOperationException("未找到" + order.getGoodsName() + "的采购数据");
            }
            //查询交易匹配
            List<TradeMatch> tradeMatchList = tradeMatchService.findByAskOrder(summaryOrderId);
            if (CollectionUtils.isEmpty(tradeMatchList)) {
                throw new PdsSummaryOperationException("未找到" + order.getGoodsName() + "的匹配记录");
            }
            //按数量减序排序
            List<BuyOrderDetail> buyOrderDetails = buyOrderDetailList.stream()
                    .sorted(Comparator.comparing(BuyOrderDetail::getGoodsNum).reversed()).collect(Collectors.toList());
            List<TradeMatch> tradeMatches = tradeMatchList.stream()
                    .sorted(Comparator.comparing(TradeMatch::getBuyNum).reversed()).collect(Collectors.toList());
            // 从汇总单获取商品分类id
            dispatchGoods(tradeMatches, buyOrderDetails, order);
            order.setIsGenTrade(ICommonConstant.YesNoType.yes.getCode());
            summaryOrderService.save(order);
        }
        baseResp.success();
        return baseResp;
    }


    private void dispatchGoods(List<TradeMatch> tradeMatches, List<BuyOrderDetail> buyOrderDetails, SummaryOrder summaryOrder) {
        Set<BuyOrderDetail> delSet;
        BigDecimal supNum;
        BuyOrder buyOrder;
        BigDecimal buyNum;
        TradeOrder tradeOrder;
        for (TradeMatch tradeMatch : tradeMatches) {
            supNum = tradeMatch.getBuyNum();
            delSet = new HashSet();
            for (BuyOrderDetail buyOrderDetail : buyOrderDetails) {
                buyOrder = buyOrderService.fetchByBuyId(buyOrderDetail.getBuyId());
                buyNum = buyOrderDetail.getGoodsNum();
                buyNum = (null == buyNum) ? BigDecimal.ZERO : buyNum;
                if (supNum.compareTo(buyNum) >= 0) {
                    tradeOrder = fetchNewTradeOrder(buyOrder, buyOrderDetail, tradeMatch, buyNum);
                    tradeOrder.setGoodsCategory(summaryOrder.getGoodsCategoryId());
                    tradeOrderService.save(tradeOrder);
                    delSet.add(buyOrderDetail);
                    supNum = supNum.subtract(buyNum);
                } else {
                    tradeOrder = fetchNewTradeOrder(buyOrder, buyOrderDetail, tradeMatch, supNum);
                    tradeOrder.setGoodsCategory(summaryOrder.getGoodsCategoryId());
                    tradeOrderService.save(tradeOrder);
                    buyOrderDetail.setGoodsNum(buyNum.subtract(supNum));
                    break;
                }
            }
            //剔除已达成交易的采购单明细
            if (CollectionUtils.isNotEmpty(delSet)) {
                for (BuyOrderDetail entity : delSet) {
                    buyOrderDetails.remove(entity);
                }
            }
        }
    }

    private TradeMatch fetchNewTradeMatch(OfferOrder offerOrder, SummaryOrder summaryOrder) {
        TradeMatch tradeMatch = new TradeMatch();
        tradeMatch.setPmId(summaryOrder.getPmId());
        tradeMatch.setAskOrderId(summaryOrder.getSummaryOrderId());
        tradeMatch.setOfferOrderId(offerOrder.getOfferOrderId());
        tradeMatch.setSupplierId(offerOrder.getSupplierId());
        tradeMatch.setSupplyNum(offerOrder.getSupplyNum());
        tradeMatch.setOfferType(offerOrder.getOfferType());
        tradeMatch.setOfferPrice(offerOrder.getSupplyPrice());
        tradeMatch.setDealPrice(offerOrder.getSupplyPrice());
        tradeMatch.setStatus(TradeMatchStatus.is_gen.getCode());
        return tradeMatch;
    }

    private TradeOrder fetchNewTradeOrder(BuyOrder buyOrder, BuyOrderDetail buyOrderDetail, TradeMatch tradeMatch, BigDecimal buyNum) {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setPmId(buyOrderDetail.getPmId());
        tradeOrder.setGoodsId(buyOrderDetail.getGoodsId());
        tradeOrder.setGoodsName(buyOrderDetail.getGoodsName());
        tradeOrder.setGoodsModel(buyOrderDetail.getGoodsModel());
        tradeOrder.setUnit(buyOrderDetail.getUnit());
        tradeOrder.setGoodsImg(buyOrderDetail.getGoodsImg());
        tradeOrder.setBuyNum(buyNum);
        tradeOrder.setMarketPrice(buyOrderDetail.getMarketPrice());
        tradeOrder.setBuyPrice(buyOrderDetail.getBuyPrice());
        tradeOrder.setDealTime(new Date());
        tradeOrder.setBuyId(buyOrderDetail.getBuyId());
        tradeOrder.setBuyerId(buyOrderDetail.getBuyerId());
        tradeOrder.setTradeId(PdsCommonUtil.incrIdByClass(TradeOrder.class, IPdsConstant.TRADE_ORDER_SN_PRE));
        tradeOrder.setSummaryBuyId(buyOrderDetail.getSmmaryBuyId());
        tradeOrder.setOfferType(tradeMatch.getOfferType());
        tradeOrder.setOfferId(tradeMatch.getOfferOrderId());
        tradeOrder.setSupplierId(tradeMatch.getSupplierId());
        tradeOrder.setSupplyPrice(tradeMatch.getOfferPrice());
        tradeOrder.setBuySeq(buyOrder.getBuySeq());
        tradeOrder.setDeliveryType(buyOrder.getDeliveryType());

        tradeOrder.setBuyTime(buyOrder.getBuyTime());
        tradeOrder.setContact(buyOrder.getContact());
        tradeOrder.setContactPhone(buyOrder.getTelephone());
        tradeOrder.setDeliveryAddress(buyOrder.getAddress());
        tradeOrder.setDeliveryTime(buyOrder.getDeliveryTime());

        //交易单状态变更(初始化)
        tradeOrder.setTransStatus(TradeOrderStatus.done.getCode());
        tradeOrder.setSupplierStatus(SupplierDealStatus.prepare.getCode());
        tradeOrder.setBuyerStatus(BuyerDealStatus.wait_ship.getCode());
        tradeOrder.setOpStatus(OperatorViewStatus.prepare.getCode());
        tradeOrder.setDeliveryStatus(DeliveryStatus.wait_delivery.getCode());
        return tradeOrder;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResp convert(List<OfferOrder> offerOrderList, String pmId) throws DataNotFoundException {
        BaseResp baseResp = BaseResp.newError();
        Map<String, List<OfferOrder>> supOfferList = offerOrderList.stream().collect(Collectors.groupingBy(OfferOrder::getSupplierId));
        for (Map.Entry<String, List<OfferOrder>> entry : supOfferList.entrySet()) {
            PdsSupplier supplier = pdsSupplierService.get(entry.getKey());
            if (null == supplier) {
                baseResp.setMsg("未找到ID为" + entry.getKey() + "的供应商信息");
                return baseResp;
            }
            if (SupplierType.stock.getCode().equals(supplier.getSupplierType())) {
                continue;
            }
            List<OfferOrder> itemList = entry.getValue();
            String destPmId = supplier.getMerchantId();
            PdsBuyer buyer = pdsBuyerService.fetchByPmMerType(destPmId, pmId, BuyerType.self);
            if (null == buyer) {
                baseResp.setMsg("供应商未配置自营采购商");
                return baseResp;
            }
            BuyOrder buyOrder = new BuyOrder();
            Date now = new Date();
            buyOrder.setPmId(destPmId);
            buyOrder.setBuyerId(buyer.getId());
            buyOrder.setBuyerName(buyer.getBuyerName());
            buyOrder.setBuyerUid(buyer.getPassportId());
            buyOrder.setBuyTime(now);
            buyOrder.setDeliveryTime(itemList.get(0).getPrepareDate());
            buyOrder.setBuySeq(itemList.get(0).getBuySeq());
            buyOrder.setContact(buyer.getContact());
            buyOrder.setTelephone(buyer.getTelephone());
            buyOrder.setAddress(buyer.getAddress());
            buyOrder.setStatus(BuyOrderStatus.submit.getCode());
            buyOrder.setDeliveryType(DeliveryType.home.getCode());
            buyOrderService.save(buyOrder);

            BigDecimal amount = BigDecimal.ZERO;
            for (OfferOrder offerItem : itemList) {
                String modelId = offerItem.getGoodsId();
                BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
                buyOrderDetail.setPmId(buyOrder.getPmId());
                buyOrderDetail.setBuyId(buyOrder.getBuyId());
                buyOrderDetail.setBuyerId(buyOrder.getBuyerId());
                SupplierGoods supplierGoods = supplierGoodsService.fetchByModelId(pmId, entry.getKey(), modelId);
                if (null == supplierGoods) {
                    throw new DataNotFoundException("供应商未出售" + offerItem.getGoodsName() + "商品");
                }
                buyOrderDetail.setGoodsId(supplierGoods.getSupplierModelId());
                GoodsModel goodsModel = goodsModelService.get(supplierGoods.getSupplierModelId());
                if (null == goodsModel) {
                    throw new DataNotFoundException("供应商商品信息" + offerItem.getGoodsName() + "有误");
                }
                Goods goods = goodsService.get(goodsModel.getGoodsId());
                if (null == goods) {
                    throw new DataNotFoundException("商品" + goodsModel.getId() + "信息有误");
                }
                buyOrderDetail.setGoodsName(goods.getGoodsName());
                buyOrderDetail.setGoodsImg(goodsModel.getModelUrl());
                buyOrderDetail.setGoodsModel(goodsModel.getModelName());
                buyOrderDetail.setGoodsNum(offerItem.getBuyNum());
                buyOrderDetail.setMarketPrice(goodsModel.getModelPrice());
                buyOrderDetail.setUnit(goodsModel.getModelUnit());
                buyOrderDetail.setStatus(BuyOrderStatus.submit.getCode());
                BigDecimal goodsNum = null == buyOrderDetail.getGoodsNum() ? BigDecimal.ZERO : buyOrderDetail.getGoodsNum();
                BigDecimal price = null == buyOrderDetail.getMarketPrice() ? BigDecimal.ZERO : buyOrderDetail.getMarketPrice();
                amount = goodsNum.multiply(price);
                buyOrderDetailService.save(buyOrderDetail);
            }
            buyOrder.setGenPrice(amount);
            buyOrderService.save(buyOrder);
        }
        baseResp.success();
        return baseResp;
    }


}
