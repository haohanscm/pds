package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopTemplate;

/**
 * 店铺模板管理DAO接口
 * @author haohan
 * @version 2017-12-25
 */
@MyBatisDao
public interface ShopTemplateDao extends CrudDao<ShopTemplate> {
	
}