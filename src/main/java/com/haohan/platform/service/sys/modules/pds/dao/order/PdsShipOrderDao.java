package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiDeliveryBuyerResp;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrder;

import java.util.List;

/**
 * 送货单DAO接口
 *
 * @author yu.shen
 * @version 2018-11-14
 */
@MyBatisDao
public interface PdsShipOrderDao extends CrudDao<PdsShipOrder> {
    List<PdsApiDeliveryBuyerResp> findShipOrderInfoList(DeliveryFlow deliveryFlow);

    List<PdsShipOrder> findJoinList(PdsShipOrder pdsShipOrder);

    void deleteByDateSeqPmId(PdsShipOrder pdsShipOrder);
}