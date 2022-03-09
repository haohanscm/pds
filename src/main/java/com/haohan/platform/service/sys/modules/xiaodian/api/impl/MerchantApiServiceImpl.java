package com.haohan.platform.service.sys.modules.xiaodian.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.inf.IXmBankPayService;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.constant.MshPayConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IMerchantApiService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IDeliveryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.OrderDeliveryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zgw on 2017/12/29.
 */
@Service
public class MerchantApiServiceImpl implements IMerchantApiService {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MerchantPayInfoService merchantPayInfoService;

    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @Autowired
    private OrderCancelService orderCancelService;
    @Autowired
    private RefundManageService refundManageService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private IXmBankPayService xmBankPayService;
    @Autowired
    private PayNotifyService payNotifyService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private OrderDeliveryService orderDeliveryService;

    @Override
    public BaseResp reg(Merchant merchant) {
        try {
            merchantService.save(merchant);
            return BaseResp.newSuccess();
        } catch (Exception e) {
            return BaseResp.newError();
        }
    }

    @Override
    public BaseResp addShop(Shop shop) {

        try {
            shopService.save(shop);
            return BaseResp.newSuccess();
        } catch (Exception e) {
            return BaseResp.newError();
        }
    }

    @Override
    public BaseResp bankAccountReg(MerchantPayInfo merchantPayInfo) {
        try {
            merchantPayInfoService.save(merchantPayInfo);

            return BaseResp.newSuccess();
        } catch (Exception e) {
            return BaseResp.newError();
        }
    }

    @Override
    public BaseResp pay(OrderPayRecord payRecord) {
        try {
            orderPayRecordService.callBankService(payRecord);

            BaseResp resp =  BaseResp.newError();

            if (IBankServiceConstant.BankServiceStatus.SUCCESS.getCode().equals(payRecord.getRespCode())
                    ||MshPayConstant.SUCCESS.getCode().toString().equals(payRecord.getRespCode())) {
                resp.setExt(payRecord);
                resp.putStatus(RespStatus.SUCCESS);
            } else {
                resp.setExt(payRecord);
                resp.setMsg(payRecord.getRespDesc());
            }
            return resp;
        } catch (Exception e) {
            return BaseResp.newError();
        }finally {
            //保存订单信息
            orderPayRecordService.save(payRecord);
        }
    }

    @Override
    public BaseResp orderCancel(OrderCancel orderCancel) {

        try {
            orderCancelService.save(orderCancel);

            BaseResp resp = xmBankPayService.orderCancel(orderCancel);

            orderCancel.setReTime(new Date());
            orderCancelService.save(orderCancel);
//            BaseResp resp = new BaseResp();
//            if (resp.isSuccess()){
//                return resp;
//            }
//            resp.error();
//            resp.setMsg(orderCancel.getRespDesc());

//            resp.setExt(orderCancel);
//            if (IBankServiceConstant.BankServiceStatus.SUCCESS.getCode().equals(orderCancel.getRespCode())||IBankServiceConstant.TransStatus.SUCCESS.getCode().equals(orderCancel.getRespCode())) {
//                resp.setCode(RespStatus.SUCCESS.getCode());
//            } else {
//                resp.setCode(RespStatus.ERROR.getCode());
//            }
//            resp.setMsg(orderCancel.getRespDesc());

            return resp;
        } catch (Exception e) {
            return BaseResp.newError();
        }

    }

    @Override
    public BaseResp refund(RefundManage refundManage) {
        //TODO 封装退款方法 待适配码尚退款
        try {
            xmBankPayService.refund(refundManage);
            refundManageService.save(refundManage);
            BaseResp resp = new BaseResp();
            resp.setExt(refundManage);
            if (IBankServiceConstant.BankServiceStatus.SUCCESS.getCode().equals(refundManage.getRespCode())) {
                resp.setCode(RespStatus.SUCCESS.getCode());
            } else {
                resp.setCode(RespStatus.ERROR.getCode());
            }
            resp.setMsg(refundManage.getRespDesc());

            return resp;
        } catch (Exception e) {
            return BaseResp.newError();
        }

    }

    @Override
    public BaseResp orderQuery(OrderQuery query) {
        orderQueryService.save(query);
        query =  orderQueryService.checkPayStatus(query.getOrderId(),XmBankEnums.TransType.consume);

        BaseResp resp = BaseResp.newError();

        resp.setExt(query.getRemarks());
        if ((StringUtils.equals(IBankServiceConstant.BankServiceStatus.SUCCESS.getCode(), query.getRespCode())||
                StringUtils.equals(MshPayConstant.SUCCESS.getCode().toString(), query.getRespCode()))) {
            resp.putStatus(RespStatus.SUCCESS);
            if (query.getRespCode().equals(IBankServiceConstant.TransStatus.SUCCESS.getCode())){
                query.setRespMsg(IBankServiceConstant.TransStatus.SUCCESS.getDesc());
            }
        }
        else {
            resp.putStatus(RespStatus.ERROR);
            resp.setMsg(query.getRespMsg());
        }
        orderQueryService.save(query);

        return resp;
    }



    @Override
    public BaseResp refundByOrderIdAndAmount(GoodsOrder goodsOrder, String refundAmount){
        BaseResp baseResp = BaseResp.newError();

        String orderId = goodsOrder.getOrderId();
        OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(goodsOrder.getOrderId());
        if (orderPayRecord == null){
            baseResp.setMsg("无此订单");
            return baseResp;
        }
        if (!IBankServiceConstant.PayStatus.SUCCESS.getCode().equals(orderPayRecord.getPayStatus())
                && !IBankServiceConstant.PayStatus.CHECK.getCode().equals(orderPayRecord.getPayStatus())
                && refundManageService.fetchRefundTotalAmount(orderId,IBankServiceConstant.RefundStatus.REFUNDED.getCode()).compareTo(orderPayRecord.getOrderAmount()) == 0){
            baseResp.setMsg("订单未支付或已全部退款");
            return baseResp;
        }

        //退款
        RefundManage refundManage = new RefundManage();
        refundManage.setMerchantId(orderPayRecord.getMerchantId());
        refundManage.setMerchantName(orderPayRecord.getMerchantName());
        BigDecimal deRefundAmount = new BigDecimal(refundAmount);
        //查询Notify
        PayNotify notify = payNotifyService.fetchByOrderId(orderId);
        if (null != notify) {
            refundManage.setOrgTransId(notify.getTransId());
            refundManage.setOrgReqId(notify.getRequestId());
            refundManage.setOrderAmount(orderPayRecord.getOrderAmount());
            refundManage.setPayAmount(orderPayRecord.getOrderAmount());
            refundManage.setRefundAmount(deRefundAmount);
            refundManage.setPartnerId(orderPayRecord.getPartnerId());
            refundManage.setOrderId(orderId);
            refundManage.setBusType("3");
            String requestId = CommonUtils.genId(orderPayRecord.getPayType());
            refundManage.setRequestId(requestId);
            refundManageService.save(refundManage);

            //退款
            baseResp = refund(refundManage);

            if (baseResp.isSuccess()){
//                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDED.getCode());
                if (orderPayRecord.getOrderAmount().compareTo(refundManageService.fetchRefundTotalAmount(orderId,IBankServiceConstant.RefundStatus.REFUNDED.getCode())) != 0){
                    orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.REFUND_PART.getCode());
                } else {
                    orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.REFUNDED.getCode());
                }
                orderPayRecordService.save(orderPayRecord);
                refundManageService.save(refundManage);
                goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.CLOSED.getCode());
                goodsOrder.setPayStatus(orderPayRecord.getPayStatus());
                goodsOrderService.save(goodsOrder);
            }else {
                if (!StringUtils.equals(orderPayRecord.getPayStatus(),IBankServiceConstant.PayStatus.REFUNDED.getCode())){
                    orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.CHECK.getCode());
                    orderPayRecordService.save(orderPayRecord);
                    refundManageService.save(refundManage);
//                    goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.REFUNDING.getCode());
                    goodsOrder.setPayStatus(orderPayRecord.getPayStatus());
                    goodsOrderService.save(goodsOrder);
                }
            }
            //修改订单配送状态
            OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
            if (null != orderDelivery){
                orderDelivery.setOrderStatus(goodsOrder.getOrderStatus());
                orderDelivery.setPayStatus(goodsOrder.getPayStatus());
                orderDelivery.setDeliveryStatus(IDeliveryConstant.OrderDeliveryStatus.cancel.getCode());
                orderDeliveryService.save(orderDelivery);
            }
        }
        return baseResp;
    }
}
