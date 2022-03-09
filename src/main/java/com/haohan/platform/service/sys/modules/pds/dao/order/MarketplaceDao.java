package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.Marketplace;

/**
 * 市场报价DAO接口
 *
 * @author haohan
 * @version 2018-12-03
 */
@MyBatisDao
public interface MarketplaceDao extends CrudDao<Marketplace> {

}