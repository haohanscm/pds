package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req.OrderPayApiReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req.OrderRefundApplyReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IMerchantApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.CommonApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.OrderPayApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.RefundOrderApiService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IDeliveryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryPlan;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryPlanService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.OrderDeliveryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/9/10
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/order/orderPay")
public class OrderPayApiCtrl extends BaseController {

    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Resource
    private IMerchantApiService merchantApiService;
    @Autowired
    private CommonApiService commonApiService;
    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private OrderDeliveryService orderDeliveryService;
    @Autowired
    private DeliveryPlanService deliveryPlanService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private RefundOrderApiService refundOrderApiService;
    @Autowired
    private OrderPayApiService orderPayApiService;

    /**
     * ????????????
     * @param orderPayApiReq
     * @param request
     * @return
     */
    @RequestMapping(value = "send")
    @ResponseBody
    public BaseResp send(@Validated OrderPayApiReq orderPayApiReq, BindingResult bindingResult, HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        try {
            //????????????
            String payType = orderPayApiReq.getPayType();
            String authCode = orderPayApiReq.getAuthCode();
            String openId = orderPayApiReq.getOpenid();

            BankServiceType serviceType = BankServiceType.valueOfServiceType(payType);
            if (BankServiceType.AliAuthPay == serviceType || BankServiceType.WexinAuthPay == serviceType && StringUtils.isBlank(authCode)) {
                baseResp.setMsg("authCode can not be null");
                return baseResp;
            }
            if (BankServiceType.WexinMpPay == serviceType || BankServiceType.AliServicePay == serviceType
                    || BankServiceType.WexinAppPay == serviceType && StringUtils.isBlank(openId)) {
                baseResp.setMsg("openId can not be null");
                return baseResp;
            }
            baseResp = orderPayApiService.sendPayReq(baseResp,orderPayApiReq,request);
        }catch (Exception e){
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("????????????");
        }finally {
            return baseResp;
        }
    }

    @RequestMapping(value = "refundApply")
    @ResponseBody
    public BaseResp refundApply(OrderRefundApplyReq orderRefundApplyReq, HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();

        try {
            String appid = orderRefundApplyReq.getAppid();
            String merchantId = orderRefundApplyReq.getMerchantId();
            String orderId = orderRefundApplyReq.getOrderId();
            String cause = orderRefundApplyReq.getCause();
            BigDecimal refundAmount = orderRefundApplyReq.getRefundAmount();

            Map<String,Object> requiredParams = new HashMap<>();
//            requiredParams.put("appid",appid);
            requiredParams.put("merchantId",merchantId);
            requiredParams.put("orderId",orderId);
            baseResp = paramsValid(requiredParams);
            if (!baseResp.isSuccess()){
                return baseResp;
            }

            List<GoodsOrderDetail> detailList = goodsOrderDetailService.findByOrderId(orderId);

            //??????????????????
            OrderPayRecord payRecord = new OrderPayRecord();
            payRecord.setOrderId(orderId);
            payRecord.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
            List<OrderPayRecord> orderPayRecordList = orderPayRecordService.findList(payRecord);
            if (CollectionUtils.isEmpty(orderPayRecordList)){
                baseResp.error();
                baseResp.setMsg("???????????????");
                return baseResp;
            }

            //??????????????????
            OrderPayRecord orderPayRecord = orderPayRecordList.get(0);
            String bankChannel= StringUtils.isEmpty(orderRefundApplyReq.getBankChannle())?
            IMerchantConstant.BankChannel.XMBANK.getCode():orderRefundApplyReq.getBankChannle();
            MerchantPayInfo payInfo = merchantPayInfoService.fetchByMerchantIdEnable(merchantId, bankChannel);
            commonApiService.validMerchantInfo(payInfo.getPartnerId(),baseResp,orderPayRecord);
            if (!baseResp.isSuccess()){
                return baseResp;
            }

            GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
            if (null == goodsOrder){
                baseResp.error();
                baseResp.setMsg("???????????????");
                return baseResp;
            }



            OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
            //???????????????????????????????????????
            detailList = detailList.stream().filter(detail ->
                    StringUtils.equals(IDeliveryConstant.DeliveryPlanType.cyclical.getCode(),detail.getDeliveryPlanType())).collect(Collectors.toCollection(ArrayList::new));
            if (null != orderDelivery && CollectionUtils.isNotEmpty(detailList)){
                //?????????????????????
                baseResp.error();
                baseResp.setMsg("???????????????????????????,???????????????");
                return baseResp;
            }else {
                //????????????????????????
                refundOrderApiService.formRefundRecord(orderRefundApplyReq,goodsOrder,orderPayRecordList.get(0));

                //????????????????????????
                goodsOrderService.save(goodsOrder);
                if (null != orderDelivery){
                    orderDelivery.setOrderStatus(IOrderServiceConstant.OrderStatus.REFUNDING.getCode());
                    orderDeliveryService.save(orderDelivery);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("????????????");
        }finally {
            return baseResp;
        }

    }

    /**
     * ??????????????????
     * @param request
     * @return
     */
    @RequestMapping(value = "refundCheck")
    @ResponseBody
    public BaseResp refundCheck(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();

        try {
            Map<String,Object> reqMap = getRequestParameters(request);
            String appid = (String) reqMap.get("appid");
            String merchantId = (String) reqMap.get("merchantId");
            String orderId = (String) reqMap.get("orderId");
            String operation = (String) reqMap.get("operation");    //????????????: 0 ???????????? 1 ????????????
            String refundAmount = (String) reqMap.get("refundAmount");

            Map<String,Object> requiredParams = new HashMap<>();
//            requiredParams.put("appid",appid);
            requiredParams.put("merchantId",merchantId);
            requiredParams.put("orderId",orderId);
            requiredParams.put("operation",operation);

            baseResp = paramsValid(requiredParams);
            if (!baseResp.isSuccess()){
                return baseResp;
            }

            if (IOrderServiceConstant.agreeOperation.equals(operation)){
                if (null == refundAmount){
                    baseResp.error();
                    baseResp.setMsg("??????????????????");
                    return baseResp;
                }
            }

            OrderPayRecord orderPayRecord = new OrderPayRecord();
            MerchantPayInfo payInfo = merchantPayInfoService.fetchByMerchantIdEnable(merchantId, IMerchantConstant.BankChannel.XMBANK.getCode());
            commonApiService.validMerchantInfo(payInfo.getPartnerId(),baseResp,orderPayRecord);
            if (!baseResp.isSuccess()){
                return baseResp;
            }

            GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
            if (null == goodsOrder){
                baseResp.error();
                baseResp.setMsg("???????????????");
                return baseResp;
            }

            //??????????????????
            if (IOrderServiceConstant.agreeOperation.equals(operation)){
                baseResp = merchantApiService.refundByOrderIdAndAmount(goodsOrder,refundAmount);
                if (baseResp.isSuccess()){
                    OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
                    if (StringUtils.equals(IDeliveryConstant.DeliveryType.home_delivery.getCode(),orderDelivery.getDeliveryType())){
                        //??????????????????,??????????????????????????????
                        DeliveryPlan deliveryPlan = new DeliveryPlan();
                        deliveryPlan.setOrderId(orderId);
                        deliveryPlan.setStatus(IDeliveryConstant.DeliveryStatus.wait.getCode());
                        List<DeliveryPlan> deliveryPlans = deliveryPlanService.findList(deliveryPlan);
                        for (DeliveryPlan plan : deliveryPlans){
                            plan.setStatus(IDeliveryConstant.DeliveryStatus.cancel.getCode());
                            deliveryPlanService.save(plan);
                        }
                    }

                }
            }

            //??????????????????
            if (IOrderServiceConstant.disAgreeOperation.equals(operation)){

            }

        }catch (Exception e){
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("????????????");
        }finally {
            return baseResp;
        }
    }


    /**
     * ??????????????????
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPayResult")
    @ResponseBody
    public BaseResp queryPayResult(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        String payStatus="";

        String orderId = request.getParameter("orderId");
        if (StringUtils.isEmpty(orderId)){
            baseResp.setMsg("?????????????????????");
            return baseResp;
        }

        //??????????????????????????????
        payStatus = JedisUtils.get("payStatus-".concat(orderId));

        //?????????????????????????????????
        if (StringUtils.isEmpty(payStatus)){
            OrderPayRecord payRecord;
            payRecord = orderPayRecordService.fetchByOrderId(orderId);
            if (null == payRecord){
                baseResp.setMsg("???????????????????????????");
                return baseResp;
            }
            payStatus = payRecord.getPayStatus();
            //??????????????????????????????
            if (StringUtils.isNotEmpty(payStatus)){
                JedisUtils.set("payStatus-".concat(orderId),payStatus,1800);
            }
        }
        baseResp.success();
        baseResp.setExt(payStatus);
        return baseResp;
    }

}
