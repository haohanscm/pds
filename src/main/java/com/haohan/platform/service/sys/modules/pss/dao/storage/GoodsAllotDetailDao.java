package com.haohan.platform.service.sys.modules.pss.dao.storage;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllotDetail;

/**
 * 商品调拨明细DAO接口
 * @author haohan
 * @version 2018-09-05
 */
@MyBatisDao
public interface GoodsAllotDetailDao extends CrudDao<GoodsAllotDetail> {
	
}