package com.haohan.platform.service.sys.modules.xiaodian.dao;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.Brand;

/**
 * 品牌DAO接口
 * @author haohan
 * @version 2017-08-05
 */
@MyBatisDao
public interface BrandDao extends CrudDao<Brand> {
	
}