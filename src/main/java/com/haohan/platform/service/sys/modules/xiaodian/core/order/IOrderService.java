package com.haohan.platform.service.sys.modules.xiaodian.core.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;

/**
 * Created by zgw on 2018/9/26.
 */
public interface IOrderService {

    BaseResp createOrder(BaseResp baseResp, BaseOrderReq baseOrderReq, GoodsOrder goodsOrder);

    BaseResp cancelOrder();

    BaseResp deleteOrder();

//    BaseResp updateOrder();

}
