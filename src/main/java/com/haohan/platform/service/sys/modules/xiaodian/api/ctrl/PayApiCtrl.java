package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankTransResult;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BSCPayResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.PayResultNotifyResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.impl.XmBankPayServiceImpl;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.service.impl.MshPayServiceImpl;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderPayMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer.IProducer;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ReqPayCancel;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ReqPayRefund;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ReqPaySend;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.params.PayNotifyTaskDto;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IMerchantApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.CommonApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.GoodsOrderApiService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.partner.PartnerApp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.partner.PartnerAppService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.PayNotifyService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.RefundManageService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant.PayStatus;


/**
 * 支付API接口
 * Created by zgw on 2017/12/10.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/pay")
public class PayApiCtrl extends BaseController {

    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private MerchantService merchantService;
    @Resource
    private IMerchantApiService imerchantApiService;
    @Autowired
    private RefundManageService refundManageService;
    @Autowired
    private PayNotifyService payNotifyService;
    @Autowired
    private XmBankPayServiceImpl xmBankPayService;
    @Autowired
    private CommonApiService commonApiService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private MshPayServiceImpl mshPayService;
    @Autowired
    private PartnerAppService partnerAppService;
    @Autowired
    private GoodsOrderApiService goodsOrderApiService;
    @Autowired
    private IProducer producer;

    @RequestMapping(value = "send")
    @ResponseBody
    public String send(HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            //获取参数并转化成对应的Bean
            ReqPaySend payReq = commonApiService.fetchByHttpParams(request, ReqPaySend.class, resp);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            if (null == payReq) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }

            PartnerApp partnerApp = payReq.getPartnerApp();
            secret = partnerApp.getAppSecret();

            //业务处理
            OrderPayRecord payRecord = new OrderPayRecord();

            //验证商户及账户信息
            resp = commonApiService.validMerchantInfo(payReq.getPartnerId(), resp, payRecord);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //值拷贝
            BeanUtils.copyProperties(payReq,payRecord);
            payRecord.setOrderInfo(JacksonUtils.toJson(payReq.getOrderInfo()));
            if(StringUtils.isEmpty(payReq.getGoodsName())){
            payRecord.setGoodsName(payReq.getOrderDesc());
            }
            payRecord.setPartnerNum(partnerApp.getPartnerNum());
            payRecord.setOrderId(payReq.getOrderId());
            payRecord.setOpenid(payReq.getOpenId());
            if(StringUtils.isNotEmpty(payReq.getOrderAmount())) {
                payRecord.setOrderAmount(new BigDecimal(payReq.getOrderAmount()));
            }
            if (null != payReq.getOrderDetail()){
                payRecord.setOrderDetail(JacksonUtils.toJson(payReq.getOrderDetail()));
            }
            payRecord.setDeviceId(payReq.getDeviceId());
            String orderId = payRecord.getOrderId();

            //验证已付款的订单信息,避免重复付款
            if (StringUtils.isNotEmpty(orderId)){
                PayNotify payNotify = payNotifyService.fetchByOrderId(orderId);
                if (null != payNotify && IBankServiceConstant.TransStatus.SUCCESS.getCode().equals(payNotify.getRespCode())){
                    resp.error();
                    resp.setMsg("订单已支付,请勿重复支付");
                    respBody = resp.toJson();
                    return resp.toJson();
                }
            }

            //完成业务处理
            Map<String, Object> requiredParams = new HashMap<>();
            String shopId = payRecord.getShopId();
            String orderType = payRecord.getOrderType();
            String clientIp = IpUtils.getRemoteIpAddr(request);
            String payChannel = payRecord.getPayChannel();
            String payType = payRecord.getPayType();
            String goodsName = payRecord.getGoodsName();
            String partnerId = payRecord.getPartnerId();
            BigDecimal orderAmount = payRecord.getOrderAmount();
            //非必要参数
            String orderInfo = payRecord.getOrderInfo();
            //HTML处理
            payRecord.setOrderInfo(HtmlUtils.htmlUnescape(orderInfo));

            String authCode = payRecord.getAuthCode();
            String openId = payRecord.getOpenid();
            String appId = payReq.getAppId();

            requiredParams.put("shopId", shopId);
            requiredParams.put("partnerId", partnerId);
            requiredParams.put("orderId", orderId);
            requiredParams.put("orderType", orderType);
            requiredParams.put("payChannel", payChannel);
            requiredParams.put("payType", payType);
            requiredParams.put("goodsName", goodsName);
            requiredParams.put("orderAmount", orderAmount);
            requiredParams.put("orderDesc", payRecord.getGoodsName());

            BankServiceType serviceType = BankServiceType.valueOfServiceType(payType);
            if (BankServiceType.AliAuthPay == serviceType || BankServiceType.WexinAuthPay == serviceType) {
                requiredParams.put("authCode", authCode);
            }
            if (BankServiceType.WexinMpPay == serviceType || BankServiceType.AliServicePay == serviceType || BankServiceType.WexinAppPay == serviceType) {
                requiredParams.put("openId", openId);
                requiredParams.put("appId", appId);
            }

            //将前端请求金额分变更为元
            payRecord.setOrderAmount(CommonUtils.amountPointTransYuan(orderAmount));

            //错误处理
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            Shop shop = shopService.get(shopId);
            if (null == shop) {
                shop = shopService.fetchByShopCode(shopId);
            }
            //填充参数
            if (null != shop) {
                String shopName = shop.getName();
                payRecord.setShopId(shop.getId());
                payRecord.setShopName(shopName);
            }


            String requestId = CommonUtils.genId(payType);

            payRecord.setClientIp(clientIp);
            payRecord.setRequestId(requestId);
            payRecord.setLimitPay("2");//默认支持信用卡
            payRecord.setOrderTime(new Date());
            payRecord.setAppid(appId);
            payRecord.setPayStatus(PayStatus.NOPAY.getCode());
            String notifyUrl = "http://" + request.getServerName().concat(request.getRequestURI().replace("send", "notify"));
            payRecord.setNotifyUrl(notifyUrl);


            resp = imerchantApiService.pay(payRecord);

            //订单加入超时关闭消息队列
            OrderMsgEntity orderMsgEntity = new OrderMsgEntity();
            orderMsgEntity.setOrderId(orderId);
            orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.ORDER_CLOSE.getTagName());
            producer.sendDelay(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity,9);

            if (resp.isSuccess()) {
                OrderPayRecord record = (OrderPayRecord) resp.getExt();
                respBody = record.getPayInfo();

                // 对订单明细不作处理

//                //保存订单数据到商品订单表
//                commonApiService.copyDataToGoodsOrder(record);
//                //商品详情转换
//                transToGoodsDetail(payReq.getOrderDetail(),payRecord);

                //加入缓存待回调后通知合作渠道
                JedisUtils.set(orderId, partnerApp.getAppKey(), 86400);
                //被扫支付推送支付结果
                if (StringUtils.equals(payRecord.getPayType(), BankServiceType.WexinAuthPay.getCode())
                        || StringUtils.equals(payRecord.getPayType(), BankServiceType.AliAuthPay.getCode())){
                    BSCPayResponse bscPayResponse = JacksonUtils.readValue(record.getPayInfo(),BSCPayResponse.class);
                    if (StringUtils.equals(bscPayResponse.getTransResult(),XmBankTransResult.SUCCESS.getResponseCode())){
                        OrderMsgEntity mqEntity = new OrderMsgEntity();
                        mqEntity.setOrderId(orderId);
                        mqEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                        producer.send(IRocketMqConstant.TopicType.ORDER.getName(),mqEntity);
                        goodsOrderService.updatePayStatus(orderId, PayStatus.SUCCESS);
                    } else if (StringUtils.equals(bscPayResponse.getTransResult(),XmBankTransResult.UNKNOEW.getResponseCode())){
                        //bsc超过一定限额时无法自动扣款,需轮询交易状态
                        OrderPayMsgEntity orderPayMsgEntity = new OrderPayMsgEntity();
                        orderMsgEntity.setMqTags(IRocketMqConstant.PayMsgTag.UNKNOW_PAY_STATUS.getTagName());
                        orderPayMsgEntity.setRetryTimes(0);
                        producer.send(IRocketMqConstant.TopicType.PAY.getName(),orderPayMsgEntity);
                    }
                }
                if (StringUtils.equals(payRecord.getPayType(), BankServiceType.CashPay.getCode())){
                    orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                    producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                }
            } else {
                //错误信息直接返回
                OrderPayRecord record = (OrderPayRecord) resp.getExt();
                respBody = record.getRespDesc();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.error();
            logger.error("pay-sendPayReq-error\n{}", e.getMessage());
        } finally {
            request.setAttribute("respBody", respBody);
          return  commonApiService.respJson(resp,secret,respBody,request);
        }
    }


    @ResponseBody
    @RequestMapping(value = "notify")
    public String notify(HttpServletRequest request) {
        PayNotify payNotify = new PayNotify();
        BaseResp baseResp = BaseResp.newError();

        try {
            Map<String, Object> respMap = getRequestParameters(request);

            //TODO 验证签名
            String sign = (String) respMap.get("sign");

            String body = (String) respMap.get("body");

            PayResultNotifyResponse resp = JacksonUtils.readValue(body, PayResultNotifyResponse.class);
            payNotify.setRequestId(resp.getReqId());
            payNotify.setOrderId(resp.getOrderId());
            payNotify.setOrderTime(DateUtils.parseDateTime(resp.getTransTime()));
            payNotify.setPayAmount(CommonUtils.amountPointTransYuan(resp.getTotalAmount()));
            payNotify.setPayTime(DateUtils.parseDateTime(resp.getTransTime()));
            payNotify.setFee(CommonUtils.amountPointTransYuan(resp.getTransAmount()));
            payNotify.setRemarks(body);
            payNotify.setTransId(resp.getTransId());
            payNotify.setRespCode(resp.getTransResult());
            payNotify.setRespDesc(resp.getTransResult());
            payNotifyService.save(payNotify);

            String orderId = resp.getOrderId();
            OrderPayRecord record = orderPayRecordService.fetchByOrderId(orderId);
//            GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
            PartnerApp partnerApp = partnerAppService.fetchByPartnerNum(record.getPartnerNum());

            PayNotifyTaskDto payNotifyTaskDto = (PayNotifyTaskDto) JedisUtils.getObject("payNotifyTask"+orderId);
            if (null == payNotifyTaskDto){
                payNotifyTaskDto = new PayNotifyTaskDto();
            }

            //发送支付结果给渠道商
            if (!payNotifyTaskDto.getNotifyPartner()) {
                if (StringUtils.isNotEmpty(partnerApp.getNotifyUrl())|| StringUtils.isNotEmpty(record.getPartnerNotifyUrl())){
                    OrderPayMsgEntity mqEntity = new OrderPayMsgEntity();
                    mqEntity.setOrderId(orderId);
                    mqEntity.setMqTags(IRocketMqConstant.PayMsgTag.NOTIFY_PARTNER.getTagName());
                    mqEntity.setRetryTimes(0);
                    mqEntity.setTransType(XmBankEnums.TransType.consume);
                    producer.send(IRocketMqConstant.TopicType.PAY.getName(),mqEntity);
                    payNotifyTaskDto.setNotifyPartner(true);
                }
                JedisUtils.setObject("payNotifyTask"+orderId,payNotifyTaskDto,7200);
            }

            if (XmBankTransResult.SUCCESS.getResponseCode().equals(resp.getTransResult())) {
                record = orderPayRecordService.fetchByOrderId(orderId);
                if (null != record) {
                    record.setPayStatus(PayStatus.SUCCESS.getCode());
                    orderPayRecordService.save(record);

                    //添加消息至商家和用户通知消息队列
                    if (!payNotifyTaskDto.getPaySuccess()){
                        OrderMsgEntity mqEntity = new OrderMsgEntity();
                        mqEntity.setOrderId(orderId);
                        mqEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                        producer.send(IRocketMqConstant.TopicType.ORDER.getName(),mqEntity);
                        payNotifyTaskDto.setPaySuccess(true);
                        JedisUtils.setObject("payNotifyTask"+orderId,payNotifyTaskDto,7200);
                    }
                    payNotify.setStatus("SUCCESS");

                    //更新商品订单支付状态
//                    goodsOrderApiService.paySuccess(record,goodsOrder);

                    baseResp.putStatus(RespStatus.SUCCESS);
                }
            }
            logger.debug("notifyMsg:\n{}", JacksonUtils.toJson(respMap));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            payNotifyService.save(payNotify);
        }
        return "SUCCESS";
    }


    @ResponseBody
    @RequestMapping(value = "cancel")
    public String cancel(HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            //JSON转Bean
            ReqPayCancel payCancel = commonApiService.fetchByHttpParams(request, ReqPayCancel.class, resp);

            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == payCancel) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }

            PartnerApp partnerApp = payCancel.getPartnerApp();
            secret = partnerApp.getAppSecret();

            //验证参数
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            String partnerId = payCancel.getPartnerId();
            String orderId = payCancel.getOrderId();
            String orgReqId = payCancel.getOrgReqId();
            String orgTransId = payCancel.getOrgTransId();
            String shopId = payCancel.getShopId();

            requiredParams.put("orderId", orderId);
            requiredParams.put("partnerId", partnerId);
            requiredParams.put("shopId", shopId);
            if (StringUtils.isEmpty(orgTransId)) {
                requiredParams.put("orgReqId", orgReqId);
            }
            if (StringUtils.isEmpty(orgReqId)) {
                requiredParams.put("orgTransId", orgTransId);
            }

            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //验证订单是否存在
            OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(orderId);
            if (null == payRecord || StringUtils.isEmpty(payRecord.getPartnerId())) {
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }
            //验证商户及账户信息
            resp = commonApiService.validMerchantInfo(partnerId, resp, payRecord);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //处理业务
            OrderCancel cancel = new OrderCancel();
            BeanUtils.copyProperties(payCancel,cancel);
            cancel.setMerchantId(payRecord.getMerchantId());
            cancel.setMerchantName(payRecord.getMerchantName());

            String requestId = CommonUtils.genId(payRecord.getPayType());
            cancel.setRequestId(requestId);

            //调用取消接口
//            orderCancelService.save(cancel);
            resp = imerchantApiService.orderCancel(cancel);

            if (resp.isSuccess()) {
                respBody = (String) resp.getExt();
            }
            respBody = (String) resp.getExt();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
         return  commonApiService.respJson(resp,secret,respBody,request);
        }
    }

    @ResponseBody
    @RequestMapping(value = "refund")
    public String refund(HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        String respBody = "";
        String secret = "";
        try {
            //JSON转Bean
            ReqPayRefund reqPayRefund = commonApiService.fetchByHttpParams(request, ReqPayRefund.class, resp);

            if (!resp.isSuccess()) {
                return resp.toJson();
            }
            if (null == reqPayRefund) {
                resp.putStatus(RespStatus.PARAMS_VALID_ERROR);
                return resp.toJson();
            }
            PartnerApp partnerApp =  reqPayRefund.getPartnerApp();
            secret = partnerApp.getAppSecret();
            OrderPayRecord orderPayRecord = null;

            //验证参数
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            String orderId = reqPayRefund.getOrderId();
            String partnerId = reqPayRefund.getPartnerId();
            String shopId = reqPayRefund.getShopId();
            BigDecimal refundAmount = reqPayRefund.getRefundAmount();
            String orgReqId = reqPayRefund.getOrgReqId();
            String orgTransId = reqPayRefund.getOrgTransId();

            //终端不传orgReqId和orgTransId
            if (IMerchantConstant.terminalPartnerNum.equals(partnerApp.getPartnerNum())){
                orderPayRecord = orderPayRecordService.fetchByOrderId(orderId);
                if (null != orderPayRecord){
                    orgReqId = orderPayRecord.getRequestId();
                    orgTransId = orderPayRecord.getTransId();
                    reqPayRefund.setOrgReqId(orgReqId);
                    reqPayRefund.setOrgTransId(orgTransId);
                }
            } else {
                if (StringUtils.isEmpty(orgTransId)) {
                    requiredParams.put("orgReqId", orgReqId);
                }
                if (StringUtils.isEmpty(orgReqId)) {
                    requiredParams.put("orgTransId", orgTransId);
                }
            }
            requiredParams.put("secret",secret);
            requiredParams.put("orderId",orderId);
            requiredParams.put("shopId",shopId);
            requiredParams.put("partnerId",partnerId);
            requiredParams.put("refundAmount",refundAmount);

            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //验证订单是否存在
            OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(orderId);
            if (null == payRecord || StringUtils.isEmpty(payRecord.getPartnerId())) {
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }
            //验证商户及账户信息
            resp = commonApiService.validMerchantInfo(partnerId, resp, payRecord);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            //填充参数
            RefundManage refundManage = new RefundManage();
            BeanUtils.copyProperties(reqPayRefund,refundManage);
            String requestId = CommonUtils.genId(payRecord.getPayType());
            refundManage.setRequestId(requestId);
            refundManage.setBusType(XmBankEnums.TransType.refund.getCode());
            refundManage.setMerchantId(payRecord.getMerchantId());
            refundManage.setMerchantName(merchantService.get(payRecord.getMerchantId()).getMerchantName());

            refundManage.setOrderAmount(payRecord.getOrderAmount());
            refundManage.setPayAmount(payRecord.getOrderAmount());
            refundManage.setRefundAmount(CommonUtils.amountPointTransYuan(refundAmount));

            //2.退款金额超过1000需要平台审核
            if (refundManage.getRefundAmount().toString().compareTo("100000") > 0) {
                    payRecord.setPayStatus(PayStatus.CHECK.getCode());
                    refundManage.setStatus(PayStatus.CHECK.getCode());
                    orderPayRecordService.save(payRecord);
                    refundManageService.save(refundManage);
                    resp.putStatus(RespStatus.SUCCESS);
                return resp.toJson();
            }

            refundManageService.save(refundManage);
            MerchantPayInfo payInfo = merchantPayInfoService.fetchByPartnerId(partnerId);
            if (StringUtils.equals(payRecord.getPayChannel(), BankServiceType.CashPay.getChannel())){
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDED.getCode());
                refundManageService.save(refundManage);
                resp.success();
            } else {
                if (StringUtils.equals(IMerchantConstant.BankChannel.XMBANK.getCode(),payInfo.getBankChannel())){
                    resp = xmBankPayService.refund(refundManage);
                }
                if (StringUtils.equals(IMerchantConstant.BankChannel.MSHPAY.getCode(),payInfo.getBankChannel())){
                    resp = mshPayService.orderRefund(refundManage);
                }
            }
            if (resp.isSuccess()) {
                //退款成功更新订单支付状态
                payRecord.setPayStatus(PayStatus.REFUNDED.getCode());
                //退款金额小于订单金额 部分退款
                if(refundAmount.compareTo(payRecord.getOrderAmount()) == -1){
                    payRecord.setPayStatus(PayStatus.REFUND_PART.getCode());
                }
                orderPayRecordService.save(payRecord);
                goodsOrderService.updatePayStatus(payRecord.getOrderId(), PayStatus.REFUNDED);
            }else {
                if (!StringUtils.equals(payRecord.getPayStatus(), PayStatus.REFUNDED.getCode())){
                    payRecord.setPayStatus(PayStatus.CHECK.getCode());
                    orderPayRecordService.save(payRecord);
                }
            }
            respBody = (String) resp.getExt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.setAttribute("respBody", respBody);
            return commonApiService.respJson(resp,secret,respBody,request);
        }
    }

    private void transToGoodsDetail(List<GoodsOrderDetail> detailList, OrderPayRecord payRecord){
        for(GoodsOrderDetail detail:detailList){
            detail.setOrderId(payRecord.getOrderId());
            goodsOrderDetailService.save(detail);
        }
    }

}
