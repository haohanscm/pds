package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantDatabase;

import java.util.List;

/**
 * 商家资料库DAO接口
 * @author haohan
 * @version 2018-04-07
 */
@MyBatisDao
public interface MerchantDatabaseDao extends CrudDao<MerchantDatabase> {

    List<MerchantDatabase> fetchByRegName(MerchantDatabase entity);
	
}