package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.pay;

import com.google.gson.Gson;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.request.*;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.*;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.ApmpSecurityUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class ApmpClient implements Serializable{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String HEADER_SIGNATURE = "X-apsignature".toLowerCase();

    private static final String HEADER_APP_KEY = "X-APPKey".toLowerCase();

    private static final String HEADER_BANK_MCHT_ID = "X-BANKMCHTID".toLowerCase();

    public Map<String, String> resHeaderMap = new HashMap<String, String>();

    private String serverUrl= XmBankConfigUtil.getUrl();

    private String appKey=XmBankConfigUtil.getAppKey();

    private String privateKey=XmBankConfigUtil.getCoopPrivateKey();

    private String publicKey=XmBankConfigUtil.getHostPubKey();

    private String bankMchtId=XmBankConfigUtil.getBankMchtId();

    private String publicKeyStr=XmBankConfigUtil.getPublicKeyStr();

    private String privateKeyStr=XmBankConfigUtil.getPrivateKeyStr();


    /**
     * 进件
     *
     * @param request
     * @return
     */
    public MerEnterResponse merEnter(MerEnterRequest request) {
        return (MerEnterResponse) send(request, MerEnterResponse.class);
    }

    /**
     * 进件查询接口
     *
     * @param request
     * @return
     */
    public EnterQueryResponse enterQuery(EnterQueryRequest request) {
        return (EnterQueryResponse) send(request, EnterQueryResponse.class);
    }

    /**
     * 对账单下载
     */
    public BillDownLoadResponse billDownload(BillDownLoadRequest request){
        return (BillDownLoadResponse) send(request,BillDownLoadResponse.class);
    }


    /**
     * json转对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    /**
     * Rest Client.
     */
    private BaseResponse send(BaseRequest request, Class className) {
//		JSONArray jsArray = null;
//		try {
//			jsArray = ApmpClient.reflect(request);
//		} catch (Exception e) {
//			System.out.println("对象解析出错！" + e);
//		}
//		String params = jsArray.toString();
        StringBuffer logStr = new StringBuffer();
        String params = "[" + new Gson().toJson(request) + "]";
        logStr.append("\n-- req: --");
//        System.out.println("-- req: --");
        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add("params", params);
        logStr.append("\nparams=" + params);
//        System.out.println("params=" + params);

        WebResource client = com.sun.jersey.api.client.Client.create().resource(getServerUrl());

        try {
            String key = this.privateKey;
            if(!MerEnterResponse.class.equals(className) && !EnterQueryResponse.class.equals(className)){
                key = this.privateKeyStr;
            }
            String sign = ApmpSecurityUtil.sign(params.getBytes("UTF-8"), key);
            Builder builder = client.type(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .acceptLanguage(Locale.SIMPLIFIED_CHINESE)
                    .header(HEADER_SIGNATURE, sign);// 报文签名

            if(!MerEnterResponse.class.equals(className) && !EnterQueryResponse.class.equals(className)){
                builder.header(HEADER_BANK_MCHT_ID, request.getMerBnkId());//商户号
                logStr.append("\nreq-header:"+ HEADER_BANK_MCHT_ID.concat("=")+request.getMerBnkId());
            }else{
                builder.header(HEADER_APP_KEY, this.getAppKey()); // APPKEY
                logStr.append("\nreq-header:"+ HEADER_APP_KEY.concat("=")+ this.getAppKey());
            }

            ClientResponse res = builder.post(ClientResponse.class, queryParams);
            MultivaluedMap<String, String> header = res.getHeaders();
            logStr.append("\n-- res: --");
//            System.out.println("-- res: --");
            logStr.append("\nheader:	 " + header);
//            System.out.println("header:\t " + header);

            if (res.getType().isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
                String jsonRes = res.getEntity(String.class);
                logStr.append("\nbody:	" + jsonRes);
//                System.out.println("body:\t" + jsonRes);
                // 验签
                sign = header.getFirst(HEADER_SIGNATURE);
                if (ApmpSecurityUtil.verify(jsonRes.getBytes(), sign, publicKey)) {
                    logStr.append("\n验签成功.");
//                    System.out.println("验签成功。 ");
                } else {
                    logStr.append("\n验签失败.");
                }

                // 解析应答报文
                JSONArray arr = JSONArray.fromObject(jsonRes);
                JSONObject obj = (JSONObject) arr.get(0);
                String resCode = obj.getString("responseCode");
                if (resCode.equals("00")) {
                    logStr.append("\n收到成功应答.");
//                    System.out.println("收到成功应答. ");
                }

                // JSONArray responseJsonArray = ApmpClient.fromJson(jsonRes,
                // JSONArray.class);
                // String responseString = responseJsonArray.get(0).toString();

                String responseString = jsonRes.substring(1, jsonRes.length() - 1);
                BaseResponse response = (BaseResponse) ApmpClient.fromJson(responseString, className);
                return response;

            } else {
                logStr.append("\n未定义的应答报文格式.");
//                System.out.println("未定义的应答报文格式. ");
                logStr.append("\n"+res.getEntity(String.class));
//                System.out.println(res.getEntity(String.class));
                BaseResponse response = (BaseResponse) ApmpClient.fromJson("{\"responseCode\":\"400\",\"errorMsg\":\"未定义的报文格式\"}", className);
              return   response;
            }

        } catch (Exception e) {
            e.printStackTrace();
            BaseResponse response = (BaseResponse) ApmpClient.fromJson("{\"responseCode\":\"400\",\"errorMsg\":\"调用接口失败\"}", className);
            return response;
        }finally {
            logger.debug(logStr.toString());
        }
    }


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }





    /**
     * @param  request
     * @return CSBPayResponse 对象 response
     */
    public CSBPayResponse csbPay(CSBPayRequest request) {
        return (CSBPayResponse) send(request, CSBPayResponse.class);
    }

    /**
     * @param  request
     * @return WITHDRAWPayResponse 对象 response
     */
    public WITHDRAWPayResponse withdrawPay(WITHDRAWPayRequest request) {
        return (WITHDRAWPayResponse) send(request, WITHDRAWPayResponse.class);
    }

    /**
     * @param  request
     * @return WithdrawQueryResponse 对象 response
     */
    public WithdrawQueryResponse withdrawQuery(WithdrawQueryRequest request) {
        return (WithdrawQueryResponse) send(request, WithdrawQueryResponse.class);
    }

    /**
     * BSC 接口
     *
     * @param request
     * @return
     */
    public BSCPayResponse bscPay(BSCPayRequest request) {
        return (BSCPayResponse) send(request, BSCPayResponse.class);
    }

    /**
     * APP消费接口（APP支付）
     *
     * @param request
     * @return
     */
    public WechatAppResponse appPay(WechatAppRequest request) {
        return (WechatAppResponse) send(request, WechatAppResponse.class);
    }

    /**
     * js消费接口（种种好支付）
     *
     * @param request
     * @return
     */
    public WechatGzhResponse gzhPay(WechatGzhRequest request) {
        return (WechatGzhResponse) send(request, WechatGzhResponse.class);
    }

    /**
     * H5消费接口
     */
    public H5PayResponse h5Pay(H5PayRequest request) {
        return (H5PayResponse) send(request, H5PayResponse.class);
    }

    /**
     * 撤销接口
     *
     * @param request
     * @return
     */
    public CancelResponse cancel(CancelRequest request) {
        return (CancelResponse) send(request, CancelResponse.class);
    }

    /**
     * 退货接口
     *
     * @param request
     * @return
     */
    public RefundResponse refund(RefundRequest request) {
        return (RefundResponse) send(request, RefundResponse.class);
    }

    /**
     * 交易查询接口
     *
     * @param request
     * @return
     */
    public PayQueryResponse payQuery(PayQueryRequest request) {
        return (PayQueryResponse) send(request, PayQueryResponse.class);
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getBankMchtId() {
        return bankMchtId;
    }

    public void setBankMchtId(String bankMchtId) {
        this.bankMchtId = bankMchtId;
    }


    public String getPublicKeyStr() {
        return publicKeyStr;
    }

    public void setPublicKeyStr(String publicKeyStr) {
        this.publicKeyStr = publicKeyStr;
    }

    public String getPrivateKeyStr() {
        return privateKeyStr;
    }

    public void setPrivateKeyStr(String privateKeyStr) {
        this.privateKeyStr = privateKeyStr;
    }
}
