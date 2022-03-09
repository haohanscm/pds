package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsTradeOrderCommonParamsResp;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.PdsShipOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 送货单明细DAO接口
 *
 * @author yu.shen
 * @version 2018-11-14
 */
@MyBatisDao
public interface PdsShipOrderDetailDao extends CrudDao<PdsShipOrderDetail> {
    BigDecimal countShipOrderAmount(DeliveryFlow deliveryFlow);

    Integer countDriverShipGoods(DeliveryFlow deliveryFlow);

    List<PdsTradeOrderCommonParamsResp> findTruckShipDetailList(DeliveryFlow deliveryFlow);


    List<PdsShipOrderDetail> findJoinList(PdsShipOrderDetail pdsShipOrderDetail);

    Integer deleteByShipOrderId(@Param(value = "shipId") String shipId);
}