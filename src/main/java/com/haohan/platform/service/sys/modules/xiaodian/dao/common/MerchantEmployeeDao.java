package com.haohan.platform.service.sys.modules.xiaodian.dao.common;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;

import java.util.List;

/**
 * 员工管理DAO接口
 * @author haohan
 * @version 2018-10-18
 */
@MyBatisDao
public interface MerchantEmployeeDao extends CrudDao<MerchantEmployee> {

    List<MerchantEmployee> findJoinList(MerchantEmployee merchantEmployee);

    List<MerchantEmployee> fetchPassword(MerchantEmployee merchantEmployee);
}