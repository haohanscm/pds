package com.haohan.platform.service.sys.modules.pds.dao.operation;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.operation.PdsTradeProcess;

/**
 * 平台交易流程管理DAO接口
 *
 * @author dy
 * @version 2018-12-03
 */
@MyBatisDao
public interface PdsTradeProcessDao extends CrudDao<PdsTradeProcess> {

}