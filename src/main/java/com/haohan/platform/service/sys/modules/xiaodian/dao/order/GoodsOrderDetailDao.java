package com.haohan.platform.service.sys.modules.xiaodian.dao.order;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.TSaleVolumeResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderCategoryStatisApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.resp.GoodsOrderSaleVolRankApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品订单明细DAO接口
 * @author haohan
 * @version 2018-09-01
 */
@MyBatisDao
public interface GoodsOrderDetailDao extends CrudDao<GoodsOrderDetail> {

    BigDecimal countMemberBoughtNum(String uid, String goodsId);

    List<TSaleVolumeResp> sumSaleVolume(GoodsOrderDetail goodsOrderDetail);

    List<GoodsOrderCategoryStatisApiResp> categorySaleStatis(GoodsOrder goodsOrder);

    List<GoodsOrderSaleVolRankApiResp> saleVolumeRank(@Param(value = "goodsOrder") GoodsOrder goodsOrder, @Param(value = "orderBy") String orderBy, @Param(value = "limit") Integer limit);
}