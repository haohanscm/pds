package com.haohan.platform.service.sys.modules.xiaodian.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.Encodes;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.util.TextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgw on 2017/12/15.
 */
public class CommonUtils {


  public static final  String orderPatten = "yyyyMMddHHmmss";


    /**
     * 订单号生成规则  微信三级类目ID+YYYYMMDDHHMMSS+3位随机数 合计20位
     * 支付流水号生成规则  支付方式payType+YYYYMMDDHHMMSS+3位随机数 合计20位
     * @param opId
     * @return
     */
    public static String genId(String opId) {
        StringBuffer orderId = new StringBuffer();
        if (StringUtils.isNotBlank(opId)) {
            orderId.append(opId);
        }
        orderId.append(DateUtils.getDate(orderPatten));
        orderId.append(IdGen.randomBase62(3));
        String oId = orderId.toString();
        if (oId.length() > 20) {
            return oId.substring(0, 20).toLowerCase();
        }

        return oId;
    };

    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, String> beanToStrMap(T bean) {
        Map<String, String> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", String.valueOf(beanMap.get(key)));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map,T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps,Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }


    /**
     * PHP sign
     * @param reqStr
     * @return
     */
    public static String phpMD5sign(String reqStr) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(reqStr.getBytes("GBK"));    //问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
            StringBuffer buf=new StringBuffer();
            for(byte b:md.digest()){
                buf.append(String.format("%02x", b&0xff));
            }

            return buf.toString();
//            System.out.println(buf.toString());
        }catch( Exception e ){
            e.printStackTrace();
        }
        return null;
    }



    public static BaseResp desUserInfo(String encryptedData, String session_key, String iv){

        BaseResp resp = new BaseResp();
        try {
            byte[] resultByte  = WxAES.decrypt(Base64.decodeBase64(encryptedData),
                    Base64.decodeBase64(session_key),
                    Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                String userInfo = new String(resultByte, "UTF-8");
                resp.setExt(userInfo);
                resp.putStatus(RespStatus.SUCCESS);
                return resp;
            }else{
                return resp.error();
            }
        }catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resp;
    }


    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }



    public static String phpSha1Sign(String str) {

        if (null == str) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes("GBK"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String doJsonPost(String urlPath, String Json) {
        // HttpClient 6.0被抛弃了
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept","application/json");
            // 往服务器里面发送数据
            if (Json != null && !TextUtils.isEmpty(Json)) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
//                  ("hlhupload", "doJsonPost: conn"+conn.getResponseCode());
            }
            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 字符串转换unicode
     * @param string
     * @return
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /*
    * unicode编码转中文
    */
    public static String unicode2String(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }


    public static String base64Encode(String str){

        return   Encodes.encodeBase64(str.getBytes());
    }

    public static String base64Decode(String str){

        return  Encodes.decodeBase64String(str);
    }

    public static String amountTransPoint(BigDecimal amount){
        return amountTransPoint(amount.toString());
    }

    public static String amountTransPoint(String amount){
        return new BigDecimal(amount).multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }


    public static BigDecimal amountPointTransYuan(BigDecimal amount){
        return amountPointTransYuan(amount.toString());
    }


    public static BigDecimal amountPointTransYuan(String amount){
       return new BigDecimal(amount).divide(new BigDecimal("100"));
    }


    /**
     * 获取request中参数
     *
     * @param request 页面请求
     */
    public static Map<String, Object> getRequestParameters(HttpServletRequest request) {

        String parameters = "";//请求参数
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if ("GET".equals(request.getMethod())) {//GET请求时的参数
                String urlParameter = request.getQueryString();//网址中的参数
                if (urlParameter != null && !"".equals(urlParameter)) {
                    try {
                        urlParameter = URLDecoder.decode(urlParameter, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    urlParameter = "";
                    return map;
                }
                parameters = urlParameter;
            } else if ("POST".equals(request.getMethod())) {//POST请求时的参数
                String totalParameter = "";//表单及网址中全部参数

                Map<String, String[]> params = request.getParameterMap();
                int parametersNum = request.getParameterMap().size();//参数个数
                if (0 == parametersNum) {
                    return map;
                }
                int flag = 1;
                for (String key : params.keySet()) {
                    if (key.equalsIgnoreCase("file")) {
                        continue;
                    }
                    String[] values = params.get(key);
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                        totalParameter += key + "=" + value;
                    }
                    if (flag < parametersNum) {
                        totalParameter += "&";
                    }
                    flag += 1;
                }
                parameters = totalParameter;
            }
            if (!parameters.contains("&")) {
                String[] arr = parameters.split("=");
                String key = arr[0];
                String value = arr[1];
                map.put(key, value);
                return map;
            }
            String[] arr = parameters.split("&");
            if (arr.length > 0 && ArrayUtils.isNotEmpty(arr)) {
                for (int i = 0; i < arr.length; i++) {
                    String key = arr[i].substring(0, arr[i].indexOf("="));
                    String value = arr[i].substring(arr[i].indexOf("=") + 1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}

