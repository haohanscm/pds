package com.haohan.platform.service.sys.modules.xiaodian.dao.pay;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;

/**
 * 商家支付信息DAO接口
 * @author haohan
 * @version 2017-12-10
 */
@MyBatisDao
public interface MerchantPayInfoDao extends CrudDao<MerchantPayInfo> {
	
}