package com.haohan.platform.service.sys.modules.pss.dao.info;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.info.SupplierPayrecord;

/**
 * 供应商结款记录DAO接口
 * @author yu
 * @version 2018-10-11
 */
@MyBatisDao
public interface SupplierPayrecordDao extends CrudDao<SupplierPayrecord> {
	
}