package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAccount;

/**
 * 管理不同渠道的商户账号DAO接口
 * @author yu.shen
 * @version 2018-06-11
 */
@MyBatisDao
public interface MerchantAccountDao extends CrudDao<MerchantAccount> {
	
}