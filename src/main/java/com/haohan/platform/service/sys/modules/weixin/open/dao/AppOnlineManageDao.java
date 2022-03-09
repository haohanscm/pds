package com.haohan.platform.service.sys.modules.weixin.open.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AppOnlineManage;

/**
 * 应用上线管理DAO接口
 * @author haohan
 * @version 2017-12-26
 */
@MyBatisDao
public interface AppOnlineManageDao extends CrudDao<AppOnlineManage> {
	
}