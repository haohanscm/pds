package com.haohan.platform.service.sys.common.utils.wx;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * '微信支付服务器签名支付请求请求类 '============================================================================ '============================================================================
 */
@Component
public class RequestHandler {

    /**
     * 预支付网关url地址
     */
    private String gateUrl;

    private String charset;

    /**
     * debug信息
     */
    private String debugInfo;

    private String last_errcode;

    public String getGateUrl() {
        return gateUrl;
    }

    public void setGateUrl(String gateUrl) {
        this.gateUrl = gateUrl;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public String getLast_errcode() {
        return last_errcode;
    }

    public void setLast_errcode(String last_errcode) {
        this.last_errcode = last_errcode;
    }

    /**
     * 初始构造函数。
     *
     * @return
     */
    public RequestHandler() {
        this.last_errcode = "0";
        this.charset = "UTF-8";

    }

    /**
     * 获取参数值
     *
     * @param parameter 参数名称
     * @return String
     */
    public String getParameter(String parameter, SortedMap<String, String> parameters) {
        String s = parameters.get(parameter);
        return null == s ? "" : s;
    }

    // 特殊字符处理
    public String UrlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, this.charset).replace("+", "%20");
    }

    // 获取package的签名包
    public String genPackage(SortedMap<String, String> packageParams) throws UnsupportedEncodingException {
        String sign = this.createSign(packageParams);

        StringBuffer sb = new StringBuffer();
        Set<?> es = packageParams.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + this.UrlEncode(v) + "&");
        }

        // 去掉最后一个&
        String packageValue = sb.append("sign=" + sign).toString();
        // System.out.println("UrlEncode后 packageValue=" + packageValue);
        return packageValue;
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public String createSign(SortedMap<String, String> packageParams) {
        StringBuffer sb = new StringBuffer();
        Set<?> es = packageParams.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
//		sb.append("key=" + IWxConfigConstant.privateKey);//partnerKey
//		LogUtils.serviceLog("RequestHandler md5 sb:" + sb);
        String sign = MD5Util.MD5Encode(sb.toString(), this.charset).toUpperCase();
//		LogUtils.serviceLog("RequestHandler packge签名:" + sign);
        return sign;

    }

    /**
     * 创建package签名
     */
    public boolean checkMd5Sign(SortedMap<String, String> parameters) {
        String sign = createSign(parameters);
        return sign.equals(parameters.get("sign"));
    }

    // 输出XML
    public String parseXML(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set<?> es = parameters.entrySet();
        Iterator<?> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">" + getParameter(k, parameters) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /***
     * 参数化的xml转map 并去除签名值用于校验
     *
     * @param xml
     * @return
     */
    public static Map<String, String> xml2Map(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            Document doc = DocumentHelper.parseText(xml);
            if (doc == null) {
                return map;
            }
            Element root = doc.getRootElement();
            for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext(); ) {
                Element e = (Element) iterator.next();
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // map.remove("sgin");
        return map;
    }

}
