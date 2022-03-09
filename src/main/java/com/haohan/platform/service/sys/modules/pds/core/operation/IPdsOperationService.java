package com.haohan.platform.service.sys.modules.pds.core.operation;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.operate.PdsAfterSaleOrderReq;
import com.haohan.platform.service.sys.modules.pds.entity.req.PdsOfferOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.exception.StorageOperationException;

import java.util.Date;

/**
 * Created by zgw on 2018/10/20.
 */
public interface IPdsOperationService {

    //获取已成交的供应商列表
    BaseResp findDealSupList(PdsOfferOrderReq pdsOfferOrderReq);

    //获取供应商供应的所有商品列表
    BaseResp findSupGoodsList(Date deliveryDate, String buySeq, String supplierId, String shipStatus);

    //揽货确认
    BaseResp freightConfirm(String operatorUid, String offerOrderId, String goodsId);

    //获取待揽货订单数
    BaseResp countDealNum(Date date, String pmId);

    //售后单
    BaseResp afterSale(PdsAfterSaleOrderReq pdsAfterSaleOrderReq);

    // 按供应商揽货
    BaseResp supplierFreightConfirm(String pmId, String supplierId, String buySeq, Date deliveryDate) throws StorageOperationException;

    //分拣列表


}
