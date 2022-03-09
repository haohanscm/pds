package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.pay;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.haohan.framework.utils.MD5Util;
import com.haohan.platform.service.sys.common.utils.JsonHttpUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util.XmBankConfigUtil;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.constant.MshPayConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.request.*;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response.*;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author shenyu
 * @create 2018/7/19
 */
@Service
public class MshPay {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String sign_key = XmBankConfigUtil.getSignKey();

    private String base_url = XmBankConfigUtil.getMshPayUrl();

    private String csb_url = base_url.concat("createPC");

    private String bsc_url = base_url.concat("micropay");

    private String jspay_url = base_url.concat("preCreate");

    private String order_query_url = base_url.concat("orderQuery");

    private String order_refund_url = base_url.concat("orderRefund");

    /**
     * 获取签名
     * @return
     */
    public String getSign(Map<String,Object> paramsMap,String signKey){
        String result = "";
        try {
            List<Map.Entry<String, Object>> entryList = new ArrayList<Map.Entry<String, Object>>(paramsMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(entryList, new Comparator<Map.Entry<String, Object>>() {
                public int compare(Map.Entry<String, Object> entry1, Map.Entry<String, Object> entry2) {
                    return (entry1.getKey()).toString().compareTo(entry2.getKey());
                }
            });

            // 构造签名键值对字符串的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> item : entryList) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    if ("goods_detail".equals(key)) {
                        continue;
                    }
                    Object val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }
            }
            sb.append("key="+signKey);
            result = sb.toString();

            //MD5加密
//            result = MD5Util.MD5Encode(result,"utf-8");
            result = MD5Util.MD5(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * 发送请求
     * @param request
     * @param clazz
     * @param reqUrl
     * @return
     */
    public MBaseResponse send(MBaseRequest request, Class clazz, String reqUrl){
        MBaseResponse resp = new MBaseResponse();

        Map<String,Object> paramsMap;
//        Map<String,Object> respMap = new HashMap<>();

        StringBuffer logStr = new StringBuffer();
//        String params = "[" + new Gson().toJson(request) + "]";
        logStr.append("\n"+"\n-- req: --");

        try {
            //生成签名
            paramsMap = CommonUtils.beanToMap(request);
            String reqSign = getSign(paramsMap,sign_key);
            request.setSign(reqSign);
            logStr.append("\n"+request.toJson());

            //发送请求
            JSONObject jsonObjectReq = JSONObject.fromObject(request);
//            String resStr = CommonUtils.doJsonPost(reqUrl,request.toJson());
            JSONObject jsonObjectResp = JsonHttpUtils.doPost(reqUrl,jsonObjectReq);

            //返回entity为空处理
            if (null == jsonObjectResp){
                jsonObjectResp.put("code", MshPayConstant.ERROR.getCode());
                jsonObjectResp.put("msg", MshPayConstant.ERROR.getMsg());
            }
//            logger.debug("\nrespData: "+jsonObjectResp);
            logStr.append("\n-- resp: --");

            //验签 TODO

            Integer code = jsonObjectResp.getInt("code");
            String msg = jsonObjectResp.getString("msg");
            String data = jsonObjectResp.getString("data");
            String jsonRes = jsonObjectResp.toString();

//            Object object = JSONObject.toBean(jsonObjectResp);
            if (!code.equals(MshPayConstant.SUCCESS.getCode())){
                logStr.append("\n"+jsonRes+"\n");
                //返回错误信息
                resp = (MBaseResponse) MshPay.fromJson(jsonRes,clazz,code,msg);
                return resp;
            }
            logStr.append("\nbody: " + jsonRes+"\n");

            resp = (MBaseResponse) MshPay.fromJson(data,clazz,code,msg);

//            respMap = JacksonUtils.readMapValue(respData,Object.class);  //验签用

            return resp;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.debug(logStr.toString());
        }
        return resp;
    }

    /**
     * 主扫支付
     * @return
     */
    public MCSBPayResponse csbPay(MCSBPayRequest csbPayRequest){
        return (MCSBPayResponse) send(csbPayRequest, MCSBPayResponse.class,csb_url);
    }

    /**
     * 被扫支付
     * @return
     */
    public MBSCPayResponse bscPay(MBSCPayRequest bscPayRequest){
        return (MBSCPayResponse) send(bscPayRequest, MBSCPayResponse.class,bsc_url);
    }

    /**
     * 公众号,服务窗支付
     * @return
     */
    public MJSPayResponse jsPay(MJSPayRequest jsPayRequest){
        return (MJSPayResponse) send(jsPayRequest, MJSPayResponse.class,jspay_url);
    }

    /**
     * 订单查询
     * @return
     */
    public MOrderQueryResponse orderQuery(MOrderQueryRequest orderQueryRequest){
        return (MOrderQueryResponse) send(orderQueryRequest, MOrderQueryResponse.class,order_query_url);
    }

    /**
     * 退款
     * @param orderRefundRequest
     * @return
     */
    public MOrderRefundResponse orderRefund(MOrderRefundRequest orderRefundRequest){
        return (MOrderRefundResponse) send(orderRefundRequest, MOrderRefundResponse.class,order_refund_url);
    }

    public static <T> T fromJson(String str, Class<T> type , Integer code , String msg) {
        Gson gson = new Gson();
        T t = gson.fromJson(str,type);
        JsonObject object = new Gson().toJsonTree(t).getAsJsonObject();
        object.addProperty("code",code);
        object.addProperty("msg",msg);
//        String result = object.getAsString();
        return gson.fromJson(object.toString(), type);
    }
}
