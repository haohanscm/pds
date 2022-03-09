package com.haohan.platform.service.sys.modules.pss.dao.goods;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.goods.PssGoodsDatabase;

/**
 * 商品数据库管理DAO接口
 * @author haohan
 * @version 2018-09-05
 */
@MyBatisDao
public interface PssGoodsDatabaseDao extends CrudDao<PssGoodsDatabase> {
	
}