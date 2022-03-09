package com.haohan.platform.service.sys.modules.pds.dao.business;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;

import java.util.List;

/**
 * 平台商品定价管理DAO接口
 *
 * @author haohan
 * @version 2018-12-08
 */
@MyBatisDao
public interface PdsPlatformGoodsPriceDao extends CrudDao<PdsPlatformGoodsPrice> {

    List<PdsPlatformGoodsPrice> findJoinList(PdsPlatformGoodsPrice pdsPlatformGoodsPrice);

    List<PdsPlatformGoodsPrice> fetchGoodsPrice(PdsPlatformGoodsPrice goodsPrice);

    Integer updateGoodsPrice(PdsPlatformGoodsPrice goodsPrice);

    Integer initBuyerGoods(PdsPlatformGoodsPrice goodsPrice);

//    List<PdsPlatformGoodsPrice> fetchPeriodList(PdsPlatformGoodsPrice goodsPrice);

    List<GoodsModel> findGoodsModelList(PdsPlatformGoodsPrice goodsPrice);

    List<PdsPlatformGoodsPrice> findGoodsListByDateJoin(PdsPlatformGoodsPrice goodsPrice);

    Integer copyToNewBuyerMerchant(PdsPlatformGoodsPrice goodsPrice);

    Integer deleteBatch(PdsPlatformGoodsPrice goodsPrice);

    /**
     * 查询参数 可选 pmId/merchantId/buyerId
     *
     * @param goodsPrice
     * @return
     */
    PdsPlatformGoodsPrice fetch(PdsPlatformGoodsPrice goodsPrice);

    /**
     * 查询 与选择时间段 有交集的 定价记录 数量
     *
     * @param goodsPrice 必需 pmId/merchantId/ startDate /endDate 可选status
     * @return
     */
    Integer countRangePrice(PdsPlatformGoodsPrice goodsPrice);
}