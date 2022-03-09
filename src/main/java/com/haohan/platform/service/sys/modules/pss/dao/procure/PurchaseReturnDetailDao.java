package com.haohan.platform.service.sys.modules.pss.dao.procure;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturnDetail;

/**
 * 采购退货明细DAO接口
 * @author haohan
 * @version 2018-09-05
 */
@MyBatisDao
public interface PurchaseReturnDetailDao extends CrudDao<PurchaseReturnDetail> {
	
}