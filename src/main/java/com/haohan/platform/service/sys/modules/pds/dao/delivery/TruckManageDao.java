package com.haohan.platform.service.sys.modules.pds.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆管理DAO接口
 *
 * @author haohan
 * @version 2018-10-18
 */
@MyBatisDao
public interface TruckManageDao extends CrudDao<TruckManage> {
    List<TruckManage> findBuyOrderTrucks(@Param(value = "buyId") String buyId);

    List<TruckManage> findJoinList(TruckManage truckManage);
}