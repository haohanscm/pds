package com.haohan.platform.service.sys.modules.xiaodian.api.service.order;

import com.aliyun.mns.model.Message;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.common.IRocketMqConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity.OrderPayMsgEntity;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer.IProducer;
import com.haohan.platform.service.sys.modules.weixin.miniapp.service.WxAppMsgService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.AliyunMsgService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.CommonApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.PartnerNotifyService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IDeliveryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ShopServiceDistrict;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.ShopServiceDistrictService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.OrderDeliveryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderQueryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.PayNotifyService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.RefundManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.printer.FeiePrinterService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/9/11
 */
@Service
public class PayNotifyApiService   {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private CommonApiService commonApiService;
    @Autowired
    private WxAppMsgService wxAppMsgService;
    @Autowired
    private AliyunMsgService aliyunMsgService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private PartnerNotifyService partnerNotifyService;
    @Autowired
    private PayNotifyService payNotifyService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private FeiePrinterService feiePrinterService;
    @Autowired
    private GoodsOrderApiService goodsOrderApiService;
    @Autowired
    private OrderDeliveryService orderDeliveryService;
    @Autowired
    private ShopServiceDistrictService shopServiceDistrictService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private OrderDeliveryApiService orderDeliveryApiService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private IProducer producer;
    @Autowired
    private RefundManageService refundManageService;

    public void paySuccessNotify(String orderId) {
        OrderMsgEntity orderMsgEntity = new OrderMsgEntity();

        if (StringUtils.isNotEmpty(orderId)) {
            OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(orderId);
            GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
            String shopId = orderPayRecord.getShopId();

            //支付宝和微信支付渠道发送短信通知
            if (StringUtils.equals(BankServiceType.WexinAuthPay.getChannel(), orderPayRecord.getPayChannel())
                    || StringUtils.equals(BankServiceType.AliAuthPay.getChannel(), orderPayRecord.getPayChannel())) {
                //发送通知消息给用户
                wxAppMsgService.sendPayMessage(orderPayRecord);
                //发支付通知给商户
                aliyunMsgService.sendMsgToMerchant(orderPayRecord);
            }

            //商品订单为空不作处理
            if(null == goodsOrder){
                return;
            }
            //创建配送订单
            String expressCode = IDeliveryConstant.DeliveryType.express.getCode();
            String homeDeliveryCode = IDeliveryConstant.DeliveryType.home_delivery.getCode();
            String deliveryType = goodsOrder.getDeliveryType();
            if (expressCode.equals(deliveryType) || homeDeliveryCode.equals(deliveryType)){
                orderDeliveryApiService.genDeliveryOrder(goodsOrder, orderPayRecord);
                if (homeDeliveryCode.equals(deliveryType)) {
                    //放入计划生成队列待消费
                    orderMsgEntity.setOrderId(orderId);
                    orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PLAN_GENERATE.getTagName());
                    producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                    logger.debug("produced one message : [{}]",orderId);
                }
            }

            //订单分配
            if (StringUtils.isEmpty(goodsOrder.getShopId())) {
                //是否开启自动分单
                Merchant merchant = merchantService.get(goodsOrder.getMerchantId());
                String yesEnumCode = ICommonConstant.YesNoType.yes.getCode();
                if (StringUtils.equals(yesEnumCode,merchant.getIsAutomaticOrder())){
                    //已开启自动接单
                    orderMsgEntity.setMqTags("dispatchOrder");
                    producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                } else {
                    //关闭自动接单,订单状态变更为待接单,不设置店铺,只推送消息至总店
                    goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_ACCEPT.getCode());
                    goodsOrderService.save(goodsOrder);
                }
            } else {
                //极光推送
                commonApiService.sendJgMsg(goodsOrder, shopId);
                //惠付静态码支付不打印小票
                if (StringUtils.equals("019", orderPayRecord.getPartnerNum())) {
                    return;
                }
                //打印小票
                BaseResp resp = feiePrinterService.printByShopId(orderPayRecord.getShopId(), feiePrinterService.fetchPrintOrderById(orderId));
                logger.debug("飞鹅打印: " + JacksonUtils.toJson(resp));
            }
        }
    }

    public boolean closeOrder(String orderId) {
        if (StringUtils.isNotEmpty(orderId)) {
            goodsOrderApiService.orderClose(orderId);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 支付结果通知渠道商
     * @param mqEntity
     */
    public void notifyPartner(OrderPayMsgEntity mqEntity) {
            String orderId = mqEntity.getOrderId();
            int retry = mqEntity.getRetryTimes();
        String reqBody = "";
        switch (mqEntity.getTransType()){
            //消费
            case consume:{
                PayNotify payNotify = payNotifyService.fetchByOrderId(orderId);
                 reqBody = payNotify.getRemarks();
            }
            //退款
            case refund: {
             RefundManage refundManage = refundManageService.fetchByOrderId(orderId);
             if(null != refundManage){
                 reqBody= JacksonUtils.toJson(refundManage);
             }
            }

        }
        //通知信息为空不作处理
        if(StringUtils.isEmpty(reqBody) || StringUtils.isEmpty(orderId)){
            logger.debug("notifyPartner--通知信息为空:"+JacksonUtils.toJson(mqEntity));
             return ;
        }
        //发送支付结果给渠道商
        BaseResp   resp = partnerNotifyService.notifyPartner(reqBody,orderId,mqEntity.getTransType());
            //通知渠道商不成功,消息重新加入队列
            if (!resp.isSuccess()) {
                retry += 1;
                logger.debug("callPartner-times:{}"+retry,orderId);
                mqEntity.setRetryTimes(retry);
                if (retry <= 6) {   //最多回调6次
                    if (retry > 3) {   //回调3次后,每30秒回调一次
                        producer.sendDelay(IRocketMqConstant.TopicType.PAY.getName(),mqEntity,4);
                    }else {
                        producer.sendDelay(IRocketMqConstant.TopicType.PAY.getName(),mqEntity,1);
                    }
                }
            }
    }

    /**
     * 查询bsc交易结果
     * @param msgEntity
     */
    public void checkPayResult(OrderPayMsgEntity msgEntity) {
        String orderId = msgEntity.getOrderId();
        int retry = msgEntity.getRetryTimes();

        XmBankEnums.TransType transType = XmBankEnums.TransType.consume;
        //查询银行接口
        OrderQuery query = orderQueryService.checkPayStatus(orderId, transType);
        //查询到未付款,继续查询,每5秒查询一次
        if (!IBankServiceConstant.TransStatus.SUCCESS.getCode().equals(query.getPayResult())) {
            retry += 1;
            if (retry <= 6) {
                msgEntity.setRetryTimes(retry);
                msgEntity.setMqTags(IRocketMqConstant.PayMsgTag.UNKNOW_PAY_STATUS.getTagName());
                producer.sendDelay(IRocketMqConstant.TopicType.PAY.getName(),msgEntity,2);
            }
        } else {
            //查询到已付款,往支付成功处理队列新增一条消息
            OrderMsgEntity orderMsgEntity = new OrderMsgEntity();
            orderMsgEntity.setOrderId(orderId);
            orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
            producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
        }

    }

    /**
     * 订单根据小区或者地址名称分配店铺
     * @param message
     */
    public void dispatchOrder(Message message) {
        String orderId = message.getMessageBody();
        String matchShopId = "";
        if (StringUtils.isNotEmpty(orderId)) {
            GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
            OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(orderId);
            OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
            OrderPayParamsExt payParamsExt = goodsOrder.fromOrderInfo();
            String merchantId = goodsOrder.getMerchantId();

            //1 先根据店铺设置分配
            Shop shop = null;
            ShopServiceDistrict shopServiceDistrict = new ShopServiceDistrict();
            shopServiceDistrict.setMerchantId(merchantId);
            shopServiceDistrict.setCommunityId(orderDelivery.getCommunityId());
            List<ShopServiceDistrict> list = shopServiceDistrictService.findList(shopServiceDistrict);
            if (CollectionUtils.isNotEmpty(list)) {
                matchShopId = list.get(0).getShopId();
                shop = shopService.get(matchShopId);
            } else {
                //2 根据地址匹配
                matchShopId = matchAddress(list, payParamsExt.getAddress());
                //3 地址匹配不到,分配给总店
                if (StringUtils.isEmpty(matchShopId)) {
                    shop = new Shop();
                    shop.setMerchantId(merchantId);
                    shop.setShopLevel(IMerchantConstant.AccountAuthCode.parentAccount.getCode());
                    shop = shopService.findList(shop).get(0);
                }
            }

            orderPayRecord.setShopId(shop.getId());
            orderPayRecord.setShopName(shop.getName());
            goodsOrder.setShopId(shop.getId());
            goodsOrder.setShopName(shop.getName());
            if (null != orderDelivery) {
                orderDelivery.setShopId(shop.getId());
                orderDeliveryService.save(orderDelivery);
            }
            orderPayRecordService.save(orderPayRecord);
            goodsOrderService.save(goodsOrder);
            commonApiService.sendJgMsg(goodsOrder, shop.getId());
            BaseResp resp = feiePrinterService.printByShopId(shop.getId(), feiePrinterService.fetchPrintOrderById(orderId));
            logger.debug("飞鹅打印: " + JacksonUtils.toJson(resp));
        }
    }


    /**
     * 生成配送计划
     */
    public void createDeliveryPlan(String orderId) {
        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
        //判断订单是否已被接单
        if (StringUtils.equals(IOrderServiceConstant.OrderStatus.WAITE_ACCEPT.getCode(),goodsOrder.getOrderStatus())){
            OrderMsgEntity mqEntity = new OrderMsgEntity();
            mqEntity.setOrderId(orderId);
            mqEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PLAN_GENERATE.getTagName());
            producer.send(IRocketMqConstant.TopicType.ORDER.getName(),mqEntity);
            return;
        }
        if (!StringUtils.equals(IDeliveryConstant.PlanGenStatus.not_gen.getCode(),orderDelivery.getPlanGenStatus())){
            return;
        }
        if (StringUtils.equals(IOrderServiceConstant.OrderStatus.WAITE_SHIP.getCode(),goodsOrder.getOrderStatus())){
            //验证订单状态
            List<GoodsOrderDetail> details = goodsOrderDetailService.findByOrderId(orderId);
            orderDelivery.setPlanGenStatus(IDeliveryConstant.PlanGenStatus.doing.getCode());
            orderDeliveryService.save(orderDelivery);
            orderDeliveryApiService.formDeliveryPlan(goodsOrder,details,orderDelivery,true);
        }
    }

    /**
     * 地址按照中文名称匹配
     *
     * @param list
     * @param address
     * @return
     */
    private String matchAddress(List<ShopServiceDistrict> list, String address) {
        String keyAry[] = address.split("-");
        String shopId = "";

        //去除二级和三级地区中的"市","区","县"
        keyAry[1] = keyAry[1].replace("市", "");
        keyAry[2] = keyAry[2].replace("区", "").replace("县", "");

        for (int i = 0; i < 3; i++) {
            String temp = keyAry[i];
            list = list.stream().filter(s ->
                    s.getDetailAddressName().indexOf(temp) != -1).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(list)) {
            return shopId;
        }
        //匹配到多个店铺
        if (list.size() >= 1) {
            shopId = list.get(0).getShopId();
        }
        return shopId;
    }
}
