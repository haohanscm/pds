package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.UnionBankNo;

/**
 * 银行行号管理DAO接口
 * @author haohan
 * @version 2017-12-10
 */
@MyBatisDao
public interface UnionBankNoDao extends CrudDao<UnionBankNo> {
	
}