package com.haohan.platform.service.sys.modules.pds.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;

import java.util.List;

/**
 * 物流配送DAO接口
 *
 * @author haohan
 * @version 2018-10-17
 */
@MyBatisDao
public interface DeliveryFlowDao extends CrudDao<DeliveryFlow> {
    List<TruckManage> findPermissionTrucks(DeliveryFlow deliveryFlow);

    List<DeliveryFlow> findJoinList(DeliveryFlow deliveryFlow);

    Integer deleteByDateSeqPmId(DeliveryFlow deliveryFlow);
}