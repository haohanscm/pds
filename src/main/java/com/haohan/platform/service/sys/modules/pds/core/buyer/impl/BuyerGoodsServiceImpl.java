package com.haohan.platform.service.sys.modules.pds.core.buyer.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsModelListReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsUpdateListReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerPriceReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer.*;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.PdsBuyerGoodsOfferResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.PdsBuyerGoodsResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsApiBuyerGoodsCategoryResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsTopNGoodsResp;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerGoodsService;
import com.haohan.platform.service.sys.modules.pds.entity.business.GoodsCollections;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import com.haohan.platform.service.sys.modules.pds.service.business.GoodsCollectionsService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsPlatformGoodsPriceService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsPriceRule;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsPriceRuleService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 平台商家  采购商 商品定价
 * Created by dy on 2018/12/06.
 */
@Service
public class BuyerGoodsServiceImpl implements IBuyerGoodsService {

    @Autowired
    @Lazy
    private PdsPlatformGoodsPriceService goodsPriceService;
    @Autowired
    @Lazy
    private GoodsService goodsService;
    @Autowired
    @Lazy
    private PdsBuyerService pdsBuyerService;
    @Autowired
    @Lazy
    private GoodsCollectionsService goodsCollectionsService;
    @Autowired
    @Lazy
    private ShopService shopService;
    @Autowired
    @Lazy
    private GoodsModelService goodsModelService;
    @Autowired
    @Lazy
    private GoodsPriceRuleService goodsPriceRuleService;

    /**
     * 复制平台商品到采购商商家平台商品库
     *
     * @param buyerPriceReq
     * @return
     */
    @Override
    public BaseResp copyGoodsToBuyerGoods(PdsBuyerPriceReq buyerPriceReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = buyerPriceReq.getPmId();
        String shopId = buyerPriceReq.getShopId();
        String buyerMerchantId = buyerPriceReq.getBuyerMerchantId();
        Date startDate = buyerPriceReq.getStartDate();
        Date endDate = buyerPriceReq.getEndDate();
        BigDecimal rate = buyerPriceReq.getRate();
        rate = null == rate ? BigDecimal.ONE : BigDecimal.ONE.add(rate);
        if (StringUtils.isAnyEmpty(pmId, shopId, buyerMerchantId) || null == startDate || null == endDate
                || startDate.getTime() > endDate.getTime()) {
            baseResp.setMsg("参数错误pmId/shopId/buyerMerchantId/date");
            return baseResp;
        }
        if (rate.compareTo(BigDecimal.ZERO) < 1) {
            baseResp.setMsg("比例设置有误");
            return baseResp;
        }
        // 验证 采购商商家
        PdsBuyer buyer = new PdsBuyer();
        buyer.setMerchantId(buyerMerchantId);
        buyer.setPmId(pmId);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(buyer);
        if (Collections3.isEmpty(buyerList)) {
            baseResp.setMsg("采购商错误");
            return baseResp;
        }
        // 从 平台店铺的零售商品库复制数据 统一上浮比例
        PdsPlatformGoodsPrice goodsPrice = new PdsPlatformGoodsPrice();
        goodsPrice.setPmId(pmId);
        goodsPrice.setShopId(shopId);
        goodsPrice.setMerchantId(buyerMerchantId);
        goodsPrice.setStartDate(startDate);
        goodsPrice.setEndDate(endDate);
        goodsPrice.setRate(rate);
        goodsPrice.setStatus(ICommonConstant.YesNoType.yes.getCode());
        if (goodsPriceService.initBuyerGoods(goodsPrice)) {
            baseResp.success();
        } else {
            baseResp.setMsg("该时间段内采购商商家已有商品价格,不需全部新增,操作失败");
        }
        return baseResp;
    }

    @Override
    public BaseResp batchUpdateGoodsPrice(PdsBuyerGoodsUpdateListReq updateListReq) {
        BaseResp baseResp = BaseResp.newError();
        List<PdsBuyerGoodsReq> goodsReqList = updateListReq.getGoodsReqList();
        int num = 0;
        int errorNum = 0;
        BaseResp resp;
        PdsPlatformGoodsPrice goodsPrice = new PdsPlatformGoodsPrice();
        updateListReq.copyToGoodsPrice(goodsPrice);
        for (PdsBuyerGoodsReq goods : goodsReqList) {
            goodsPrice.setId(goods.getId());
            goodsPrice.setPrice(goods.getPrice());
            resp = updateGoodsPrice(goodsPrice);
            if (resp.isSuccess()) {
                num++;
            }
        }
        if (num > 0) {
            baseResp.success();
            if (errorNum > 0) {
                baseResp.setMsg("批量修改成功，其中" + errorNum + "条记录有误");
            }
        }
        return baseResp;
    }

    @Override
    public BaseResp updateGoodsPrice(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        BaseResp baseResp = BaseResp.newError();
        Boolean flag = true;
        String id = pdsPlatformGoodsPrice.getId();
        BigDecimal price = pdsPlatformGoodsPrice.getPrice();
        if (null == price) {
            flag = false;
        } else {
            int intPrice = price.intValue();
            if (intPrice <= 0 || intPrice > IGoodsConstant.MAX_PRICE) {
                flag = false;
            }
        }
        if (!flag) {
            baseResp.setMsg("参数错误");
            return baseResp;
        }
        PdsPlatformGoodsPrice goodsPrice;
        // 必传id pmId  可传merchantId/buyerId查询
        goodsPrice = goodsPriceService.fetch(pdsPlatformGoodsPrice);
        if (null == goodsPrice) {
            baseResp.setMsg("无权修改信息");
            return baseResp;
        }
        goodsPrice = new PdsPlatformGoodsPrice();
        goodsPrice.setId(id);
        goodsPrice.setPrice(price);
        if (goodsPriceService.updateGoodsPrice(goodsPrice)) {
            baseResp.success();
        } else {
            baseResp.setMsg("修改失败");
        }
        return baseResp;
    }

    @Override
    public BaseResp batchMarketableChange(List<PdsBuyerGoodsReq> goodsReqList) {
        BaseResp baseResp = BaseResp.newError();

        for (PdsBuyerGoodsReq goodsReq : goodsReqList) {
            if (StringUtils.isAnyEmpty(goodsReq.getId(), goodsReq.getStatus())) {
                baseResp.setMsg("priceId,status不能为空");
                return baseResp;
            }
            PdsPlatformGoodsPrice platformGoodsPrice = goodsPriceService.get(goodsReq.getId());
            if (null != platformGoodsPrice) {
                platformGoodsPrice.setStatus(goodsReq.getStatus());
                goodsPriceService.save(platformGoodsPrice);
            }
        }
        baseResp.success();
        return baseResp;
    }

    /**
     * 该功能不使用
     *
     * @param copyPmPriceReq
     * @return
     */
    @Override
    public BaseResp copyGoodsByBuyer(PdsCopyPmPriceApiReq copyPmPriceReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsBuyer sourceBuyer = pdsBuyerService.get(copyPmPriceReq.getBuyerId());
        if (null == sourceBuyer || StringUtils.isBlank(sourceBuyer.getMerchantId())) {
            baseResp.setMsg("来源采购商数据有误");
            return baseResp;
        }
        PdsBuyer pdsBuyer = pdsBuyerService.get(copyPmPriceReq.getDestBuyerId());
        if (null == pdsBuyer || StringUtils.isBlank(pdsBuyer.getMerchantId())) {
            baseResp.setMsg("目标采购商数据有误");
            return baseResp;
        }
        PdsPlatformGoodsPrice pdsPlatformGoodsPrice = new PdsPlatformGoodsPrice();
        pdsPlatformGoodsPrice.setPmId(copyPmPriceReq.getPmId());
        pdsPlatformGoodsPrice.setMerchantId(sourceBuyer.getMerchantId());
        pdsPlatformGoodsPrice.setRate(copyPmPriceReq.getRate());
        pdsPlatformGoodsPrice.setNewMerchantId(pdsBuyer.getMerchantId());
        pdsPlatformGoodsPrice.setQueryDate(copyPmPriceReq.getQueryDate());
//        baseResp = copyGoodsByMerchant(pdsPlatformGoodsPrice);
        return baseResp;
    }

    /**
     * 复制商家的定价数据
     *
     * @param pdsPlatformGoodsPrice 必需 pmId/MerchantId/NewMerchantId/BeginStartDate/BeginEndDate/StartDate/EndDate
     * @return
     */
    @Override
    public BaseResp copyGoodsByMerchant(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = pdsPlatformGoodsPrice.getPmId();
        String sourceMerchantId = pdsPlatformGoodsPrice.getMerchantId();
        String targetMerchantId = pdsPlatformGoodsPrice.getNewMerchantId();
        // 来源定价 时间段
        Date sourceStartDate = pdsPlatformGoodsPrice.getBeginStartDate();
        Date sourceEndDate = pdsPlatformGoodsPrice.getBeginEndDate();
        // 目标定价时间
        Date targetStartDate = pdsPlatformGoodsPrice.getStartDate();
        Date targetEndDate = pdsPlatformGoodsPrice.getEndDate();
        if (StringUtils.isAnyEmpty(pmId, sourceMerchantId, targetMerchantId)
                || null == sourceStartDate || null == sourceEndDate || null == targetStartDate || null == targetEndDate) {
            baseResp.setMsg("缺少参数");
            return baseResp;
        }
        // 源定价数据 列表
        PdsPlatformGoodsPrice querySource = new PdsPlatformGoodsPrice();
        querySource.setPmId(pmId);
        querySource.setMerchantId(sourceMerchantId);
        querySource.setStartDate(sourceStartDate);
        querySource.setEndDate(sourceEndDate);
        querySource.setStatus(ICommonConstant.YesNoType.yes.getCode());
        List<PdsPlatformGoodsPrice> sourceList = goodsPriceService.findList(querySource);
        if (Collections3.isEmpty(sourceList)) {
            baseResp.setMsg("无来源定价数据");
            return baseResp;
        }

        PdsPlatformGoodsPrice queryTarget = new PdsPlatformGoodsPrice();
        queryTarget.setPmId(pmId);
        queryTarget.setMerchantId(targetMerchantId);
        queryTarget.setStartDate(targetStartDate);
        queryTarget.setEndDate(targetEndDate);
        queryTarget.setStatus(ICommonConstant.YesNoType.yes.getCode());
        int num = goodsPriceService.countRangePrice(queryTarget);
        List<PdsPlatformGoodsPrice> existList = goodsPriceService.findList(queryTarget);
        int size = existList.size();
        // 判断是否有时间段的交叉
        if (size < num) {
            baseResp.setMsg("目标商家定价不可重复");
            return baseResp;
        }
        size = size * 4 / 3 + 1;
        size = (size > 8) ? size : 8;
        Map<String, String> existMap = new HashMap<>(size);
        for (PdsPlatformGoodsPrice p : existList) {
            existMap.put(p.getModelId(), p.getId());
        }
        // 新增或修改
        String id;
        String modelId;
        for (PdsPlatformGoodsPrice s : sourceList) {
            s.setMerchantId(targetMerchantId);
            s.setStartDate(targetStartDate);
            s.setEndDate(targetEndDate);
            modelId = s.getModelId();
            id = existMap.containsKey(modelId) ? existMap.get(modelId) : "";
            s.setId(id);
            goodsPriceService.save(s);
        }
        baseResp.success();
        baseResp.setMsg("复制商品数据成功,共保存" + sourceList.size() + "条数据");
        return baseResp;
    }

    @Override
    public BaseResp copyGoodsByPmShop(PdsBuyerPriceReq buyerPriceReq) {
        return null;
    }

    /**
     * 查询 采购商商品[平台商、采购商、最大开始时间、最小结束时间]
     *
     * @param buyerPriceReq
     * @return
     */
    @Override
    public BaseResp queryBuyerGoodsList(PdsBuyerPriceReq buyerPriceReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = buyerPriceReq.getPmId();
        Date queryDate = buyerPriceReq.getQueryDate();
        // 需传入 buyerId 或 buyerMerchantId
        String buyerId = buyerPriceReq.getBuyerId();
        String buyerMerchantId = buyerPriceReq.getBuyerMerchantId();
        // 验证 采购商 获取基础信息
        PdsBuyer buyer = null;
        PdsBuyer queryBuyer = new PdsBuyer();
        queryBuyer.setPmId(pmId);
        if (StringUtils.isNotEmpty(buyerMerchantId)) {
            queryBuyer.setMerchantId(buyerMerchantId);
            List<PdsBuyer> buyerList = pdsBuyerService.findJoinList(queryBuyer);
            if (!Collections3.isEmpty(buyerList)) {
                buyer = buyerList.get(0);
            }
        } else if (StringUtils.isNotEmpty(buyerId)) {
            buyer = pdsBuyerService.getWithName(buyerId);
        } else {
            baseResp.setMsg("缺少参数buyerId或buyerMerchantId");
            return baseResp;
        }
        if (null == buyer || !StringUtils.equals(buyer.getPmId(), pmId)) {
            baseResp.setMsg("采购商错误");
            return baseResp;
        }
        // 原使用buyerId 修改后使用merchantId查询 采购商采购价
        String merchantId = buyer.getMerchantId();
        // 唯一时间段
        PdsPlatformGoodsPrice goodsPrice = new PdsPlatformGoodsPrice();
        goodsPrice.setPmId(pmId);
        goodsPrice.setMerchantId(merchantId);
        goodsPrice.setQueryDate(queryDate);

        List<PdsPlatformGoodsPrice> existList = goodsPriceService.findGoodsListByDateJoin(goodsPrice);
        if (Collections3.isEmpty(existList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        //结果按分类分组
        // 返回值
        PdsBuyer pdsbuyer = new PdsBuyer();
        pdsbuyer.setPmName(buyer.getPmName());
        pdsbuyer.setBuyerName(buyer.getBuyerName());
        pdsbuyer.setMerchantName(buyer.getMerchantName());
        pdsbuyer.setContact(buyer.getContact());
        pdsbuyer.setTelephone(buyer.getTelephone());
        pdsbuyer.setAddress(buyer.getAddress());
        PdsBuyerGoodsOfferResp result = new PdsBuyerGoodsOfferResp();
        result.setPdsBuyer(pdsbuyer);
        // 时间段是唯一的
        result.setStartDate(existList.get(0).getStartDate());
        result.setEndDate(existList.get(0).getEndDate());

        List<PdsApiBuyerGoodsCategoryResp> categoryRespList = transToCategoryList(existList);
        result.setCategoryList(categoryRespList);
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * 根据固定日期查询平台商家报价 (无数据时返回空)
     *
     * @param pmId
     * @param buyerId
     * @param modelId
     * @param date
     * @return
     */
    @Override
    public PdsPlatformGoodsPrice fetchPlatformGoodsPrice(String pmId, String buyerId, String modelId, Date date) {
        PdsBuyer pdsBuyer = pdsBuyerService.get(buyerId);
        if (null == pdsBuyer || StringUtils.isEmpty(pdsBuyer.getMerchantId())) {
            return null;
        }
        PdsPlatformGoodsPrice goods = new PdsPlatformGoodsPrice();
        goods.setPmId(pmId);
        goods.setMerchantId(pdsBuyer.getMerchantId());
        goods.setModelId(modelId);
        goods.setQueryDate(date);
        PdsPlatformGoodsPrice goodsPrice = goodsPriceService.fetchGoodsPrice(goods);
        return goodsPrice;
    }

    @Override
    public ApiRespPage fetchGoodsList(PdsBuyerGoodsReq goodsReq) {
        ApiRespPage respPage = new ApiRespPage<>();
        String buyerId = goodsReq.getBuyerId();
        PdsBuyer buyer = pdsBuyerService.get(buyerId);
        if (null == buyer) {
            return respPage;
        }
        Page page = new Page(goodsReq.getPageNo(), goodsReq.getPageSize());
        Goods goods = new Goods();
        goods.setMerchantId(goodsReq.getPmId());
        goods.setShopId(goodsReq.getShopId());
        goods.setGoodsName(goodsReq.getGoodsName());
        goods.setGoodsCategoryId(goodsReq.getCategoryId());
        goods.setGoodsStatus(goodsReq.getGoodsStatus());
        goods.setIsMarketable(1);
        goods.setPage(page);
        goods.setBuyerMerchantId(buyer.getMerchantId());
        goods.setBuyerUid(goodsReq.getUid());
        goods.setDeliveryDate(goodsReq.getDeliveryDate());
        goods.setGoodsSn(goodsReq.getGoodsSn());
        // 查询商品数据
        respPage = goodsService.findWithModelPds(goods);
        return respPage;
    }

    @Override
    public BaseResp goodsCollectList(PdsCollectGoodsListApiReq goodsListReq, Page<PdsTopNGoodsResp> page) {
        BaseResp baseResp = BaseResp.newError();

        String pmId = goodsListReq.getPmId();
        String uid = goodsListReq.getUid();
        GoodsCollections goodsCollections = new GoodsCollections();
        goodsCollections.setPmId(pmId);
        goodsCollections.setUid(uid);
        PdsBuyer pdsBuyer = pdsBuyerService.fetchByPassPortId(uid, pmId, "");
        if (null != pdsBuyer) {
            goodsCollections.setBuyerId(pdsBuyer.getId());
            goodsCollections.setBuyerMerchantId(pdsBuyer.getMerchantId());
        }
        // 查询上架商品
        goodsCollections.setQueryStatus(ICommonConstant.YesNoType.yes.getCode());
        page = goodsCollectionsService.selectCollectPage(page, goodsCollections);
        List<PdsTopNGoodsResp> respList = page.getList();
        if (CollectionUtils.isEmpty(respList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        BaseList<PdsTopNGoodsResp> baseList = new BaseList<>();
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalPage(page.getTotalPage());
        baseList.setList(respList);
        baseList.setPageSize(page.getPageSize());
        return baseList;
    }

    @Override
    public BaseResp addCollect(PdsCollectGoodsApiReq goodsReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = goodsReq.getPmId();
        String uid = goodsReq.getUid();
        String goodsId = goodsReq.getGoodsId();
        GoodsCollections exsistData = goodsCollectionsService.fetchByGoodsId(pmId, uid, goodsId);
        if (null != exsistData) {
            baseResp.setMsg("已收藏该商品");
            return baseResp;
        }

        GoodsCollections goodsCollections = new GoodsCollections();
        goodsCollections.setPmId(pmId);
        goodsCollections.setUid(uid);
        goodsCollections.setGoodsId(goodsId);
        goodsCollectionsService.save(goodsCollections);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp fetchGoodsModelInfoList(PdsBuyerGoodsModelListReq goodsReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = goodsReq.getPmId();
        String buyerId = goodsReq.getBuyerId();
        if (StringUtils.isAnyEmpty(pmId, buyerId) || Collections3.isEmpty(goodsReq.getGoodsIdList())) {
            baseResp.setMsg("参数错误pmId/buyerId/goodsIdList");
            return baseResp;
        }
        // 验证 采购商
        PdsBuyer buyer = pdsBuyerService.get(buyerId);
        if (null == buyer || !StringUtils.equals(buyer.getPmId(), pmId)) {
            baseResp.setMsg("采购商错误");
            return baseResp;
        }
        // 商品规格列表
        List<GoodsModel> modelList = new ArrayList<>();
        List<GoodsModel> queryModel;
        PdsPlatformGoodsPrice goodsPrice = new PdsPlatformGoodsPrice();
        goodsPrice.setPmId(goodsReq.getPmId());
        goodsPrice.setBuyerId(buyerId);
        goodsPrice.setQueryDate(new Date());
        goodsPrice.setStatus(ICommonConstant.YesNoType.yes.getCode());
        for (String modelId : goodsReq.getGoodsIdList()) {
            goodsPrice.setModelId(modelId);
            queryModel = goodsPriceService.findGoodsModelList(goodsPrice);
            if (!Collections3.isEmpty(queryModel)) {
                modelList.add(queryModel.get(0));
            }
        }
        if (!Collections3.isEmpty(modelList)) {
            baseResp.success();
            baseResp.setExt(new ArrayList<>(modelList));
        } else {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        }
        return baseResp;
    }

    private List<PdsApiBuyerGoodsCategoryResp> transToCategoryList(List<PdsPlatformGoodsPrice> list) {
        List<PdsApiBuyerGoodsCategoryResp> respList = new ArrayList<>();
        //按categoryname分组
        Map<String, List<PdsBuyerGoodsResp>> respMap = list.stream().map(PdsPlatformGoodsPrice -> {
            PdsBuyerGoodsResp goodsResp = new PdsBuyerGoodsResp();
            goodsResp.copyFromPlatformGoods(PdsPlatformGoodsPrice);
            return goodsResp;
        }).collect(Collectors.groupingBy(PdsBuyerGoodsResp::getCategoryId));

        for (String key : respMap.keySet()) {
            PdsApiBuyerGoodsCategoryResp categoryResp = new PdsApiBuyerGoodsCategoryResp();
            List<PdsBuyerGoodsResp> goodsRespList = respMap.get(key);
            categoryResp.setCategoryId(key);
            categoryResp.setCategoryName(goodsRespList.get(0).getCategoryName());
            categoryResp.setGoodsList(goodsRespList);
            respList.add(categoryResp);
        }
        return respList;
    }

    @Override
    public BaseResp cancelCollect(PdsCollectGoodsApiReq goodsReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = goodsReq.getPmId();
        String uid = goodsReq.getUid();
        String goodsId = goodsReq.getGoodsId();

        GoodsCollections goodsCollections = goodsCollectionsService.fetchByGoodsId(pmId, uid, goodsId);
        if (null == goodsCollections) {
            baseResp.setMsg("未找到收藏商品");
            return baseResp;
        }
        goodsCollectionsService.delete(goodsCollections);
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp updatePriceToPmShop(PdsUpdateShopPriceReq updateReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = updateReq.getPmId();
        String buyerMerchantId = updateReq.getBuyerMerchantId();
        String shopId = updateReq.getShopId();
        Date startDate = updateReq.getStartDate();
        Date endDate = updateReq.getEndDate();
        if (startDate.getTime() > endDate.getTime()) {
            baseResp.setMsg("时间范围有误!");
            return baseResp;
        }
        // 平台商家和采购商商家 须相同
        if (!StringUtils.equals(pmId, buyerMerchantId)) {
            baseResp.setMsg("商家有误!");
            return baseResp;
        }
        Shop shop = shopService.get(shopId);
        if (null == shop || !StringUtils.equals(buyerMerchantId, shop.getMerchantId())) {
            baseResp.setMsg("店铺有误!");
            return baseResp;
        }
        PdsPlatformGoodsPrice queryPrice = new PdsPlatformGoodsPrice();
        updateReq.copyToPrice(queryPrice);
        // 需修改的价格 来源
        List<PdsPlatformGoodsPrice> sourceList = goodsPriceService.findList(queryPrice);
        if (Collections3.isEmpty(sourceList)) {
            baseResp.setMsg("找不到价格信息");
            return baseResp;
        }
        GoodsModel queryModel = new GoodsModel();
        queryModel.setShopId(shopId);
        GoodsModel targetModel;
        BigDecimal updatePrice;
        int successNum = 0;
        GoodsPriceRule priceRule;
        for (PdsPlatformGoodsPrice goodsPrice : sourceList) {
            queryModel.setId(goodsPrice.getModelId());
            updatePrice = goodsPrice.getPrice();
            targetModel = goodsModelService.fetchWithShop(queryModel);
            if (null != targetModel && targetModel.getModelPrice().compareTo(updatePrice) != 0) {
                targetModel.setModelPrice(updatePrice);
                goodsModelService.save(targetModel);
                successNum++;
                // 修改商品spu价格
                priceRule = goodsPriceRuleService.fetchByGoodsId(targetModel.getGoodsId());
                if (null != priceRule && updatePrice.compareTo(priceRule.getMarketPrice()) > 0) {
                    priceRule.setMarketPrice(updatePrice);
                    goodsPriceRuleService.save(priceRule);
                }
            }
        }
        if (successNum > 0) {
            baseResp.success();
            baseResp.setMsg("共修改" + successNum + "条规格价格信息");
        } else {
            baseResp.setMsg("无需要修改的价格信息");
        }
        return baseResp;
    }

    @Override
    public BaseResp deletePriceBatch(PdsDeletePriceBatchReq deleteReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsPlatformGoodsPrice delGoodsPrice = new PdsPlatformGoodsPrice();
        deleteReq.copyToPrice(delGoodsPrice);
        int rows = goodsPriceService.deleteBatch(delGoodsPrice);
        String msg;
        if (rows > 0) {
            baseResp.success();
            msg = "删除已有采购商商品报价信息" + rows + "条";
        } else {
            msg = "找不到对应采购商商品报价信息";
        }
        baseResp.setMsg(msg);
        return baseResp;
    }

    /**
     * 目前未使用状态操作
     *
     * @param req
     * @return
     */
    @Override
    public BaseResp deletePrice(PdsDeletePriceReq req) {
        BaseResp baseResp = BaseResp.newError();
        PdsPlatformGoodsPrice del = new PdsPlatformGoodsPrice();
        del.setPmId(req.getPmId());
        del.setId(req.getPriceId());
        int num = goodsPriceService.deletePrice(del);
        if (num > 0) {
            return baseResp.success();
        }
        return baseResp;
    }

    @Override
    public BaseResp addPrice(PdsAddPriceReq req) {
        BaseResp baseResp = BaseResp.newError();
        // 商品
        GoodsModel model = goodsModelService.get(req.getModelId());
        if (null == model) {
            baseResp.setMsg("商品规格有误");
            return baseResp;
        }
        Goods goods = goodsService.getExt(model.getGoodsId());
        if (null == goods) {
            baseResp.setMsg("商品有误");
            return baseResp;
        }
        PdsPlatformGoodsPrice platformPrice = new PdsPlatformGoodsPrice();
        BeanUtils.copyProperties(req, platformPrice);
        // 目前未使用状态操作
        platformPrice.setStatus(ICommonConstant.YesNoType.yes.getCode());
        platformPrice.setCategoryId(goods.getGoodsCategoryId());
        platformPrice.setGoodsId(goods.getId());
        platformPrice.setCategoryName(goods.getCategoryName());
        platformPrice.setGoodsName(goods.getGoodsName());
        platformPrice.setModelName(model.getModelName());
        platformPrice.setUnit(model.getModelUnit());
        goodsPriceService.save(platformPrice);
        baseResp.success();
        return baseResp;
    }
}
