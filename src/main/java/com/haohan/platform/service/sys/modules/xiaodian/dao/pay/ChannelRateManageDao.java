package com.haohan.platform.service.sys.modules.xiaodian.dao.pay;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.ChannelRateManage;

/**
 * 支付渠道费率管理DAO接口
 * @author haohan
 * @version 2017-12-11
 */
@MyBatisDao
public interface ChannelRateManageDao extends CrudDao<ChannelRateManage> {
	
}