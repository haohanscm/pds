package com.haohan.platform.service.sys.modules.pds.core.buyer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;

import java.util.List;

/**
 * Created by zgw on 2018/10/20.
 */
public interface IBuyerPaymentService {


    /**
     * 查看账款列表
     *
     * @param buyerPayment
     * @param page
     * @return
     */
    BaseResp queryPaymentList(BuyerPayment buyerPayment, Page page);

    /**
     * 查看账款统计
     *
     * @param buyerPayment
     * @return
     */
    BaseResp totalPayment(BuyerPayment buyerPayment);

    /**
     * 生成采购商货款记录 单个
     *
     * @param buyOrder
     * @return
     */
    BaseResp paymentRecord(BuyOrder buyOrder);

    /**
     * 生成采购商货款记录 批量
     *
     * @param buyOrderList
     * @param successMsg
     * @param errorMsg
     * @return
     */
    BaseResp paymentRecordBatch(List<BuyOrder> buyOrderList, StringBuilder successMsg, StringBuilder errorMsg);
}
