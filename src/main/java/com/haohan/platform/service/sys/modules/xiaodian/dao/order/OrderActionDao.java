package com.haohan.platform.service.sys.modules.xiaodian.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderAction;

/**
 * 订单操作DAO接口
 * @author haohan
 * @version 2017-08-06
 */
@MyBatisDao
public interface OrderActionDao extends CrudDao<OrderAction> {
	
}