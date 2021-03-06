package com.haohan.platform.service.sys.modules.xiaodian.api.service.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankTransResult;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BSCPayResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderPayMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer.IProducer;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req.OrderPayApiReq;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IMerchantApiService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AppPayRelation;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.PayNotify;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.AppPayRelationService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.PayNotifyService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenyu
 * @create 2019/1/16
 */
@Service
public class OrderPayApiService {
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private PayNotifyService payNotifyService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private IMerchantApiService merchantApiService;
    @Autowired
    private GoodsOrderApiService goodsOrderApiService;
    @Autowired
    private AppPayRelationService appPayRelationService;
    @Autowired
    private IProducer producer;


    public BaseResp sendPayReq(BaseResp baseResp, OrderPayApiReq orderPayApiReq, HttpServletRequest request){
        String orderId = orderPayApiReq.getOrderId();
        String appId = orderPayApiReq.getAppid();
        String authCode = orderPayApiReq.getAuthCode();
        String payType = orderPayApiReq.getPayType();
        BigDecimal orderAmount = orderPayApiReq.getOrderAmount();
        BankServiceType serviceType = BankServiceType.valueOfServiceType(payType);

        //????????????????????????/?????????
        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        PayNotify payNotify = payNotifyService.fetchByOrderId(orderId);
        if (null == goodsOrder){
            baseResp.error();
            baseResp.setMsg("????????????????????????");
            return baseResp;
        }
        if (StringUtils.equals(IBankServiceConstant.PayStatus.SUCCESS.getCode(),goodsOrder.getPayStatus())){
            baseResp.error();
            baseResp.setMsg("??????????????? ");
            return baseResp;
        }else if (null != payNotify && IBankServiceConstant.TransStatus.SUCCESS.getCode().equals(payNotify.getRespCode())){
            baseResp.error();
            baseResp.setMsg("???????????????");
            return baseResp;
        }

        //??????????????????
        String merchantId = goodsOrder.getMerchantId();
        Integer sucCode = IBankServiceConstant.BankRegStatus.SUCCESS.getCode();
        Merchant merchant = merchantService.get(merchantId);
        MerchantPayInfo payInfo = null;
        if (StringUtils.isBlank(appId)){
            payInfo = merchantPayInfoService.fetchByMerchantIdEnable(merchantId, null);
        }else {
            AppPayRelation appPayRelation = appPayRelationService.fetchByAppId(appId);
            if(null == appPayRelation){
                baseResp.setMsg("APP?????????????????????");
                return baseResp;
            }
            payInfo = merchantPayInfoService.fetchByPartnerId(appPayRelation.getPartnerId());
        }

        //??????????????????
        if (null == payInfo || !sucCode.equals(payInfo.getRegStatus())) {
            baseResp.setCode(900);
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        //??????????????????
        if (!sucCode.equals(merchant.getStatus())) {
            baseResp.setCode(900);
            baseResp.setMsg("???????????????");
            return baseResp;
        }

        if (BankServiceType.AliAuthPay == serviceType || BankServiceType.WexinAuthPay == serviceType) {
            String wxAuthCodeRegStr = "^1[0-5][0-9]{16}$";
            String aliAuthCodeRegStr = "^(2[5-9][0-9]*)|(30[0-9]*)$";
            Pattern pWxAuth = Pattern.compile(wxAuthCodeRegStr);
            Pattern pAliAuth = Pattern.compile(aliAuthCodeRegStr);
            Matcher mWxAuth = pWxAuth.matcher(authCode);
            Matcher mAliAuth = pAliAuth.matcher(authCode);

            if (mWxAuth.matches()){
                orderPayApiReq.setPayChannel("wechat");
                orderPayApiReq.setPayType(BankServiceType.WexinAuthPay.getCode());
            }else if (mAliAuth.matches()){
                orderPayApiReq.setPayChannel("alipay");
                orderPayApiReq.setPayType(BankServiceType.AliAuthPay.getCode());
            }else {
                baseResp.error();
                baseResp.setMsg("????????????????????????,????????????????????????????????????");
                return baseResp;
            }
        }

        //????????????
        OrderPayRecord orderPayRecord = new OrderPayRecord();
        BeanUtils.copyProperties(goodsOrder,orderPayRecord);
        BeanUtils.copyProperties(orderPayApiReq,orderPayRecord);
        orderPayRecord.setId(IdGen.uuid());
        orderPayRecord.setIsNewRecord(true);
        orderPayRecord.setAppid(goodsOrder.getAppid());

        String requestId = CommonUtils.genId(payType);
        String partnerId = payInfo.getPartnerId();
        String clientIp = IpUtils.getRemoteIpAddr(request);
        orderPayRecord.setPartnerId(partnerId);
        orderPayRecord.setClientIp(clientIp);
        orderPayRecord.setOpenid(orderPayApiReq.getOpenid());
        orderPayRecord.setRequestId(requestId);
        orderPayRecord.setLimitPay("2");//?????????????????????
        orderPayRecord.setOrderTime(new Date());
        orderPayRecord.setOrderType(goodsOrder.getOrderType());
        orderPayRecord.setGoodsName(goodsOrder.getOrderDesc());
        orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.NOPAY.getCode());
        orderPayRecord.setNotifyUrl(XmBankConfigUtil.getPayNotifyUrl());

        baseResp = merchantApiService.pay(orderPayRecord);

        //????????????????????????
        OrderMsgEntity orderMsgEntity = new OrderMsgEntity();
        orderMsgEntity.setOrderId(orderId);
        orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.ORDER_CLOSE.getTagName());
        producer.sendDelay(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity,9);

        OrderPayRecord respRecord = (OrderPayRecord) baseResp.getExt();
        goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
        if (baseResp.isSuccess()) {
            baseResp.setExt(respRecord.getPayInfo());
            //????????????????????????????????????
            if (orderPayRecord.getPayType().equals(BankServiceType.WexinAuthPay.getCode())
                    ||orderPayRecord.getPayType().equals(BankServiceType.AliAuthPay.getCode())){
                BSCPayResponse bscPayResponse = JacksonUtils.readValue(respRecord.getPayInfo(),BSCPayResponse.class);
                if (StringUtils.equals(bscPayResponse.getTransResult(),XmBankTransResult.SUCCESS.getResponseCode())){
                    goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
                    orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                    producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                } else if (StringUtils.equals(bscPayResponse.getTransResult(),XmBankTransResult.UNKNOEW.getResponseCode())){
                    //bsc???????????????????????????????????????,?????????????????????
                    OrderPayMsgEntity orderPayMsgEntity = new OrderPayMsgEntity();
                    orderMsgEntity.setMqTags(IRocketMqConstant.PayMsgTag.UNKNOW_PAY_STATUS.getTagName());
                    orderPayMsgEntity.setRetryTimes(0);
                    producer.send(IRocketMqConstant.TopicType.PAY.getName(),orderPayMsgEntity);
                }
            }
            //????????????
            if (BankServiceType.CashPay.getCode().equals(orderPayRecord.getPayType())){
                orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
                goodsOrderApiService.paySuccess(orderPayRecord,goodsOrder);
            }

            goodsOrder.setPayId(requestId);
            goodsOrder.setPayType(payType);
            goodsOrder.setOrderAmount(orderAmount);
            goodsOrder.setPayTime(new Date());
            goodsOrderService.save(goodsOrder);
        } else {
            baseResp.setExt(null);
        }
        return baseResp;
    }
}
