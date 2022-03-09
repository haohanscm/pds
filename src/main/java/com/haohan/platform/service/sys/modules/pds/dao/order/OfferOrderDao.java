package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiOfferOrderResp;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.entity.req.PdsOfferOrderReq;
import com.haohan.platform.service.sys.modules.pds.entity.resp.PdsGoodsListParams;
import com.haohan.platform.service.sys.modules.pds.entity.resp.PdsSupListParams;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 报价单DAO接口
 *
 * @author haohan
 * @version 2018-10-18
 */
@MyBatisDao
public interface OfferOrderDao extends CrudDao<OfferOrder> {

    List<PdsSupListParams> findSupList(PdsOfferOrderReq pdsOfferOrderReq);

    List<PdsGoodsListParams> findSupGoodsList(PdsOfferOrderReq PdsOfferOrderReq);

    Integer countCategoryNum(PdsOfferOrderReq pdsOfferOrderReq);

    Integer countGoodsNum(PdsOfferOrderReq pdsOfferOrderReq);

    BigDecimal countSupplyAvgPrice(@Param("summaryOrderId") String summaryOrderId);

    List<PdsApiOfferOrderResp> findAdmRespPage(PdsApiOfferOrderResp pdsApiOfferOrderResp);

    List<OfferOrder> findJoinList(OfferOrder offerOrder);

    void deleteByDateSeqPmId(OfferOrder offerOrder);
}