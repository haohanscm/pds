package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery.reserve;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverManManage;

import java.util.List;

public interface DeliveryManDao extends CrudDao<DeliverManManage> {

    // 根据uid查找配送员
    List<DeliverManManage> findDeliverManByUid(String uid);



}
