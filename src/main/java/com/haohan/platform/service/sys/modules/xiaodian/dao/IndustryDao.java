package com.haohan.platform.service.sys.modules.xiaodian.dao;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.Industry;

import java.util.List;

/**
 * 行业分类管理DAO接口
 * @author haohan
 * @version 2017-08-05
 */
@MyBatisDao
public interface IndustryDao extends TreeDao<Industry> {

    List<Industry> fetchByName(Industry entity);
	
}