package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankRespStatus;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankTransResult;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.pay.ApmpClient;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request.*;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.*;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.NotifyParams;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.PayInfo;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.ReqParams;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.inf.IXmBankPayService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.partner.PartnerAppService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.ChannelRateManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.RefundManageService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by zgw on 2018/3/21.
 */
@Service
public class XmBankPayServiceImpl implements IXmBankPayService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApmpClient apmpClient;

    @Autowired
    private ChannelRateManageService channelRateManageService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Autowired
    private RefundManageService refundManageService;
    @Autowired
    private PartnerAppService partnerAppService;

    @Override
    public MerchantPayInfo merchantReg(MerchantPayInfo merchantPayInfo) {

        String merchantId = merchantPayInfo.getMerchantId();
        MerEnterRequest  request = new MerEnterRequest();
        request.setAction("mcht/info/enter");
        request.setVersion("2.0");
        request.setExpanderCd(apmpClient.getBankMchtId());//拓展商号
        request.setCoopMchtId(merchantPayInfo.getId());//账户ID
        request.setPublicKeyStr(XmBankConfigUtil.getPublicKeyStr());//商户公钥
        request.setMchtName(merchantPayInfo.getMercNm());//商户名称
        request.setMchtShortName(merchantPayInfo.getMercAbbr());//商户简称
        request.setParentMchtId("");
        request.setBizLicense(merchantPayInfo.getRegId());//执照号
        request.setLegalIdExpiredTime(merchantPayInfo.getIdExpireTime());// 执照有效期
        request.setIdNo(merchantPayInfo.getCrpIdNo());//身份证号
        request.setIdExpireDay(merchantPayInfo.getCrpIdExpireDay());//过期日期
        request.setProvince(merchantPayInfo.getMercProv());//上海
        request.setCity(merchantPayInfo.getMercCity());//上海市
        request.setArea(merchantPayInfo.getMercArea());// 地区
        request.setMchtAddr(merchantPayInfo.getBusAddr()); //详细地址
        request.setAccountType(merchantPayInfo.getBusPsnFlg());//0-公户，1-私户
        request.setAccount(merchantPayInfo.getStlOac());//账号
        request.setAccountName(merchantPayInfo.getBnkAcnm());//账号名称
        request.setBankName(merchantPayInfo.getLbnkNm()); //支行名称
        request.setBankCode(merchantPayInfo.getWcLbnkNo()); //开户行号 清算行号
        request.setOpenBranch(merchantPayInfo.getBankBranchNo());// 网点联行号
        request.setContactName(merchantPayInfo.getUsrOprNm()); //联系人名称
        request.setContactMobile(merchantPayInfo.getUsrOprMbl());//联系人电话
        request.setContactEmail(merchantPayInfo.getContactEmail());// 联系人邮件
        request.setMchtLevel(merchantPayInfo.getMerchantLevel());//1-分店，2-商户  级别 ，默认为2
        request.setOpenType(merchantPayInfo.getMercTyp());//1-个人，2-企业   类型
        request.setNotifyUrl(XmBankConfigUtil.getRegNotifyUrl());//注册结果回调通知地址

        JSONArray array = new JSONArray();

        List<ChannelRateManage> rateManageList = channelRateManageService.fetchByPayInfoId(merchantPayInfo.getId());
        if(CollectionUtils.isEmpty(rateManageList)){
            merchantPayInfo.setRespCode(IBankServiceConstant.BankRegStatus.FAIL.getCode()+"");
            merchantPayInfo.setRespDesc("未设置支付渠道");
            return merchantPayInfo;
        }

        for(ChannelRateManage rate:rateManageList) {
            if(IBankServiceConstant.BankRegStatus.TODO.getCode().toString().equals(rate.getStatus())) {
                if (StringUtils.isNoneEmpty(rate.getChannel(), rate.getRate(), rate.getCategory())) {
                    JSONObject obj = new JSONObject();
                    obj.put("acquirerType", rate.getChannel());
                    obj.put("scale", rate.getRate());
                    obj.put("countRole", "0");
                    obj.put("tradeType", rate.getCategory());
                    array.add(obj);
                    if (StringUtils.equalsAny(rate.getChannel(), "jd", "jdOnline")) {
                        request.setMchtFlag("2");
                        request.setWebChat(merchantPayInfo.getMercTrdCls());//京东渠道 传值 微信AppId
                    }
                }
            }
        }

        request.setAcquirerTypes(array.toString());

        MerEnterResponse merEnterResponse = apmpClient.merEnter(request);


        String returnCode = merEnterResponse.getResponseCode();
        merchantPayInfo.setRespCode(returnCode);
        merchantPayInfo.setRespDesc(merEnterResponse.getErrorMsg());
        merchantPayInfo.setRegTime(new Date());

        if (StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(),returnCode)) {
            merchantPayInfo.setRegStatus(IBankServiceConstant.BankRegStatus.CHECK.getCode());
            merchantPayInfo.setPartnerId(merEnterResponse.getBankMchtId());
            merchantPayInfo.setRespDesc(merEnterResponse.getResultMsg());
        } else {
            merchantPayInfo.setRegStatus(IBankServiceConstant.BankRegStatus.FAIL.getCode());
        }
        logger.debug("\nreqParams:{},\nrespParams:{}", JacksonUtils.toJson(merchantPayInfo),JacksonUtils.toJson(merEnterResponse));
        return merchantPayInfo;
    }

    @Override
    public MerchantPayInfo merchantQuery(ReqParams params) {

        EnterQueryRequest request = new EnterQueryRequest();
        request.setAction("mcht/info/query");
        request.setVersion("2.0");
        request.setCoopMchtId(params.getCoopMchtId());
        request.setAcquirerType(params.getAcquirerType());
        EnterQueryResponse enterQueryResponse = apmpClient.enterQuery(request);
        MerchantPayInfo payInfo = merchantPayInfoService.get(params.getCoopMchtId());
        if(null != payInfo) {
            if (enterQueryResponse.isSuccess()) {
                payInfo.setRegStatus(Integer.valueOf(enterQueryResponse.getCheckStatus()));
                    payInfo.setRespDesc(enterQueryResponse.getCheckMessag());
                    payInfo.setCustId(enterQueryResponse.getCustId());
            }else{
                payInfo.setRegStatus(IBankServiceConstant.BankRegStatus.FAIL.getCode());
                payInfo.setRespDesc(enterQueryResponse.getErrorMsg());
            }
        }
        return payInfo;
    }

    @Override
    public MerchantPayInfo merchantRegNotify(NotifyParams params) {
        return null;
    }

    @Override
    public BillDownLoadResponse downloadBill(ReqParams params)
    {
        return null;
    }

    @Override
    public OrderPayRecord csbPay(OrderPayRecord orderPay) {

        CSBPayRequest request = new CSBPayRequest();
        request.setAction("wallet/trans/csbSale");
        request.setVersion("2.0");
        commonParamsConfig(request,orderPay);

        CSBPayResponse csbPayResponse = apmpClient.csbPay(request);

        csbPayResponse.setOrderId(orderPay.getOrderId());
        orderPay.setRespCode(csbPayResponse.getResponseCode());
        orderPay.setRespDesc(csbPayResponse.getErrorMsg());
        orderPay.setRespTime(new Date());
        orderPay.setRemarks(JacksonUtils.toJson(csbPayResponse));
        if (StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(),csbPayResponse.getResponseCode())) {
            orderPay.setPayInfo(JacksonUtils.toJson(csbPayResponse));
            orderPay.setPrepayId(csbPayResponse.getTransId());
            orderPay.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
            orderPay.setOrderQrcode(csbPayResponse.getQrCode());
            orderPay.setTransId(csbPayResponse.getTransId());
        }
        logger.debug("\nreqParams:{},\nrespParams:{}", JacksonUtils.toJson(orderPay),JacksonUtils.toJson(csbPayResponse));
        return orderPay;
    }

    @Override
    public OrderPayRecord bscPay(OrderPayRecord orderPay) {
        BSCPayRequest request = new BSCPayRequest();
        request.setAction("wallet/trans/bscSale");

        commonParamsConfig(request,orderPay);
        request.setWalletAuthCode(orderPay.getAuthCode());
        BSCPayResponse response= apmpClient.bscPay(request);
        response.setOrderId(orderPay.getOrderId());
        orderPay.setRespCode(response.getResponseCode());
        orderPay.setRespDesc(response.getErrorMsg());
        orderPay.setRespTime(new Date());
        orderPay.setRemarks(JacksonUtils.toJson(response));
        if (StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(),response.getResponseCode())) {
            orderPay.setPayInfo(JacksonUtils.toJson(response));
            orderPay.setPrepayId(response.getTransId());
            orderPay.setRespDesc(response.getResultMsg());
            orderPay.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
            orderPay.setTransId(response.getTransId());
            if(StringUtils.equals(XmBankTransResult.SUCCESS.getResponseCode(),response.getTransResult())){
                orderPay.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
            }else if (StringUtils.equals(XmBankTransResult.UNKNOEW.getResponseCode(),response.getTransResult())){
                orderPay.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
            }else {
                orderPay.setPayStatus(IBankServiceConstant.PayStatus.FAIL.getCode());
            }
        }
        logger.debug("\nreqParams:{},\nrespParams:{}", JacksonUtils.toJson(orderPay),JacksonUtils.toJson(response));
        return orderPay;
    }

    @Override
    public OrderPayRecord h5Pay(OrderPayRecord orderPay)
    {
        H5PayRequest request = new H5PayRequest();
        request.setAction("wallet/trans/h5Sale");
        request.setVersion("2.0");
        commonParamsConfig(request,orderPay);
        request.setDeviceIP(orderPay.getClientIp());
        JSONObject sceneInfo=new JSONObject();
        JSONObject h5Info=new JSONObject();
        h5Info.put("type", "Wap");
        h5Info.put("wap_name", "H5应用支付");
        h5Info.put("wap_url", XmBankConfigUtil.getH5PayUrl());
        sceneInfo.put("h5_info", h5Info);
        request.setSceneInfo(sceneInfo.toString());
        H5PayResponse h5PayResponse = apmpClient.h5Pay(request);
        h5PayResponse.setOrderId(orderPay.getOrderId());
        orderPay.setRespCode(h5PayResponse.getResponseCode());
        orderPay.setRespDesc(h5PayResponse.getErrorMsg());
        orderPay.setRespTime(new Date());
        orderPay.setPayInfo(JacksonUtils.toJson(h5PayResponse));
        if (StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(),h5PayResponse.getResponseCode())) {
            orderPay.setPrepayId(h5PayResponse.getTransId());
            orderPay.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
            orderPay.setRespDesc(h5PayResponse.getMwUrl());
            orderPay.setTransId(h5PayResponse.getTransId());
        }
        logger.debug("\nreqParams:{},\nrespParams:{}", JacksonUtils.toJson(orderPay),JacksonUtils.toJson(h5PayResponse));
        return orderPay;
    }

    @Override
    public OrderPayRecord jsPay(OrderPayRecord orderPay)
    {
        try {
            WechatGzhRequest request = new WechatGzhRequest();
            request.setAction("wallet/trans/jsSale");
            commonParamsConfig(request, orderPay);
            request.setAcquirerType("wechat");
            request.setAppId(orderPay.getAppid());
            request.setUuid(orderPay.getOpenid());

            WechatGzhResponse wechatGzhResponse = apmpClient.gzhPay(request);
            wechatGzhResponse.setOrderId(orderPay.getOrderId());
            orderPay.setRespCode(wechatGzhResponse.getResponseCode());
            orderPay.setRespDesc(wechatGzhResponse.getErrorMsg());
            orderPay.setRespTime(new Date());
            orderPay.setRemarks(JacksonUtils.toJson(wechatGzhResponse));
            if (StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(), wechatGzhResponse.getResponseCode())) {
                orderPay.setPayInfo(wechatGzhResponse.getPayInfo());
                String str = wechatGzhResponse.getPayInfo();
                orderPay.setRespDesc(wechatGzhResponse.toJson());
                orderPay.setTransId(wechatGzhResponse.getTransId());
                if (StringUtils.isNotEmpty(str)) {
                    PayInfo info = JacksonUtils.readValue(str, PayInfo.class);
                    orderPay.setPaySign(info.getPaySign());
                    orderPay.setNoncestr(info.getNonceStr());
                    orderPay.setTimeStamp(info.getTimeStamp());
                    orderPay.setPrepayId(info.getPackageStr());
                }
                orderPay.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
            }
            return orderPay;
        }catch (Exception e){

            e.printStackTrace();

        }finally {
            return orderPay;
        }
    }

    @Override
    public OrderPayRecord appPay(OrderPayRecord payRecord) {
        return null;
    }

    @Override
    public BaseResp payStatusQuery(OrderQuery query) {
        BaseResp baseResp = new BaseResp();
        try {
            PayQueryRequest request = new PayQueryRequest();
            request.setAction("query/trans/detail");
            request.setVersion("2.0");
            request.setMerBnkId(query.getPartnerId());
            request.setReqTime(DateUtils.getWsTime());
		    request.setOrderId(query.getOrderId());
		    request.setReqId(query.getRequestId());

		    if(XmBankEnums.TransType.refund.getCode().equals(query.getTransType())){
                request.setRefundOrderId(query.getOrderId());
            }
            PayQueryResponse payQueryResponse = apmpClient.payQuery(request);
		    payQueryResponse.setOrderId(query.getOrderId());
            query.setRemarks(JacksonUtils.toJson(payQueryResponse));
            query.setRespCode(payQueryResponse.getResponseCode());
            if (!StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(), payQueryResponse.getResponseCode())) {
                query.setRespMsg(payQueryResponse.getErrorMsg());
                query.setRespCode(IBankServiceConstant.PayStatus.UNKNOW.getCode());
                query.setPayResult(IBankServiceConstant.TransStatus.UNKNOW.getCode());
                return baseResp;
            }
            query.setThirdOrdNo(payQueryResponse.getTransId());
            query.setPayAmount(CommonUtils.amountTransPoint(payQueryResponse.getTotalAmount()));
            query.setPayTime(DateUtils.parseDateTime(payQueryResponse.getTransTime()));

            query.setPayResult(payQueryResponse.getTransStatus());
            baseResp.putStatus(RespStatus.SUCCESS);
            baseResp.setExt(JacksonUtils.toJson(payQueryResponse));
            if(StringUtils.equals(payQueryResponse.getTransStatus(), IBankServiceConstant.PayStatus.SUCCESS.getCode())) {
                query.setRespMsg(IBankServiceConstant.PayStatus.SUCCESS.getDesc());
            }else {
                query.setRespMsg("交易未完成");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            return baseResp;
        }
    }

    @Override
    public BaseResp refund(RefundManage refundManage) {
        BaseResp baseResp = new BaseResp();
        try {
            RefundRequest request = new RefundRequest();
            request.setAction("wallet/trans/refund");
            request.setVersion("2.0");
            request.setReqTime(DateUtils.getWsTime());

//            request.setOrderId(refundManage.getOrderId());
            request.setReqId(refundManage.getRequestId());
            request.setMerBnkId(refundManage.getPartnerId());
            request.setOrgReqId(refundManage.getOrgReqId());

            //平台交易全局ID
            request.setOrgTransId(refundManage.getOrgTransId());
            //金额单位设置为分
            request.setTotalAmount(CommonUtils.amountTransPoint(refundManage.getRefundAmount()));
//            request.setRefundOrderId(refundManage.getRequestId());
            request.setRefundOrderId(refundManage.getOrderId());

            refundManage.setRefundApplyTime(new Date());
            RefundResponse refundResponse = apmpClient.refund(request);
            refundResponse.setOrderId(refundManage.getOrderId());
            refundManage.setRespCode(refundResponse.getResponseCode());
            refundManage.setRemarks(JacksonUtils.toJson(refundResponse));
            refundManage.setRespTime(new Date());
            if (!StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(), refundResponse.getResponseCode())) {
                refundManage.setRespDesc(refundResponse.getErrorMsg());
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDFAIL.getCode());
                refundManageService.save(refundManage);
                baseResp.setCode(RespStatus.ERROR.getCode());
                baseResp.setMsg(refundResponse.getErrorMsg());
                baseResp.setExt(JacksonUtils.toJson(refundResponse));
                return baseResp;
            }
            if (StringUtils.equals(XmBankTransResult.SUCCESS.getResponseCode(),refundResponse.getTransResult())){
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDED.getCode());
                refundManage.setRespDesc("退款成功");
            } else {
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDFAIL.getCode());
                refundManage.setRespDesc(refundResponse.getResultMsg());
            }
//            refundManage.setStatus(refundResponse.getTransResult());
            refundManage.setTradeNo(refundResponse.getTransId());
            refundManageService.save(refundManage);
            baseResp.setExt(JacksonUtils.toJson(refundResponse));
            baseResp.putStatus(RespStatus.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return baseResp;
        }
    }

    @Override
    public BaseResp orderCancel(OrderCancel orderCancel) {

        BaseResp resp = BaseResp.newError();

        try {
            CancelRequest request = new CancelRequest();
            request.setAction("wallet/trans/saleVoid");
            request.setVersion("2.0");
            request.setReqTime(DateUtils.getWsTime());

            request.setOrderId(orderCancel.getOrderId());
            request.setReqId(orderCancel.getId());
            request.setMerBnkId(orderCancel.getPartnerId());

            //平台交易全局ID
            request.setOrgTransId(orderCancel.getOrgTransId());
            request.setOrgReqId(orderCancel.getOrgReqId());
            CancelResponse cancelResponse =  apmpClient.cancel(request);
            if (!StringUtils.equals(XmBankRespStatus.SUCCESS.getResponseCode(), cancelResponse.getResponseCode())) {
                cancelResponse.setResultMsg(cancelResponse.getErrorMsg());
                orderCancel.setStatus(IBankServiceConstant.CancelStatus.CANCELFAIL.getCode());
                orderCancel.setRespDesc(cancelResponse.getErrorMsg());
                orderCancel.setRespCode(cancelResponse.getResponseCode());
                resp.setCode(RespStatus.ERROR.getCode());
                resp.setMsg(cancelResponse.getErrorMsg());
                resp.setExt(JacksonUtils.toJson(cancelResponse));
                return resp;
            }
            if (StringUtils.equals(XmBankTransResult.SUCCESS.getResponseCode(),cancelResponse.getTransResult())){
                orderCancel.setStatus(IBankServiceConstant.CancelStatus.CANCELEDSUCCESS.getCode());
            }else {
                orderCancel.setStatus(IBankServiceConstant.CancelStatus.CANCELFAIL.getCode());
            }
            orderCancel.setRespDesc(cancelResponse.getResultMsg());
            orderCancel.setRespCode(cancelResponse.getTransResult());
            resp.putStatus(RespStatus.SUCCESS);
            resp.setExt(JacksonUtils.toJson(cancelResponse));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return resp;
        }


    }


    private void commonParamsConfig(BaseRequest request, OrderPayRecord orderPay){

        request.setVersion("2.0");
        request.setReqTime(DateUtils.getWsTime());
        request.setCurrency("156");
        request.setOrderId(orderPay.getOrderId());
        request.setReqId(orderPay.getRequestId());
        String goodsName = orderPay.getGoodsName();
        if(StringUtils.isNotEmpty(goodsName)) {
            request.setOrderSubject(goodsName);
            request.setOrderDesc(goodsName);
            if(goodsName.length()>32){
                request.setOrderSubject(goodsName.substring(0,30));
            }
            if(goodsName.length()>80){
                request.setOrderDesc(goodsName.substring(0,64));
            }
        }else{
            request.setOrderSubject(orderPay.getMerchantName().concat("支付"));
            request.setOrderDesc(orderPay.getMerchantName().concat("支付"));
        }

		request.setTransTimeOut("12"); //请求超时时间
        request.setDeviceId("");
        request.setOperatorId("");
        request.setMerBnkId(orderPay.getPartnerId());
        request.setAcquirerType(orderPay.getPayChannel());
        request.setTotalAmount(CommonUtils.amountTransPoint(orderPay.getOrderAmount()));
        request.setNotifyUrl(orderPay.getNotifyUrl());

        }





}
