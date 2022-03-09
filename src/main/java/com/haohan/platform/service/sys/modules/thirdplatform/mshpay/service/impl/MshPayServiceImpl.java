package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.service.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankRespStatus;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankTransResult;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BSCPayResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.CSBPayResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.constant.MshPayConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.pay.MshPay;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request.MBSCPayRequest;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request.MCSBPayRequest;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request.MOrderQueryRequest;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request.MOrderRefundRequest;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response.*;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.service.inf.IMshPayService;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BSCPayResponse;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderQuery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.RefundManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.RefundManageService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/7/20
 */
@Service
public class MshPayServiceImpl implements IMshPayService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MshPay mshPay;
    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private RefundManageService refundManageService;

    @Override
    public OrderPayRecord csbPay(OrderPayRecord orderPayRecord) {

        //请求参数封装
        MCSBPayRequest csbPayRequest = new MCSBPayRequest();
        csbPayRequest.setOrg_no(MshPayConstant.ORG_NO);
        csbPayRequest.setMerchantId(orderPayRecord.getPartnerId());
        csbPayRequest.setOutTradeNo(orderPayRecord.getOrderId());
        csbPayRequest.setTotalFee(CommonUtils.amountTransPoint(orderPayRecord.getOrderAmount()));
        csbPayRequest.setNotify_url(XmBankConfigUtil.getMshNotifyUrl());
        csbPayRequest.setVersion("1.0.0");
        csbPayRequest.setRandom_str(IdGen.uuid().substring(0,9));
        csbPayRequest.setGoods_detail(orderPayRecord.getGoodsName());

        //发送请求
        MCSBPayResponse mCSBPayResponse = mshPay.csbPay(csbPayRequest);

        //保存并返回支付结果
        orderPayRecord.setRespCode(mCSBPayResponse.getCode().toString());
        orderPayRecord.setRespDesc(mCSBPayResponse.getMsg());
        orderPayRecord.setRespTime(new Date());
        orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.NOPAY.getCode());
        orderPayRecord.setRemarks(JacksonUtils.toJson(mCSBPayResponse));
        orderPayRecord.setPaySign(mCSBPayResponse.getSign());
        orderPayRecord.setNoncestr(mCSBPayResponse.getRandom_str());
        orderPayRecord.setOrderQrcode(mCSBPayResponse.getCode_url());
        orderPayRecord.setTransId(mCSBPayResponse.getHf_order_no());

        if (mCSBPayResponse.isSuccess()) {
            orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
        }

        //返回结果封装
        wrapPayResponse(orderPayRecord,mCSBPayResponse);
        return orderPayRecord;
    }

    @Override
    public OrderPayRecord bscPay(OrderPayRecord orderPayRecord) {
        MBSCPayRequest bscPayRequest = new MBSCPayRequest();
        String deviceId = orderPayRecord.getDeviceId();
        bscPayRequest.setOrg_no(MshPayConstant.ORG_NO);
        bscPayRequest.setMerchantId(orderPayRecord.getPartnerId());
        if (orderPayRecord.getPayChannel().equals("wechat")){
            bscPayRequest.setChannel(MshPayConstant.wechat_channel);
        }
        if (orderPayRecord.getPayChannel().equals("alipay")){
            bscPayRequest.setChannel(MshPayConstant.alipay_channel);
        }
        bscPayRequest.setOutTradeNo(orderPayRecord.getOrderId());
        bscPayRequest.setTotalFee(CommonUtils.amountTransPoint(orderPayRecord.getOrderAmount()));
        bscPayRequest.setAuthCode(orderPayRecord.getAuthCode());
        bscPayRequest.setVersion("1.0.0");
        bscPayRequest.setRandom_str(IdGen.uuid().substring(0,9));
        if (StringUtils.isNotEmpty(deviceId)){
            bscPayRequest.setDevice_id(deviceId);
        }else {
            bscPayRequest.setDevice_id("nodeviceid");
        }
        bscPayRequest.setGoods_detail(orderPayRecord.getGoodsName());

        //发送请求
        MBSCPayResponse mBSCPayResponse = mshPay.bscPay(bscPayRequest);

        //保存并返回支付结果
        orderPayRecord.setRespCode(mBSCPayResponse.getCode().toString());
        orderPayRecord.setRespDesc(mBSCPayResponse.getMsg());
        orderPayRecord.setRespTime(new Date());
        orderPayRecord.setRemarks(JacksonUtils.toJson(mBSCPayResponse));
        orderPayRecord.setPaySign(mBSCPayResponse.getSign());
        orderPayRecord.setTimeStamp(mBSCPayResponse.getTime_end());
        orderPayRecord.setTransId(mBSCPayResponse.getHf_order_no());
        if (mBSCPayResponse.isSuccess()){
            orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
        }else {
            orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.FAIL.getCode());
        }

        wrapPayResponse(orderPayRecord,mBSCPayResponse);
        return orderPayRecord;

    }

    @Override
    public OrderPayRecord jsPay(OrderPayRecord orderPayRecord) {
        return csbPay(orderPayRecord);
    }

    @Override
    public BaseResp orderQuery(OrderQuery orderQuery) {
        BaseResp baseResp = new BaseResp();
        try {
            OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(orderQuery.getOrderId());

            //请求参数封装
            MOrderQueryRequest mOrderQueryRequest = new MOrderQueryRequest();
            mOrderQueryRequest.setOrg_no(MshPayConstant.ORG_NO);
            mOrderQueryRequest.setMerchantId(orderQuery.getPartnerId());
            if (orderPayRecord.getPayChannel().equals("wechat")){
                mOrderQueryRequest.setChannel("WECHAT");
            }
            if (orderPayRecord.getPayChannel().equals("alipay")){
                mOrderQueryRequest.setChannel("ALIPAY");
            }
            mOrderQueryRequest.setOutTradeNo(orderQuery.getOrderId());
            mOrderQueryRequest.setHf_order_no(orderQuery.getThirdOrdNo());
            mOrderQueryRequest.setVersion("1.0.0");
            mOrderQueryRequest.setRandom_str(IdGen.uuid().substring(0,9));

            //发送请求
            MOrderQueryResponse mOrderQueryResponse = mshPay.orderQuery(mOrderQueryRequest);

            //保存并返回查询结果
            orderQuery.setRemarks(JacksonUtils.toJson(mOrderQueryResponse));
            orderQuery.setRespCode(mOrderQueryResponse.getCode().toString());
            if (mOrderQueryResponse.isSuccess()) {
                switch (mOrderQueryResponse.getOrderStatus()){
                    case "SUCCESS":
                        orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());break;
                    case "USERPAYING":
                        orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.UNKNOW.getCode());break;
                    case "PAYERROR":
                        orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.FAIL.getCode());break;
                    case "REFUND_SUCCESS":
                        orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.REFUNDED.getCode());break;
                        default:
                           orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.UNKNOW.getCode());
                }
                orderQuery.setPayResult(orderPayRecord.getPayStatus());
                orderQuery.setPayTime(DateUtils.parseDateTime(mOrderQueryResponse.getTime_end()));
            } else {
                orderQuery.setPayResult(IBankServiceConstant.PayStatus.UNKNOW.getCode());
                orderQuery.setRespMsg(mOrderQueryResponse.getMsg());
                return baseResp;
            }
            orderQuery.setThirdOrdNo(mOrderQueryResponse.getHf_order_no());
            if (StringUtils.isNotEmpty(mOrderQueryResponse.getTotalFee())){
                orderQuery.setPayAmount(CommonUtils.amountTransPoint(mOrderQueryResponse.getTotalFee()));
            }
            wrapOrderQueryParams(orderQuery,mOrderQueryResponse);

            baseResp.putStatus(RespStatus.SUCCESS);
            baseResp.setExt(mOrderQueryResponse.toJson());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return baseResp;
        }
    }

    @Override
    public BaseResp orderRefund(RefundManage refundManage) {
        BaseResp resp = BaseResp.newError();
        try {
            OrderPayRecord record = orderPayRecordService.fetchByOrderId(refundManage.getOrderId());
            MOrderRefundRequest mOrderRefundRequest = new MOrderRefundRequest();
            mOrderRefundRequest.setRefundFee(Integer.parseInt(CommonUtils.amountTransPoint(refundManage.getRefundAmount())));
//            mOrderRefundRequest.setOutTransactionId();
            mOrderRefundRequest.setOrg_no(MshPayConstant.ORG_NO);
            if ("wechat".equals(record.getPayChannel())){
                mOrderRefundRequest.setChannel(MshPayConstant.wechat_channel);
            } else if ("alipay".equals(record.getPayChannel())){
                mOrderRefundRequest.setChannel(MshPayConstant.alipay_channel);
            }
            mOrderRefundRequest.setRefundNo(refundManage.getRequestId());
            mOrderRefundRequest.setMerchantId(refundManage.getPartnerId());
            mOrderRefundRequest.setOutTradeNo(refundManage.getOrderId());
            mOrderRefundRequest.setVersion("1.0.0");
            mOrderRefundRequest.setRandom_str(IdGen.uuid().substring(0,9));

            //发送请求
            MOrderRefundResponse mOrderRefundResponse = mshPay.orderRefund(mOrderRefundRequest);

            //保存退款记录
            refundManage.setRespCode(mOrderRefundResponse.getCode().toString());
            refundManage.setRemarks(mOrderRefundResponse.toJson());
            refundManage.setRespTime(new Date());
            refundManage.setRespDesc(mOrderRefundResponse.getMsg());
            if (mOrderRefundResponse.isSuccess()){
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDED.getCode());
                refundManage.setRefundAmount(CommonUtils.amountPointTransYuan(mOrderRefundResponse.getRefundFee()));
            }else {
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDFAIL.getCode());
            }
            refundManage.setRespDesc(mOrderRefundResponse.getMsg());
            refundManage.setTradeNo(mOrderRefundResponse.getOutTradeNo());

            //返回结果封装 TODO
            wrapOrderRefundParams(refundManage,mOrderRefundResponse,resp);
            refundManageService.save(refundManage);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return resp;
        }
    }

    /**
     * 封装响应
     * @param orderPayRecord
     * @param response
     * @return
     */
    private OrderPayRecord wrapPayResponse(OrderPayRecord orderPayRecord, MBaseResponse response){
        try {
//            Map<String,Object> responseMap = new HashMap<>();
            String payType = orderPayRecord.getPayType();
            switch (BankServiceType.valueOfServiceType(payType)) {
                case AliPayQrCode:
                case WexinQrCode:
                    csbParamsFill(orderPayRecord, response);
                    break;
                case AliAuthPay:
                case WexinAuthPay:
                    bscParamsFill(orderPayRecord, response);
                    break;
                case WexinMpPay:
                case AliServicePay:
                    jsParamsFill(orderPayRecord,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            return orderPayRecord;
        }
    }

    private OrderQuery wrapOrderQueryParams(OrderQuery orderQuery, MBaseResponse response){
        String jsonStr = JacksonUtils.toJson(response);
        OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(orderQuery.getOrderId());
        Map<String,Object> respMap = new HashMap<>();
        MOrderQueryResponse mOrderQueryResponse = JacksonUtils.readValue(jsonStr, MOrderQueryResponse.class);
        if (response.isSuccess()){
            respMap.put("action", MshPayConstant.queryActionName);
            respMap.put("responseCode",XmBankRespStatus.SUCCESS.getResponseCode());
            respMap.put("transId",mOrderQueryResponse.getHf_order_no());
            respMap.put("reqId",orderQuery.getRequestId());
            respMap.put("transId",orderQuery.getThirdOrdNo());
            respMap.put("totalAmount",mOrderQueryResponse.getTotalFee());
            respMap.put("transAmount",orderQuery.getPayAmount());
            respMap.put("transTime",mOrderQueryResponse.getTime_end());
            respMap.put("transStatus", IBankServiceConstant.TransStatus.SUCCESS.getCode());
            respMap.put("acquirerType",payRecord.getPayChannel());
            respMap.put("walletTransId",mOrderQueryResponse.getOut_transaction_id());

        }else {
            respMap.put("action", MshPayConstant.queryActionName);
            respMap.put("responseCode",XmBankRespStatus.FAIL.getResponseCode());
            respMap.put("orderId",orderQuery.getOrderId());
            respMap.put("requestId",orderQuery.getRequestId());
            respMap.put("transId",orderQuery.getThirdOrdNo());
            respMap.put("respMsg",mOrderQueryResponse.getMsg());
        }
        orderQuery.setRemarks(JacksonUtils.toJson(respMap));
        return orderQuery;
    }

    private BaseResp wrapOrderRefundParams(RefundManage refundManage, MBaseResponse response, BaseResp resp){
        String jsonStr = JacksonUtils.toJson(response);
//        OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(refundManage.getOrderId());
        Map<String,Object> respMap = new HashMap<>();
        MOrderRefundResponse mOrderRefundResponse = JacksonUtils.readValue(jsonStr, MOrderRefundResponse.class);
        if (response.isSuccess()){
            respMap.put("action", MshPayConstant.refundActionName);
            respMap.put("responseCode",XmBankRespStatus.SUCCESS.getResponseCode());
            respMap.put("reqId",mOrderRefundResponse.getRefundNo());
            respMap.put("transId",mOrderRefundResponse.getOut_transaction_id());
            respMap.put("transAmount",mOrderRefundResponse.getRefundFee());
            respMap.put("transTime", DateUtils.getWsTime());
            respMap.put("transResult",XmBankTransResult.SUCCESS.getResponseCode());
            resp.success();
        }else {
            respMap.put("action", MshPayConstant.refundActionName);
            respMap.put("responseCode",XmBankRespStatus.FAIL.getResponseCode());
            respMap.put("orderId",refundManage.getOrderId());
            respMap.put("requestId",refundManage.getRequestId());
            respMap.put("resultMsg",mOrderRefundResponse.getMsg());
        }
        resp.setExt(JacksonUtils.toJson(respMap));
        return resp;
    }


    private OrderPayRecord csbParamsFill(OrderPayRecord orderPayRecord, MBaseResponse response){
        String jsonStr = JacksonUtils.toJson(response);
        MCSBPayResponse mcsbPayResponse = JacksonUtils.readValue(jsonStr, MCSBPayResponse.class);
        CSBPayResponse csbPayResponse = new CSBPayResponse();
        if (response.isSuccess()){
            csbPayResponse.setAction(MshPayConstant.csbActionName);
            csbPayResponse.setResponseCode(XmBankRespStatus.SUCCESS.getResponseCode());
            csbPayResponse.setReqId(orderPayRecord.getRequestId());
            csbPayResponse.setTransId(mcsbPayResponse.getHf_order_no());
            csbPayResponse.setQrCode(mcsbPayResponse.getCode_url());

        }else {
            csbPayResponse.setAction(MshPayConstant.csbActionName);
            csbPayResponse.setResponseCode(XmBankRespStatus.FAIL.getResponseCode());
            csbPayResponse.setReqId(orderPayRecord.getRequestId());
            csbPayResponse.setErrorMsg(mcsbPayResponse.getMsg());

        }

        orderPayRecord.setPayInfo(JacksonUtils.toJson(csbPayResponse));
        return orderPayRecord;
    }

    private OrderPayRecord bscParamsFill(OrderPayRecord orderPayRecord, MBaseResponse response){
        String jsonStr = JacksonUtils.toJson(response);
        MBSCPayResponse mbscPayResponse = JacksonUtils.readValue(jsonStr, MBSCPayResponse.class);
        BSCPayResponse bscPayResponse = new BSCPayResponse();
        if (response.isSuccess()){
            bscPayResponse.setAction(MshPayConstant.bscActionName);
            bscPayResponse.setResponseCode(XmBankRespStatus.SUCCESS.getResponseCode());
            bscPayResponse.setReqId(orderPayRecord.getRequestId());
            bscPayResponse.setTransId(mbscPayResponse.getHf_order_no());
            bscPayResponse.setTotalAmount(CommonUtils.amountTransPoint(orderPayRecord.getOrderAmount().toString()));
            bscPayResponse.setTransAmount(mbscPayResponse.getTotalFee());
            bscPayResponse.setTransTime(mbscPayResponse.getTime_end());
            bscPayResponse.setTransResult(XmBankTransResult.SUCCESS.getResponseCode());
            bscPayResponse.setResultMsg(mbscPayResponse.getMsg());
            bscPayResponse.setWalletTransId(mbscPayResponse.getOut_transaction_id());

        }else {
            bscPayResponse.setAction(MshPayConstant.bscActionName);
            bscPayResponse.setResponseCode(XmBankRespStatus.FAIL.getResponseCode());
            bscPayResponse.setReqId(orderPayRecord.getRequestId());
            bscPayResponse.setErrorMsg(mbscPayResponse.getMsg());

        }

        orderPayRecord.setPayInfo(JacksonUtils.toJson(bscPayResponse));
        return orderPayRecord;
    }

    private OrderPayRecord jsParamsFill(OrderPayRecord orderPayRecord, MBaseResponse response){
        return csbParamsFill(orderPayRecord,response);
    }

    private OrderQuery orderQueryParamsFill(OrderPayRecord orderPayRecord, MBaseResponse response){

        return null;
    }



}
