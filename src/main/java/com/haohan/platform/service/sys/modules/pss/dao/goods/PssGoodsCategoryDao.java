package com.haohan.platform.service.sys.modules.pss.dao.goods;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.goods.PssGoodsCategory;

/**
 * 商品总分类DAO接口
 * @author haohan
 * @version 2018-09-07
 */
@MyBatisDao
public interface PssGoodsCategoryDao extends TreeDao<PssGoodsCategory> {
	
}