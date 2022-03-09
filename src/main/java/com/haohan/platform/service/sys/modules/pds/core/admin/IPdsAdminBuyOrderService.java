package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery.PdsShipBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiOfferOrderResp;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;

/**
 * @author shenyu
 * @create 2018/10/31
 */
public interface IPdsAdminBuyOrderService {

    //获取采购单列表
    BaseResp fetchBuyOrderList(Page<BuyOrder> reqPage, PdsBuyOrderApiReq apiBuyOrderReq);

    //获取采购汇总明细列表
    BaseResp fetchBuyDetailOfferList(String buyOrderId);   //未使用

    //获取采购单明细列表
    BaseResp findBuyOrderdetails(Page<BuyOrderDetail> page, String buyOrderId);

    //删除采购单明细
    BaseResp deleteBuyDetail(String detailId);

    //修改运费
    BaseResp editShipFee(PdsBuyShipFeeApiReq shipFeeReq);

    //获取报价单列表
    BaseResp findOfferPage(Page<PdsApiOfferOrderResp> page, PdsOfferOrderApiReq offerOrderReq);

    //获取送货单列表
    BaseResp findShipOrderInfoList(PdsShipOrderApiReq shipOrderReq, Page reqPage);

    //获取送货单明细
    BaseResp findShipOrderPage(Page reqPage, PdsShipBuyerApiReq shipBuyerReq);

    //获取自提订单采购商列表
    BaseResp findSelfOrderBuyerList(PdsSelfOrderApiReq apiSelfOrderReq);

    //获取采购商自提订单列表
    BaseResp findSelfOrderPage(Page reqPage, PdsSelfOrderApiReq apiSelfOrderReq);


}
