package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;

/**
 * 图片资源关系DAO接口
 * @author haohan
 * @version 2018-01-12
 */
@MyBatisDao
public interface PhotoManageDao extends CrudDao<PhotoManage> {
	
}