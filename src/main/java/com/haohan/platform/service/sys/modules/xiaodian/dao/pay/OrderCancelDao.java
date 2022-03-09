package com.haohan.platform.service.sys.modules.xiaodian.dao.pay;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderCancel;

/**
 * 订单撤销DAO接口
 * @author haohan
 * @version 2017-12-30
 */
@MyBatisDao
public interface OrderCancelDao extends CrudDao<OrderCancel> {
	
}