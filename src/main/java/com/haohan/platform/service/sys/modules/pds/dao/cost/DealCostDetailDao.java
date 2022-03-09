package com.haohan.platform.service.sys.modules.pds.dao.cost;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.DealCostDetail;

/**
 * 交易成本明细DAO接口
 *
 * @author haohan
 * @version 2018-12-03
 */
@MyBatisDao
public interface DealCostDetailDao extends CrudDao<DealCostDetail> {

}