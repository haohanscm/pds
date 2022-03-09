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
 * 采购商API接口
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
     * 新增采购单
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/add")
    @ResponseBody
    public BaseResp addBuyOrder(@RequestBody BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        // 验证参数
        if (null == buyOrder.getDeliveryTime() || StringUtils.isAnyEmpty(buyOrder.getBuySeq(), buyOrder.getBuyerId())) {
            baseResp.setMsg("缺少参数采购商id/采购批次/送货时间/pmId");
            return baseResp;
        }
        if (Collections3.isEmpty(buyOrder.getBuyOrderDetailList())) {
            baseResp.setMsg("缺少buyOrderDetailList");
            return baseResp;
        }
        baseResp = buyerOrderService.addBuyOrder(buyOrder);
        return baseResp;
    }

    /**
     * 修改采购单
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/modify")
    @ResponseBody
    public BaseResp modifyBuyOrder(@RequestBody BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        // 验证参数
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.setMsg("缺少参数buyId");
            return baseResp;
        }
        // todo 流程阶段判断并调用
//        baseResp = operationService.modifyBuyOrder(buyOrder);
        baseResp = buyerOrderService.modifyBuyOrder(buyOrder);
        return baseResp;
    }

    /**
     * 采购单列表 查询  带采购明细
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/query")
    @ResponseBody
    public BaseResp queryBuyOrder(BuyOrder buyOrder, Page page) {

        BaseResp baseResp = BaseResp.newError();
        // 验证参数
        String status = buyOrder.getStatus();
        String buyerId = buyOrder.getBuyerId();
        String pmId = buyOrder.getPmId();
        Date deliveryTime = buyOrder.getDeliveryTime();
        String buyId = buyOrder.getBuyId();
        if (StringUtils.isEmpty(buyerId)) {
            baseResp.setMsg("缺少参数buyerId");
            return baseResp;
        }
        // 设置参数
        buyOrder = new BuyOrder();
        buyOrder.setBuyerId(buyerId);
        buyOrder.setPmId(pmId);
        buyOrder.setDeliveryTime(deliveryTime);
        buyOrder.setBuyId(buyId);
        int pageNo = page.getPageNo() <= 1 ? 1 : page.getPageNo();
        int pageSize = page.getPageSize() <= 5 ? 5 : page.getPageSize();
        buyOrder.setPage(new Page<>(pageNo, pageSize));
        // 状态处理  待确认状态 可查看 已下单和待确认订单
        if (null == IPdsConstant.BuyOrderStatus.getTypeByCode(status)) {
            status = "";
        }
        if (StringUtils.isEmpty(status) || StringUtils.equals(status, IPdsConstant.BuyOrderStatus.wait.getCode())) {
            status = IPdsConstant.BuyOrderStatus.submit.getCode().concat(",").concat(IPdsConstant.BuyOrderStatus.wait.getCode());
        } else if (StringUtils.equals(status, IPdsConstant.BuyOrderStatus.arrive.getCode())) {
            // 查看待收货和待发货订单
            status = IPdsConstant.BuyOrderStatus.delivery.getCode().concat(",").concat(IPdsConstant.BuyOrderStatus.arrive.getCode());
        }
        buyOrder.setStatus(status);
        baseResp = buyerOrderService.queryBuyOrderList(buyOrder);
        return baseResp;
    }

    /**
     * 采购单 带采购明细
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/detail")
    @ResponseBody
    public BaseResp detailBuyOrder(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.putStatus(RespStatus.PARAMS_VALID_ERROR);
            return baseResp;
        }
        // 请求参数
        baseResp = buyerOrderService.queryBuyOrderDetailList(buyOrder);
        return baseResp;
    }

    /**
     * 采购商确认报价
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "buyOrder/confirm")
    @ResponseBody
    public BaseResp confirmBuyOrder(@RequestBody BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId()) || CollectionUtils.isEmpty(buyOrder.getBuyOrderDetailList())) {
            baseResp.setMsg("缺少参数buyId/buyerId/buyOrderDetailList");
            return baseResp;
        }
        // todo 流程阶段判断并调用
//        baseResp = operationService.confirmPrice(buyOrder);
        baseResp = buyerOrderService.confirmBuyOrder(buyOrder);
        return baseResp;
    }


    /**
     * 采购商 查看采购交易商品列表 分页
     *
     * @param tradeOrder
     * @param page
     * @return
     */
    @RequestMapping(value = "tradeGoods/fetchList")
    @ResponseBody
    public BaseResp tradeGoodsList(TradeOrder tradeOrder, Page page) {
        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isEmpty(tradeOrder.getBuyerId())) {
            baseResp.setMsg("缺少参数buyerId");
            return baseResp;
        }
        // 分页
        int pageNo = page.getPageNo() <= 1 ? 1 : page.getPageNo();
        int pageSize = page.getPageSize() <= 10 ? 10 : page.getPageSize();
        page = new Page<>(pageNo, pageSize);
        // 配送状态 (不需要限制时间)
        String status = tradeOrder.getBuyerStatus();
        if (StringUtils.isEmpty(status)) {
            // 默认 状态为待验货
            tradeOrder.setBuyerStatus(IPdsConstant.BuyerDealStatus.wait_check.getCode());
        }
        baseResp = buyerOrderService.tradeGoodsList(tradeOrder, page);
        return baseResp;
    }


    /**
     * 采购商 商品确认收货
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "tradeGoods/confirm")
    @ResponseBody
    public BaseResp confirmTradeGoods(@RequestBody BuyOrder buyOrder) {

        BaseResp baseResp = BaseResp.newError();

        //验证参数
        if (CollectionUtils.isEmpty(buyOrder.getBuyOrderDetailList()) || StringUtils.isEmpty(buyOrder.getBuyerId())) {
            baseResp.setMsg("缺少参数buyOrderDetailList/buyerId");
            return baseResp;
        }
        // 按商品分别确认收货
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
        // 若传入buyId
        if (!StringUtils.isEmpty(buyOrder.getBuyId())) {
            buyId = buyOrder.getBuyId();
        }
        if (successNum > 0) {
            baseResp.success();
            baseResp.setExt(successNum);
            BuyOrder order = buyOrderService.fetchByBuyId(buyId);
            // 推送消息
            if (null != order) {
                UserOpenPlatform userOpenPlatform = pdsCommonServiceImpl.fetchOpenUserByUid(order.getBuyerUid(), IPdsConstant.WX_MP_APPID, order.getBuyerId(), IPdsConstant.CompanyType.buyer);
                if (null != userOpenPlatform) {
                    wxMpMessageService.orderDealCloseNotify(userOpenPlatform, order);
                }
            }
        } else {
            baseResp.setMsg("收货操作失败");
        }
        return baseResp;
    }

    /**
     * 按采购商商家 统计 采购单
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
        // 采购单状态 默认查已下单的
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
            baseResp.setMsg("请求参数有误");
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
     * 按采购商商家 统计 采购单明细
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
        // 采购单状态 默认查已下单的
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
     * 确认收货
     *
     * @param buyOrder
     * @return
     */
    @RequestMapping(value = "confirmAllGoods")
    @ResponseBody
    public BaseResp confirmAllGoods(BuyOrder buyOrder) {
        BaseResp baseResp = BaseResp.newError();
        if (StringUtils.isAnyEmpty(buyOrder.getBuyId(), buyOrder.getBuyerId())) {
            baseResp.setMsg("参数有误,必须buyId/buyerId");
            return baseResp;
        }
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setBuyId(buyOrder.getBuyId());
        tradeOrder.setBuyerId(buyOrder.getBuyerId());
        baseResp = buyerOrderService.confirmAllGoods(tradeOrder);
        return baseResp;
    }
}
