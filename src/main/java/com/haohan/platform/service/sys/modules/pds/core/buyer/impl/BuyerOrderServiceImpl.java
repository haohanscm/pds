package com.haohan.platform.service.sys.modules.pds.core.buyer.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerGoodsService;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerOrderService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.entity.resp.BuyOrderDetailResp;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderDetailService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.TradeOrderService;
import com.haohan.platform.service.sys.modules.pds.utils.ComputeSumPrice;
import com.haohan.platform.service.sys.modules.pds.utils.PdsCommonUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.FeiePrintMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer.IProducer;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintParams;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.PrintProduct;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.PrintTemplateType;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by zgw on 2018/10/20.
 */
@Service
public class BuyerOrderServiceImpl implements IBuyerOrderService {

    @Resource
    @Lazy(true)
    private BuyOrderService buyOrderService;
    @Resource
    @Lazy(true)
    private BuyOrderDetailService buyOrderDetailService;
    @Autowired
    @Lazy(true)
    private TradeOrderService tradeOrderService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService buyerService;
    @Autowired
    @Lazy(true)
    private GoodsModelService goodsModelService;
    @Autowired
    @Lazy(true)
    private IBuyerGoodsService buyerGoodsService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    private IProducer producer;
    @Autowired
    private FeiePrinterService feiePrinterService;

    // ???????????????
    @Override
    public BaseResp addBuyOrder(BuyOrder buyOrder) {

        BaseResp baseResp = BaseResp.newError();
        // ?????????????????????
        String buyerId = buyOrder.getBuyerId();
        PdsBuyer buyer = buyerService.get(buyerId);
        if (null == buyer) {
            baseResp.setMsg("????????????,buyerId??????");
            return baseResp;
        }
        // ??????????????????
        String pmId = buyer.getPmId();
        if (StringUtils.isNotEmpty(buyOrder.getPmId()) && !StringUtils.equals(pmId, buyOrder.getPmId())) {
            baseResp.setMsg("????????????,pmId??????");
            return baseResp;
        }

        buyOrder.setPmId(pmId);
        buyOrder.setBuyerUid(buyer.getPassportId());
        buyOrder.setBuyerName(buyer.getBuyerName());

        List<BuyOrderDetail> orderDetails = buyOrder.getBuyOrderDetailList();
        String buyId = PdsCommonUtil.incrIdByClass(BuyOrder.class, IPdsConstant.BUY_ORDER_SN_PRE);
        buyOrder.setBuyId(buyId);
        buyOrder.setStatus(BuyOrderStatus.submit.getCode());
        buyOrder.setBuyTime(new Date());
        int num = 0;
        // ??????????????????
        List<BuyOrderDetail> errorList = new ArrayList<>();
        Date date = new Date();
        // ????????????
        BigDecimal genPrice = BigDecimal.ZERO;

        // ??????????????????
        int size = orderDetails.size() * 4 / 3 + 1;
        size = Math.max(size, 8);
        Map<String, BuyOrderDetail> detailMap = new HashMap<>(size);
        for (BuyOrderDetail detail : orderDetails) {
            // ????????????id
            String goodsModelId = detail.getGoodsId();
            // ???????????? ?????? ????????????
            if (!fetchBuyOrderDetail(buyerId, pmId, buyId, goodsModelId, errorList, date, detail)) {
                continue;
            }
            BigDecimal goodsNum = detail.getGoodsNum();
            genPrice = genPrice.add(detail.getBuyPrice().multiply(goodsNum));
            if (detailMap.containsKey(goodsModelId)) {
                BuyOrderDetail exist = detailMap.get(goodsModelId);
                detail.setId(exist.getId());
                // ????????????????????????
//                goodsNum = goodsNum.add(exist.getGoodsNum());
//                detail.setGoodsNum(goodsNum);
//                detail.setOrderGoodsNum(goodsNum);
                // ????????????????????????, ??????
//                genPrice = genPrice.add((detail.getBuyPrice().subtract(exist.getBuyPrice())).multiply(exist.getGoodsNum()));
                genPrice = genPrice.subtract(exist.getGoodsNum().multiply(exist.getBuyPrice()));
            }
            // ????????????????????????
            detail.setSummaryFlag(IPdsConstant.DetailSummaryFlag.wait.getCode());
            buyOrderDetailService.save(detail);
            num++;
            detailMap.put(goodsModelId, detail);
        }
        int errorNum = errorList.size();
        if (num > 0) {
            buyOrder.setId(null);
            genPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            buyOrder.setGenPrice(genPrice);
            buyOrderService.save(buyOrder);
            //???????????????
            try {
//                if (buyOrder.getDeliveryTime().compareTo(DateUtils.getDayEndTime(DateUtils.getOffsetDate(new Date(),1))) < 0){
                produceCreateOrderPrintMsg(buyOrder, buyer);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            baseResp.success();
            baseResp.setExt(buyOrder);
            if (errorNum > 0) {
                baseResp.setMsg("??????????????????????????????" + errorNum + "????????????????????????");
            }
        } else {
            if (errorNum > 0) {
                baseResp.setMsg("???????????????????????????" + errorNum + "????????????????????????");
            }
        }
        return baseResp;
    }

    /**
     * ?????? ????????? ??????????????? ??????
     *
     * @param buyerId
     * @param pmId
     * @param buyId
     * @param modelId
     * @param errorList
     * @param date
     * @param detail
     * @return
     */
    private boolean fetchBuyOrderDetail(String buyerId, String pmId, String buyId, String modelId, List<BuyOrderDetail> errorList, Date date, BuyOrderDetail detail) {
        GoodsModel goodsModel = goodsModelService.fetch(modelId);
        BigDecimal goodsNum = detail.getGoodsNum();
        if (null == goodsModel || null == goodsModel.getModelPrice() || null == goodsNum
                || goodsNum.compareTo(BigDecimal.ZERO) <= 0) {
            errorList.add(detail);
            return false;
        }
        detail.setId(null);
        // ?????????????????????????????????
        detail.setOrderGoodsNum(goodsNum);
        // ????????????????????????
        PdsPlatformGoodsPrice price = buyerGoodsService.fetchPlatformGoodsPrice(pmId, buyerId, modelId, date);
        detail.setMarketPrice(null == price ? goodsModel.getModelPrice() : price.getPrice());

        detail.setGoodsImg(goodsModel.getModelUrl());
        detail.setGoodsName(goodsModel.getGoodsName());
        detail.setGoodsModel(goodsModel.getModelName());
        detail.setUnit(goodsModel.getModelUnit());
        // ???????????? ????????????
        BigDecimal buyPrice = detail.getBuyPrice();
        if (null == buyPrice || BigDecimal.ZERO.compareTo(buyPrice) >= 0) {
            buyPrice = detail.getMarketPrice();
        }
        detail.setBuyPrice(buyPrice);

        detail.setPmId(pmId);
        detail.setBuyId(buyId);
        detail.setBuyerId(buyerId);
        detail.setStatus(BuyOrderStatus.submit.getCode());
        return true;
    }

    /**
     * ??????????????? ?????????????????????????????????
     *
     * @param buyOrder
     * @return
     */
    @Override
    public BaseResp modifyBuyOrder(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        String buyId = buyOrder.getBuyId();
        BuyOrder oldBuyOrder = buyOrderService.fetchByBuyId(buyId);
        String buyerId = buyOrder.getBuyerId();
        if (null == oldBuyOrder || !StringUtils.equals(oldBuyOrder.getBuyerId(), buyerId)) {
            baseResp.setMsg("?????????????????????,????????????");
            return baseResp;
        }
        // ?????????????????????????????????
        if (!StringUtils.equals(oldBuyOrder.getStatus(), BuyOrderStatus.submit.getCode())) {
            baseResp.setMsg("?????????????????????????????????");
            return baseResp;
        }
        // ??????????????????
        String pmId = oldBuyOrder.getPmId();
        if (StringUtils.isNotEmpty(buyOrder.getPmId()) && !StringUtils.equals(pmId, buyOrder.getPmId())) {
            baseResp.setMsg("????????????,pmId??????");
            return baseResp;
        }
        // ???????????????
        List<BuyOrderDetail> existDetailList = buyOrderDetailService.findListByBuyId(buyOrder.getBuyId());
        oldBuyOrder.setBuyOrderDetailList(existDetailList);
        // ????????????
        List<BuyOrderDetail> modifyList = buyOrder.getBuyOrderDetailList();
        if (null == modifyList) {
            modifyList = new ArrayList<>();
        }

        // ??????????????????????????????
        boolean flag = true;
        int size = existDetailList.size() * 4 / 3 + 1;
        size = Math.max(size, 8);
        Map<String, BuyOrderDetail> existDetailMap = new HashMap<>(size);
        // ??????????????????
        size = (existDetailList.size() + modifyList.size()) * 4 / 3 + 1;
        size = Math.max(size, 8);
        Map<String, BuyOrderDetail> detailMap = new HashMap<>(size);
        // ??????????????????
        BigDecimal genPrice = BigDecimal.ZERO;
        for (BuyOrderDetail detail : existDetailList) {
            // ???????????????????????? ???????????? TODO ??????????????????
            if (!StringUtils.isEmpty(detail.getSmmaryBuyId())) {
                flag = false;
                break;
            }
            existDetailMap.put(detail.getId(), detail);
            detailMap.put(detail.getGoodsId(), detail);
            genPrice = genPrice.add(detail.getBuyPrice().multiply(detail.getGoodsNum()));
        }
        if (!flag) {
            baseResp.setMsg("?????????,?????????????????????????????????");
            return baseResp;
        }
        // ?????????????????????, ??????????????????
        if (StringUtils.equals(buyOrder.getStatus(), BuyOrderStatus.cancel.getCode())) {
            BuyOrderDetail detail = new BuyOrderDetail();
            detail.setBuyId(buyId);
            detail.setStatus(BuyOrderStatus.submit.getCode());
            detail.setFinalStatus(BuyOrderStatus.cancel.getCode());
            int i = buyOrderDetailService.updateStatusByBuyId(detail);
            if (i > 0) {
                baseResp.success();
            } else {
                baseResp.setMsg("?????????????????????");
            }
            try {
//                BuyOrder b = buyOrderService.fetchByBuyId(buyId);
                produceCancelOrderPrintMsg(oldBuyOrder, buyerService.get(buyerId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return baseResp;
        }
        Date date = new Date();
        oldBuyOrder.setBuyTime(date);
        // ?????????????????? ??????
        if (StringUtils.isNotEmpty(buyOrder.getNeedNote())) {
            oldBuyOrder.setNeedNote(buyOrder.getNeedNote());
        }
        if (StringUtils.isNotEmpty(buyOrder.getRemarks())) {
            oldBuyOrder.setRemarks(buyOrder.getRemarks());
        }
        // ??????????????????/??????
        if (null != buyOrder.getDeliveryTime()) {
            oldBuyOrder.setDeliveryTime(buyOrder.getDeliveryTime());
        }
        String buySeq = buyOrder.getBuySeq();
        if (null != IPdsConstant.BuySeq.valueOfBuySeq(buySeq)) {
            oldBuyOrder.setBuySeq(buySeq);
        }

        BuyOrderDetail saveDetail;
        BigDecimal goodsNum;
        BigDecimal buyPrice;
        int successNum = 0;
        List<BuyOrderDetail> errorList = new ArrayList<>();
        Set<BuyOrderDetail> removeSet = new HashSet<>();
        Set<BuyOrderDetail> modifyNumSet = new HashSet<>();
        Set<BuyOrderDetail> insertSet = new HashSet<>();
        BigDecimal changeNum = BigDecimal.ZERO;
        for (BuyOrderDetail detail : modifyList) {
            if (null == detail) {
                continue;
            }
            // ??????????????????
            goodsNum = BigDecimal.ZERO;
            if (null != detail.getGoodsNum()) {
                goodsNum = detail.getGoodsNum();
            }
            // ????????????????????????
            if (StringUtils.isBlank(detail.getId()) && detailMap.containsKey(detail.getGoodsId())) {
                BuyOrderDetail exist = detailMap.get(detail.getGoodsId());
                detail.setId(exist.getId());
                goodsNum = goodsNum.add(exist.getGoodsNum());
                detail.setGoodsNum(goodsNum);
                genPrice = genPrice.subtract(exist.getGoodsNum().multiply(exist.getBuyPrice()));
            }
            // ?????????????????????,???????????????
            if (StringUtils.isNotEmpty(detail.getId()) && existDetailMap.containsKey(detail.getId())) {
                saveDetail = existDetailMap.get(detail.getId());

                existDetailMap.remove(detail.getId());
                if (StringUtils.equals(detail.getDelFlag(), "1") || goodsNum.compareTo(BigDecimal.ZERO) <= 0) {
                    // ??????????????????
                    goodsNum = BigDecimal.ZERO;
                    saveDetail.setStatus(BuyOrderStatus.cancel.getCode());
                    saveDetail.setDelFlag("1");
                    removeSet.add(saveDetail);
                } else if (saveDetail.getGoodsNum().compareTo(detail.getGoodsNum()) != 0) {
                    changeNum = detail.getGoodsNum().subtract(saveDetail.getGoodsNum());
                    modifyNumSet.add(detail);
                    // ?????? ????????????
                    saveDetail.setOrderGoodsNum(goodsNum);
                }
                saveDetail.setGoodsNum(goodsNum);
                // ?????? ????????????
                buyPrice = detail.getBuyPrice();
                if (null == buyPrice || BigDecimal.ZERO.compareTo(buyPrice) >= 0) {
                    buyPrice = saveDetail.getBuyPrice();
                }
                if (null == buyPrice || BigDecimal.ZERO.compareTo(buyPrice) >= 0) {
                    buyPrice = saveDetail.getMarketPrice();
                }
                saveDetail.setBuyPrice(buyPrice);
                if (StringUtils.isNotEmpty(detail.getRemarks())) {
                    saveDetail.setRemarks(detail.getRemarks());
                }
            } else {
                // ????????????
                if (!fetchBuyOrderDetail(buyerId, pmId, buyId, detail.getGoodsId(), errorList, date, detail)) {
                    continue;
                }
                saveDetail = detail;
                insertSet.add(detail);
            }
            genPrice = genPrice.add(saveDetail.getBuyPrice().multiply(saveDetail.getGoodsNum()));
            successNum++;
            buyOrderDetailService.save(saveDetail);
        }
        try {
            produceModifyOrderPrintMsg(oldBuyOrder, insertSet, modifyNumSet, removeSet, changeNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int errorNum = errorList.size();
        if (successNum > 0) {
            // ??????????????????????????????????????? ???????????????
            genPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            oldBuyOrder.setGenPrice(genPrice);
            // ?????????????????????0 ?????????
            if (BigDecimal.ZERO.compareTo(genPrice) >= 0) {
                oldBuyOrder.setStatus(BuyOrderStatus.cancel.getCode());
            }
            buyOrderService.save(oldBuyOrder);
            baseResp.success();
            if (errorNum > 0) {
                baseResp.setMsg("????????????,??????" + errorNum + "?????????????????????");
            }
        } else {
            baseResp.setMsg("???????????????????????????");
        }
        return baseResp;
    }

    @Override
    public BaseResp queryBuyOrderList(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        // ???????????????  ?????????????????????,??????????????????
        String buyerId = buyOrder.getBuyerId();
        PdsBuyer buyer = buyerService.get(buyerId);
        if (null == buyer || !StringUtils.equals(buyer.getPmId(), buyOrder.getPmId())) {
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        String buyerType = buyer.getBuyerType();
        if (StringUtils.equals(buyerType, BuyerType.operator.getCode())) {
            // ?????? ????????? ??????????????????
            buyOrder.setBuyerId(null);
        } else if (StringUtils.equals(buyerType, BuyerType.boss.getCode())) {
            // ?????? ????????? ??????????????????
            buyOrder.setBuyerId(null);
            buyOrder.setMerchantId(buyer.getMerchantId());
        }
        Page<BuyOrder> page = buyOrder.getPage();
        page = buyOrderService.findPage(page, buyOrder);

        ApiRespPage<BuyOrder> respPage = new ApiRespPage();
        respPage.fetchFromPage(page);

        if (CollectionUtils.isEmpty(respPage.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            BuyOrderDetail orderDetail;
            for (BuyOrder b : respPage.getList()) {
                orderDetail = new BuyOrderDetail();
                orderDetail.setBuyId(b.getBuyId());
                b.setBuyOrderDetailList(buyOrderDetailService.findList(orderDetail));
                b.setDetailNum(b.getBuyOrderDetailList().size());
            }
        }
        baseResp.success();
        baseResp.setExt(respPage);

        return baseResp;
    }

    @Override
    public BaseResp queryBuyOrderDetailList(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        BuyOrder order = buyOrderService.fetchByBuyId(buyOrder.getBuyId());
        if (null == order) {
            baseResp.setMsg("buyId??????");
            return baseResp;
        }
        BuyOrderDetail orderDetail = new BuyOrderDetail();
        orderDetail.setBuyId(buyOrder.getBuyId());
        order.setBuyOrderDetailList(buyOrderDetailService.findList(orderDetail));
        order.setDetailNum(order.getBuyOrderDetailList().size());
        baseResp.success();
        baseResp.setExt(order);
        return baseResp;
    }

    // ????????? ?????????
    @Override
    public BaseResp modifyBuyNum(String detailId, int buyNum) {
        BaseResp baseResp = BaseResp.newError();
//        BuyOrderDetail detail =  buyOrderDetailService.get(detailId);
//        detail.setGoodsNum(new BigDecimal(buyNum));
//        buyOrderDetailService.save(detail);
//        //??????????????????
//        computeOrderSumPrice(detail.getBuyId());
        return baseResp;
    }

    // ????????? ?????????
    @Override
    public BaseResp removeBuyGoods(String detailId) {
        BaseResp baseResp = BaseResp.newError();
//        BuyOrderDetail detail = new BuyOrderDetail();
//        detail.setId(detailId);
//        buyOrderDetailService.delete(detail);
//        //??????????????????
//        computeOrderSumPrice(detail.getBuyId());
        return baseResp;
    }

    /**
     * ?????????????????????
     * buyId/buyerId/buyOrderDetailList
     *
     * @param buyOrder
     * @return
     */
    @Override
    public BaseResp confirmBuyOrder(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        BuyOrder order = buyOrderService.fetchByBuyId(buyOrder.getBuyId());
        if (null == order) {
            baseResp.setMsg("buyId??????");
            return baseResp;
        }
        // ???????????????????????????????????????
        if (!StringUtils.equals(order.getStatus(), BuyOrderStatus.wait.getCode())) {
            baseResp.setMsg("?????????????????????????????????");
            return baseResp;
        }
        // ???????????????
        List<BuyOrderDetail> existDetailList = buyOrderDetailService.findListByBuyId(buyOrder.getBuyId());
        // ?????????????????????, ??????????????????
        if (StringUtils.equals(buyOrder.getStatus(), BuyOrderStatus.cancel.getCode())) {
            BuyOrderDetail detail = new BuyOrderDetail();
            detail.setBuyId(buyOrder.getBuyId());
            detail.setStatus(BuyOrderStatus.wait.getCode());
            detail.setFinalStatus(BuyOrderStatus.cancel.getCode());
            buyOrderDetailService.updateStatusByBuyId(detail);
            baseResp.success();
            return baseResp;
        }
        Map<String, BuyOrderDetail> existDetailMap = new HashMap<>(existDetailList.size() / 3 * 4 + 1);
        for (BuyOrderDetail detail : existDetailList) {
            // ?????? ????????????????????????
            if (StringUtils.equals(BuyOrderStatus.cancel.getCode(), detail.getStatus())) {
                continue;
            }
            existDetailMap.put(detail.getId(), detail);
        }
        // ???????????????
        List<BuyOrderDetail> confirmList = buyOrder.getBuyOrderDetailList();
        if (null == confirmList) {
            confirmList = new ArrayList<>();
        }
        BuyOrderDetail saveDetail;
        // ???????????? ??????
        List<ComputeSumPrice> sumPriceList = new ArrayList<>();
        for (BuyOrderDetail detail : confirmList) {
            // ???????????????????????????
            if (existDetailMap.containsKey(detail.getId())) {
                saveDetail = existDetailMap.get(detail.getId());
                existDetailMap.remove(detail.getId());
                BigDecimal goodsNum = BigDecimal.ZERO;
                if (null != detail.getGoodsNum()) {
                    goodsNum = detail.getGoodsNum();
                }
                saveDetail.setGoodsNum(goodsNum);
                if (StringUtils.equals(detail.getDelFlag(), "1") || goodsNum.compareTo(BigDecimal.ZERO) <= 0) {
                    // ??????????????????
                    saveDetail.setStatus(BuyOrderStatus.cancel.getCode());
                } else {
                    saveDetail.setStatus(BuyOrderStatus.delivery.getCode());
                    BigDecimal buyPrice = saveDetail.getBuyPrice();
                    // ??????????????????????????????
                    if (null == buyPrice || buyPrice.compareTo(BigDecimal.ZERO) <= 0) {
                        buyPrice = saveDetail.getMarketPrice();
                        saveDetail.setBuyPrice(buyPrice);
                    }
                    sumPriceList.add(new ComputeSumPrice(saveDetail.getGoodsNum(), buyPrice));
                }
                buyOrderDetailService.save(saveDetail);
            }
        }
        // ??????????????????????????????????????? ?????? ???????????????
        if (!existDetailMap.isEmpty()) {
            for (BuyOrderDetail detail : existDetailMap.values()) {
                detail.setStatus(BuyOrderStatus.delivery.getCode());
                sumPriceList.add(new ComputeSumPrice(detail.getGoodsNum(), detail.getBuyPrice()));
                buyOrderDetailService.save(detail);
            }
        }
        order.setStatus(BuyOrderStatus.delivery.getCode());
        // ???????????????  ???????????????????????????
        BigDecimal shipFee = BigDecimal.ZERO;
        if (null != order.getShipFee()) {
            shipFee = order.getShipFee();
        }
        order.setTotalPrice(ComputeSumPrice.sum(sumPriceList).add(shipFee));
        buyOrderService.save(order);
        baseResp.success();
        baseResp.setExt(order);
        return baseResp;
    }

    // ?????????????????????????????? (????????? ??????????????? ????????????) ??????
    @Override
    public BaseResp tradeGoodsList(TradeOrder tradeOrder, Page page) {
        BaseResp baseResp = BaseResp.newError();

        // ?????????????????????
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setBuyerId(tradeOrder.getBuyerId());
        buyOrderDetail.setBuySeq(tradeOrder.getBuySeq());
        buyOrderDetail.setDeliveryDate(tradeOrder.getDeliveryTime());
        String tradeStatus = tradeOrder.getBuyerStatus();
        buyOrderDetail.setBuyerStatus(tradeStatus);
        // ????????????????????? ??????  (?????? buyOrderStatus)
        String status = BuyOrderStatus.arrive.getCode();
        if (StringUtils.equals(BuyerDealStatus.received.getCode(), tradeStatus)) {
            status = BuyOrderStatus.success.getCode();
        }
        buyOrderDetail.setStatus(status);
        buyOrderDetail.setPage(page);
        List<BuyOrderDetailResp> list = buyOrderDetailService.findListWithTrade(buyOrderDetail);
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage<>(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    // ??????????????????(????????????)  buyerId/buyId/goodsId
    @Override
    public BaseResp confirmTradeGoods(TradeOrder tradeOrder) {
        BaseResp baseResp = BaseResp.newError();
        List<TradeOrder> orderList = tradeOrderService.findList(tradeOrder);
        if (Collections3.isEmpty(orderList)) {
            baseResp.setMsg("????????????????????????");
            return baseResp;
        }
        // ??????????????? ?????????????????????????????????????????????
        BigDecimal sortOutNum = BigDecimal.ZERO;
        for (TradeOrder order : orderList) {
            if (null != order.getSortOutNum()) {
                sortOutNum = sortOutNum.add(order.getSortOutNum());
            }
            order.setBuyerStatus(BuyerDealStatus.received.getCode());
            tradeOrderService.save(order);
        }
        BuyOrderDetail detail = new BuyOrderDetail();
        detail.setBuyerId(tradeOrder.getBuyerId());
        detail.setBuyId(tradeOrder.getBuyId());
        detail.setGoodsId(tradeOrder.getGoodsId());
        List<BuyOrderDetail> detailList = buyOrderDetailService.findList(detail);
        // ??????????????????
        if (!Collections3.isEmpty(detailList)) {
            detail = detailList.get(0);
            detail.setGoodsNum(sortOutNum);
            detail.setStatus(BuyOrderStatus.success.getCode());
            buyOrderDetailService.save(detail);
        }
        baseResp.success();
        return baseResp;
    }

    // ??????????????????(?????? ) ???buyId/buyerId
    @Override
    public BaseResp confirmAllGoods(TradeOrder tradeOrder) {
        BaseResp baseResp = BaseResp.newError();
        BuyOrder buyOrder = buyOrderService.fetchByBuyId(tradeOrder.getBuyId());
        // ??????????????? ?????????
        if (null == buyOrder || !StringUtils.equals(buyOrder.getStatus(), BuyOrderStatus.arrive.getCode())) {
            baseResp.setMsg("????????????????????????");
            return baseResp;
        }
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setBuyId(tradeOrder.getBuyId());
        buyOrderDetail.setBuyerId(tradeOrder.getBuyerId());
        // ??????????????? ?????????
        buyOrderDetail.setStatus(BuyOrderStatus.arrive.getCode());
        // ?????????????????? ???????????? ?????????????????? ?????????
        List<BuyOrderDetailResp> buyOrderDetailList = buyOrderDetailService.findListWithTrade(buyOrderDetail);
        int num = 0;
        for (BuyOrderDetailResp detailResp : buyOrderDetailList) {
            if (StringUtils.equals(detailResp.getBuyerStatus(), BuyerDealStatus.received.getCode())) {
                num++;
            }
        }
        if (num == buyOrderDetailList.size()) {
            baseResp.setMsg("?????????????????????");
            return baseResp;
        }
        // ???????????????
        List<TradeOrder> tradeOrderList = tradeOrderService.findList(tradeOrder);
        if (Collections3.isEmpty(tradeOrderList)) {
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        // ?????????????????????
        Map<String, BigDecimal> goodsNumMap = new HashMap<>(tradeOrderList.size());
        // ??????????????? ?????????????????????????????????????????????
        BigDecimal sortOutNum;
        for (TradeOrder order : tradeOrderList) {
            order.setBuyerStatus(BuyerDealStatus.received.getCode());
            order.setDeliveryStatus(DeliveryStatus.arrived.getCode());
            sortOutNum = (order.getSortOutNum() == null) ? BigDecimal.ZERO : order.getSortOutNum();
            if (goodsNumMap.containsKey(order.getGoodsId())) {
                sortOutNum = sortOutNum.add(goodsNumMap.get(order.getGoodsId()));
            }
            // ??????/?????? ??????????????????
            goodsNumMap.put(order.getGoodsId(), sortOutNum);
            tradeOrderService.save(order);
        }
        // ???????????? ??????
        List<ComputeSumPrice> sumPriceList = new ArrayList<>();
        for (BuyOrderDetail detail : buyOrderDetailList) {
            sortOutNum = BigDecimal.ZERO;
            if (goodsNumMap.containsKey(detail.getGoodsId())) {
                sortOutNum = goodsNumMap.get(detail.getGoodsId());
            }
            detail.setGoodsNum(sortOutNum);
            detail.setStatus(BuyOrderStatus.success.getCode());
            buyOrderDetailService.save(detail);
            sumPriceList.add(new ComputeSumPrice(sortOutNum, detail.getBuyPrice()));
        }
        // ?????????????????? ?????????????????????
        buyOrder.setStatus(BuyOrderStatus.success.getCode());
        // ???????????????  ???????????????????????????
        BigDecimal shipFee = BigDecimal.ZERO;
        if (null != buyOrder.getShipFee()) {
            shipFee = buyOrder.getShipFee();
        }
        buyOrder.setTotalPrice(ComputeSumPrice.sum(sumPriceList).add(shipFee));
        buyOrderService.updatePart(buyOrder);
        baseResp.success();
        return baseResp;
    }

    private void computeOrderSumPrice(String buyId) {

        BuyOrder buyOrder = buyOrderService.get(buyId);
        BuyOrderDetail orderDetail = new BuyOrderDetail();
        orderDetail.setBuyId(buyId);
        List<BuyOrderDetail> orderDetailList = buyOrderDetailService.findList(orderDetail);
        List<ComputeSumPrice> sumPriceList = new ArrayList<>();
        BigDecimal sumPrice = BigDecimal.ZERO;
        for (BuyOrderDetail detail : orderDetailList) {
            sumPriceList.add(new ComputeSumPrice(detail.getGoodsNum(), detail.getBuyPrice()));
            sumPrice = sumPrice.add(ComputeSumPrice.sum(sumPriceList));
        }
        buyOrder.setTotalPrice(sumPrice);
        buyOrderService.save(buyOrder);

    }

    @Override
    public boolean produceCreateOrderPrintMsg(BuyOrder buyOrder, PdsBuyer pdsBuyer) {
//        if (buyOrder.getDeliveryTime().compareTo(DateUtils.getDayEndTime(DateUtils.getOffsetDate(new Date(),1))) < 0) {
        List<FeiePrinter> printers = feiePrinterService.findByMerchantIdAndTplType(buyOrder.getPmId(), PrintTemplateType.NEW_ORDER);
        if (CollectionUtils.isNotEmpty(printers)) {

            List<PrintParams> printParams = getPrintParamsOfOrderCreate(buyOrder, pdsBuyer);
            if (null != printParams) {
                producer.send(IRocketMqConstant.TopicType.CLOUD_PRINT.getName(), new FeiePrintMsgEntity(printParams, IRocketMqConstant.PrintMsgTag.CREATE_ORDER, buyOrder.getPmId()));
                return Boolean.TRUE;
            }
        }
//        }
        return Boolean.FALSE;
    }

    @Override
    public boolean produceCancelOrderPrintMsg(BuyOrder buyOrder, PdsBuyer pdsBuyer) {
        if (buyOrder.getDeliveryTime().compareTo(DateUtils.getDayEndTime(DateUtils.getOffsetDate(new Date(), 1))) < 0) {
            List<FeiePrinter> printers = feiePrinterService.findByMerchantIdAndTplType(buyOrder.getPmId(), PrintTemplateType.NEW_ORDER);
            if (CollectionUtils.isNotEmpty(printers)) {
                List<PrintParams> printParams = getPrintParamsOfOrderCancel(buyOrder, pdsBuyer);
                if (null != printParams) {
                    producer.send(IRocketMqConstant.TopicType.CLOUD_PRINT.getName(), new FeiePrintMsgEntity(printParams, IRocketMqConstant.PrintMsgTag.CANCEL_ORDER, buyOrder.getPmId()));
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean produceModifyOrderPrintMsg(BuyOrder buyOrder, Set<BuyOrderDetail> insert, Set<BuyOrderDetail> modify, Set<BuyOrderDetail> remove, BigDecimal ofNum) {
        if (buyOrder.getDeliveryTime().compareTo(DateUtils.getDayEndTime(DateUtils.getOffsetDate(new Date(), 1))) < 0) {
            List<FeiePrinter> printers = feiePrinterService.findByMerchantIdAndTplType(buyOrder.getPmId(), PrintTemplateType.NEW_ORDER);
            if (CollectionUtils.isNotEmpty(printers)) {
                List<PrintParams> printParams = getPrintParamsOfOrderModify(buyOrder, insert, modify, remove, ofNum);
                if (CollectionUtils.isNotEmpty(printParams)) {
                    producer.send(IRocketMqConstant.TopicType.CLOUD_PRINT.getName(), new FeiePrintMsgEntity(printParams, IRocketMqConstant.PrintMsgTag.UPDATE_ORDER, buyOrder.getPmId()));
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    private List<PrintParams> getPrintParamsOfOrderCreate(BuyOrder buyOrder, PdsBuyer pdsBuyer) {
        List<PrintParams> results = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        String tittle = "?????????";
        String contact = null == pdsBuyer ? "" : pdsBuyer.getContact();
        String buyTime = DateUtils.formatDate(buyOrder.getBuyTime(), "yyyy-MM-dd HH:mm");
        String phone = null == pdsBuyer ? "" : hideTelephone(pdsBuyer.getTelephone());
        String deliveryDate = DateUtils.formatDate(buyOrder.getDeliveryTime(), "yyyy-MM-dd");
        String deliverySeq = BuySeq.valueOfBuySeq(buyOrder.getBuySeq()).getDesc();
        String needNote = null == buyOrder.getNeedNote() ? "" : buyOrder.getNeedNote();

        String[] header = new String[]{tittle, buyOrder.getBuyId(), buyOrder.getBuyerName()};
        String[] header2 = new String[]{buyOrder.getBuyId(), buyOrder.getBuyerName()};

        List<PrintProduct> productsList = new ArrayList<>();
        List<PrintProduct> productList2 = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(buyOrder.getBuyOrderDetailList())) {
            for (BuyOrderDetail orderDetail : buyOrder.getBuyOrderDetailList()) {
                String goodsName = orderDetail.getGoodsName();
                String goodsNum = orderDetail.getGoodsNum().setScale(1, RoundingMode.HALF_UP).toString();
                String unit = orderDetail.getUnit();
                String price = orderDetail.getBuyPrice().setScale(2, RoundingMode.HALF_DOWN).toString();
                ArrayList<String> paramList1 = new ArrayList<>();
                paramList1.add(goodsName);
                paramList1.add(goodsNum);
                paramList1.add(unit);
                paramList1.add(price);
                PrintProduct product = new PrintProduct(paramList1.toArray(new String[]{}));
                productsList.add(product);
                totalAmount = totalAmount.add(orderDetail.getBuyPrice().multiply(orderDetail.getGoodsNum()).setScale(2, RoundingMode.HALF_DOWN));

                ArrayList<String> paramList2 = new ArrayList<>();
                paramList2.add(goodsName);
                paramList2.add(goodsNum);
                paramList2.add(unit);
                PrintProduct product1 = new PrintProduct(paramList2.toArray(new String[]{}));
                productList2.add(product1);
            }
        }
        PrintProduct[] products = productsList.toArray(new PrintProduct[]{});
        PrintProduct[] products2 = productList2.toArray(new PrintProduct[]{});

        String[] footer = new String[]{totalAmount.toString(), buyTime, contact, phone, deliveryDate, deliverySeq, needNote};
        String[] footer2 = new String[]{needNote};
        if (products.length != 0) {
            results.add(new PrintParams(header, products, footer));
            results.add(new PrintParams(header2, products2, footer2));
        } else {
            return null;
        }
        return results;
    }

    private List<PrintParams> getPrintParamsOfOrderCancel(BuyOrder buyOrder, PdsBuyer pdsBuyer) {
        List<PrintParams> results = new ArrayList<>();
//        buyOrder = buyOrderService.fetchByBuyId(buyOrder.getBuyId());
//        buyOrder.setBuyOrderDetailList(buyOrderDetailService.findListByBuyId(buyOrder.getBuyId()));
        String title = "????????????";
        BigDecimal totalAmount = BigDecimal.ZERO;
        String contact = null == pdsBuyer ? "" : pdsBuyer.getContact();
        String buyTime = DateUtils.formatDate(buyOrder.getBuyTime(), "yyyy-MM-dd HH:mm");
        String phone = null == pdsBuyer ? "" : hideTelephone(pdsBuyer.getTelephone());
        String deliveryDate = DateUtils.formatDate(buyOrder.getDeliveryTime(), "yyyy-MM-dd");
        String deliverySeq = BuySeq.valueOfBuySeq(buyOrder.getBuySeq()).getDesc();
        String needNote = null == buyOrder.getNeedNote() ? "" : buyOrder.getNeedNote();
        String cancelTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");


        String[] header = new String[]{title, buyOrder.getBuyId(), buyOrder.getBuyerName()};
        String[] header2 = new String[]{buyOrder.getBuyId(), buyOrder.getBuyerName()};

        List<PrintProduct> productsList = new ArrayList<>();
        List<PrintProduct> productList2 = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(buyOrder.getBuyOrderDetailList())) {
            for (BuyOrderDetail orderDetail : buyOrder.getBuyOrderDetailList()) {
                String goodsName = orderDetail.getGoodsName();
                String goodsNum = orderDetail.getGoodsNum().setScale(1, RoundingMode.HALF_UP).toString();
                String unit = orderDetail.getUnit();
                String price = orderDetail.getBuyPrice().setScale(2, RoundingMode.HALF_DOWN).toString();

                ArrayList<String> paramList1 = new ArrayList<>();
                paramList1.add(goodsName);
                paramList1.add(goodsNum);
                paramList1.add(unit);
                paramList1.add(price);
                PrintProduct product = new PrintProduct(paramList1.toArray(new String[]{}));
                productsList.add(product);
                totalAmount = totalAmount.add(orderDetail.getBuyPrice().multiply(orderDetail.getGoodsNum()).setScale(2, RoundingMode.HALF_DOWN));

                ArrayList<String> paramList2 = new ArrayList<>();
                paramList2.add(goodsName);
                paramList2.add(goodsNum);
                paramList2.add(unit);
                PrintProduct product1 = new PrintProduct(paramList2.toArray(new String[]{}));
                productList2.add(product1);
            }
        }

        PrintProduct[] products = productsList.toArray(new PrintProduct[]{});
        PrintProduct[] products2 = productList2.toArray(new PrintProduct[]{});

        String[] footer = new String[]{totalAmount.toString(), cancelTime, buyTime, contact, phone, deliveryDate, deliverySeq, needNote};
        String[] footer2 = new String[]{needNote};
        if (products.length != 0) {
            results.add(new PrintParams(header, products, footer));
            results.add(new PrintParams(header2, products2, footer2));
        } else {
            return null;
        }
        return results;
    }

    private List<PrintParams> getPrintParamsOfOrderModify(BuyOrder buyOrder, Set<BuyOrderDetail> insert, Set<BuyOrderDetail> modify, Set<BuyOrderDetail> remove, BigDecimal ofNum) {
        List<PrintParams> results = new ArrayList<>();
//        BuyOrder buyOrder = buyOrderService.fetchByBuyId(buyId);
        String tittle = "????????????";

        PdsBuyer pdsBuyer = pdsBuyerService.get(buyOrder.getBuyerId());
        String contact = null == pdsBuyer ? "" : pdsBuyer.getContact();
        String buyTime = DateUtils.formatDate(buyOrder.getBuyTime(), "yyyy-MM-dd HH:mm");
        String phone = null == pdsBuyer ? "" : hideTelephone(pdsBuyer.getTelephone());
        String deliveryDate = DateUtils.formatDate(buyOrder.getDeliveryTime(), "yyyy-MM-dd");
        String deliverySeq = BuySeq.valueOfBuySeq(buyOrder.getBuySeq()).getDesc();
        String needNote = null == buyOrder.getNeedNote() ? "" : buyOrder.getNeedNote();
        String modifyTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");

        String[] header = new String[]{tittle, buyOrder.getBuyId(), buyOrder.getBuyerName()};
        String[] header2 = new String[]{buyOrder.getBuyId(), buyOrder.getBuyerName()};

        List<PrintProduct> productsList = new ArrayList<>();
        List<PrintProduct> productList2 = new ArrayList<>();
        StringBuffer remarks = new StringBuffer();

        Iterator<BuyOrderDetail> it1 = insert.iterator();
        if (CollectionUtils.isNotEmpty(insert)) {
            while (it1.hasNext()) {
                BuyOrderDetail detail = it1.next();
                String goodsName = detail.getGoodsName();
                String goodsNum = detail.getGoodsNum().setScale(1, RoundingMode.HALF_UP).toString();
                String unit = detail.getUnit();
                String price = detail.getBuyPrice().setScale(2, RoundingMode.HALF_DOWN).toString();

                ArrayList<String> paramList1 = new ArrayList<>();
                paramList1.add("(???)" + goodsName);
                paramList1.add(goodsNum);
                paramList1.add(unit);
                paramList1.add(price);
                PrintProduct product = new PrintProduct(paramList1.toArray(new String[]{}));
                productsList.add(product);

                ArrayList<String> paramList2 = new ArrayList<>();
                paramList2.add("???" + goodsName);
                paramList2.add(goodsNum);
                paramList2.add(unit);
                PrintProduct product1 = new PrintProduct(paramList2.toArray(new String[]{}));
                productList2.add(product1);
            }
        }

        if (CollectionUtils.isNotEmpty(modify)) {
            Iterator<BuyOrderDetail> it2 = modify.iterator();
            while (it2.hasNext()) {
                BuyOrderDetail detail = it2.next();
//                PrintProduct product = new PrintProduct(detail.getGoodsName(),detail.getBuyPrice(),detail.getGoodsNum(),detail.getUnit(),"(????????????)");
                String goodsName = detail.getGoodsName();
                String goodsNum = detail.getGoodsNum().setScale(1, RoundingMode.HALF_UP).toString();
                String unit = detail.getUnit();
                String price = detail.getBuyPrice().setScale(2, RoundingMode.HALF_DOWN).toString();
                String symbol = "";
                if (ofNum.compareTo(BigDecimal.ZERO) != 0) {
                    symbol = ofNum.compareTo(BigDecimal.ZERO) > 0 ? "+" : "";
                    remarks.append(goodsName + " " + symbol + ofNum.setScale(1).toString() + unit + ";<BR>");
                }

                ArrayList<String> paramList1 = new ArrayList<>();
                paramList1.add("(???)" + goodsName);
                paramList1.add(symbol + ofNum.setScale(1, RoundingMode.HALF_UP).toString());
                paramList1.add(unit);
                paramList1.add(price);
                PrintProduct product = new PrintProduct(paramList1.toArray(new String[]{}));
                productsList.add(product);

                ArrayList<String> paramList2 = new ArrayList<>();
                paramList2.add("(???)" + goodsName);
                paramList2.add(symbol + ofNum.toString());
                paramList2.add(unit);
                PrintProduct product1 = new PrintProduct(paramList2.toArray(new String[]{}));
                productList2.add(product1);
            }
        }
        if (CollectionUtils.isNotEmpty(remove)) {
            Iterator<BuyOrderDetail> it3 = remove.iterator();
            while (it3.hasNext()) {
                BuyOrderDetail detail = it3.next();
                String goodsName = detail.getGoodsName();
                String goodsNum = detail.getGoodsNum().setScale(1, RoundingMode.HALF_UP).toString();
                String unit = detail.getUnit();
                String price = detail.getBuyPrice().setScale(2, RoundingMode.HALF_DOWN).toString();

                ArrayList<String> paramList1 = new ArrayList<>();
                paramList1.add("(???)" + goodsName);
                paramList1.add(goodsNum);
                paramList1.add(unit);
                paramList1.add(price);
                PrintProduct product = new PrintProduct(paramList1.toArray(new String[]{}));
                productsList.add(product);

                ArrayList<String> paramList2 = new ArrayList<>();
                paramList2.add("(???)" + goodsName);
                paramList2.add(goodsNum);
                paramList2.add(unit);
                PrintProduct product1 = new PrintProduct(paramList2.toArray(new String[]{}));
                productList2.add(product1);
            }
        }

        PrintProduct[] products = productsList.toArray(new PrintProduct[]{});
        PrintProduct[] products2 = productList2.toArray(new PrintProduct[]{});
        String[] footer = new String[]{modifyTime, buyTime, contact, phone, deliveryDate, deliverySeq, remarks.toString()};
        String[] footer2 = new String[]{remarks.toString()};
        if (products.length == 0) {
            return null;
        } else {
            results.add(new PrintParams(header, products, footer));
            results.add(new PrintParams(header2, products2, footer2));
        }
        return results;
    }

    /**
     * ?????????
     *
     * @param buyId
     * @param modifyList
     * @return
     */
    private PrintParams getPrintParamsOfGoodsRemove(String buyId, List<BuyOrderDetail> modifyList) {
        BuyOrder buyOrder = buyOrderService.fetchByBuyId(buyId);
        buyOrder.setBuyOrderDetailList(buyOrderDetailService.findListByBuyId(buyId));
        String cancelTittle = "(????????????)";
        BigDecimal totalAmount = BigDecimal.ZERO;
        PdsBuyer pdsBuyer = pdsBuyerService.get(buyOrder.getBuyerId());
        String contact = null == pdsBuyer ? "" : pdsBuyer.getContact();
        String buyTime = DateUtils.formatDate(buyOrder.getBuyTime(), "yyyy-MM-dd HH:mm");
        String phone = null == pdsBuyer ? "" : hideTelephone(pdsBuyer.getTelephone());
        String deliveryDate = DateUtils.formatDate(buyOrder.getDeliveryTime(), "yyyy-MM-dd");
        String deliverySeq = BuySeq.valueOfBuySeq(buyOrder.getBuySeq()).getDesc();
        String needNote = null == buyOrder.getNeedNote() ? "" : buyOrder.getNeedNote();
        String modifyTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");


        String[] header = new String[]{buyOrder.getBuyerName(), cancelTittle};
        List<PrintProduct> productsList = new ArrayList<>();
        for (BuyOrderDetail orderDetail : buyOrder.getBuyOrderDetailList()) {
            PrintProduct product = new PrintProduct(orderDetail.getGoodsName(), orderDetail.getBuyPrice(), orderDetail.getGoodsNum().setScale(1, RoundingMode.HALF_UP), orderDetail.getUnit());
            productsList.add(product);
            totalAmount = totalAmount.add(product.getPrice().multiply(product.getNumber()).setScale(2, RoundingMode.HALF_DOWN));
        }

        PrintProduct[] products = productsList.toArray(new PrintProduct[]{});
        String[] footer = new String[]{totalAmount.toString(), buyOrder.getBuyId(), modifyTime, buyTime, contact, phone, deliveryDate, deliverySeq, needNote};
        return new PrintParams(header, products, footer);
    }

    /**
     * ???????????????,?????????4???
     *
     * @param telephone
     * @return
     */
    private String hideTelephone(String telephone) {
        return "******" + StringUtils.substring(telephone, -4);
    }
}
