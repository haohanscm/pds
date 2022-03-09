package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.TreeDict;

/**
 * 树形字典DAO接口
 * @author haohan
 * @version 2017-12-11
 */
@MyBatisDao
public interface TreeDictDao extends TreeDao<TreeDict> {


}