package com.haohan.platform.service.sys.modules.xiaodian.api.service;

import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.MerchantPayInfoService;
import com.hyt.cap.sdk.HytConfig;
import com.hyt.cap.sdk.HytUtil;
import com.hyt.cap.sdk.RSASignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.util.ResourceUtils.getFile;


/**
 * Created by zgw on 2017/12/8.
 */
@Service
public class BankPayService implements IBankServiceConstant {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private MerchantPayInfoService merchantPayInfoService;


    //    800075500010046
//    private static String partner_id="800075500020009";
//    private static String partner_id="800059100010067";
//      private static String partner_id="800010000010255";
    private static String partner_id = "800010000010258";

    //    private static String auth_code ="q2a33fn5r8y1jpx310a0dz53kfcs6i0y";
//    private static String auth_code ="od8fbh4h5805z86hw2okm8wc5i24nv2v";
//private static String auth_code ="mxzh0q15bwdvs4kbona06zihy92luj77";
    private static String auth_code = "j4jppeczcyp83id0gnh8zbxr36ypu3t3";
    private static final String charSet = "UTF-8";//char_set 02 UTF-8
    private static final String versionNo = "1.0";//version_no 1.0
    private static final String signType = "RSA";//sign_type RSA

    private static final String timePatten = "yyyyMMddHHmmss";

    private static final String bankServiceUrl = "http://119.23.126.119:8280/hipos/dxtpayTransaction";

    private static final String notifyUrl = "http://pay.haohanwork.com/service-sys/f/xiaodian/api/bank/notify";

    private static HytConfig hytconfig = HytConfig.getConfig();

    private static RSASignUtil privateRsaSignUtil;

    private static RSASignUtil publicRsaSignUtil;

    public static RSASignUtil getPrivateRsaSignUtil() {
        return privateRsaSignUtil;
    }

    public static RSASignUtil getPublicRsaSignUtil() {
        return publicRsaSignUtil;
    }

    {
        //加载配置文件 TODO
        try {
            String fileName = partner_id + ".p12";
            hytconfig.loadPropertiesFromSrc();
//            partner_id = hytconfig.getMerchantId();
            hytconfig.getConfig().setMerchantId(partner_id);
            String hytServerCerPath = getFile("classpath:pay/bank.cer").getAbsolutePath();
            String merChantCertPath = getFile("classpath:pay/" + fileName).getAbsolutePath();
            hytconfig.sethytServerCertPath(hytServerCerPath);
            hytconfig.setMerchantCertPath(merChantCertPath.replace(fileName, ""));
            privateRsaSignUtil = new RSASignUtil(merChantCertPath);
            privateRsaSignUtil.loadPrivateKey();
            publicRsaSignUtil = new RSASignUtil(hytServerCerPath);
            //对支付平台返回数据使用平台公钥解密
            publicRsaSignUtil.loadPublicKey();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 商户开户入驻
     *
     * @param merchantPayInfo
     * @return MerchantPayInfo
     */
    public MerchantPayInfo merchantReg(MerchantPayInfo merchantPayInfo) {
        Map<String, String> req = baseReqMapParams(BankServiceType.MerchantReg);

//        req.put("partner_id", partner_id);
        req.put("merc_typ", merchantPayInfo.getMercTyp());
        req.put("merc_nm", merchantPayInfo.getMercNm());
        req.put("merc_trd_cls", merchantPayInfo.getMercTrdCls());
        req.put("merc_sub_cls", merchantPayInfo.getMercSubCls());
        req.put("merc_third_cls", merchantPayInfo.getMercThirdCls());
        req.put("merc_trird_cls_ali", merchantPayInfo.getMercThirdClsAll());
        req.put("crp_nm", merchantPayInfo.getCrpNm());
        req.put("crp_id_no", merchantPayInfo.getCrpIdNo());
        req.put("reg_id", merchantPayInfo.getRegId());
        req.put("organization_code", merchantPayInfo.getOrganizationCode());
        req.put("bus_addr", merchantPayInfo.getBusAddr());
        req.put("merc_prov", merchantPayInfo.getMercProv());
        req.put("merc_city", merchantPayInfo.getMercCity());
        req.put("agr_no", merchantPayInfo.getAgrNo());
        req.put("usr_opr_nm", merchantPayInfo.getUsrOprNm());
        req.put("usr_opr_mbl", merchantPayInfo.getUsrOprMbl());
        req.put("stl_perd", merchantPayInfo.getStlPerd());
        req.put("stl_day_flg", merchantPayInfo.getStlDayFlg());
        req.put("wc_bnk", merchantPayInfo.getWcBank());
        req.put("opn_bnk_prov", merchantPayInfo.getOpnBnkProv());
        req.put("opn_bnk_city", merchantPayInfo.getOpnBnkCity());
        req.put("lbnk_nm", merchantPayInfo.getLbnkNm());
        req.put("wc_lbnk_no", merchantPayInfo.getWcLbnkNo());
        req.put("bnk_acnm", merchantPayInfo.getBnkAcnm());
        req.put("stl_oac", merchantPayInfo.getStlOac());
        req.put("bus_psn_flg", merchantPayInfo.getBusPsnFlg().toString());
        req.put("usr_opr_log_id", merchantPayInfo.getUsrOprLogId());


        Map<String, String> respMap = bankReq(req);

        String returnCode = respMap.get("returnCode");
        merchantPayInfo.setRespCode(returnCode);
        merchantPayInfo.setRespDesc(respMap.get("returnMessage"));
        merchantPayInfo.setRegTime(new Date());

        if (BankServiceStatus.SUCCESS.getCode().equals(returnCode)) {
            merchantPayInfo.setRegStatus(BankRegStatus.SUCCESS.getCode());
            merchantPayInfo.setSubPartnerId(respMap.get("sub_partner_id"));
        } else {
            merchantPayInfo.setRegStatus(BankRegStatus.FAIL.getCode());
        }

        return merchantPayInfo;

    }


    /**
     * 商户费率设置
     *
     * @param channelRate
     * @return Boolean
     */
    public boolean merchantPayRateConfig(ChannelRateManage channelRate) {

        Map<String, String> reqMap = baseReqMapParams(BankServiceType.PayRateConfig);


        Map<String, BigDecimal> rates = new HashMap<>();

        //获取参数
        rates.put(BankServiceType.WexinQrCode.getCode(), channelRate.getWxQrcode());
        rates.put("02", channelRate.getWxPaycard());
        rates.put("03", channelRate.getWxMp());
        rates.put("05", channelRate.getAlipayQrcode());
        rates.put("06", channelRate.getAlipayBarcode());
        rates.put("07", channelRate.getAlipayService());

        String rateStr = genRateStr(rates, channelRate.getPayInfoId());

        reqMap.put("sub_partner_id", channelRate.getPayInfoId());
        reqMap.put("fee_list", rateStr);

        Map<String, String> respMap = bankReq(reqMap);

        String returnCode = respMap.get("returnCode");

        channelRate.setRespCode(returnCode);

        channelRate.setRespMsg(respMap.get("returnMessage"));
        if (BankServiceStatus.SUCCESS.getCode().equals(returnCode)) {
            return true;
        }

        return false;
    }


    /**
     * 微信扫码支付
     *
     * @param payRecord
     * @return OrderPayRecord
     */
    public OrderPayRecord wexinQrcode(OrderPayRecord payRecord) {

        Map<String, String> reqMap = baseReqMapParams(BankServiceType.WexinQrCode);

        configPartnerInfo(reqMap, payRecord.getMerchantId());

        payApiCommonParams(reqMap, payRecord);

        Map<String, String> respMap = bankReq(reqMap);
        //返回公共参数
        payRecord.setRespCode(respMap.get("returnCode"));
        payRecord.setRespDesc(respMap.get("returnMessage"));
        payRecord.setRespTime(new Date());

        payRecord.setOrderQrcode(respMap.get("code_url"));

        return payRecord;
    }


    /**
     * 微信刷卡支付
     *
     * @param payRecord
     * @return OrderPayRecord
     */
    public OrderPayRecord weixinAuthPay(OrderPayRecord payRecord) {
        Map<String, String> reqMap = baseReqMapParams(BankServiceType.WexinAuthPay);
        configPartnerInfo(reqMap, payRecord.getMerchantId());
        //设置请求参数
        payApiCommonParams(reqMap, payRecord);
        reqMap.remove("offlineNotifyUrl");//条码支付不需要notify
        //设备读取用户授权码
        reqMap.put("auth_code", payRecord.getAuthCode());

        Map<String, String> respMap = bankReq(reqMap);
        payRecord.setRespCode(respMap.get("returnCode"));
        payRecord.setRespDesc(respMap.get("returnMessage"));
        payRecord.setRespTime(new Date());

        IBankServiceConstant.PayStatus p = IBankServiceConstant.PayStatus.valueOfPayStatus(respMap.get("status"));

        if (null != p) {
            payRecord.setPayStatus(p.getCode().toString());
        }


        return payRecord;
    }


    /**
     * 微信公众号支付
     *
     * @param payRecord
     * @return OrderPayRecord
     */
    public OrderPayRecord weixinMpPay(OrderPayRecord payRecord) {
        Map<String, String> reqMap = baseReqMapParams(BankServiceType.WexinMpPay);

        configPartnerInfo(reqMap, payRecord.getMerchantId());
        //设置请求参数
        payApiCommonParams(reqMap, payRecord);
        //用户标识
        reqMap.put("openId", payRecord.getOpenid());
        //公众号ID
        reqMap.put("appid", payRecord.getAppid());

        Map<String, String> respMap = bankReq(reqMap);
        payRecord.setRespCode(respMap.get("returnCode"));
        payRecord.setRespDesc(respMap.get("returnMessage"));
        payRecord.setRespTime(new Date());

        payRecord.setPrepayId(respMap.get("prepay_id"));
        payRecord.setPaySign(respMap.get("pay_sign"));
        payRecord.setTimeStamp(respMap.get("timeStamp"));
        payRecord.setNoncestr(respMap.get("nonceStr"));

        return payRecord;
    }

    /**
     * 支付宝扫码
     *
     * @param payRecord
     * @return OrderPayRecord
     */
    public OrderPayRecord aliPayQrCode(OrderPayRecord payRecord) {
        Map<String, String> reqMap = baseReqMapParams(BankServiceType.AliPayQrCode);

        configPartnerInfo(reqMap, payRecord.getMerchantId());
        //设置请求参数
        payApiCommonParams(reqMap, payRecord);

        Map<String, String> respMap = bankReq(reqMap);
        payRecord.setRespCode(respMap.get("returnCode"));
        payRecord.setRespDesc(respMap.get("returnMessage"));
        payRecord.setRespTime(new Date());
        payRecord.setOrderQrcode(respMap.get("code_url"));
        return payRecord;
    }


    /**
     * 支付宝条码支付
     *
     * @param payRecord
     * @return
     */
    public OrderPayRecord aliAuthPay(OrderPayRecord payRecord) {
        Map<String, String> reqMap = baseReqMapParams(BankServiceType.AliAuthPay);

        //设置请求参数
        payApiCommonParams(reqMap, payRecord);

        Map<String, String> respMap = bankReq(reqMap);
        payRecord.setRespCode(respMap.get("returnCode"));
        payRecord.setRespDesc(respMap.get("returnMessage"));
        payRecord.setRespTime(new Date());

        PayStatus p = IBankServiceConstant.PayStatus.valueOfPayStatus(respMap.get("status"));
        if (null != p) {
            payRecord.setPayStatus(p.getCode().toString());
        }
        return payRecord;
    }

    /**
     * 支付宝服务窗
     *
     * @param payRecord
     * @return
     */
    public OrderPayRecord aliServicePay(OrderPayRecord payRecord) {
        Map<String, String> reqMap = baseReqMapParams(BankServiceType.AliServicePay);

        configPartnerInfo(reqMap, payRecord.getMerchantId());
        //设置请求参数
        payApiCommonParams(reqMap, payRecord);
        //用户标识
        reqMap.put("openId", payRecord.getOpenid());
        Map<String, String> respMap = bankReq(reqMap);
        payRecord.setRespCode(respMap.get("returnCode"));
        payRecord.setRespDesc(respMap.get("returnMessage"));
        payRecord.setPrepayId(respMap.get("prepay_id"));
        payRecord.setPaySign(respMap.get("pay_sign"));
        payRecord.setTimeStamp(respMap.get("timeStamp"));
        payRecord.setNoncestr(respMap.get("nonceStr"));
        payRecord.setRespTime(new Date());
        return payRecord;

    }


    /**
     * 退款
     *
     * @param refundManage
     * @return
     */
    public RefundManage refund(RefundManage refundManage) {

        Map<String, String> reqMap = baseReqMapParams(BankServiceType.RefundApply);
        configPartnerInfo(reqMap, refundManage.getMerchantId());
        reqMap.put("requestId", refundManage.getRequestId());
        reqMap.put("orderId", refundManage.getOrderId());

        //金额单位设置为分
        String refundAmount = new BigDecimal("100").multiply(refundManage.getRefundAmount()).toString();
        reqMap.put("refundAmount", refundAmount);

        Map<String, String> respMap = bankReq(reqMap);
        refundManage.setRefundApplyTime(new Date());
        refundManage.setRespCode(respMap.get("returnCode"));
        refundManage.setRespDesc(respMap.get("returnMessage"));
        refundManage.setStatus(respMap.get("status"));
        refundManage.setTradeNo(respMap.get("tradeNo"));
        return refundManage;
    }


    /**
     * 查询交易状态
     *
     * @param query
     * @return
     */
    public OrderQuery payStatusQuery(OrderQuery query) {

        Map<String, String> reqMap = baseReqMapParams(BankServiceType.PayStatusQuery);
        configPartnerInfo(reqMap, query.getMerchantId());

        reqMap.put("requestId", query.getRequestId());
        reqMap.put("orderId", query.getOrderId());

//查询设置交易类型
        reqMap.put("trans_type", query.getTransType());

        Map<String, String> respMap = bankReq(reqMap);

        if (!query.getRequestId().equals(respMap.get("requestId"))) {
            query.setRespCode("900");
            query.setRespMsg("返回支付流水号不一致");
            query.setRemarks(respMap.toString());
        }
        query.setRespCode(respMap.get("returnCode"));
        query.setRespMsg(respMap.get("returnMessage"));
        query.setThirdOrdNo(respMap.get("third_ord_no"));
        query.setPayResult(respMap.get("status"));
        String payAmount = respMap.get("totalAmount");
        if (null != payAmount && !payAmount.startsWith("0"))
            payAmount = new BigDecimal(payAmount).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP).toString();
        query.setPayAmount(payAmount);
        try {
            String payTime = respMap.get("payTime");
            String orderTime = respMap.get("orderTime");
            if (StringUtils.isNotBlank(payTime)) {
                query.setPayTime(DateUtils.parseDate(payTime, timePatten));
            }
            if (StringUtils.isNotBlank(orderTime)) {
                query.setOrderTime(DateUtils.parseDate(orderTime, timePatten));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return query;
    }


    /**
     * 订单撤销
     *
     * @param orderCancel
     * @return
     */
    public ApiResp orderCancel(OrderCancel orderCancel) {

        if (null == orderCancel) {
            return new ApiResp(900, "订单不存在", null);
        }
        Map<String, String> reqMap = baseReqMapParams(BankServiceType.OrderCancel);
        configPartnerInfo(reqMap, orderCancel.getMerchantId());
        reqMap.put("requestId", orderCancel.getRequestId());
        reqMap.put("orderId", orderCancel.getOrderId());

        Map<String, String> respMap = bankReq(reqMap);

        orderCancel.setRespTime(new Date());
        orderCancel.setRespDesc(respMap.get("returnMessage"));
        orderCancel.setRespCode(respMap.get("returnCode"));
        if (BankServiceStatus.SUCCESS.getCode().equals(respMap.get("returnCode"))) {
            orderCancel.setEndReTime(new Date());
            return new ApiResp(200, "SUCCESS", JsonMapper.toJsonString(respMap));
        }

        return new ApiResp(900, respMap.get("returnCode"), respMap.get("returnMessage"));
    }


    /**
     * 查询余额
     *
     * @param merchantId
     * @return
     */
    public ApiResp queryBalance(String merchantId) {

        Map<String, String> reqMap = baseReqMapParams(BankServiceType.OrderCancel);

        configPartnerInfo(reqMap, merchantId);

        Map<String, String> respMap = bankReq(reqMap);


        if (BankServiceStatus.SUCCESS.getCode().equals(respMap.get("returnCode"))) {

            return new ApiResp(200, "SUCCESS", respMap.get("bal"));
        }

        return new ApiResp(900, respMap.get("returnCode"), respMap.get("returnMessage"));
    }

    ;


    /**
     * 请求参数设置
     *
     * @param serviceType
     * @return map
     */
    private static Map<String, String> baseReqMapParams(BankServiceType serviceType) {
        // Head参数设置
        Map req = new HashMap();
        req.put("char_set", "02");//UTF-8
        req.put("version_no", versionNo);
        req.put("sign_type", signType);
        req.put("biz_type", serviceType.getBizType());
        req.put("partner_id", partner_id);
        req.put("merc_auto_key", auth_code);

        return req;
    }


    private void configPartnerInfo(Map<String, String> reqMap, String merchantIdOrPartnerId) {
        MerchantPayInfo merchantPayInfo = merchantPayInfoService.fetchByPartnerId(merchantIdOrPartnerId);
        if (null != merchantPayInfo) {
            reqMap.put("partner_id", merchantPayInfo.getPartnerId());
            reqMap.put("merc_auto_key", merchantPayInfo.getAuthCode());
        } else {
            merchantPayInfo = merchantPayInfoService.fetchByPartnerId(merchantIdOrPartnerId);
            if (null != merchantPayInfo) {
                reqMap.put("partner_id", merchantPayInfo.getPartnerId());
                reqMap.put("merc_auto_key", merchantPayInfo.getAuthCode());
            }
        }

    }


    /**
     * 支付API接口公共参数
     *
     * @param reqMap
     * @param payRecord
     * @return
     */
    private Map<String, String> payApiCommonParams(Map reqMap, OrderPayRecord payRecord) {


        BankServiceType serviceType = BankServiceType.valueOfServiceType(payRecord.getPayType());

        //接口为授权扫码支付,没有notify,不设置渠道
        if (BankServiceType.AliAuthPay == serviceType || BankServiceType.WexinAuthPay == serviceType) {
            reqMap.put("auth_code", payRecord.getAuthCode());
        } else {
            reqMap.put("offlineNotifyUrl", notifyUrl);
            reqMap.put("cap_corg", serviceType.getChannel());
            reqMap.put("pay_typ", serviceType.getPayType());
        }

        reqMap.put("clientIP", payRecord.getClientIp());
        reqMap.put("requestId", payRecord.getRequestId());
//        reqMap.put("partner_id", partner_id);
//        reqMap.put("sub_partner_id", payRecord.getSubPartnerId());
        reqMap.put("productName", payRecord.getGoodsName());
        reqMap.put("orderId", payRecord.getOrderId());
        reqMap.put("orderTime", DateUtils.formatDate(payRecord.getOrderTime(), timePatten));
        //金额单位设置为分
        String amount = new BigDecimal(payRecord.getOrderAmount().toString()).multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        reqMap.put("totalAmount", amount);
        reqMap.put("limit_pay", payRecord.getLimitPay());


        return reqMap;
    }

    /**
     * 银行接口封装请求
     *
     * @param reqParams
     * @return map
     */
    private Map<String, String> bankReq(Map<String, String> reqParams) {

        // 封装请求参数
        Map<String, String> retMap = new LinkedHashMap();
        StringBuffer str = new StringBuffer();
        try {
            hytconfig.getConfig().setMerchantId(reqParams.get("partner_id"));
            String reqData = privateRsaSignUtil.coverMap2String(reqParams);
//            str.append("请求参数:+\n" + reqData);
            String reqStr = privateRsaSignUtil.genRequestData(reqData, charSet);
            str.append("\n请求加密串:+\n" + reqData);
            //发送请求
            String res = HytUtil.sendAndRecv(bankServiceUrl, reqStr, charSet);

            str.append("\n返回加密串：" + res);
            //解析返回参数
            String respData = publicRsaSignUtil.getValue(res, "data");
            String signature = publicRsaSignUtil.getValue(res, "signature");
            String partner_id = publicRsaSignUtil.getValue(res, "partner_id");
            String charset = publicRsaSignUtil.getValue(res, "charset");
            retMap.put("data", respData);
            retMap.put("signature", signature);
            retMap.put("partner_id", partner_id);
            retMap.put("charset", charset);

            //验证签名
            boolean flag = false;

            String rspPlainText = new String(publicRsaSignUtil.decryptByPublickey(retMap.get("data").getBytes(charSet)));
            //对于支付平台返回明文数据,可能由于系统查询导致后面带空格的需要trim处理
            flag = publicRsaSignUtil.verifyBySoft(signature, rspPlainText.trim(), charset);
            str.append("\n返回参数：" + rspPlainText);
//            partner_id=800020000020015&RespCode=IPS0001&RespDesc=交易正在维护,请稍后提交                              
            if (rspPlainText.contains("RespCode")) {
                retMap.put("returnCode", "900");
                retMap.put("returnMessage", "银行接口服务不通");
                return retMap;
            }
            if (!flag) {
                //签名验证失败
                retMap.put("returnCode", "900");
                retMap.put("returnMessage", "签名验证失败");

                return retMap;
            }
            Map<String, String> dataMap = publicRsaSignUtil.coverString2Map(rspPlainText);

            retMap.putAll(dataMap);

        } catch (Exception e) {
            retMap.put("returnCode", "901");
            retMap.put("returnMessage", e.getCause().getMessage());
            return retMap;
        } finally {
            // log
            logger.debug(str.toString());
        }
        return retMap;
    }


    /**
     * 费率字符串拼接
     *
     * @param rateMap
     * @param sub_partner_id
     * @return
     */
    private String genRateStr(Map<String, BigDecimal> rateMap, String sub_partner_id) {

        StringBuffer rates = new StringBuffer();
        for (String key : rateMap.keySet()) {
            if (null != rateMap.get(key)) {
                //转成字符串
                String rate = rateMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                rates.append(key.concat(",").concat(rate).concat(",").concat(sub_partner_id)).append("|");
            }
        }
        //去掉最后一个"|"竖线
        String rateStr = rates.toString();

        return rateStr.substring(0, rateStr.length() - 1);
    }


}

