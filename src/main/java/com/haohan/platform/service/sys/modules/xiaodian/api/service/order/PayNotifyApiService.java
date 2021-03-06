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

            //????????????????????????????????????????????????
            if (StringUtils.equals(BankServiceType.WexinAuthPay.getChannel(), orderPayRecord.getPayChannel())
                    || StringUtils.equals(BankServiceType.AliAuthPay.getChannel(), orderPayRecord.getPayChannel())) {
                //???????????????????????????
                wxAppMsgService.sendPayMessage(orderPayRecord);
                //????????????????????????
                aliyunMsgService.sendMsgToMerchant(orderPayRecord);
            }

            //??????????????????????????????
            if(null == goodsOrder){
                return;
            }
            //??????????????????
            String expressCode = IDeliveryConstant.DeliveryType.express.getCode();
            String homeDeliveryCode = IDeliveryConstant.DeliveryType.home_delivery.getCode();
            String deliveryType = goodsOrder.getDeliveryType();
            if (expressCode.equals(deliveryType) || homeDeliveryCode.equals(deliveryType)){
                orderDeliveryApiService.genDeliveryOrder(goodsOrder, orderPayRecord);
                if (homeDeliveryCode.equals(deliveryType)) {
                    //?????????????????????????????????
                    orderMsgEntity.setOrderId(orderId);
                    orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PLAN_GENERATE.getTagName());
                    producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                    logger.debug("produced one message : [{}]",orderId);
                }
            }

            //????????????
            if (StringUtils.isEmpty(goodsOrder.getShopId())) {
                //????????????????????????
                Merchant merchant = merchantService.get(goodsOrder.getMerchantId());
                String yesEnumCode = ICommonConstant.YesNoType.yes.getCode();
                if (StringUtils.equals(yesEnumCode,merchant.getIsAutomaticOrder())){
                    //?????????????????????
                    orderMsgEntity.setMqTags("dispatchOrder");
                    producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
                } else {
                    //??????????????????,??????????????????????????????,???????????????,????????????????????????
                    goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_ACCEPT.getCode());
                    goodsOrderService.save(goodsOrder);
                }
            } else {
                //????????????
                commonApiService.sendJgMsg(goodsOrder, shopId);
                //????????????????????????????????????
                if (StringUtils.equals("019", orderPayRecord.getPartnerNum())) {
                    return;
                }
                //????????????
                BaseResp resp = feiePrinterService.printByShopId(orderPayRecord.getShopId(), feiePrinterService.fetchPrintOrderById(orderId));
                logger.debug("????????????: " + JacksonUtils.toJson(resp));
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
     * ???????????????????????????
     * @param mqEntity
     */
    public void notifyPartner(OrderPayMsgEntity mqEntity) {
            String orderId = mqEntity.getOrderId();
            int retry = mqEntity.getRetryTimes();
        String reqBody = "";
        switch (mqEntity.getTransType()){
            //??????
            case consume:{
                PayNotify payNotify = payNotifyService.fetchByOrderId(orderId);
                 reqBody = payNotify.getRemarks();
            }
            //??????
            case refund: {
             RefundManage refundManage = refundManageService.fetchByOrderId(orderId);
             if(null != refundManage){
                 reqBody= JacksonUtils.toJson(refundManage);
             }
            }

        }
        //??????????????????????????????
        if(StringUtils.isEmpty(reqBody) || StringUtils.isEmpty(orderId)){
            logger.debug("notifyPartner--??????????????????:"+JacksonUtils.toJson(mqEntity));
             return ;
        }
        //??????????????????????????????
        BaseResp   resp = partnerNotifyService.notifyPartner(reqBody,orderId,mqEntity.getTransType());
            //????????????????????????,????????????????????????
            if (!resp.isSuccess()) {
                retry += 1;
                logger.debug("callPartner-times:{}"+retry,orderId);
                mqEntity.setRetryTimes(retry);
                if (retry <= 6) {   //????????????6???
                    if (retry > 3) {   //??????3??????,???30???????????????
                        producer.sendDelay(IRocketMqConstant.TopicType.PAY.getName(),mqEntity,4);
                    }else {
                        producer.sendDelay(IRocketMqConstant.TopicType.PAY.getName(),mqEntity,1);
                    }
                }
            }
    }

    /**
     * ??????bsc????????????
     * @param msgEntity
     */
    public void checkPayResult(OrderPayMsgEntity msgEntity) {
        String orderId = msgEntity.getOrderId();
        int retry = msgEntity.getRetryTimes();

        XmBankEnums.TransType transType = XmBankEnums.TransType.consume;
        //??????????????????
        OrderQuery query = orderQueryService.checkPayStatus(orderId, transType);
        //??????????????????,????????????,???5???????????????
        if (!IBankServiceConstant.TransStatus.SUCCESS.getCode().equals(query.getPayResult())) {
            retry += 1;
            if (retry <= 6) {
                msgEntity.setRetryTimes(retry);
                msgEntity.setMqTags(IRocketMqConstant.PayMsgTag.UNKNOW_PAY_STATUS.getTagName());
                producer.sendDelay(IRocketMqConstant.TopicType.PAY.getName(),msgEntity,2);
            }
        } else {
            //??????????????????,?????????????????????????????????????????????
            OrderMsgEntity orderMsgEntity = new OrderMsgEntity();
            orderMsgEntity.setOrderId(orderId);
            orderMsgEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
            producer.send(IRocketMqConstant.TopicType.ORDER.getName(),orderMsgEntity);
        }

    }

    /**
     * ????????????????????????????????????????????????
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

            //1 ???????????????????????????
            Shop shop = null;
            ShopServiceDistrict shopServiceDistrict = new ShopServiceDistrict();
            shopServiceDistrict.setMerchantId(merchantId);
            shopServiceDistrict.setCommunityId(orderDelivery.getCommunityId());
            List<ShopServiceDistrict> list = shopServiceDistrictService.findList(shopServiceDistrict);
            if (CollectionUtils.isNotEmpty(list)) {
                matchShopId = list.get(0).getShopId();
                shop = shopService.get(matchShopId);
            } else {
                //2 ??????????????????
                matchShopId = matchAddress(list, payParamsExt.getAddress());
                //3 ??????????????????,???????????????
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
            logger.debug("????????????: " + JacksonUtils.toJson(resp));
        }
    }


    /**
     * ??????????????????
     */
    public void createDeliveryPlan(String orderId) {
        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
        //??????????????????????????????
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
            //??????????????????
            List<GoodsOrderDetail> details = goodsOrderDetailService.findByOrderId(orderId);
            orderDelivery.setPlanGenStatus(IDeliveryConstant.PlanGenStatus.doing.getCode());
            orderDeliveryService.save(orderDelivery);
            orderDeliveryApiService.formDeliveryPlan(goodsOrder,details,orderDelivery,true);
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param list
     * @param address
     * @return
     */
    private String matchAddress(List<ShopServiceDistrict> list, String address) {
        String keyAry[] = address.split("-");
        String shopId = "";

        //?????????????????????????????????"???","???","???"
        keyAry[1] = keyAry[1].replace("???", "");
        keyAry[2] = keyAry[2].replace("???", "").replace("???", "");

        for (int i = 0; i < 3; i++) {
            String temp = keyAry[i];
            list = list.stream().filter(s ->
                    s.getDetailAddressName().indexOf(temp) != -1).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(list)) {
            return shopId;
        }
        //?????????????????????
        if (list.size() >= 1) {
            shopId = list.get(0).getShopId();
        }
        return shopId;
    }
}
