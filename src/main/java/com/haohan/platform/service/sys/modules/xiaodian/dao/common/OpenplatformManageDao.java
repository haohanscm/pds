package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenplatformManage;

/**
 * 开放平台应用资料管理DAO接口
 * @author haohan
 * @version 2018-02-01
 */
@MyBatisDao
public interface OpenplatformManageDao extends TreeDao<OpenplatformManage> {
	
}