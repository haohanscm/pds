package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.BusinessNote;

/**
 * 商务留言管理DAO接口
 * @author haohan
 * @version 2018-02-23
 */
@MyBatisDao
public interface BusinessNoteDao extends CrudDao<BusinessNote> {
	
}