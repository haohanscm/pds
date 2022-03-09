package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DistrictManage;

import java.util.List;

/**
 * 片区管理DAO接口
 * @author yu.shen
 * @version 2018-09-03
 */
@MyBatisDao
public interface DistrictManageDao extends CrudDao<DistrictManage> {

    List<DistrictManage> findListJoin(DistrictManage districtManage);
}