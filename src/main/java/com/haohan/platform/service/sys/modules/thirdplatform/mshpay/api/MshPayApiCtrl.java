package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.api;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.DateUtils;
import com.haohan.framework.utils.IpUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.PayResultNotifyResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.constant.MshPayConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderPayMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer.IProducer;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.CommonApiService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.PayNotify;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.PayNotifyService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/7/26
 */
@Controller
@RequestMapping(value = "${frontPath}/openplatform/mshPay/api/pay")
public class MshPayApiCtrl extends BaseController {

    @Autowired
    private PayNotifyService payNotifyService;
    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CommonApiService commonApiService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private IProducer producer;




    @RequestMapping(value = "payNotify")
    @ResponseBody
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        PayNotify payNotify = new PayNotify();

        BaseResp baseResp = BaseResp.newError();
        try {
            Map<String,Object> reqMap = CommonUtils.getRequestParameters(request);
            String code = (String) reqMap.get("code");
            String msg = (String) reqMap.get("msg");
            String totalAmount = (String) reqMap.get("total_amt");
            String orderId = (String) reqMap.get("mchnt_order_no");
            String sign = (String) reqMap.get("sign");
            String mshOrderId = (String) reqMap.get("wwt_order_no");
            String randomStr = (String) reqMap.get("random_str");
            String orderType = (String) reqMap.get("order_type");

            //验签 TODO

            if (code.equals(MshPayConstant.SUCCESS.getCode().toString())){
                payNotify.setRespCode(IBankServiceConstant.TransStatus.SUCCESS.getCode());
            } else {
                payNotify.setRespCode(IBankServiceConstant.TransStatus.FAIL.getCode());
            }
            OrderPayRecord record = orderPayRecordService.fetchByOrderId(orderId);
            payNotify.setOrderId(orderId);
            payNotify.setOrderTime(record.getOrderTime());
            payNotify.setPayTime(record.getRespTime());
            payNotify.setRespDesc(msg);
            payNotify.setFee(new BigDecimal(totalAmount));
            payNotify.setPayAmount(new BigDecimal(totalAmount));
            payNotify.setRequestId(record.getRequestId());
            payNotify.setRemarks(JacksonUtils.toJson(reqMap));

            PayResultNotifyResponse resp = new PayResultNotifyResponse();
            resp.setOrderId(orderId);
            resp.setReqId(record.getRequestId());
            resp.setTransId(record.getRequestId());
            resp.setTotalAmount(CommonUtils.amountTransPoint(totalAmount));
            resp.setTransAmount(CommonUtils.amountTransPoint(payNotify.getPayAmount().toString()));
            resp.setTransResult(payNotify.getRespCode());
            resp.setAcquirerType(record.getPayChannel());
            resp.setTransTime(DateUtils.formatDate(record.getRespTime().getTime(),DateUtils.UNIX_TIME_FORMAT));

            //支付结果通知渠道商
            if (IMerchantConstant.terminalPartnerNum.equals(record.getPartnerNum())){
                if (!JedisUtils.exists("partnerNotify-".concat(record.getOrderId()))) {
                    OrderPayMsgEntity mqEntity = new OrderPayMsgEntity();
                    mqEntity.setOrderId(orderId);
                    mqEntity.setMqTags(IRocketMqConstant.PayMsgTag.NOTIFY_PARTNER.getTagName());
                    mqEntity.setRetryTimes(0);
                    mqEntity.setTransType(XmBankEnums.TransType.consume);
                    producer.send(IRocketMqConstant.TopicType.PAY.getName(),mqEntity);
                    JedisUtils.set("partnerNotify-".concat(record.getOrderId()),"success",120);
                }
            }

            if (MshPayConstant.SUCCESS.getCode().toString().equals(code)) {
                if (null != record) {
                    record.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
                    orderPayRecordService.save(record);

                    //支付成功通知
                    if (!JedisUtils.exists("payNotify-".concat(record.getOrderId()))){
                        OrderMsgEntity mqEntity = new OrderMsgEntity();
                        mqEntity.setOrderId(orderId);
                        mqEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                        producer.send(IRocketMqConstant.TopicType.ORDER.getName(),mqEntity);
                        JedisUtils.set("payNotify-".concat(record.getOrderId()),"success",120);
                    }

                    payNotify.setStatus("SUCCESS");
                    //更新商品订单支付状态
                    goodsOrderService.updatePayStatus(payNotify.getOrderId(), IBankServiceConstant.PayStatus.SUCCESS);
                    baseResp.putStatus(RespStatus.SUCCESS);
                }
            }
            logger.debug("\n--notifyMsg--\n{}", JacksonUtils.toJson(reqMap));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            payNotifyService.save(payNotify);
            return "success";
        }

    }

    @RequestMapping("staticPayNotify")
    @ResponseBody
    public String staticPayNotify(HttpServletRequest request,HttpServletResponse response){
        BaseResp resp = BaseResp.newError();

        try{
            Map<String,Object> reqMap = CommonUtils.getRequestParameters(request);
            String hf_order_no = (String) reqMap.get("hf_order_no");
            String partnerId = (String) reqMap.get("merchantId");
            String channel = (String) reqMap.get("channel");
            String total_fee = (String) reqMap.get("total_fee");
            String pay_time = (String) reqMap.get("pay_time");
            String service_fee = (String) reqMap.get("service_fee");
            String rate = (String) reqMap.get("rate");

            Map<String,Object> requiredParams = new HashMap<>();
            requiredParams.put("hf_order_no",hf_order_no);
            requiredParams.put("merchantId",partnerId);
            requiredParams.put("channel",channel);
            requiredParams.put("total_fee",total_fee);
            requiredParams.put("pay_time",pay_time);
            requiredParams.put("service_fee",service_fee);
            requiredParams.put("rate",rate);
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()){
                return "fail";
            }

            OrderPayRecord orderPayRecord = new OrderPayRecord();
            BigDecimal payRate = new BigDecimal(rate).divide(new BigDecimal(100));
            BigDecimal orderAmount = new BigDecimal(total_fee);

            orderPayRecord.setTransId(hf_order_no);
            List<OrderPayRecord> orderList = orderPayRecordService.findList(orderPayRecord);
            if (CollectionUtils.isNotEmpty(orderList)){
                return "success";
            }

            String shopId = "";
            String shopName = "";
            MerchantPayInfo payInfo = merchantPayInfoService.fetchByPartnerId(partnerId);
            if (null != payInfo){
                Shop shop = new Shop();
                shop.setMerchantId(payInfo.getMerchantId());
                shop.setShopLevel(IMerchantConstant.AccountAuthCode.parentAccount.getCode());
                shop.setStatus(IMerchantConstant.available);
                List<Shop> shopList = shopService.findList(shop);
                if (CollectionUtils.isNotEmpty(shopList)){
                    shop = shopList.get(0);
                    shopId = shop.getId();
                    shopName = shop.getName();
                }
            }

            String orderId = "m019".concat(DateUtils.getWsTime());
            String goodsName = payInfo.getMercNm().concat("静态码支付");
            orderPayRecord.setOrderId(orderId);
            orderPayRecord.setRequestId(CommonUtils.genId("19"));
            orderPayRecord.setTransId(hf_order_no);
            orderPayRecord.setPayChannel("1".equals(channel)?"wechat":"2".equals(channel)?"alipay":"4".equals(channel)?"jd":"");
            orderPayRecord.setPayType("1".equals(channel)?"01":"05");
            orderPayRecord.setMerchantId(payInfo.getMerchantId());
            orderPayRecord.setMerchantName(payInfo.getMercNm());
            orderPayRecord.setShopId(shopId);
            orderPayRecord.setShopName(shopName);
            orderPayRecord.setOrderType(IOrderServiceConstant.OrderType.face_pay.getCode());
            orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
            orderPayRecord.setPartnerId(partnerId);
            orderPayRecord.setOrderAmount(CommonUtils.amountPointTransYuan(total_fee));
            orderPayRecord.setRespTime(new Date());
            orderPayRecord.setOrderTime(com.haohan.platform.service.sys.common.utils.DateUtils.parseDateTime(pay_time));
            orderPayRecord.setPartnerNum("019");
            orderPayRecord.setGoodsName(goodsName);
            orderPayRecord.setClientIp(IpUtils.getRemoteIpAddr(request));
            orderPayRecord.setRate(payRate);
            orderPayRecord.setFee(CommonUtils.amountPointTransYuan(payRate.multiply(orderAmount)));
            orderPayRecord.setPayInfo(JacksonUtils.toJson(reqMap));

            orderPayRecordService.save(orderPayRecord);

            //创建商品订单
            GoodsOrderDetail goodsOrderDetail = new GoodsOrderDetail();
            goodsOrderDetail.setOrderId(orderId);
            if (CollectionUtils.isEmpty(goodsOrderDetailService.findList(goodsOrderDetail))){
                commonApiService.copyDataToGoodsOrder(orderPayRecord);
                goodsOrderDetail.setOrderId(orderId);
                goodsOrderDetail.setGoodsName(goodsName);
                goodsOrderDetail.setShopId(shopId);
                goodsOrderDetail.setMerchantId(payInfo.getMerchantId());
                goodsOrderDetail.setGoodsId("0");
                goodsOrderDetail.setGoodsNum(BigDecimal.ONE);
                goodsOrderDetail.setGoodsPrice(CommonUtils.amountPointTransYuan(orderAmount));
                goodsOrderDetailService.save(goodsOrderDetail);
            }

            //发送支付成功通知
            OrderMsgEntity mqEntity = new OrderMsgEntity();
            mqEntity.setOrderId(orderId);
            mqEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
            producer.send(IRocketMqConstant.TopicType.ORDER.getName(),mqEntity);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

}
