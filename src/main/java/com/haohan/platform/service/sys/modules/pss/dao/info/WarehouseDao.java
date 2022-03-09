package com.haohan.platform.service.sys.modules.pss.dao.info;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;

/**
 * 仓库管理DAO接口
 * @author haohan
 * @version 2018-09-05
 */
@MyBatisDao
public interface WarehouseDao extends CrudDao<PssWarehouse> {
	
}