package com.haohan.platform.service.sys.modules.pds.core.pss.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.pss.IPdsGoodsStorageOpService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.order.OfferOrderService;
import com.haohan.platform.service.sys.modules.pds.service.order.SummaryOrderService;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssProcurementService;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import com.haohan.platform.service.sys.modules.pss.service.info.SupplierService;
import com.haohan.platform.service.sys.modules.pss.service.info.WarehouseService;
import com.haohan.platform.service.sys.modules.pss.service.procure.ProcurementDetailService;
import com.haohan.platform.service.sys.modules.pss.service.procure.ProcurementService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/12/1
 */
@Service
public class PdsGoodsStorageOpServiceImpl implements IPdsGoodsStorageOpService {
    @Resource
    private SummaryOrderService summaryOrderService;
    @Resource
    private IPssProcurementService mercProcurementService;
    @Resource
    private OfferOrderService offerOrderService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private PdsSupplierService pdsSupplierService;
    @Resource
    private WarehouseService warehouseService;
    @Resource
    private ProcurementDetailService procurementDetailService;
    @Resource
    private ProcurementService procurementService;

    @Override
    public BaseResp freightGoodsEnterStock(String[] offerIds, String merchantId) throws StorageOperationException {
        BaseResp baseResp = BaseResp.newError();
        List<OfferOrder> offerOrderList = new ArrayList<>();

        for (String offerId : offerIds) {
            OfferOrder offerOrder = offerOrderService.fetchByOrderId(offerId);
            if (null != offerOrder) {
                offerOrderList.add(offerOrder);
            }
        }
        if (CollectionUtils.isEmpty(offerOrderList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Procurement procurement = createProcurement(offerOrderList, merchantId);
        if (null != procurement) {
            baseResp = mercProcurementService.saveProcurement(procurement);

            //揽货直接修改进货单状态为已入库
            if (baseResp.isSuccess()) {
                procurement.setStockStatus(IPssConstant.StockStatus.entered.getCode());
                List<ProcurementDetail> procurementDetails = procurementDetailService.findByProcureNumWithStatus(procurement.getProcureNum(), IPssConstant.StockStatus.not_in);
                for (ProcurementDetail detail : procurementDetails) {
                    detail.setStockStatus(IPssConstant.StockStatus.entered.getCode());
                    procurementDetailService.save(detail);
                }
                procurementService.save(procurement);
            }
        }
        baseResp.success();
        return baseResp;

    }

    private Procurement createProcurement(List<OfferOrder> offerOrderList, String merchantId) {
        Procurement procurement = null;
        try {
            List<ProcurementDetail> detailList = new ArrayList<>();

            String supplierId = offerOrderList.get(0).getSupplierId();
//            Supplier pssSupplier = supplierService.get(supplierId);
            PdsSupplier pdsSupplier = pdsSupplierService.get(supplierId);
            if (null == pdsSupplier) {
                return procurement;
            }
//            if (null == pssSupplier){
//                pssSupplier = new Supplier();
//                BeanUtils.copyProperties(pdsSupplier, pssSupplier);
//                pssSupplier.setIsNewRecord(true);
//                pssSupplier.setUid(pdsSupplier.getPassportId());
//                supplierService.save(pssSupplier);
//            }
            PssWarehouse warehouse = new PssWarehouse();
            warehouse.setMerchantId(merchantId);
            List<PssWarehouse> warehouseList = warehouseService.findList(warehouse);
            if (CollectionUtils.isEmpty(warehouseList)) {
                warehouse.setName("默认仓库");
                warehouse.setStatus(ICommonConstant.IsEnable.enable.getCode());
                warehouseService.save(warehouse);
            } else {
                warehouse = warehouseList.get(0);
            }

            BigDecimal sumAmount = BigDecimal.ZERO;

            for (OfferOrder offerOrder : offerOrderList) {
                PdsSupplier supplier = pdsSupplierService.get(offerOrder.getSupplierId());
                if (null != supplier) {
                    // 库存供应商 不创建 采购单明细
                    if (!IPdsConstant.SupplierType.stock.getCode().equals(supplier.getSupplierType())) {
                        SummaryOrder summaryOrder = summaryOrderService.fetchBySummaryOrderId(offerOrder.getAskOrderId());
                        BigDecimal dealPrice = offerOrder.getDealPrice();
                        BigDecimal goodsNum = offerOrder.getBuyNum();
                        ProcurementDetail procurementDetail = new ProcurementDetail();
                        procurementDetail.setStockStatus(IPssConstant.StockStatus.not_in.getCode());
                        procurementDetail.setGoodsModelId(summaryOrder.getGoodsId());
                        procurementDetail.setGoodsName(summaryOrder.getGoodsName());
                        procurementDetail.setCategrory(summaryOrder.getGoodsCategoryId());
                        procurementDetail.setModelName(summaryOrder.getGoodsModel());
                        procurementDetail.setUnit(summaryOrder.getUnit());
                        procurementDetail.setPrice(dealPrice);
                        procurementDetail.setGoodsNum(goodsNum);
                        // 设置采购明细对应报价单 保存时不再创建报价单
                        procurementDetail.setOfferOrderId(offerOrder.getOfferOrderId());
                        sumAmount = sumAmount.add(dealPrice.multiply(goodsNum));
                        detailList.add(procurementDetail);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(detailList)) {
                procurement = new Procurement();
                procurement.setStockStatus(IPssConstant.StockStatus.not_in.getCode());
                procurement.setNum(offerOrderList.size());
                procurement.setSupplierId(supplierId);
                procurement.setOpTime(new Date());
                procurement.setMerchantId(merchantId);
                procurement.setSumAmount(sumAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
                procurement.setOtherAmount(BigDecimal.ZERO);
                procurement.setPayAmount(BigDecimal.ZERO);
                procurement.setTotalAmount(procurement.fetchTotalAmount());
                procurement.setWarehouseId(warehouse.getId());
                procurement.setDetailList(detailList);
                procurement.setProcureDate(offerOrderList.get(0).getPrepareDate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return procurement;
        }
    }
}
