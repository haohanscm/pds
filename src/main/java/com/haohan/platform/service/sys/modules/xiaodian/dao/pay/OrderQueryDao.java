package com.haohan.platform.service.sys.modules.xiaodian.dao.pay;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderQuery;

/**
 * 交易状态查询DAO接口
 * @author haohan
 * @version 2017-12-12
 */
@MyBatisDao
public interface OrderQueryDao extends CrudDao<OrderQuery> {
	
}