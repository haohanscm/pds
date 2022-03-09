package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AuthAccount;

/**
 * 账号信息认证管理DAO接口
 * @author dy
 * @version 2018-10-15
 */
@MyBatisDao
public interface AuthAccountDao extends CrudDao<AuthAccount> {
	
}