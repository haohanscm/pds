package com.haohan.platform.service.sys.modules.pss.dao.storage;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;

/**
 * 商品调拨DAO接口
 * @author haohan
 * @version 2018-09-05
 */
@MyBatisDao
public interface GoodsAllotDao extends CrudDao<GoodsAllot> {
	
}