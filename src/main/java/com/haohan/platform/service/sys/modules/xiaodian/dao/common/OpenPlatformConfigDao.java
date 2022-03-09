package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenPlatformConfig;

/**
 * 开放平台管理DAO接口
 * @author haohan
 * @version 2017-08-06
 */
@MyBatisDao
public interface OpenPlatformConfigDao extends CrudDao<OpenPlatformConfig> {
	
}