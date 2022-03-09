package com.haohan.platform.service.sys.modules.xiaodian.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.Cart;

/**
 * 购物车DAO接口
 * @author haohan
 * @version 2017-12-07
 */
@MyBatisDao
public interface CartDao extends CrudDao<Cart> {
	
}