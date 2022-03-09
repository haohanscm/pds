package com.haohan.platform.service.sys.modules.pds.core.supplier;

import com.haohan.framework.entity.BaseResp;

import java.util.Date;

/**
 * 供应商  订单
 * Created by zgw on 2018/10/18.
 */
public interface ISupplierOrderService {


//    // 报价单初始化 单个供应商（根据采购单汇总 生成对应报价单）
//    BaseResp createOfferOrder(String supplierId, String buySeq, Date deliveryTime);

//    // 报价单查询 单个供应商
//    BaseResp queryOfferOrderList(ReqOfferOrders reqOfferOrders);
//
//    // 报价单提交（状态修改） 单个供应商
//    BaseResp supplierOfferOrders(ReqOfferOrders reqOfferOrders);

    // 查询待报价商品列表(采购单汇总)
    BaseResp queryWaitOfferList(String supplierId, String buySeq, Date deliveryTime);


}
