package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AppPayRelation;

import java.util.List;

/**
 * app支付账户DAO接口
 * @author yu.shen
 * @version 2019-01-15
 */
@MyBatisDao
public interface AppPayRelationDao extends CrudDao<AppPayRelation> {

    /**
     * 带 appName
     * @param appPayRelation
     * @return
     */
    List<AppPayRelation> findJoinList(AppPayRelation appPayRelation);
	
}