package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopCategory;

/**
 * 店铺分类管理DAO接口
 * @author haohan
 * @version 2019-01-15
 */
@MyBatisDao
public interface ShopCategoryDao extends TreeDao<ShopCategory> {
	
}