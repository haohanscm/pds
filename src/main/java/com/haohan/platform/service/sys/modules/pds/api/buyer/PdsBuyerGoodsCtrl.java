package com.haohan.platform.service.sys.modules.pds.api.buyer;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsModelListReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsUpdateListReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerPriceReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerPriceUpdateReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer.*;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerGoodsService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 采购商 信息 采购商商品
 * Created by dy on 2018/12/10.
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/buyer/goods")
public class PdsBuyerGoodsCtrl extends BaseController {

    @Autowired
    private IBuyerGoodsService buyerGoodsService;

    /**
     * 采购商 一段时间
     *
     * @return
     */
    @RequestMapping(value = "queryList")
    @ResponseBody
    public BaseResp queryList(@Validated PdsBuyerPriceReq buyerPrice, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        baseResp = buyerGoodsService.queryBuyerGoodsList(buyerPrice);
        return baseResp;
    }

    /**
     * 采购商 初始化复制
     *
     * @return
     */
    @RequestMapping(value = "initPrice")
    @ResponseBody
    public BaseResp initPrice(PdsBuyerPriceReq buyerPrice) {

        BaseResp baseResp = buyerGoodsService.copyGoodsToBuyerGoods(buyerPrice);
        return baseResp;
    }

    /**
     * 采购商 批量修改 采购价
     *
     * @return
     */
    @RequestMapping(value = "batchUpdateGoodsPrice")
    @ResponseBody
    public BaseResp batchUpdateGoodsPrice(@Validated @RequestBody PdsBuyerGoodsUpdateListReq updateListReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        return buyerGoodsService.batchUpdateGoodsPrice(updateListReq);
    }

    /**
     * 采购商 修改 采购价
     *
     * @return
     */
    @RequestMapping(value = "updateGoodsPrice")
    @ResponseBody
    public BaseResp updateGoodsPrice(@Validated PdsBuyerPriceUpdateReq updateReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        PdsPlatformGoodsPrice goodsPrice = new PdsPlatformGoodsPrice();
        updateReq.copyToGoodsPrice(goodsPrice);
        return buyerGoodsService.updateGoodsPrice(goodsPrice);
    }

    /**
     * 采购商 批量上下架
     *
     * @return
     */
    @RequestMapping(value = "batchUpdateShelfStatus")
    @ResponseBody
    public BaseResp batchUpdateShelfStatus(@RequestBody PdsApiBuyerGoodsShelfReq pdsApiBuyerGoodsShelfReq, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (null == pdsApiBuyerGoodsShelfReq || CollectionUtils.isEmpty(pdsApiBuyerGoodsShelfReq.getGoodsReqList())) {
            baseResp.setMsg("请求参数有误");
            return baseResp;
        }

        baseResp = buyerGoodsService.batchMarketableChange(pdsApiBuyerGoodsShelfReq.getGoodsReqList());
        return baseResp;
    }

    @RequestMapping(value = "copyGoodsByBuyer")
    @ResponseBody
    public BaseResp copyGoodsByBuyer(@Validated PdsCopyPmPriceApiReq copyPmPriceReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = buyerGoodsService.copyGoodsByBuyer(copyPmPriceReq);
        return baseResp;
    }


    @RequestMapping(value = "collect/list")
    @ResponseBody
    public BaseResp goodsCollectList(@Validated PdsCollectGoodsListApiReq collectGoodsReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        Integer pageNo = collectGoodsReq.getPageNo();
        Integer pageSize = collectGoodsReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 10 : pageSize);
        baseResp = buyerGoodsService.goodsCollectList(collectGoodsReq, reqPage);
        return baseResp;
    }

    @RequestMapping(value = "collect/add")
    @ResponseBody
    public BaseResp addCollect(@Validated PdsCollectGoodsApiReq goodsReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = buyerGoodsService.addCollect(goodsReq);
        return baseResp;
    }

    @RequestMapping(value = "collect/cancel")
    @ResponseBody
    public BaseResp cancelCollect(@Validated PdsCollectGoodsApiReq goodsReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = buyerGoodsService.cancelCollect(goodsReq);
        return baseResp;
    }

    /**
     * 采购商商品(sku)信息列表查询
     *
     * @return
     */
    @RequestMapping(value = "modelInfoList")
    @ResponseBody
    public BaseResp fetchGoodsModelInfoList(@RequestBody PdsBuyerGoodsModelListReq goodsModelListReq) {
        BaseResp baseResp = buyerGoodsService.fetchGoodsModelInfoList(goodsModelListReq);
        return baseResp;
    }

    /**
     * 批量修改平台商家店铺 商品的零售定价
     * 价格来源于 pmId=buyerMerchantId
     *
     * @param updateReq
     * @return
     */
    @RequestMapping(value = "shopPrice/batchUpdate")
    @ResponseBody
    public BaseResp updatePriceToPmShop(@Validated PdsUpdateShopPriceReq updateReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        return buyerGoodsService.updatePriceToPmShop(updateReq);
    }

    /**
     * 批量删除采购商 采购价
     *
     * @param deleteReq
     * @return
     */
    @RequestMapping(value = "buyerPrice/deleteUpdate")
    @ResponseBody
    public BaseResp deletePriceBatch(@Validated PdsDeletePriceBatchReq deleteReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        return buyerGoodsService.deletePriceBatch(deleteReq);
    }

    // 删除 商家 商品定价
    @RequestMapping(value = "deletePrice")
    @ResponseBody
    public BaseResp deletePrice(@Validated PdsDeletePriceReq req, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        return buyerGoodsService.deletePrice(req);
    }

    // 新增 商品定价
    @RequestMapping(value = "addPrice")
    @ResponseBody
    public BaseResp addPrice(@Validated PdsAddPriceReq req, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        return buyerGoodsService.addPrice(req);
    }

    /**
     * 复制商家的 商品定价
     * 必需 pmId/merchantId/newMerchantId/beginStartDate/beginEndDate/startDate/endDate
     *
     * @param pdsPlatformGoodsPrice
     * @return
     */
    @RequestMapping(value = "copyPrice")
    @ResponseBody
    public BaseResp copyPrice(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        return buyerGoodsService.copyGoodsByMerchant(pdsPlatformGoodsPrice);
    }

}