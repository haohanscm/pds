package com.haohan.platform.service.sys.modules.weixin.open.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;

/**
 * 授权应用管理DAO接口
 * @author haohan
 * @version 2018-01-05
 */
@MyBatisDao
public interface AuthAppDao extends CrudDao<AuthApp> {
	
}