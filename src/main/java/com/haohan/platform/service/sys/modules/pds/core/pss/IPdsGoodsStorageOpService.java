package com.haohan.platform.service.sys.modules.pds.core.pss;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;

/**
 * @author shenyu
 * @create 2018/12/1
 */
public interface IPdsGoodsStorageOpService {
    //揽货商品入库
    BaseResp freightGoodsEnterStock(String[] offerIds, String merchantId) throws StorageOperationException;


}
