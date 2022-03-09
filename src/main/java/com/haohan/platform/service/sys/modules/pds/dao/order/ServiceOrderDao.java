package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;

import java.util.List;

/**
 * 售后单DAO接口
 *
 * @author haohan
 * @version 2018-10-20
 */
@MyBatisDao
public interface ServiceOrderDao extends CrudDao<ServiceOrder> {

    //  带pmName / buyerName / supplierName
    List<ServiceOrder> findJoinList(ServiceOrder serviceOrder);
}