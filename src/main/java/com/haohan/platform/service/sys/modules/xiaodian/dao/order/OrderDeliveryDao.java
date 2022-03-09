package com.haohan.platform.service.sys.modules.xiaodian.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;

/**
 * 订单配送信息管理DAO接口
 * @author haohan
 * @version 2018-08-31
 */
@MyBatisDao
public interface OrderDeliveryDao extends CrudDao<OrderDelivery> {

    // 修改 订单配送状态  deliveryStatus/orderStatus
    int modifyStatus(OrderDelivery orderDelivery);
}