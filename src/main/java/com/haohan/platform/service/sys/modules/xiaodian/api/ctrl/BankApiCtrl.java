package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.producer.IProducer;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankTransResult;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.PayResultNotifyResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.RegNotifyResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.AcquirerType;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.params.PayNotifyTaskDto;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.BankPayService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.GoodsOrderApiService;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.GoodsOrderPdsService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.ChannelRateManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.PayNotify;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.ChannelRateManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.PayNotifyService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import com.hyt.cap.sdk.RSASignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant.BankRegStatus;
import static com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant.PayStatus;

/**
 * 银行API接口
 * Created by zgw on 2017/12/10.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/bank")
public class BankApiCtrl extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PayNotifyService payNotifyService;
    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
 
    @Autowired
    private ChannelRateManageService channelRateManageService;
    private static RSASignUtil publicRsaSignUtil = BankPayService.getPublicRsaSignUtil();
    @Autowired
    private GoodsOrderApiService goodsOrderApiService;

    @Autowired
    private GoodsOrderPdsService goodsOrderPdsService;
    @Autowired
    private BuyOrderService buyOrderService;
    @Autowired
    private IProducer producer;



    @RequestMapping(value = "xmBankRegNotify")
    @ResponseBody
    public String xmBankRegNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer logStr = new StringBuffer();
        try {
            Map<String, Object> respMap = getRequestParameters(request);

            //TODO 验证签名
            String sign = (String) respMap.get("sign");

            String body = (String) respMap.get("body");
            logStr.append(body);

            RegNotifyResponse regResp = JacksonUtils.readValue(body, RegNotifyResponse.class);

            String partnerId = regResp.getBankMchtId();
            String payInfoId = regResp.getCoopMchtId();

            MerchantPayInfo payInfo = merchantPayInfoService.get(payInfoId);
            if (null == payInfo) {
                return "FAIL:不存在此商户";
            }
            payInfo.setRespDesc(body);
            List<AcquirerType> list = regResp.getAcquirerTypes();
            for (AcquirerType type : list) {
                ChannelRateManage rateManage = channelRateManageService.fetchByChannel(payInfoId, type.getChannel());
                if (null != rateManage) {
                    rateManage.setRespMsg(type.getResultMsg());
                    rateManage.setStatus(type.getCheckStatus());
                    rateManage.setCustId(type.getCustId());
                    channelRateManageService.save(rateManage);
                    if ("2".equals(type.getCheckStatus())) {
                        payInfo.setRegStatus(BankRegStatus.SUCCESS.getCode());
                    }
                }
            }

            merchantPayInfoService.save(payInfo);
            return "SUCCESS";

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            logger.debug("notifyInfo:\n{}", logStr);
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "xmBankPayNotify")
    @ResponseBody
    public String xmBankPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PayNotify payNotify = new PayNotify();
        BaseResp baseResp = BaseResp.newError();

//        try {
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

            if (XmBankTransResult.SUCCESS.getResponseCode().equals(resp.getTransResult())) {
                OrderPayRecord record = orderPayRecordService.fetchByOrderId(resp.getOrderId());

                PayNotifyTaskDto payNotifyTaskDto = (PayNotifyTaskDto) JedisUtils.getObject("payNotifyTask" + resp.getOrderId());
                if (null == payNotifyTaskDto) {
                    payNotifyTaskDto = new PayNotifyTaskDto();
                }

                if (null != record) {
                    record.setPayStatus(PayStatus.SUCCESS.getCode());
                    record.setBankChannel(IMerchantConstant.BankChannel.XMBANK.getCode());
                    orderPayRecordService.save(record);
                    payNotify.setIsNotifyJsapp(ICommonConstant.YesNoType.no.getCode());
                    payNotify.setStatus("SUCCESS");

                }
                    /**
                    GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(resp.getOrderId());
                    //支付完成异步处理
                    if (!payNotifyTaskDto.getPaySuccess() && StringUtils.isNotEmpty(goodsOrder.getOrderId())) {
                        OrderMsgEntity mqEntity = new OrderMsgEntity();
                        mqEntity.setOrderId(goodsOrder.getOrderId());
                        mqEntity.setMqTags(IRocketMqConstant.OrderMsgTag.PAY_SUCCESS.getTagName());
                        producer.send(IRocketMqConstant.TopicType.ORDER.getName(),mqEntity);
                        payNotifyTaskDto.setPaySuccess(true);
                        JedisUtils.setObject("payNotifyTask" + resp.getOrderId(), payNotifyTaskDto, 7200);
                        //更新商品订单支付状态
                        goodsOrderApiService.paySuccess(record,goodsOrder);

                        //同步即速应用订单
                        baseResp = jsappApiService.payNotify(record,payNotify);
                        if (baseResp.isSuccess()){
                            JsYunResp jsYunResp = (JsYunResp) baseResp.getExt();

                            if (IJisuYunConstant.JsYunRespStatus.success.getCode() == jsYunResp.getStatus()){
                                payNotify.setIsNotifyJsapp(ICommonConstant.YesNoType.yes.getCode());
                                payNotify.setRespDesc("同步即速应用订单成功");
                            }else {
                                payNotify.setIsNotifyJsapp(ICommonConstant.YesNoType.no.getCode());
                                payNotify.setRespDesc(jsYunResp.getData());
                            }
                        }

                        //零售单转采购单
                        String orderId = goodsOrder.getOrderId();
                        String orderType = goodsOrder.getOrderType();
                        IOrderServiceConstant.OrderType orderTypeEnum = IOrderServiceConstant.OrderType.valueOfCode(orderType);
                        switch (orderTypeEnum){
                            case purchase:
                                BuyOrder order = buyOrderService.fetchByGoodsOrderId(orderId);
                                if (null == order){
                                    goodsOrderPdsService.convertToBuyOrder(goodsOrder);
                                }
                                break;
                            case prodService:
                                prodServiceApiService.bankNotifySuc(goodsOrder);
                                break;
                        }
                    }
                }
            }

            logger.debug("notifyMsg:\n{}", JacksonUtils.toJson(respMap));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            payNotifyService.save(payNotify);
        }
                     **/
            }
        return "SUCCESS";
    }


    @RequestMapping(value = "notify")
    @ResponseBody
    public String notify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PayNotify payNotify = new PayNotify();
        StringBuffer str = new StringBuffer();
        //从request中获取参数
        try {
            Map<String, Object> respMap = getRequestParameters(request);

            String charSet = (String) respMap.get("charset");
            String partner_id = (String) respMap.get("partner_id");
            String data = (String) respMap.get("data");
            String signature = (String) respMap.get("signature");

            boolean flag = false;

            String rspPlainText = new String(publicRsaSignUtil.decryptByPublickey(data.getBytes(charSet)));
            //对于支付平台返回明文数据,可能由于系统查询导致后面带空格的需要trim处理
            flag = publicRsaSignUtil.verifyBySoft(signature, rspPlainText.trim(), charSet);
            str.append("\n返回参数：" + rspPlainText);
            payNotify.setRemarks(str.toString());
            if (!flag) {
                //签名验证失败
                payNotify.setRespCode("900");
                payNotify.setRespDesc(partner_id + "签名验证失败");
            }
            Map<String, String> map = publicRsaSignUtil.coverString2Map(rspPlainText);

            String payStaus = map.get("status").toString();
            String returnCode = map.get("returnCode");
            String returnMessage = map.get("returnMessage");
            String requestId = map.get("requestId");
            String orderId = map.get("orderId");
            String orderTime = map.get("orderTime");
            String totalAmount = map.get("totalAmount");
            String payTime = map.get("payTime");
            String acDate = map.get("acDate");
            String fee = map.get("fee");

            payNotify.setStatus(payStaus);
            payNotify.setRespCode(returnCode);
            payNotify.setRespDesc(returnMessage);
            payNotify.setRequestId(requestId);
            payNotify.setOrderId(orderId);
            payNotify.setOrderTime(DateUtils.parseDate(orderTime));
            payNotify.setPayAmount(new BigDecimal(totalAmount).divide(new BigDecimal("100")));
            payNotify.setPayTime(DateUtils.parseDate(payTime));
            payNotify.setAcDate(acDate);
            if (!fee.startsWith("0") && null != fee) {
                fee = "0";
                payNotify.setFee(new BigDecimal(fee).divide(new BigDecimal("100")));
            } else {
                payNotify.setFee(new BigDecimal(BigInteger.ZERO));
            }

            String appId = null;
            if (payStaus.equalsIgnoreCase("SUCCESS") && StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(requestId)) {
                //变更订单支付状态
                OrderPayRecord payRecord = orderPayRecordService.fetchByOrderId(orderId);
                if (null != payRecord && payRecord.getRequestId().equals(requestId)) {
                    payRecord.setPayStatus(PayStatus.SUCCESS.getCode().toString());
                    appId = payRecord.getAppid();
                    orderPayRecordService.save(payRecord);
                }
                GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
                if (null != goodsOrder && goodsOrder.getPayId().equals(requestId)) {
                    goodsOrder.setPayStatus(PayStatus.SUCCESS.getCode());
                    goodsOrderService.save(goodsOrder);
                }
                payNotify.setResult("SUCCESS");
            }
        } catch (Exception e) {
            str.append("\nexception:" + e.getMessage());
            e.printStackTrace();
            return "FAIL";
        } finally {
            logger.debug(str.toString());
            payNotifyService.save(payNotify);
        }
        return "SUCCESS";
    }





}
