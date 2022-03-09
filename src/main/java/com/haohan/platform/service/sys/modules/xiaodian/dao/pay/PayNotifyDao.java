package com.haohan.platform.service.sys.modules.xiaodian.dao.pay;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.PayNotify;

/**
 * 支付结果通知DAO接口
 * @author haohan
 * @version 2017-12-07
 */
@MyBatisDao
public interface PayNotifyDao extends CrudDao<PayNotify> {
	
}