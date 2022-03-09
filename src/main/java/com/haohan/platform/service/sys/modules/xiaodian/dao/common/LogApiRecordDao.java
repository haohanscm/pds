package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.LogApiRecord;

/**
 * 接口日志记录DAO接口
 * @author haohan
 * @version 2018-05-29
 */
@MyBatisDao
public interface LogApiRecordDao extends CrudDao<LogApiRecord> {
	
}