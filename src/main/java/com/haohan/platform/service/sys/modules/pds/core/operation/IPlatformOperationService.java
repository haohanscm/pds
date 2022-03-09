package com.haohan.platform.service.sys.modules.pds.core.operation;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.SummaryOrder;

import java.util.Date;
import java.util.List;

/**
 * 运营平台操作  每步操作前判断平台交易流程状态
 * Created by dy on 2018/10/26.
 */
public interface IPlatformOperationService {

    // 交易流程:步骤1  采购商下单
    // 单个采购商修改采购单
    BaseResp modifyBuyOrder(BuyOrder buyOrder);

    // 触发 采购商品汇总 生成(更新) 采购单汇总记录
    // 交易流程:步骤2  采购商下单 -> 采购单汇总(修改状态)
    BaseResp triggerSummary(String buySeq, Date deliveryTime);

    // 触发 初始化供应商的报价单  (完成后推送信息给供应商)
    // 交易流程:步骤3  采购单汇总 -> 供应商报价(修改状态)
    BaseResp triggerOfferOrderInit(List<String> supplierIdList, String buySeq, Date deliveryTime);

    // 交易流程:步骤4  供应商报价

    // 交易流程:步骤5  供应商报价 -> 商品定价 (当所有供应商报价完成后修改状态)

    // 平台商品定价(计算或修改 采购单汇总中平台报价)
    // 交易流程:步骤6  商品定价
    BaseResp goodsValuation(List<SummaryOrder> list, String buySeq, Date deliveryTime);

    // 修改 采购单及明细的状态(待报价) (完成后推送信息给采购商)
    BaseResp updateBuyOrderStatus(String buySeq, Date deliveryTime);

    // 单个采购商确认报价
    BaseResp confirmPrice(BuyOrder buyOrder);

    // 触发 交易匹配  生成(更新) 交易匹配记录
    // 交易流程:步骤7  商品定价 -> 交易匹配  (当所有采购商确认价格完成后修改状态)
    BaseResp triggerTradeMatch(String buySeq, Date deliveryTime);

    // 交易流程:步骤8  交易匹配  (确认/修改交易匹配)

    // 触发交易订单生成  生成(更新)
    // 交易流程:步骤9  交易匹配 -> 订单生成  (修改状态)
    BaseResp triggerTradeOrder(String buySeq, Date deliveryTime);

    // 交易流程:步骤10  订单生成 -> 交易完成 (配送及售后处理完成;此刻生成供应商/采购商的账单)
    BaseResp tradeFinish(String buySeq, Date deliveryTime);

}
