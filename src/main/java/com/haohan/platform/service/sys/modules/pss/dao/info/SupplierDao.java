package com.haohan.platform.service.sys.modules.pss.dao.info;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;

/**
 * 供应商管理DAO接口
 * @author haohan
 * @version 2018-09-06
 */
@MyBatisDao
public interface SupplierDao extends CrudDao<Supplier> {
	
}