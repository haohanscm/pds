package com.haohan.platform.service.sys.modules.pds.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsBuyerTopNGoodsApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiDeliveryBuyerResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsApiTradeSortProcessResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsTradeOrderCommonParamsResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsTopNGoodsResp;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.DeliveryFlow;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 交易订单DAO接口
 *
 * @author haohan
 * @version 2018-10-24
 */
@MyBatisDao
public interface TradeOrderDao extends CrudDao<TradeOrder> {
    Integer countProcess(TradeOrder tradeOrder);

    List<PdsApiTradeSortProcessResp> goodsSortProcessList(TradeOrder tradeOrder);

    Integer countGoodsProcess(TradeOrder tradeOrder);

    Integer countFreightNum(TradeOrder tradeOrder);

    int updateStatus(TradeOrder tradeOrder);

    //  按配送情况(配送编号/配送日期/物流车号) 修改配送状态
    int updateStatusByDelivery(DeliveryFlow deliveryFlow);

    List<PdsTradeOrderCommonParamsResp> findSimpleTradeOrderList(TradeOrder order);

    List<String> findBuyerIdList(TradeOrder tradeOrder);

    List<PdsApiDeliveryBuyerResp> findSelfOrderInfoList(TradeOrder tradeOrder);

    BigDecimal countOrderAmount(TradeOrder tradeOrder);

    List<TradeOrder> findJoinList(TradeOrder tradeOrder);

    Integer deleteByDateSeqPmId(TradeOrder tradeOrder);

    List<PdsTopNGoodsResp> selectBuyerGoodsTopN(PdsBuyerTopNGoodsApiReq pdsApiBuyerTopNGoodsReq);

    Integer countBuyOrderNum(TradeOrder tradeOrder);

    Integer countTradeOrderNum(TradeOrder tradeOrder);
}