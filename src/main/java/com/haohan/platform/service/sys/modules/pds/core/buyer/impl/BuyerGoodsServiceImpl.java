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
 * ????????????  ????????? ????????????
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
     * ???????????????????????????????????????????????????
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
            baseResp.setMsg("????????????pmId/shopId/buyerMerchantId/date");
            return baseResp;
        }
        if (rate.compareTo(BigDecimal.ZERO) < 1) {
            baseResp.setMsg("??????????????????");
            return baseResp;
        }
        // ?????? ???????????????
        PdsBuyer buyer = new PdsBuyer();
        buyer.setMerchantId(buyerMerchantId);
        buyer.setPmId(pmId);
        List<PdsBuyer> buyerList = pdsBuyerService.findList(buyer);
        if (Collections3.isEmpty(buyerList)) {
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        // ??? ?????????????????????????????????????????? ??????????????????
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
            baseResp.setMsg("????????????????????????????????????????????????,??????????????????,????????????");
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
                baseResp.setMsg("???????????????????????????" + errorNum + "???????????????");
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
            baseResp.setMsg("????????????");
            return baseResp;
        }
        PdsPlatformGoodsPrice goodsPrice;
        // ??????id pmId  ??????merchantId/buyerId??????
        goodsPrice = goodsPriceService.fetch(pdsPlatformGoodsPrice);
        if (null == goodsPrice) {
            baseResp.setMsg("??????????????????");
            return baseResp;
        }
        goodsPrice = new PdsPlatformGoodsPrice();
        goodsPrice.setId(id);
        goodsPrice.setPrice(price);
        if (goodsPriceService.updateGoodsPrice(goodsPrice)) {
            baseResp.success();
        } else {
            baseResp.setMsg("????????????");
        }
        return baseResp;
    }

    @Override
    public BaseResp batchMarketableChange(List<PdsBuyerGoodsReq> goodsReqList) {
        BaseResp baseResp = BaseResp.newError();

        for (PdsBuyerGoodsReq goodsReq : goodsReqList) {
            if (StringUtils.isAnyEmpty(goodsReq.getId(), goodsReq.getStatus())) {
                baseResp.setMsg("priceId,status????????????");
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
     * ??????????????????
     *
     * @param copyPmPriceReq
     * @return
     */
    @Override
    public BaseResp copyGoodsByBuyer(PdsCopyPmPriceApiReq copyPmPriceReq) {
        BaseResp baseResp = BaseResp.newError();
        PdsBuyer sourceBuyer = pdsBuyerService.get(copyPmPriceReq.getBuyerId());
        if (null == sourceBuyer || StringUtils.isBlank(sourceBuyer.getMerchantId())) {
            baseResp.setMsg("???????????????????????????");
            return baseResp;
        }
        PdsBuyer pdsBuyer = pdsBuyerService.get(copyPmPriceReq.getDestBuyerId());
        if (null == pdsBuyer || StringUtils.isBlank(pdsBuyer.getMerchantId())) {
            baseResp.setMsg("???????????????????????????");
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
     * ???????????????????????????
     *
     * @param pdsPlatformGoodsPrice ?????? pmId/MerchantId/NewMerchantId/BeginStartDate/BeginEndDate/StartDate/EndDate
     * @return
     */
    @Override
    public BaseResp copyGoodsByMerchant(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = pdsPlatformGoodsPrice.getPmId();
        String sourceMerchantId = pdsPlatformGoodsPrice.getMerchantId();
        String targetMerchantId = pdsPlatformGoodsPrice.getNewMerchantId();
        // ???????????? ?????????
        Date sourceStartDate = pdsPlatformGoodsPrice.getBeginStartDate();
        Date sourceEndDate = pdsPlatformGoodsPrice.getBeginEndDate();
        // ??????????????????
        Date targetStartDate = pdsPlatformGoodsPrice.getStartDate();
        Date targetEndDate = pdsPlatformGoodsPrice.getEndDate();
        if (StringUtils.isAnyEmpty(pmId, sourceMerchantId, targetMerchantId)
                || null == sourceStartDate || null == sourceEndDate || null == targetStartDate || null == targetEndDate) {
            baseResp.setMsg("????????????");
            return baseResp;
        }
        // ??????????????? ??????
        PdsPlatformGoodsPrice querySource = new PdsPlatformGoodsPrice();
        querySource.setPmId(pmId);
        querySource.setMerchantId(sourceMerchantId);
        querySource.setStartDate(sourceStartDate);
        querySource.setEndDate(sourceEndDate);
        querySource.setStatus(ICommonConstant.YesNoType.yes.getCode());
        List<PdsPlatformGoodsPrice> sourceList = goodsPriceService.findList(querySource);
        if (Collections3.isEmpty(sourceList)) {
            baseResp.setMsg("?????????????????????");
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
        // ?????????????????????????????????
        if (size < num) {
            baseResp.setMsg("??????????????????????????????");
            return baseResp;
        }
        size = size * 4 / 3 + 1;
        size = (size > 8) ? size : 8;
        Map<String, String> existMap = new HashMap<>(size);
        for (PdsPlatformGoodsPrice p : existList) {
            existMap.put(p.getModelId(), p.getId());
        }
        // ???????????????
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
        baseResp.setMsg("????????????????????????,?????????" + sourceList.size() + "?????????");
        return baseResp;
    }

    @Override
    public BaseResp copyGoodsByPmShop(PdsBuyerPriceReq buyerPriceReq) {
        return null;
    }

    /**
     * ?????? ???????????????[???????????????????????????????????????????????????????????????]
     *
     * @param buyerPriceReq
     * @return
     */
    @Override
    public BaseResp queryBuyerGoodsList(PdsBuyerPriceReq buyerPriceReq) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = buyerPriceReq.getPmId();
        Date queryDate = buyerPriceReq.getQueryDate();
        // ????????? buyerId ??? buyerMerchantId
        String buyerId = buyerPriceReq.getBuyerId();
        String buyerMerchantId = buyerPriceReq.getBuyerMerchantId();
        // ?????? ????????? ??????????????????
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
            baseResp.setMsg("????????????buyerId???buyerMerchantId");
            return baseResp;
        }
        if (null == buyer || !StringUtils.equals(buyer.getPmId(), pmId)) {
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        // ?????????buyerId ???????????????merchantId?????? ??????????????????
        String merchantId = buyer.getMerchantId();
        // ???????????????
        PdsPlatformGoodsPrice goodsPrice = new PdsPlatformGoodsPrice();
        goodsPrice.setPmId(pmId);
        goodsPrice.setMerchantId(merchantId);
        goodsPrice.setQueryDate(queryDate);

        List<PdsPlatformGoodsPrice> existList = goodsPriceService.findGoodsListByDateJoin(goodsPrice);
        if (Collections3.isEmpty(existList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        //?????????????????????
        // ?????????
        PdsBuyer pdsbuyer = new PdsBuyer();
        pdsbuyer.setPmName(buyer.getPmName());
        pdsbuyer.setBuyerName(buyer.getBuyerName());
        pdsbuyer.setMerchantName(buyer.getMerchantName());
        pdsbuyer.setContact(buyer.getContact());
        pdsbuyer.setTelephone(buyer.getTelephone());
        pdsbuyer.setAddress(buyer.getAddress());
        PdsBuyerGoodsOfferResp result = new PdsBuyerGoodsOfferResp();
        result.setPdsBuyer(pdsbuyer);
        // ?????????????????????
        result.setStartDate(existList.get(0).getStartDate());
        result.setEndDate(existList.get(0).getEndDate());

        List<PdsApiBuyerGoodsCategoryResp> categoryRespList = transToCategoryList(existList);
        result.setCategoryList(categoryRespList);
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * ?????????????????????????????????????????? (?????????????????????)
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
        // ??????????????????
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
        // ??????????????????
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
            baseResp.setMsg("??????????????????");
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
            baseResp.setMsg("????????????pmId/buyerId/goodsIdList");
            return baseResp;
        }
        // ?????? ?????????
        PdsBuyer buyer = pdsBuyerService.get(buyerId);
        if (null == buyer || !StringUtils.equals(buyer.getPmId(), pmId)) {
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        // ??????????????????
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
        //???categoryname??????
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
            baseResp.setMsg("?????????????????????");
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
            baseResp.setMsg("??????????????????!");
            return baseResp;
        }
        // ?????????????????????????????? ?????????
        if (!StringUtils.equals(pmId, buyerMerchantId)) {
            baseResp.setMsg("????????????!");
            return baseResp;
        }
        Shop shop = shopService.get(shopId);
        if (null == shop || !StringUtils.equals(buyerMerchantId, shop.getMerchantId())) {
            baseResp.setMsg("????????????!");
            return baseResp;
        }
        PdsPlatformGoodsPrice queryPrice = new PdsPlatformGoodsPrice();
        updateReq.copyToPrice(queryPrice);
        // ?????????????????? ??????
        List<PdsPlatformGoodsPrice> sourceList = goodsPriceService.findList(queryPrice);
        if (Collections3.isEmpty(sourceList)) {
            baseResp.setMsg("?????????????????????");
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
                // ????????????spu??????
                priceRule = goodsPriceRuleService.fetchByGoodsId(targetModel.getGoodsId());
                if (null != priceRule && updatePrice.compareTo(priceRule.getMarketPrice()) > 0) {
                    priceRule.setMarketPrice(updatePrice);
                    goodsPriceRuleService.save(priceRule);
                }
            }
        }
        if (successNum > 0) {
            baseResp.success();
            baseResp.setMsg("?????????" + successNum + "?????????????????????");
        } else {
            baseResp.setMsg("??????????????????????????????");
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
            msg = "???????????????????????????????????????" + rows + "???";
        } else {
            msg = "??????????????????????????????????????????";
        }
        baseResp.setMsg(msg);
        return baseResp;
    }

    /**
     * ???????????????????????????
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
        // ??????
        GoodsModel model = goodsModelService.get(req.getModelId());
        if (null == model) {
            baseResp.setMsg("??????????????????");
            return baseResp;
        }
        Goods goods = goodsService.getExt(model.getGoodsId());
        if (null == goods) {
            baseResp.setMsg("????????????");
            return baseResp;
        }
        PdsPlatformGoodsPrice platformPrice = new PdsPlatformGoodsPrice();
        BeanUtils.copyProperties(req, platformPrice);
        // ???????????????????????????
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
