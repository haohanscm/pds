package com.haohan.platform.service.sys.modules.pds.core.buyer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;

import java.util.Date;
import java.util.List;

/**
 * Created by zgw on 2018/10/20.
 */
public interface IBuyerDeliveryService {


    //查看配送货物列表
    List<TradeOrder> queryDeliveryGoods(String buyerId, Date deliveryDate);

    //验货
    BaseResp accecptGoods(TradeOrder tradeOrder);

    //提交评价

}
