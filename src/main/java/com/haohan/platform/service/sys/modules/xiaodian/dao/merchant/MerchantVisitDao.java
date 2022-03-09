package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantVisit;

/**
 * 商家拜访记录DAO接口
 * @author haohan
 * @version 2018-04-07
 */
@MyBatisDao
public interface MerchantVisitDao extends CrudDao<MerchantVisit> {
	
}