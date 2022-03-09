package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ServiceSelection;

/**
 * 服务选项管理DAO接口
 * @author haohan
 * @version 2018-08-31
 */
@MyBatisDao
public interface ServiceSelectionDao extends CrudDao<ServiceSelection> {

    void deleteByGoodsId(ServiceSelection serviceSelection);
}