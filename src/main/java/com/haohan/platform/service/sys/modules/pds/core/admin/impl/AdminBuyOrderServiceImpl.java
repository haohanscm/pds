package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery.PdsShipBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiDeliveryBuyerResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiOfferOrderResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiSumBuyOrderResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsTradeOrderCommonParamsResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminBuyOrderService;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.dto.LastDealDTO;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.delivery.DeliveryFlowService;
import com.haohan.platform.service.sys.modules.pds.service.delivery.TruckManageService;
import com.haohan.platform.service.sys.modules.pds.service.order.*;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/10/31
 */
@Service
public class AdminBuyOrderServiceImpl implements IPdsAdminBuyOrderService {
    @Resource
    private BuyOrderService buyOrderService;
    @Resource
    private OfferOrderService offerOrderService;
    @Resource
    private BuyOrderDetailService buyOrderDetailService;
    @Resource
    private TruckManageService truckManageService;
    @Resource
    private TradeOrderService tradeOrderService;
    @Resource
    private PdsBuyerService pdsBuyerService;
    @Resource
    private PdsShipOrderService pdsShipOrderService;
    @Resource
    private DeliveryFlowService deliveryFlowService;
    @Resource
    private PdsShipOrderDetailService pdsShipOrderDetailService;

    /**
     * 获取采购单列表
     *
     * @return
     */
    @Override
    public BaseResp fetchBuyOrderList(Page<BuyOrder> reqPage, PdsBuyOrderApiReq apiBuyOrderReq) {
        BaseResp baseResp = BaseResp.newError();
        BuyOrder buyOrder = new BuyOrder();
        apiBuyOrderReq.copyToBuyOrder(buyOrder);
        buyOrder.setPage(reqPage);
        reqPage.setList(buyOrderService.findJoinList(buyOrder));

        if (CollectionUtils.isEmpty(reqPage.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        BaseList<BuyOrder> baseList = new BaseList<>();
        baseList.setTotalRows(new Long(reqPage.getCount()).intValue());
        baseList.setCurPage(reqPage.getPageNo());
        baseList.setTotalPage(reqPage.getTotalPage());
        baseList.setList(reqPage.getList());
        baseList.setPageSize(reqPage.getPageSize());
        return baseList;
    }

    /**
     * 获取采购单汇总
     *
     * @param buyOrderId
     * @return
     */
    @Override
    public BaseResp fetchBuyDetailOfferList(String buyOrderId) {
        BaseResp baseResp = BaseResp.newError();

        PdsApiSumBuyOrderResp pdsApiSumBuyOrderResp = buyOrderService.findAdmSumList(buyOrderId);
        if (null == pdsApiSumBuyOrderResp) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(pdsApiSumBuyOrderResp);
        return baseResp;
    }

    @Override
    public BaseResp findBuyOrderdetails(Page<BuyOrderDetail> page, String buyOrderId) {
        BaseResp baseResp = BaseResp.newError();

        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setBuyId(buyOrderId);
        buyOrderDetailService.findPage(page, buyOrderDetail);
        // 添加上次采购价
        LastDealDTO lasePurchase;
        for (BuyOrderDetail detail : page.getList()) {
            lasePurchase = buyOrderDetailService.fetchLastDeal(detail.getPmId(), detail.getGoodsId(), detail.getBuyerId());
            detail.setLastPrice(lasePurchase.getLastPrice());
            detail.setLastDate(lasePurchase.getLastDate());
        }
        BaseList<BuyOrderDetail> baseList = new BaseList<>();
        baseList.setCurPage(page.getPageNo());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setTotalPage(page.getTotalPage());
        baseResp.success();
        baseResp.setExt(baseList);
        return baseResp;
    }

    @Override
    public BaseResp deleteBuyDetail(String detailId) {
        BaseResp baseResp = BaseResp.newError();

        BuyOrderDetail buyOrderDetail = buyOrderDetailService.get(detailId);
        if (!IPdsConstant.BuyOrderStatus.submit.getCode().equals(buyOrderDetail.getStatus())) {
            baseResp.setMsg("采购单状态不支持删除");
            return baseResp;
        }
        if (null != buyOrderDetail) {
            buyOrderDetailService.delete(buyOrderDetail);
        }
        return baseResp.success();
    }

    /**
     * 修改运费
     *
     * @param shipFeeReq
     * @return
     */
    @Override
    public BaseResp editShipFee(PdsBuyShipFeeApiReq shipFeeReq) {
        BaseResp baseResp = BaseResp.newError();
        BuyOrder buyOrder = buyOrderService.fetchByBuyId(shipFeeReq.getBuyId());
        if (null == buyOrder) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        buyOrder.setShipFee(shipFeeReq.getShipFee());
        buyOrderService.save(buyOrder);
        baseResp.success();
        return baseResp;
    }


    /**
     * 获取报价单列表
     *
     * @return
     */
    @Override
    public BaseResp findOfferPage(Page<PdsApiOfferOrderResp> page, PdsOfferOrderApiReq offerOrderReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsApiOfferOrderResp pdsApiOfferOrderResp = new PdsApiOfferOrderResp();
        org.springframework.beans.BeanUtils.copyProperties(offerOrderReq, pdsApiOfferOrderResp);

        offerOrderService.findAdmRespPage(page, pdsApiOfferOrderResp);
        if (CollectionUtils.isEmpty(page.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        BaseList<PdsApiOfferOrderResp> baseList = new BaseList<>();
        baseList.setCurPage(page.getPageNo());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setTotalPage(page.getTotalPage());
        baseResp.success();
        baseResp.setExt(baseList);
        return baseResp;
    }


    @Override
    public BaseResp findShipOrderInfoList(PdsShipOrderApiReq shipOrderReq, Page reqPage) {
        BaseResp baseResp = BaseResp.newError();
        Date deliveryDate = shipOrderReq.getDeliveryDate();
        String buySeq = shipOrderReq.getBuySeq();
//        String driverId = pdsAdmShipOrderReq.getDriverId();
        String pmId = shipOrderReq.getPmId();
//        String buyerId = pdsAdmShipOrderReq.getBuyerId();
        DeliveryFlow deliveryFlow = deliveryFlowService.fetchByDateSeqDriver(shipOrderReq, null);
        if (null == deliveryFlow) {
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
            BigDecimal totalAmount = pdsShipOrderDetailService.countShipOrderAmount(deliveryDate, buySeq, deliveryFlow.getDriver(), entity.getBuyerId(), pmId);
            entity.setTotalAmount(totalAmount);
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
    public BaseResp findShipOrderPage(Page reqPage, PdsShipBuyerApiReq shipBuyerReq) {
        BaseResp baseResp = BaseResp.newError();

        reqPage.setOrderBy("shipId");
        Page<PdsTradeOrderCommonParamsResp> respPage = pdsShipOrderDetailService.findTruckShipDetailList(reqPage, shipBuyerReq);
        if (CollectionUtils.isEmpty(respPage.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        // 下单数量设置
        BuyOrderDetail detail = new BuyOrderDetail();
        for (PdsTradeOrderCommonParamsResp r : respPage.getList()) {
            detail.setBuyId(r.getBuyId());
            detail.setGoodsId(r.getGoodsModelId());
            List<BuyOrderDetail> detailList = buyOrderDetailService.findList(detail);
            if (!Collections3.isEmpty(detailList)) {
                r.setOrderGoodsNum(detailList.get(0).getOrderGoodsNum());
            }
        }

        BaseList<PdsTradeOrderCommonParamsResp> baseList = new BaseList<>();
        baseList.setCurPage(reqPage.getPageNo());
        baseList.setPageSize(reqPage.getPageSize());
        baseList.setList(respPage.getList());
        baseList.setTotalRows(new Long(reqPage.getCount()).intValue());
        baseList.setTotalPage(reqPage.getTotalPage());
        baseList.success();
        return baseList;
    }

    @Override
    public BaseResp findSelfOrderBuyerList(PdsSelfOrderApiReq apiSelfOrderReq) {
        BaseResp baseResp = BaseResp.newError();
        Date deliveryDate = apiSelfOrderReq.getDeliveryDate();
        String buySeq = apiSelfOrderReq.getBuySeq();
        String pmId = apiSelfOrderReq.getPmId();

        List<PdsApiDeliveryBuyerResp> buyerList = tradeOrderService.findSelfOrderInfoList(deliveryDate, buySeq, pmId);
        if (CollectionUtils.isEmpty(buyerList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        for (PdsApiDeliveryBuyerResp entity : buyerList) {
            String buyerId = entity.getBuyerId();
            entity.setSelfOrderAddress(DictUtils.getDictLabel("0", "pds_self_order_address", ""));
            entity.setTotalAmount(tradeOrderService.countOrderAmount(deliveryDate, buySeq, buyerId, pmId));
        }

        baseResp.success();
        baseResp.setExt(buyerList.stream().collect(Collectors.toCollection(ArrayList::new)));
        return baseResp;
    }

    @Override
    public BaseResp findSelfOrderPage(Page reqPage, PdsSelfOrderApiReq apiSelfOrderReq) {
        BaseResp baseResp = BaseResp.newError();
        BaseList<PdsTradeOrderCommonParamsResp> baseList = new BaseList<>();

        try {
            apiSelfOrderReq.setOpStatus(IPdsConstant.OperatorViewStatus.sorted.getCode());
            apiSelfOrderReq.setTransStatus(IPdsConstant.TradeOrderStatus.done.getCode());
            apiSelfOrderReq.setDeliveryType(IPdsConstant.DeliveryType.self.getCode());
            TradeOrder tradeOrder = new TradeOrder();
            BeanUtils.copyProperties(tradeOrder, apiSelfOrderReq);
            tradeOrder.setDeliveryTime(apiSelfOrderReq.getDeliveryDate());

            Page<PdsTradeOrderCommonParamsResp> respPage = tradeOrderService.findSimpleTradeOrderPage(reqPage, tradeOrder);
            if (CollectionUtils.isEmpty(respPage.getList())) {
                baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return baseResp;
            }

            baseList.setCurPage(reqPage.getPageNo());
            baseList.setPageSize(reqPage.getPageSize());
            baseList.setList(respPage.getList());
            baseList.setTotalRows(new Long(reqPage.getCount()).intValue());
            baseList.setTotalPage(reqPage.getTotalPage());
            baseList.success();
            return baseList;
        } catch (Exception e) {
            e.printStackTrace();
            baseList.error();
            baseList.setMsg("系统错误");
            return baseList;
        }
    }
}
