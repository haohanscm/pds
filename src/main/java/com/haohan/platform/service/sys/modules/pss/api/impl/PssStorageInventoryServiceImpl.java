package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssStorageInventoryService;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseInventoryDetail;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WarehouseStock;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseInventoryDetailService;
import com.haohan.platform.service.sys.modules.pss.service.storage.WarehouseStockService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/11/28
 */
@Service
public class PssStorageInventoryServiceImpl implements IPssStorageInventoryService {
    @Resource
    private WarehouseInventoryDetailService warehouseInventoryDetailService;
    @Resource
    private WarehouseStockService warehouseStockService;

    @Override
    public BaseResp confirm(String merchantId, String warehouseStockId, BigDecimal num) {
        BaseResp baseResp = BaseResp.newError();

        WarehouseStock warehouseStock = warehouseStockService.get(warehouseStockId);
        WarehouseInventoryDetail warehouseInventoryDetail = new WarehouseInventoryDetail();
        warehouseInventoryDetail.setMerchantId(merchantId);
        warehouseInventoryDetail.setGoodsCode(warehouseStock.getGoodsCode());
        warehouseInventoryDetail.setCheckTime(new Date());
        warehouseInventoryDetail.setSupplierId(warehouseStock.getSupplierId());
        warehouseInventoryDetail.setOrigNum(warehouseStock.getStockNum());
        warehouseInventoryDetail.setModifyNum(num);
        warehouseInventoryDetail.setWarehouseStockId(warehouseStockId);
        warehouseInventoryDetailService.save(warehouseInventoryDetail);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp inventoryRecordPage(String warehouseStockId,Page page) {
        BaseList<WarehouseInventoryDetail> baseList = new BaseList<>();

        WarehouseInventoryDetail inventoryDetail = new WarehouseInventoryDetail();
        inventoryDetail.setWarehouseStockId(warehouseStockId);
        warehouseInventoryDetailService.findPage(page,inventoryDetail);

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
    public BaseResp addInventoryRecord(String warehouseStockId, BigDecimal originStock, BigDecimal editStock, String merchantId) {
        BaseResp baseResp = BaseResp.newError();

        WarehouseStock warehouseStock = warehouseStockService.get(warehouseStockId);
        if (null != warehouseStock){
            WarehouseInventoryDetail inventoryDetail = new WarehouseInventoryDetail();
            inventoryDetail.setWarehouseId(warehouseStock.getWarehouseId());
            inventoryDetail.setWarehouseStockId(warehouseStockId);
            inventoryDetail.setOrigNum(originStock);
            inventoryDetail.setModifyNum(editStock);
            inventoryDetail.setSupplierId(warehouseStock.getSupplierId());
            inventoryDetail.setCheckTime(new Date());
            inventoryDetail.setMerchantId(warehouseStock.getMerchantId());
            inventoryDetail.setGoodsCode(warehouseStock.getGoodsCode());
            warehouseInventoryDetailService.save(inventoryDetail);
        }
        baseResp.success();
        return baseResp;
    }
}
