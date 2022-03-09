package com.haohan.platform.service.sys.modules.pss.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssWarehouseStockApiReq;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturnDetail;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllotDetail;
import com.haohan.platform.service.sys.modules.xiaodian.exception.GoodsStockNotEnoughException;

import java.math.BigDecimal;

/**
 * 商品库存管理Service
 * @author shenyu
 * @create 2018/11/27
 */
public interface IPssGoodsStorageService {

    //查看商品库存列表
    BaseResp findGoodsStoragePage(PssWarehouseStockApiReq stockApiReq, Page page) throws Exception;

    //库存数量直接修改
    BaseResp adjustNum(String warehouseStockId, BigDecimal stockNum);

    //采购入库
    BaseResp procureEnterStock(Procurement procurement, ProcurementDetail detail);

    //销售出库
    BaseResp outStock(String warehouseId, String goodsModelId, BigDecimal num);

    //退货出库
    BaseResp returnOutStock(PurchaseReturn purchaseReturn, PurchaseReturnDetail purchaseReturnDetail) throws GoodsStockNotEnoughException;

    //库存调拨
    BaseResp allotStockModify(GoodsAllot goodsAllot, GoodsAllotDetail goodsAllotDetail) throws GoodsStockNotEnoughException;

}
