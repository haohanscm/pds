package com.haohan.platform.service.sys.modules.pss.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/11/28
 */
@Service
public interface IPssStorageInventoryService {
    //数量确认
    BaseResp confirm(String merchantId, String warehouseStockId, BigDecimal num);

    //盘点记录列表
    BaseResp inventoryRecordPage(String warehouseStockId, Page page);

    //添加盘点记录
    BaseResp addInventoryRecord(String warehouseStockId, BigDecimal originStock, BigDecimal editStock, String merchantId);
}
