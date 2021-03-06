package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.exception.ApiException;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsAdminSumBuyOrder;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsOfferOrderSaveParams;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSumOrderDetail;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiSumBuyDetailBatchReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsPreSumOrderApiResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminSummaryService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.dto.LastDealDTO;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderDetailService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.SummaryOrderService;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseStockService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/10/30
 */
@Service
public class AdminSummaryServiceImpl implements IPdsAdminSummaryService {
    @Resource
    private SummaryOrderService summaryOrderService;
    @Resource
    private BuyOrderDetailService buyOrderDetailService;
    @Resource
    private OfferOrderService offerOrderService;
    @Resource
    private BuyOrderService buyOrderService;
    @Autowired
    private WarehouseStockService warehouseStockService;
    @Autowired
    private PdsSupplierService pdsSupplierService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Override
    public BaseResp findSummaryOrderPage(Page<PdsAdminSumBuyOrder> reqPage, PdsAdminSumBuyOrder pdsAdminSumBuyOrder) {
        String pmId = pdsAdminSumBuyOrder.getPmId();
        BaseResp baseResp = BaseResp.newError();
        reqPage.setOrderBy("summaryOrderId");
        summaryOrderService.findAdminSumOrderPage(reqPage, pdsAdminSumBuyOrder);
        if (CollectionUtils.isEmpty(reqPage.getList())) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        List<PdsAdminSumBuyOrder> respList = reqPage.getList();
        Map<String, String> categoryMap = new HashMap<>(16);
        for (PdsAdminSumBuyOrder sumBuyOrder : respList) {
            String summaryId = sumBuyOrder.getSummaryOrderId();
            if (StringUtils.isNotEmpty(summaryId)) {
                List<PdsAdminSumOrderDetail> buyOrderDetailList = buyOrderDetailService.findBySummaryOrder(summaryId);
                // ?????????????????????
                LastDealDTO lasePurchase;
                for (PdsAdminSumOrderDetail detail : buyOrderDetailList) {
                    lasePurchase = buyOrderDetailService.fetchLastDeal(pmId, detail.getGoodsId(), detail.getBuyerId());
                    detail.setLastPrice(lasePurchase.getLastPrice());
                    detail.setLastDate(lasePurchase.getLastDate());
                }
                sumBuyOrder.setBuyOrderDetailList(buyOrderDetailList);
                List<OfferOrder> offerOrderList = offerOrderService.findByAskId(summaryId);
                sumBuyOrder.setOfferOrderList(offerOrderList);
            }
            WarehouseStock warehouseStock = warehouseStockService.fetchStockGoods(sumBuyOrder.getGoodsId(), "");
            if (null != warehouseStock) {
                BigDecimal stockNum = warehouseStock.getStockNum();
                sumBuyOrder.setStockNum(stockNum);
            }
            String parentCategoryId = sumBuyOrder.getParentCategoryId();
            String parentCategoryName = "";
            if (categoryMap.containsKey(parentCategoryId)) {
                parentCategoryName = categoryMap.get(parentCategoryId);
            } else {
                // ??????????????????
                GoodsCategory goodsCategory = goodsCategoryService.get(sumBuyOrder.getParentCategoryId());
                if (null != goodsCategory) {
                    categoryMap.put(goodsCategory.getId(), goodsCategory.getName());
                    parentCategoryName = goodsCategory.getName();
                }
            }
            sumBuyOrder.setParentCategoryName(parentCategoryName);
        }

        BaseList<PdsAdminSumBuyOrder> baseList = new BaseList<>();
        baseList.setCurPage(reqPage.getPageNo());
        baseList.setPageSize(reqPage.getPageSize());
        baseList.setList(respList);
        baseList.setTotalRows(new Long(reqPage.getCount()).intValue());
        baseList.setTotalPage(reqPage.getTotalPage());
        baseList.success();
        return baseList;
    }

    @Override
    public BaseResp findBuyDetailBySumId(String summaryOrderId, String pmId) {
        BaseResp baseResp = BaseResp.newError();

        List<PdsAdminSumOrderDetail> list = buyOrderDetailService.findBySummaryOrder(summaryOrderId);
        if (CollectionUtils.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.setExt(list.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    /**
     * ????????????????????????  ??????
     *
     * @param detailId
     * @param buyNum
     * @param buyPrice
     * @return
     */
    @Override
    public BaseResp editBuyOrderDetail(String detailId, BigDecimal buyNum, BigDecimal buyPrice) {
        BaseResp baseResp = BaseResp.newError();

        BuyOrderDetail buyOrderDetail = buyOrderDetailService.get(detailId);
        if (null == buyOrderDetail) {
            baseResp.setMsg("?????????????????????");
            return baseResp;
        }
        String iniCode = IPdsConstant.BuyOrderStatus.submit.getCode();
        if (!StringUtils.equals(buyOrderDetail.getStatus(), iniCode)) {
            baseResp.setMsg("???????????????????????????");
            return baseResp;
        }
        if (null != buyNum) {
            buyOrderDetail.setGoodsNum(buyNum);
        }
        if (null != buyPrice) {
            buyOrderDetail.setBuyPrice(buyPrice);
        }

        buyOrderDetailService.save(buyOrderDetail);
        BuyOrder buyOrder = buyOrderService.fetchByBuyId(buyOrderDetail.getBuyId());
        if (null != buyOrder) {
            BuyOrder updateBuyOrder = new BuyOrder(buyOrder.getId());
            BigDecimal totalAmount = buyOrderDetailService.countTotalAmount(buyOrder.getBuyId());
            updateBuyOrder.setTotalPrice(totalAmount.add(null == buyOrder.getShipFee() ? BigDecimal.ZERO : buyOrder.getShipFee()));
            buyOrderService.updatePart(updateBuyOrder);
        }

        //???????????????????????????
        SummaryOrder summaryOrder = summaryOrderService.fetchBySummaryOrderId(buyOrderDetail.getSmmaryBuyId());
        if (null != summaryOrder) {
            SummaryOrder updateOrder = new SummaryOrder(summaryOrder.getId());
            updateOrder.setBuyAvgPrice(buyOrderDetailService.countBuyAvgPrice(buyOrderDetail.getSmmaryBuyId()));
            summaryOrderService.updatePrice(updateOrder);
        }
        baseResp.success();
        return baseResp;
    }

    /**
     * ???????????????????????? ??????
     *
     * @param buyDetailBatchReq
     * @return
     */
    @Override
    public BaseResp editBuyOrderDetailBatch(PdsApiSumBuyDetailBatchReq buyDetailBatchReq) {
        BaseResp baseResp = BaseResp.newError();
        String detailId;
        BigDecimal buyNum;
        BigDecimal buyPrice;
        Set<String> buyIdSet = new HashSet<>();
        Set<String> summaryIdSet = new HashSet<>();
        BuyOrderDetail buyOrderDetail;
        String iniCode = IPdsConstant.BuyOrderStatus.submit.getCode();
        String iniCode2 = IPdsConstant.BuyOrderStatus.wait.getCode();
        for (PdsAdminSumOrderDetail pdsAdminSumOrderDetail : buyDetailBatchReq.getDetailList()) {
            // ??????
            detailId = pdsAdminSumOrderDetail.getDetailId();
            buyNum = pdsAdminSumOrderDetail.getGoodsNum();
            buyPrice = pdsAdminSumOrderDetail.getBuyPrice();
            if (StringUtils.isEmpty(detailId)) {
                baseResp.setMsg("????????????detailId");
                return baseResp;
            } else if (null == buyPrice || BigDecimal.ZERO.compareTo(buyPrice) >= 0) {
                baseResp.setMsg("?????????????????????");
                return baseResp;
            } else {
                buyOrderDetail = buyOrderDetailService.get(detailId);
                if (null == buyOrderDetail) {
                    baseResp.setMsg("?????????????????????");
                    return baseResp;
                } else if (!StringUtils.equalsAny(buyOrderDetail.getStatus(), iniCode, iniCode2)) {
                    baseResp.setMsg("???????????????????????????");
                    return baseResp;
                }
            }
            // ??????????????????/??????
            if (null != buyNum) {
                buyOrderDetail.setGoodsNum(buyNum);
            }
            buyOrderDetail.setBuyPrice(buyPrice);
            // ????????????
            buyOrderDetailService.save(buyOrderDetail);
            if (!StringUtils.isEmpty(buyOrderDetail.getBuyId())) {
                buyIdSet.add(buyOrderDetail.getBuyId());
            }
            if (!StringUtils.isEmpty(buyOrderDetail.getSmmaryBuyId())) {
                summaryIdSet.add(buyOrderDetail.getSmmaryBuyId());
            }
        }
        // ?????????????????????
        BuyOrder buyOrder;
        BuyOrder updateBuyOrder;
        for (String buyId : buyIdSet) {
            buyOrder = buyOrderService.fetchByBuyId(buyId);
            if (null != buyOrder) {
                updateBuyOrder = new BuyOrder(buyOrder.getId());
                BigDecimal totalAmount = buyOrderDetailService.countTotalAmount(buyId);
                updateBuyOrder.setTotalPrice(totalAmount.add(null == buyOrder.getShipFee() ? BigDecimal.ZERO : buyOrder.getShipFee()));
                buyOrderService.updatePart(updateBuyOrder);
            }
        }
        //???????????????????????????
        SummaryOrder summaryOrder;
        SummaryOrder updateOrder;
        for (String summaryId : summaryIdSet) {
            summaryOrder = summaryOrderService.fetchBySummaryOrderId(summaryId);
            if (null != summaryOrder) {
                updateOrder = new SummaryOrder(summaryOrder.getId());
                updateOrder.setBuyAvgPrice(buyOrderDetailService.countBuyAvgPrice(summaryId));
                summaryOrderService.updatePrice(updateOrder);
            }
        }
        baseResp.success();
        return baseResp;
    }

    /**
     * ????????????????????????
     * ?????????????????? ??????/????????????
     *
     * @param summaryOrder            ????????????id
     * @param pdsOfferOrderSaveParams
     * @return
     * @throws Exception
     */
    @Override
    public BaseResp saveOfferOrder(SummaryOrder summaryOrder, PdsOfferOrderSaveParams pdsOfferOrderSaveParams) throws Exception {
        BaseResp baseResp = BaseResp.newError();

        String supplierId = pdsOfferOrderSaveParams.getSupplierId();
        String supplierName = pdsOfferOrderSaveParams.getSupplierName();
        BigDecimal supplierNum = pdsOfferOrderSaveParams.getSupplyNum();
        Integer minSaleNum = pdsOfferOrderSaveParams.getMinSaleNum();
        minSaleNum = (null == minSaleNum || minSaleNum <= 0) ? 0 : minSaleNum;
        BigDecimal buyNum = pdsOfferOrderSaveParams.getBuyNum();
        BigDecimal supplyPrice = pdsOfferOrderSaveParams.getSupplyPrice();
        String summaryOrderId = pdsOfferOrderSaveParams.getAskOrderId();
        BigDecimal otherAmount = pdsOfferOrderSaveParams.getOtherAmount();

        OfferOrder offerOrder = null;
        Date now = new Date();
        // ?????????????????? ????????????????????????
        if (StringUtils.isNoneEmpty(summaryOrderId, supplierId)) {
            OfferOrder queryOffer = new OfferOrder();
            queryOffer.setAskOrderId(summaryOrderId);
            queryOffer.setSupplierId(supplierId);
            List<OfferOrder> offerOrderList = offerOrderService.findList(queryOffer);
            if (CollectionUtils.isNotEmpty(offerOrderList)) {
                offerOrder = offerOrderList.get(0);
                pdsOfferOrderSaveParams.setOfferId(offerOrder.getId());
            }
        }
        if (StringUtils.isEmpty(pdsOfferOrderSaveParams.getOfferId())) {
            //??????
            if (StringUtils.isAnyEmpty(supplierId, supplierName, summaryOrderId) || null == supplierNum
                    || null == minSaleNum || null == supplyPrice) {
                baseResp.putStatus(RespStatus.PARAMS_DATA_NULL);
                return baseResp;
            }
            offerOrder = new OfferOrder();
            // ??????spring???beanUtils
            BeanUtils.copyProperties(pdsOfferOrderSaveParams, offerOrder);
            offerOrder.setOfferType(IPdsConstant.OfferType.platform.getCode());
            offerOrder.setStatus(IPdsConstant.OfferOrderStatus.submit.getCode());
            offerOrder.setAskPriceTime(now);
            offerOrder.setOfferPriceTime(now);
            offerOrder.setPrepareDate(summaryOrder.getDeliveryTime());
            offerOrder.setBuySeq(summaryOrder.getBuySeq());
            offerOrder.setGoodsName(summaryOrder.getGoodsName());
            offerOrder.setGoodsId(summaryOrder.getGoodsId());
            offerOrder.setPmId(summaryOrder.getPmId());
            // ???????????????modelId ???goodsId???
            offerOrder.setGoodsModelId(summaryOrder.getGoodsId());
            offerOrder.setGoodsImg(summaryOrder.getGoodsImg());
            offerOrder.setUnit(summaryOrder.getUnit());
            offerOrder.setModelName(summaryOrder.getGoodsModel());
            offerOrder.setReceiveType(IPdsConstant.ReceiveType.delivery.getCode());
            offerOrder.setGoodsCategoryId(summaryOrder.getGoodsCategoryId());
        } else {
            //??????
            if (null == offerOrder) {
                offerOrder = offerOrderService.get(pdsOfferOrderSaveParams.getOfferId());
            }
            if (null == offerOrder) {
                baseResp.setMsg("?????????ID??????");
                return baseResp;
            }
            if (StringUtils.isNotEmpty(supplierId)) {
                offerOrder.setSupplierId(supplierId);
            }
            if (StringUtils.isNotEmpty(supplierName)) {
                offerOrder.setSupplierName(supplierName);
            }
            if (null != supplierNum) {
                offerOrder.setSupplyNum(supplierNum);
            }
            offerOrder.setMinSaleNum(minSaleNum);
            if (null != supplyPrice) {
                offerOrder.setSupplyPrice(supplyPrice);
            }
            if (null != otherAmount) {
                offerOrder.setOtherAmount(otherAmount);
            }
        }
        PdsSupplier pdsSupplier = pdsSupplierService.get(supplierId);
        if (null != pdsSupplier) {
            offerOrder.setSupplierName(pdsSupplier.getSupplierName());
        }
        offerOrder.setBuyNum(null == buyNum ? BigDecimal.ZERO : buyNum);
        offerOrder.setOfferPriceTime(now);
        offerOrder.setPmId(summaryOrder.getPmId());
        // ????????????
        supplyPrice = offerOrder.getSupplyPrice();
        buyNum = offerOrder.getBuyNum();
        if (null != supplyPrice && null != buyNum) {
            otherAmount = offerOrder.getOtherAmount();
            otherAmount = (null == otherAmount) ? BigDecimal.ZERO : otherAmount;
            BigDecimal total = supplyPrice.multiply(buyNum).add(otherAmount);
            offerOrder.setTotalAmount(total);
        }
        offerOrderService.save(offerOrder);
        //??????????????????????????? ?????????????????????
        SummaryOrder order = new SummaryOrder(summaryOrder.getId());
        BigDecimal supplyAvgPrice = offerOrderService.countSupplyAvgPrice(summaryOrderId);
        order.setStatus(IPdsConstant.SummaryOrderStatus.offered.getCode());
        order.setSupplyAvgPrice(supplyAvgPrice);
        summaryOrderService.updatePrice(order);
        //???????????????ID
        pdsOfferOrderSaveParams.setOfferId(offerOrder.getId());
        baseResp.success();
        return baseResp;
    }

    /**
     * ??????????????? ????????????/????????????
     *
     * @param sumOrderId
     * @param platformPrice
     * @param buyAvgPrice
     * @return
     */
    @Override
    public BaseResp editSumOrder(String sumOrderId, BigDecimal platformPrice, BigDecimal buyAvgPrice) {
        BaseResp baseResp = BaseResp.newError();

        SummaryOrder summaryOrder = summaryOrderService.fetchBySummaryOrderId(sumOrderId);
        if (null == summaryOrder) {
            baseResp.setMsg("??????????????????");
            return baseResp;
        }
        SummaryOrder order = new SummaryOrder(summaryOrder.getId());
        order.setPlatformPrice(platformPrice);
        order.setBuyAvgPrice(buyAvgPrice);
        summaryOrderService.updatePrice(order);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp delOfferOrder(String offerOrderId) {
        BaseResp baseResp = BaseResp.newError();

        OfferOrder offerOrder = offerOrderService.fetchByOrderId(offerOrderId);
        if (null == offerOrder) {
            baseResp.setMsg("??????????????????");
            return baseResp;
        }

        offerOrderService.delete(offerOrder);
        SummaryOrder summaryOrder = summaryOrderService.fetchBySummaryOrderId(offerOrder.getAskOrderId());
        if (null != summaryOrder) {
            BigDecimal supplyAvgPrice = offerOrderService.countSupplyAvgPrice(summaryOrder.getSummaryOrderId());
            summaryOrder.setSupplyAvgPrice(supplyAvgPrice);
            summaryOrderService.save(summaryOrder);
        }

        baseResp.success();
        return baseResp;
    }

    public List<PdsPreSumOrderApiResp> preSummaryOrderList(Date reqDate) {
        List<PdsPreSumOrderApiResp> respList = buyOrderDetailService.selectPreSummaryOrders(reqDate);
        if (CollectionUtils.isEmpty(respList)) {
            throw new ApiException(RespStatus.DATA_RECODE_IS_NULL);
        }
        return respList;
    }


}
