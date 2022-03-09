package com.haohan.platform.service.sys.modules.pds.core.buyer;

import com.haohan.platform.service.sys.modules.pds.entity.order.ServiceOrder;

import java.util.List;

/**
 * 采购商 售后/评价
 * Created by dy on 2018/10/29.
 */
public interface IBuyerSaleEvaluateService {

    // 查看售后单列表
    List<ServiceOrder> queryServiceOrderList(ServiceOrder serviceOrder);

    // 查看售后单列表
    List<ServiceOrder> queryServiceOrder(ServiceOrder serviceOrder);


}
