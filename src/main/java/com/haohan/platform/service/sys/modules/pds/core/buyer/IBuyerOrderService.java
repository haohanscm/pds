package com.haohan.platform.service.sys.modules.pds.core.buyer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;

import java.math.BigDecimal;
import java.util.Set;

/**
 * 采购商
 * Created by zgw on 2018/10/18.
 */
public interface IBuyerOrderService extends IPdsConstant {

    //下采购单
    BaseResp addBuyOrder(BuyOrder buyOrder);

    // 修改采购单
    BaseResp modifyBuyOrder(BuyOrder buyOrder);

    //查看采购单  带采购明细
    BaseResp queryBuyOrderList(BuyOrder buyOrder);

    //查看采购单明细
    BaseResp queryBuyOrderDetailList(BuyOrder buyOrder);

    //修改采购数量
    BaseResp modifyBuyNum(String detailId, int buyNum);

    //删除购买商品
    BaseResp removeBuyGoods(String detailId);

    // 确认报价
    BaseResp confirmBuyOrder(BuyOrder buyOrder);

    // 查看采购交易商品列表 (按商品 采购单明细 交易订单) 分页
    BaseResp tradeGoodsList(TradeOrder tradeOrder, Page page);

    // 商品确认收货(单个商品)
    BaseResp confirmTradeGoods(TradeOrder tradeOrder);

    // 商品确认收货(批量 按buyId/buyerId)
    BaseResp confirmAllGoods(TradeOrder tradeOrder);


    //查看各种订单详情

    //评价

    //退货

    //查看评价单

    //生产打印消息
    boolean produceCreateOrderPrintMsg(BuyOrder buyOrder, PdsBuyer pdsBuyer);

    boolean produceCancelOrderPrintMsg(BuyOrder buyOrder, PdsBuyer pdsBuyer);

    boolean produceModifyOrderPrintMsg(BuyOrder buyOrder, Set<BuyOrderDetail> insert, Set<BuyOrderDetail> modifyNum, Set<BuyOrderDetail> remove, BigDecimal ofNum);

}
