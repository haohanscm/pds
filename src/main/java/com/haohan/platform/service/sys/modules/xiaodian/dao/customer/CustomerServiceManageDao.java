package com.haohan.platform.service.sys.modules.xiaodian.dao.customer;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerServiceManage;

/**
 * 客户服务管理DAO接口
 * @author haohan
 * @version 2018-04-22
 */
@MyBatisDao
public interface CustomerServiceManageDao extends CrudDao<CustomerServiceManage> {
	
}