package com.haohan.platform.service.sys.modules.pds.core.buyer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsModelListReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsUpdateListReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerPriceReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer.*;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsTopNGoodsResp;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;

import java.util.Date;
import java.util.List;

/**
 * 平台商家  采购商 商品定价
 * Created by dy on 2018/12/06.
 */
public interface IBuyerGoodsService {

    /**
     * 复制商品到采购商平台商品库
     *
     * @param buyerPriceReq
     * @return
     */
    BaseResp copyGoodsToBuyerGoods(PdsBuyerPriceReq buyerPriceReq);

    /**
     * 批量修改 采购商定价
     *
     * @param updateListReq
     * @return
     */
    BaseResp batchUpdateGoodsPrice(PdsBuyerGoodsUpdateListReq updateListReq);

    /**
     * 修改采购商定价
     *
     * @param pdsPlatformGoodsPrice
     * @return
     */
    BaseResp updateGoodsPrice(PdsPlatformGoodsPrice pdsPlatformGoodsPrice);

    /**
     * 批量 商品上下架
     *
     * @param goodsReqList
     * @return
     */
    BaseResp batchMarketableChange(List<PdsBuyerGoodsReq> goodsReqList);

    /**
     * 按采购商复制商品库
     *
     * @param copyPmPriceReq
     * @return
     */
    BaseResp copyGoodsByBuyer(PdsCopyPmPriceApiReq copyPmPriceReq);

    /**
     * 按采购商商家复制商品库
     *
     * @param pdsPlatformGoodsPrice
     * @return
     */
    BaseResp copyGoodsByMerchant(PdsPlatformGoodsPrice pdsPlatformGoodsPrice);

    /**
     * TODO 新增 缺失商品 (平台商家店铺商品)
     *
     * @param buyerPriceReq
     * @return
     */
    BaseResp copyGoodsByPmShop(PdsBuyerPriceReq buyerPriceReq);

    /**
     * 查询 采购商商品[平台商、采购商、最大开始时间、最小结束时间]
     *
     * @param buyerPriceReq
     * @return
     */
    BaseResp queryBuyerGoodsList(PdsBuyerPriceReq buyerPriceReq);

    /**
     * 根据固定日期查询平台商家报价 (无数据时返回空)
     *
     * @param pmId
     * @param buyerId
     * @param modelId
     * @param date
     * @return
     */
    PdsPlatformGoodsPrice fetchPlatformGoodsPrice(String pmId, String buyerId, String modelId, Date date);

    // 查询 平台商品列表 关联 采购商采购价
    ApiRespPage fetchGoodsList(PdsBuyerGoodsReq goodsReq);

    //收藏商品列表
    BaseResp goodsCollectList(PdsCollectGoodsListApiReq goodsListReq, Page<PdsTopNGoodsResp> page);

    //添加收藏
    BaseResp addCollect(PdsCollectGoodsApiReq goodsReq);

    // 查询 平台商品信息
    BaseResp fetchGoodsModelInfoList(PdsBuyerGoodsModelListReq goodsReq);

    //取消收藏
    BaseResp cancelCollect(PdsCollectGoodsApiReq goodsReq);

    /**
     * 批量修改平台商家店铺 商品的零售定价
     * 价格来源于采购定价 平台商家和采购商商家须相同pmId=buyerMerchantId
     *
     * @param updateReq
     * @return
     */
    BaseResp updatePriceToPmShop(PdsUpdateShopPriceReq updateReq);

    /**
     * 批量删除采购商 采购价
     *
     * @param deleteReq
     * @return
     */
    BaseResp deletePriceBatch(PdsDeletePriceBatchReq deleteReq);

    BaseResp deletePrice(PdsDeletePriceReq req);

    BaseResp addPrice(PdsAddPriceReq req);
}

