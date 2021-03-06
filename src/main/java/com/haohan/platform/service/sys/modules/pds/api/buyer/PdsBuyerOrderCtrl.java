package com.haohan.platform.service.sys.modules.pds.api.buyer;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer.PdsBuyerOrderApiReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerOrderService;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrderDetail;
import com.haohan.platform.service.sys.modules.pds.entity.order.TradeOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderDetailService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.weixin.mp.message.WxMpMessageService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * ?????????API??????
 *
 * @author shenyu
 * @create 2018/10/19
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/buyer/order")
public class PdsBuyerOrderCtrl extends BaseController {

    @Resource
    private IBuyerOrderService buyerOrderService;
    @Resource
    private IPdsCommonService pdsCommonServiceImpl;
    @Resource
    private WxMpMessageService wxMpMessageService;
    @Resource
    private BuyOrderService buyOrderService;
    @Resource
    private BuyOrderDetailService buyOrderDetailService;
    @Autowired
    private PdsBuyerService pdsBuyerService;


    /**
     * ???????????????
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/add")
    @ResponseBody
    public BaseResp addBuyOrder(@RequestBody BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        // ????????????
        if (null == buyOrder.getDeliveryTime() || StringUtils.isAnyEmpty(buyOrder.getBuySeq(), buyOrder.getBuyerId())) {
            baseResp.setMsg("?????????????????????id/????????????/????????????/pmId");
            return baseResp;
        }
        if (Collections3.isEmpty(buyOrder.getBuyOrderDetailList())) {
            baseResp.setMsg("??????buyOrderDetailList");
            return baseResp;
        }
        baseResp = buyerOrderService.addBuyOrder(buyOrder);
        return baseResp;
    }

    /**
     * ???????????????
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/modify")
    @ResponseBody
    public BaseResp modifyBuyOrder(@RequestBody BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        // ????????????
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.setMsg("????????????buyId");
            return baseResp;
        }
        // todo ???????????????????????????
//        baseResp = operationService.modifyBuyOrder(buyOrder);
        baseResp = buyerOrderService.modifyBuyOrder(buyOrder);
        return baseResp;
    }

    /**
     * ??????????????? ??????  ???????????????
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/query")
    @ResponseBody
    public BaseResp queryBuyOrder(BuyOrder buyOrder, Page page) {

        BaseResp baseResp = BaseResp.newError();
        // ????????????
        String status = buyOrder.getStatus();
        String buyerId = buyOrder.getBuyerId();
        String pmId = buyOrder.getPmId();
        Date deliveryTime = buyOrder.getDeliveryTime();
        String buyId = buyOrder.getBuyId();
        if (StringUtils.isEmpty(buyerId)) {
            baseResp.setMsg("????????????buyerId");
            return baseResp;
        }
        // ????????????
        buyOrder = new BuyOrder();
        buyOrder.setBuyerId(buyerId);
        buyOrder.setPmId(pmId);
        buyOrder.setDeliveryTime(deliveryTime);
        buyOrder.setBuyId(buyId);
        int pageNo = page.getPageNo() <= 1 ? 1 : page.getPageNo();
        int pageSize = page.getPageSize() <= 5 ? 5 : page.getPageSize();
        buyOrder.setPage(new Page<>(pageNo, pageSize));
        // ????????????  ??????????????? ????????? ???????????????????????????
        if (null == IPdsConstant.BuyOrderStatus.getTypeByCode(status)) {
            status = "";
        }
        if (StringUtils.isEmpty(status) || StringUtils.equals(status, IPdsConstant.BuyOrderStatus.wait.getCode())) {
            status = IPdsConstant.BuyOrderStatus.submit.getCode().concat(",").concat(IPdsConstant.BuyOrderStatus.wait.getCode());
        } else if (StringUtils.equals(status, IPdsConstant.BuyOrderStatus.arrive.getCode())) {
            // ?????????????????????????????????
            status = IPdsConstant.BuyOrderStatus.delivery.getCode().concat(",").concat(IPdsConstant.BuyOrderStatus.arrive.getCode());
        }
        buyOrder.setStatus(status);
        baseResp = buyerOrderService.queryBuyOrderList(buyOrder);
        return baseResp;
    }

    /**
     * ????????? ???????????????
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/detail")
    @ResponseBody
    public BaseResp detailBuyOrder(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        //????????????
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.putStatus(RespStatus.PARAMS_VALID_ERROR);
            return baseResp;
        }
        // ????????????
        baseResp = buyerOrderService.queryBuyOrderDetailList(buyOrder);
        return baseResp;
    }

    /**
     * ?????????????????????
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/confirm")
    @ResponseBody
    public BaseResp confirmBuyOrder(@RequestBody BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        //????????????
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId()) || CollectionUtils.isEmpty(buyOrder.getBuyOrderDetailList())) {
            baseResp.setMsg("????????????buyId/buyerId/buyOrderDetailList");
            return baseResp;
        }
        // todo ???????????????????????????
//        baseResp = operationService.confirmPrice(buyOrder);
        baseResp = buyerOrderService.confirmBuyOrder(buyOrder);
        return baseResp;
    }


    /**
     * ????????? ?????????????????????????????? ??????
     *
     * @param tradeOrder
     * @param page
     * @return
     */
    @RequestMapping(value = "tradeGoods/fetchList")
    @ResponseBody
    public BaseResp tradeGoodsList(TradeOrder tradeOrder, Page page) {
        BaseResp baseResp = BaseResp.newError();
        //????????????
        if (StringUtils.isEmpty(tradeOrder.getBuyerId())) {
            baseResp.setMsg("????????????buyerId");
            return baseResp;
        }
        // ??????
        int pageNo = page.getPageNo() <= 1 ? 1 : page.getPageNo();
        int pageSize = page.getPageSize() <= 10 ? 10 : page.getPageSize();
        page = new Page<>(pageNo, pageSize);
        // ???????????? (?????????????????????)
        String status = tradeOrder.getBuyerStatus();
        if (StringUtils.isEmpty(status)) {
            // ?????? ??????????????????
            tradeOrder.setBuyerStatus(IPdsConstant.BuyerDealStatus.wait_check.getCode());
        }
        baseResp = buyerOrderService.tradeGoodsList(tradeOrder, page);
        return baseResp;
    }


    /**
     * ????????? ??????????????????
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "tradeGoods/confirm")
    @ResponseBody
    public BaseResp confirmTradeGoods(@RequestBody BuyOrder buyOrder) {

        BaseResp baseResp = BaseResp.newError();

        //????????????
        if (CollectionUtils.isEmpty(buyOrder.getBuyOrderDetailList()) || StringUtils.isEmpty(buyOrder.getBuyerId())) {
            baseResp.setMsg("????????????buyOrderDetailList/buyerId");
            return baseResp;
        }
        // ???????????????????????????
        TradeOrder tradeOrder;
        String buyId = "1";
        BaseResp goodsResp;
        int successNum = 0;
        for (BuyOrderDetail detail : buyOrder.getBuyOrderDetailList()) {
            buyId = detail.getBuyId();
            String goodsId = detail.getGoodsId();
            if (StringUtils.isAnyEmpty(buyId, goodsId)) {
                continue;
            }
            tradeOrder = new TradeOrder();
            tradeOrder.setBuyerId(buyOrder.getBuyerId());
            tradeOrder.setBuyId(buyId);
            tradeOrder.setGoodsId(goodsId);
            goodsResp = buyerOrderService.confirmTradeGoods(tradeOrder);
            if (goodsResp.isSuccess()) {
                successNum++;
            }
        }
        // ?????????buyId
        if (!StringUtils.isEmpty(buyOrder.getBuyId())) {
            buyId = buyOrder.getBuyId();
        }
        if (successNum > 0) {
            baseResp.success();
            baseResp.setExt(successNum);
            BuyOrder order = buyOrderService.fetchByBuyId(buyId);
            // ????????????
            if (null != order) {
                UserOpenPlatform userOpenPlatform = pdsCommonServiceImpl.fetchOpenUserByUid(order.getBuyerUid(), IPdsConstant.WX_MP_APPID, order.getBuyerId(), IPdsConstant.CompanyType.buyer);
                if (null != userOpenPlatform) {
                    wxMpMessageService.orderDealCloseNotify(userOpenPlatform, order);
                }
            }
        } else {
            baseResp.setMsg("??????????????????");
        }
        return baseResp;
    }

    /**
     * ?????????????????? ?????? ?????????
     *
     * @param orderReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "merchant/buyOrderList")
    @ResponseBody
    public BaseResp buyOrderList(@Validated PdsBuyerOrderApiReq orderReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setPmId(orderReq.getPmId());
        buyOrder.setDeliveryTime(orderReq.getDeliveryDate());
        buyOrder.setBuySeq(orderReq.getBuySeq());
        buyOrder.setMerchantId(orderReq.getMerchantId());
        // ??????????????? ?????????????????????
        String status = orderReq.getStatus();
        if (StringUtils.isEmpty(status)) {
            status = IPdsConstant.BuyOrderStatus.submit.getCode();
        }
        buyOrder.setStatus(status);
        List<BuyOrder> list = buyOrderService.findListGroupByMerchant(buyOrder);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            HashMap<String, Object> result = new HashMap<>(8);
            result.put("count", list.size());
            result.put("list", list);
            baseResp.setExt(result);
        }
        return baseResp;
    }

    @RequestMapping(value = "buyOrder/printComplete")
    @ResponseBody
    public BaseResp printCompleteOrder(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        boolean resultFlag;
        if (null == buyOrder || StringUtils.isAnyBlank(buyOrder.getPmId(), buyOrder.getBuyId())) {
            baseResp.setMsg("??????????????????");
            return baseResp;
        } else {
            buyOrder = buyOrderService.fetchByBuyId(buyOrder.getBuyId());
            buyOrder.setBuyOrderDetailList(buyOrderDetailService.findListByBuyId(buyOrder.getBuyId()));
            PdsBuyer pdsBuyer = pdsBuyerService.get(buyOrder.getBuyerId());
            resultFlag = buyerOrderService.produceCreateOrderPrintMsg(buyOrder, pdsBuyer);
        }
        return resultFlag == Boolean.TRUE ? baseResp.success() : baseResp;
    }

    /**
     * ?????????????????? ?????? ???????????????
     *
     * @param orderReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "merchant/buyOrderDetailList")
    @ResponseBody
    public BaseResp buyOrderDetailList(@Validated PdsBuyerOrderApiReq orderReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
        buyOrderDetail.setPmId(orderReq.getPmId());
        buyOrderDetail.setDeliveryDate(orderReq.getDeliveryDate());
        buyOrderDetail.setBuySeq(orderReq.getBuySeq());
        buyOrderDetail.setMerchantId(orderReq.getMerchantId());
        // ??????????????? ?????????????????????
        String status = orderReq.getStatus();
        if (StringUtils.isEmpty(status)) {
            status = IPdsConstant.BuyOrderStatus.submit.getCode();
        }
        buyOrderDetail.setStatus(status);
        List<BuyOrderDetail> list = buyOrderDetailService.findListGroupByMerchant(buyOrderDetail);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            baseResp.success();
            HashMap<String, Object> result = new HashMap<>(8);
            result.put("count", list.size());
            result.put("list", list);
            baseResp.setExt(result);
        }
        return baseResp;
    }

    /**
     * ????????????
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "confirmAllGoods")
    @ResponseBody
    public BaseResp confirmAllGoods(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.setMsg("????????????,??????buyId/buyerId");
            return baseResp;
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setBuyId(buyOrder.getBuyId());
        tradeOrder.setBuyerId(buyOrder.getBuyerId());
        baseResp = buyerOrderService.confirmAllGoods(tradeOrder);
        return baseResp;
    }
}
