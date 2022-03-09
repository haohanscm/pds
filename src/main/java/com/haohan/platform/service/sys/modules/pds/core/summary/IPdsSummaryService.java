package com.haohan.platform.service.sys.modules.pds.core.summary;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.entity.order.OfferOrder;
import com.haohan.platform.service.sys.modules.pds.exception.PdsSummaryOperationException;
import com.haohan.platform.service.sys.modules.xiaodian.exception.DataNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/10/20
 */
public interface IPdsSummaryService {

    //采购单汇总
    BaseResp summaryBuyOrders(String pmId, Date deliveryDate, String buySeq);

    //交易匹配
    BaseResp tradeMatch(String pmId, String buySeq, Date deliveryDate) throws PdsSummaryOperationException;

    //生成交易单
    BaseResp createTradeOrder(String pmId, String buySeq, Date deliveryDate) throws PdsSummaryOperationException;

    //确认报价
    BaseResp confirmOffer(String pmId, String buySeq, Date deliveryDate) throws PdsSummaryOperationException;

    //自营汇总单转采购单
    BaseResp convert(List<OfferOrder> offerOrderList, String pmId) throws DataNotFoundException;
}
