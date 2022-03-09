package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.BuildingsManage;

/**
 * 楼栋管理DAO接口
 * @author yu.shen
 * @version 2018-08-31
 */
@MyBatisDao
public interface BuildingsManageDao extends TreeDao<BuildingsManage> {
	
}