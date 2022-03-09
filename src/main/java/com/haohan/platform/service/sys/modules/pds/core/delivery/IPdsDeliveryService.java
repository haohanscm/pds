package com.haohan.platform.service.sys.modules.pds.core.delivery;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsShipOrderApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery.PdsShipBuyerApiReq;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/10/20
 */
public interface IPdsDeliveryService {

    // 配送  商品送达采购商 修改状态
    BaseResp goodsArrived(DeliveryFlow deliveryFlow);

    //获取送货单列表
    BaseResp findDeliveryBuyerList(PdsShipBuyerApiReq shipBuyerReq, Page reqPage);

    //装车
    BaseResp truckLoad(TradeOrder tradeOrder) throws Exception;

    //发车
    BaseResp depart(PdsShipOrderApiReq apiShipOrderReq);

    //自提单送达
    BaseResp selfOrderArrived(Date deliveryDate, String buySeq, String pmId);

    //送货单送达
    BaseResp shipOrderArrived(String shipId);
}
