package com.haohan.platform.service.sys.common.utils.wx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WxUtils {

    /**** 微信最低版本 ****/
    public static BigDecimal WX_MIN_VERSION = new BigDecimal("5.0");

    /**** 微信版本前缀 ****/
    public static String VERSION_PREFIX = "MicroMessenger";

    // Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like
    // Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501
    // NetType/WIFI WindowsWechat
    /**** 正则匹配微信版本号 ****/
    public static Pattern pattern = Pattern.compile(VERSION_PREFIX + "\\/([\\d\\.]+)");

//	@Resource
//	private HttpUtils httpUtils;

    public static synchronized String getNonceStr() {
        String currTime = WxUtils.getCurrTime();// 8位日期
        return currTime.substring(8, currTime.length()).concat(WxUtils.buildRandom(4).toString());
    }

    /***
     * 是否满足最低版本要求
     *
     * @param header
     * @return
     */
    public static boolean isSuite(String header) {
        String v = getWxVersion(header);
        BigDecimal cur = new BigDecimal(v);
        if (cur.compareTo(WX_MIN_VERSION) >= 0) {
            return true;
        }
        return false;
    }

    /***
     * 获取微信版本号
     *
     * @param header
     * @return
     */
    public static String getWxVersion(String header) {
        Matcher matcher = pattern.matcher(header);
        String ver = "";
        while (matcher.find()) {
            ver = matcher.group();
        }
        // MicroMessenger/6.3.13.49
        ver = ver.replace(VERSION_PREFIX, "");
        return ver.substring(1, ver.indexOf(".") + 2);
    }

    /***
     * 判断请求是否来自微信
     *
     * @param header
     * @return
     */
    public static boolean isWx(String header) {
        Matcher matcher = pattern.matcher(header);
        String ver = "";
        while (matcher.find()) {
            ver = matcher.group();
        }

        if (ver.contains(VERSION_PREFIX)) {
            return true;
        }
        return false;
    }

    /**
     * 把对象转换成字符串
     *
     * @param obj
     * @return String 转换成字符串,若对象为null,则返回空字符串.
     */
    public static String toString(Object obj) {
        if (obj == null)
            return "";

        return obj.toString();
    }

    /**
     * 把对象转换为int数值.
     *
     * @param obj 包含数字的对象.
     * @return int 转换后的数值,对不能转换的对象返回0。
     */
    public static int toInt(Object obj) {
        int a = 0;
        try {
            if (obj != null)
                a = Integer.parseInt(obj.toString());
        } catch (Exception e) {

        }
        return a;
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     *
     * @return String
     */
    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    /**
     * 获取当前日期 yyyyMMdd
     *
     * @param date
     * @return String
     */
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String strDate = formatter.format(date);
        return strDate;
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static Integer buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 获取编码字符集
     *
     * @param request
     * @param response
     * @return String
     */

    public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {

        if (null == request || null == response) {
            return "gbk";
        }

        String enc = request.getCharacterEncoding();
        if (null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }

        if (null == enc || "".equals(enc)) {
            enc = "gbk";
        }
        return enc;
    }

    public static String URLencode(String content) {
        return content.replaceAll("\\+", "%20");
    }

    /**
     * 获取unix时间，从1970-01-01 00:00:00开始的秒数
     *
     * @param date
     * @return long
     */
    public static Long getUnixTime(Date date) {
        if (null == date) {
            return 0L;
        }

        return date.getTime() / 1000;
    }

    public static String QRfromGoogle(String chl) {
        int widhtHeight = 300;
        String EC_level = "L";
        int margin = 0;
        String QRfromGoogle;
        chl = URLencode(chl);

        QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight + "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;

        return QRfromGoogle;
    }

    /**
     * 时间转换成字符串
     *
     * @param date       时间
     * @param formatType 格式化类型
     * @return String
     */
    public static String date2String(Date date, String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(date);
    }

    public static Map<String, String> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    String value = (String) getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;
    }

    /****
     * 将一个输入流转化为字符串
     *
     * @param tInputStream
     * @return
     */
    public static String getStreamString(InputStream tInputStream) {
        if (tInputStream != null) {
            try {
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
                StringBuffer tStringBuffer = new StringBuffer();
                String sTempOneLine = new String("");
                while ((sTempOneLine = tBufferedReader.readLine()) != null) {
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
