package com.haohan.platform.service.sys.modules.pss.dao.storage;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.WareHouseInventory;

/**
 * 库存盘点DAO接口
 * @author haohan
 * @version 2018-09-05
 */
@MyBatisDao
public interface WareHouseInventoryDao extends CrudDao<WareHouseInventory> {
	
}