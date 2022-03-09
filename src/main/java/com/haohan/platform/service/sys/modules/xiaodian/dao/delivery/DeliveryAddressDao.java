package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryAddress;

/**
 * 配送信息DAO接口
 * @author shenyu
 * @version 2018-08-18
 */
@MyBatisDao
public interface DeliveryAddressDao extends CrudDao<DeliveryAddress> {
	
}