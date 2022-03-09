package com.haohan.platform.service.sys.modules.pds.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.RouteManage;

import java.util.List;

/**
 * 路线管理DAO接口
 *
 * @author haohan
 * @version 2018-12-03
 */
@MyBatisDao
public interface RouteManageDao extends CrudDao<RouteManage> {

    List<RouteManage> findJoinList(RouteManage routeManage);
}