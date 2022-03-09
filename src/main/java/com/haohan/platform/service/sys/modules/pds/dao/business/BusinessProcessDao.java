package com.haohan.platform.service.sys.modules.pds.dao.business;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.BusinessProcess;

/**
 * 业务流程DAO接口
 *
 * @author haohan
 * @version 2018-12-03
 */
@MyBatisDao
public interface BusinessProcessDao extends CrudDao<BusinessProcess> {

}