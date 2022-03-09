package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssWarehouseStockApiReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturnDetail;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllotDetail;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseStockService;
import com.haohan.platform.service.sys.modules.xiaodian.exception.GoodsStockNotEnoughException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/11/28
 */
@Service
public class PssGoodsStorageServiceImpl implements IPssGoodsStorageService {
    @Resource
    private WarehouseStockService warehouseStockService;

    @Override
    public BaseResp findGoodsStoragePage(PssWarehouseStockApiReq stockApiReq, Page page) throws Exception {
        BaseList<WarehouseStock> baseList = new BaseList<>();

        WarehouseStock warehouseStock = new WarehouseStock();
        BeanUtils.copyProperties(stockApiReq, warehouseStock);
        warehouseStockService.findPage(page,warehouseStock);
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

    //直接修改库存
    @Override
    public BaseResp adjustNum(String warehouseStockId, BigDecimal stockNum) {
        BaseResp baseResp = BaseResp.newError();

        WarehouseStock warehouseStock = warehouseStockService.get(warehouseStockId);
        if (null != warehouseStock){
            warehouseStock.setStockNum(stockNum);
            warehouseStockService.save(warehouseStock);
        } else {

        }

        baseResp.success();
        return baseResp;
    }

    //采购入库
    @Override
    public BaseResp procureEnterStock(Procurement procurement, ProcurementDetail detail) {
        BaseResp baseResp = BaseResp.newError();
        if (IPssConstant.StockStatus.entered.getCode().equals(procurement.getStockStatus())){
            baseResp.setMsg("重复入库");
            return baseResp;
        }
        String goodsModelId = detail.getGoodsModelId();
        String warehouseId = procurement.getWarehouseId();
        WarehouseStock warehouseStock = warehouseStockService.fetchStockGoods(goodsModelId,warehouseId);
        if (null == warehouseStock){
            warehouseStock = new WarehouseStock();
            warehouseStock.setWarehouseId(warehouseId);
            warehouseStock.setMerchantId(detail.getMerchantId());
            warehouseStock.setGoodsCode(detail.getGoodsModelId());
            warehouseStock.setInstockTime(new Date());
            warehouseStock.setStockNum(detail.getGoodsNum());
//            warehouseStock.setSupplierId(supplierId);
            warehouseStock.setGoodsName(detail.getGoodsName());
            warehouseStock.setUnit(detail.getUnit());
            warehouseStock.setAmount(detail.getSumAmount());
            warehouseStock.setGoodsCategory(detail.getCategrory());
            warehouseStock.setAttr(detail.getModelName());
            warehouseStock.setPrice(detail.getPrice());
        }else {
            warehouseStock.setStockNum(warehouseStock.getStockNum().add(detail.getGoodsNum()));
        }
        warehouseStock.setAmount(warehouseStock.getAmount());
        detail.setStockStatus(IPssConstant.StockStatus.entered.getCode());
        warehouseStockService.save(warehouseStock);
        baseResp.success();
        return baseResp;
    }

    //退货出库
    @Override
    public BaseResp returnOutStock(PurchaseReturn purchaseReturn, PurchaseReturnDetail purchaseReturnDetail) throws GoodsStockNotEnoughException {
        BaseResp baseResp = BaseResp.newError();

        String goodsCode = purchaseReturnDetail.getGoodsCode();
        String warehouseId = purchaseReturnDetail.getWarehouseId();
//        String supplierId = purchaseReturn.getSupplierId();
        WarehouseStock warehouseStock = warehouseStockService.fetchStockGoods(goodsCode,warehouseId);
        if (null == warehouseStock || warehouseStock.getStockNum().compareTo(purchaseReturnDetail.getNum()) < 0){
            throw new GoodsStockNotEnoughException("操作失败,商品库存不足");
        }
        warehouseStock.setStockNum(warehouseStock.getStockNum().subtract(purchaseReturnDetail.getNum()));
        warehouseStockService.save(warehouseStock);
        baseResp.success();
        return baseResp;
    }

    //正常销售出库
    @Override
    public BaseResp outStock(String warehouseId, String goodsModelId, BigDecimal num) {
        BaseResp baseResp = BaseResp.newError();

        WarehouseStock warehouseStock = warehouseStockService.fetchStockGoods(goodsModelId,warehouseId);
//        List<WarehouseStock> warehouseStockList = warehouseStockService.findByModelAndWarehouse(goodsModelId,warehouseId);

        if (null == warehouseStock){
            baseResp.setMsg("操作失败,商品库存不足");
            return baseResp;
        }
        warehouseStock.setStockNum(warehouseStock.getStockNum().subtract(num));
        warehouseStockService.save(warehouseStock);
        baseResp.success();
        return baseResp;
    }

    //库存调拨
    @Override
    public BaseResp allotStockModify(GoodsAllot goodsAllot, GoodsAllotDetail goodsAllotDetail) throws GoodsStockNotEnoughException {
        BaseResp baseResp = BaseResp.newError();

        //调出方减库存
        String allotOutId = goodsAllot.getAllotoutId();
        String allotInId = goodsAllot.getAllotinId();
        BigDecimal allotNum = goodsAllotDetail.getGoodsNum();
        WarehouseStock warehouseStockOut = warehouseStockService.fetchStockGoods(goodsAllotDetail.getGoodsCode(),allotOutId);
        if (null == warehouseStockOut || warehouseStockOut.getStockNum().compareTo(allotNum) < 0){
            throw new GoodsStockNotEnoughException("操作失败,商品库存不足");
        }
        BigDecimal orgOutNum = warehouseStockOut.getStockNum();
        BigDecimal nowOutNum = orgOutNum.subtract(allotNum);       //原库存
        warehouseStockOut.setStockNum(nowOutNum);       //调出后库存
        warehouseStockService.save(warehouseStockOut);

        //调入方增库存(不存在库存则新增记录)
        WarehouseStock warehouseStockIn = warehouseStockService.fetchStockGoods(goodsAllotDetail.getGoodsCode(),allotInId);
        BigDecimal orgInNum = BigDecimal.ZERO;
        if (null == warehouseStockIn){
            warehouseStockIn = new WarehouseStock();
            warehouseStockIn.setWarehouseId(allotInId);
            warehouseStockIn.setSupplierId("");
            warehouseStockIn.setPrice(goodsAllotDetail.getPrice());
            warehouseStockIn.setAttr(goodsAllotDetail.getModelName());
            warehouseStockIn.setGoodsCategory(goodsAllotDetail.getGoodsCategory());
            warehouseStockIn.setUnit(goodsAllotDetail.getUnit());
            warehouseStockIn.setGoodsCode(goodsAllotDetail.getGoodsCode());
            warehouseStockIn.setInstockTime(new Date());
            warehouseStockIn.setMerchantId(goodsAllot.getMerchantId());
            warehouseStockIn.setGoodsName(goodsAllotDetail.getGoodsName());
            warehouseStockIn.setStockNum(goodsAllotDetail.getGoodsNum());
        }else {
            orgInNum = warehouseStockIn.getStockNum();
            warehouseStockIn.setStockNum(warehouseStockIn.getStockNum().add(goodsAllotDetail.getGoodsNum()));
        }
        goodsAllotDetail.setOutorigStock(orgOutNum);
        goodsAllotDetail.setOutStock(nowOutNum);
        goodsAllotDetail.setInorigStock(orgInNum);
        goodsAllotDetail.setInStock(warehouseStockIn.getStockNum());
        warehouseStockService.save(warehouseStockIn);
        baseResp.success();
        return baseResp;
    }


}
