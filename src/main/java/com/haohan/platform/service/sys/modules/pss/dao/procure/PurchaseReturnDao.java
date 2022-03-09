package com.haohan.platform.service.sys.modules.pss.dao.procure;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;

/**
 * 采购退货DAO接口
 * @author haohan
 * @version 2018-09-05
 */
@MyBatisDao
public interface PurchaseReturnDao extends CrudDao<PurchaseReturn> {
	
}