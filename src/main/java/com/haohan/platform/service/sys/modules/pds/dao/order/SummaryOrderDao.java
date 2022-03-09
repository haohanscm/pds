package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsAdminSumBuyOrder;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsApiStatisOverViewReq;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购单汇总DAO接口
 *
 * @author haohan
 * @version 2018-10-24
 */
@MyBatisDao
public interface SummaryOrderDao extends CrudDao<SummaryOrder> {

    void updateStatus(@Param("summaryOrderId") String summaryOrderId, @Param("status") String status);

    List<SummaryOrder> queryWaitOfferList(SummaryOrder summaryOrder);

    List<SummaryOrder> findJoinList(SummaryOrder summaryOrder);

    List<PdsAdminSumBuyOrder> findAdminSumOrderPage(PdsAdminSumBuyOrder pdsAdminSumBuyOrder);

    void deleteByDateSeqPmId(SummaryOrder summaryOrder);

    Integer countNotOffer(PdsApiStatisOverViewReq overViewReq);

    Integer updatePrice(SummaryOrder summaryOrder);
}