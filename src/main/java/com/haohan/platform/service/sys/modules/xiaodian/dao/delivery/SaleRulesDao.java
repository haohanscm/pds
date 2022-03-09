package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.SaleRules;

/**
 * 售卖规则管理DAO接口
 * @author haohan
 * @version 2018-08-31
 */
@MyBatisDao
public interface SaleRulesDao extends CrudDao<SaleRules> {

    void deleteByGoodsId(SaleRules saleRules);
}