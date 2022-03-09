package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssProcurementApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssProcurementService;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import com.haohan.platform.service.sys.modules.pss.service.procure.ProcurementDetailService;
import com.haohan.platform.service.sys.modules.pss.service.procure.ProcurementService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author shenyu
 * @create 2018/11/16
 */
@Service
public class PssProcureServiceImpl implements IPssProcurementService {
    @Resource
    private ProcurementService procurementService;
    @Resource
    private ProcurementDetailService procurementDetailService;
    @Resource
    private IPssGoodsStorageService mercGoodsStorageService;
    @Resource
    private OfferOrderService offerOrderService;
    @Resource
    private GoodsModelService goodsModelService;

    /**
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp saveProcurement(Procurement procurement) throws StorageOperationException{
        BaseResp baseResp = BaseResp.newError();

        if (IPssConstant.StockStatus.entered.getCode().equals(procurement.getStockStatus())){
            baseResp.setMsg("订单已入库,无法修改");
            return baseResp;
        }
        List<ProcurementDetail> procurementDetailList = procurement.getDetailList();

        procurement.setOpTime(new Date());
        procurement.setNum(procurementDetailList.size());
        procurement.setTotalAmount(procurement.fetchTotalAmount());
//        if (StringUtils.isEmpty(procurement.getStockStatus())){
            procurement.setStockStatus(IPssConstant.StockStatus.not_in.getCode());
//        }
        procurementService.save(procurement);

        for (ProcurementDetail item : procurementDetailList){
            if (StringUtils.isNotEmpty(item.getId())){
                if (IPssConstant.StockStatus.entered.getCode().equals(item.getStockStatus())){
                    throw new StorageOperationException(item.getGoodsName()+"重复入库");
                }
            }
            //保存采购明细
            item.setProcureNum(procurement.getProcureNum());
            item.setSumAmount(item.getPrice().multiply(item.getGoodsNum()));
            item.setMerchantId(procurement.getMerchantId());
            if (StringUtils.isEmpty(item.getStockStatus())){
                item.setStockStatus(IPssConstant.StockStatus.not_in.getCode());
            }
            procurementDetailService.save(item);
            //库存变更
            if (IPssConstant.StockStatus.not_in.getCode().equals(item.getStockStatus())){
                baseResp = mercGoodsStorageService.procureEnterStock(procurement,item);
                if (!baseResp.isSuccess()){
                    return baseResp;
                }
            }
        }
        procurement.setStockStatus(IPssConstant.StockStatus.not_in.getCode());
        procurementService.save(procurement);

        // 创建报价单
        baseResp = createOfferOrder(procurement);

        baseResp.success();
        baseResp.setExt(procurement.getId());
        return baseResp;
    }


//    /**
//     * 修改采购单 (弃用)
//     * @param procurement
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public BaseResp editProcurement(Procurement procurement) throws Exception{
//        BaseResp baseResp = new BaseResp();
//
//        //已入库不能修改
//        if (IPssConstant.StockStatus.entered.getCode().equals(procurement.getStockStatus())){
//            baseResp.setMsg("商品已入库,无法修改");
//            return baseResp;
//        }
//        Procurement procure = procurementService.fetchByProcureNum(procurement.getProcureNum());
//        if (null == procure){
//            throw new DataNotFoundException("未找到采购单");
//        }
//        procure.setSupplierId(procurement.getSupplierId());
//        procure.setWarehouseId(procurement.getWarehouseId());
//        procure.setPayAmount(procurement.getPayAmount());
//        procure.setPayType(procurement.getPayType());
//        procure.setBizNote(procurement.getBizNote());
//        procure.setTotalAmount(procure.fetchTotalAmount());
//        procure.setOpTime(new Date());
//        procurementService.save(procure);
//
//        baseResp.success();
//        return baseResp;
//    }
//
//    //弃用
//    @Override
//    public BaseResp editProcurementDetail(List<ProcurementDetail> procurementDetailList, String merchantId,String procureNum) {
//        BaseResp baseResp = new BaseResp();
//
//        for (ProcurementDetail detail : procurementDetailList){
//            detail.setMerchantId(merchantId);
//            detail.setProcureNum(procureNum);
//            if (StringUtils.isEmpty(detail.getId())){
//                procurementDetailService.save(detail);
//            }else {
//                procurementDetailService.updateSelective(detail);
//            }
//        }
//        //修改采购单商品数量,总价
//        Integer totalGoodsNum = procurementDetailService.countTotalGoodsNum(procureNum);
//        BigDecimal sumAmount = procurementDetailService.countSumAmount(procureNum);
//        Procurement procurement = procurementService.fetchByProcureNum(procureNum);
//        procurement.setNum(totalGoodsNum);
//        procurement.setSumAmount(sumAmount);
//        procurement.setTotalAmount(procurement.fetchTotalAmount());
//        procurementService.save(procurement);
//
//        baseResp.success();
//        return baseResp;
//    }

    @Override
    public BaseResp delProcurement(String merchantId,String id) {
        BaseResp baseResp = BaseResp.newError();

        Procurement procurement = procurementService.get(id);
        if (null == procurement){
            baseResp.setMsg("记录已删除");
            return baseResp;
        }
        if (!StringUtils.equals(merchantId,procurement.getMerchantId())){
            baseResp.setMsg("merchantId有误");
            return baseResp;
        }

        //先删除明细
        List<ProcurementDetail> detailList = procurementDetailService.findByProcureNumWithStatus(procurement.getProcureNum(),null);
        for (ProcurementDetail detail : detailList){
            procurementDetailService.delete(detail);
        }
        //删除采购单
        procurementService.delete(procurement);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp delDetail(String detailId) {
        BaseResp baseResp = BaseResp.newError();

        ProcurementDetail procurementDetail = procurementDetailService.get(detailId);
        if (null == procurementDetail){
            baseResp.setMsg("记录已删除");
            return baseResp;
        }
        procurementDetailService.delete(procurementDetail);

        //更新采购单num
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp listProcurement(PssProcurementApiReq procurementApiReq,Page page) {
        BaseList<Procurement> baseList = new BaseList<>();

        try {
            Procurement procurement = new Procurement();
            BeanUtils.copyProperties(procurementApiReq, procurement);

            procurementService.findPage(page,procurement);
            if (CollectionUtils.isEmpty(page.getList())){
                baseList.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return baseList;
            }

            baseList.setTotalRows(new Long(page.getCount()).intValue());
            baseList.setCurPage(page.getPageNo());
            baseList.setTotalPage(page.getTotalPage());
            baseList.setList(page.getList());
            baseList.setPageSize(page.getPageSize());
            baseList.success();
        } catch (Exception e) {
            baseList.setMsg("系统错误");
            e.printStackTrace();
        } finally {
            return baseList;
        }
    }

    @Override
    public BaseResp listProcureDetails(Page page,String procureNum) {
        BaseList baseList = new BaseList<>();

        ProcurementDetail procurementDetail = new ProcurementDetail();
        procurementDetail.setProcureNum(procureNum);
        procurementDetailService.findPage(page,procurementDetail);

        if (CollectionUtils.isEmpty(page.getList())){
            baseList.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseList;
        }
        baseList.setTotalPage(page.getTotalPage());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.success();
        return baseList;
    }

    @Override
    public BaseResp enterStock(String procureId) {
        BaseResp baseResp = BaseResp.newError();

        Procurement procurement = procurementService.get(procureId);
        if (null == procurement || procurement.getStockStatus().equals(IPssConstant.StockStatus.entered.getCode())){
            baseResp.setMsg("操作失败,重复入库");
            return baseResp;
        }

        List<ProcurementDetail> detailList = procurementDetailService.findByProcureNumWithStatus(procurement.getProcureNum(),IPssConstant.StockStatus.not_in);
        if (CollectionUtils.isEmpty(detailList)){
            baseResp.setMsg("操作失败,暂无待入库的采购单");
            return baseResp;
        }

        List<String> offerOrderIdList = new ArrayList<>();

        for (ProcurementDetail detail : detailList){
            baseResp = mercGoodsStorageService.procureEnterStock(procurement,detail);
            if (!baseResp.isSuccess()){
                return baseResp;
            }
            procurementDetailService.save(detail);
            String offerOrderId = detail.getOfferOrderId();
            if(StringUtils.isNotEmpty(offerOrderId)){
                offerOrderIdList.add(offerOrderId);
            }
        }

        procurement.setStockStatus(IPssConstant.StockStatus.entered.getCode());
        procurementService.save(procurement);

        modifyOfferOrder(offerOrderIdList, procurement.getMerchantId());
        return baseResp;
    }

    /**
     * 根据采购单 修改报价单为已收货
     */
    private BaseResp modifyOfferOrder(List<String> offerIdList, String pmId) {
        BaseResp baseResp = BaseResp.newError();
        List<OfferOrder> offerList;
        OfferOrder query = new OfferOrder();
        query.setPmId(pmId);
        for(String offerOrderId : offerIdList){
            query.setOfferOrderId(offerOrderId);
            offerList = offerOrderService.findList(query);
            if(!Collections3.isEmpty(offerList)){
                OfferOrder update = offerList.get(0);
                update.setShipStatus(IPdsConstant.OfferShipStatus.receiveCargo.getCode());
                offerOrderService.save(update);
            }
        }
        return baseResp;
    }

    /**
     * 根据采购单 创建报价单
     *
     * @param req 采购单id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResp createOfferOrder(Procurement req) {
        BaseResp baseResp = BaseResp.newError();
        // 采购单及明细
        Procurement procurement = procurementService.get(req.getId());
        if (null == procurement) {
            baseResp.setMsg("采购单有误");
            return baseResp;
        }
        ProcurementDetail query = new ProcurementDetail();
        query.setMerchantId(procurement.getMerchantId());
        query.setProcureNum(procurement.getProcureNum());
        List<ProcurementDetail> detailList = procurementDetailService.findList(query);
        if (CollectionUtils.isEmpty(detailList)) {
            baseResp.setMsg("采购单明细有误");
            return baseResp;
        }
        // 明细对应报价单  采购单其他金额放在一个报价单上
        BigDecimal other = BigDecimal.ZERO;
        if (null != procurement.getOtherAmount()) {
            other= procurement.getOtherAmount();
        }
        int num = 0;
        OfferOrder offer;
        BigDecimal total;
        for (ProcurementDetail detail : detailList) {
            if (StringUtils.isNotEmpty(detail.getOfferOrderId())) {
                baseResp.setMsg("采购单已存在报价单");
                return baseResp;
            }
            offer = new OfferOrder();
            // 通用参数  报价单状态  中标  待备货
            initOffer(procurement, offer);
            // 明细参数
            if(!copyDetail(detail, offer)){
                baseResp.setMsg("商品规格有误");
                return baseResp;
            }
            if(num == 0){
                offer.setOtherAmount(other);
            }
            total = offer.getBuyNum().multiply(offer.getDealPrice()).add(offer.getOtherAmount());
            offer.setTotalAmount(total);
            offerOrderService.save(offer);
            detail.setOfferOrderId(offer.getOfferOrderId());
            procurementDetailService.save(detail);
            num++;
        }
        return baseResp.success();
    }

    private Boolean copyDetail(ProcurementDetail detail, OfferOrder offer) {
        offer.setBuyNum(detail.getGoodsNum());
        offer.setSupplyPrice(detail.getPrice());
        offer.setDealPrice(detail.getPrice());

        GoodsModel model = goodsModelService.fetch(detail.getGoodsModelId());
        if(null == model){
            return false;
        }
        // 目前goodsId使用的 商品规格id
        offer.setGoodsId(detail.getGoodsModelId());
        offer.setGoodsModelId(detail.getGoodsModelId());
        offer.setGoodsName(model.getGoodsName());
        offer.setUnit(model.getModelUnit());
        offer.setModelName(model.getModelName());
        offer.setGoodsCategoryId(model.getGoodsCategoryId());
        offer.setGoodsImg(model.getModelUrl());
//        offer.setTotalAmount();
        return true;
    }

    private void initOffer(Procurement procurement, OfferOrder offer) {
        offer.setPmId(procurement.getMerchantId());
        offer.setOfferType(IPdsConstant.OfferType.platform.getCode());
        offer.setSupplierId(procurement.getSupplierId());
        offer.setSupplierName(procurement.getSupplierName());
        offer.setStatus(IPdsConstant.OfferOrderStatus.success.getCode());
        Date date = new Date();
        offer.setAskPriceTime(date);
        offer.setOfferPriceTime(date);
        offer.setDealTime(date);
        offer.setShipStatus(IPdsConstant.OfferShipStatus.prepare.getCode());
        offer.setPrepareDate(procurement.getProcureDate());
        offer.setBuySeq(IPdsConstant.BuySeq.first.getCode());
        offer.setOtherAmount(BigDecimal.ZERO);
        offer.setReceiveType(IPdsConstant.ReceiveType.delivery.getCode());
        // 默认供应数量
        offer.setSupplyNum(new BigDecimal(9999));
        offer.setMinSaleNum(0);
    }

}
