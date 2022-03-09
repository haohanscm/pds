package com.haohan.platform.service.sys.modules.xiaodian.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.req.MercSaleOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp.MercSaleOrderResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderSaleCurveApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品订单DAO接口
 * @author haohan
 * @version 2017-12-12
 */
@MyBatisDao
public interface GoodsOrderDao extends CrudDao<GoodsOrder> {

    public BigDecimal sumSaleAmount(GoodsOrder goodsOrder);

    // 修改 订单状态  orderStatus
    int modifyOrderStatus(GoodsOrder goodsOrder);

    List<MercSaleOrderResp> findMercSaleOrderList(MercSaleOrderReq mercSaleOrderReq);

    Integer countOrderNum(GoodsOrder goodsOrder);

    BigDecimal sumRefundAmount(GoodsOrder goodsOrder);

    List<GoodsOrderSaleCurveApiResp> saleCurve(GoodsOrder goodsOrder);

    Integer countBuyerNum(GoodsOrder goodsOrder);
}
