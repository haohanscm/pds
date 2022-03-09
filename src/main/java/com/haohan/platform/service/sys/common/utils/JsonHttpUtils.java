package com.haohan.platform.service.sys.common.utils;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by shenyu 2018/07/26
 */
public class JsonHttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonHttpUtils.class);

    /**
     * json 字符串
     *
     * @param url
     * @param param
     * @return
     */
    public static String getSerchPersion(String url, String param) {
        HttpClient httpClient = new HttpClient();
        // 设置 Http 连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        // 设置 get 请求超时为 5 秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";
        // Get请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断响应码
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("请求出错: " + getMethod.getStatusLine());
            }
            // HTTP响应头部信息，这里简单打印
            Header[] headers = getMethod.getResponseHeaders();
            for (Header h : headers)
                logger.debug(h.getName() + "------------ " + h.getValue());
            // 读取 HTTP 响应内容，这里简单打印网页内容
            byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
            response = new String(responseBody, param);
            logger.debug("----------response:" + response);
            // 读取为 InputStream，在网页内容数据量大时候推荐使用
            // InputStream response = getMethod.getResponseBodyAsStream();
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            logger.info("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            logger.error("发生网络异常!");
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return response;
    }

    /**
     * post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, JSONObject json) {
        CloseableHttpClient client = HttpClients.createDefault();
        JSONObject response = null;
        try {
            HttpPost post = new HttpPost(url);
            String result = "";
            String jsStr = json.toString();
            ByteArrayEntity byteEntity = new ByteArrayEntity(jsStr.getBytes("UTF-8"));
            byteEntity.setContentType("application/json");
            post.setEntity(byteEntity);

//            StringEntity strEntity = new StringEntity(json.toString());
//            strEntity.setContentEncoding("UTF-8");
//            strEntity.setContentType("application/json");
//            post.setEntity(strEntity);
//            System.out.println("--request--"+post.toString());
//            HttpResponse res = client.execute(post);
            CloseableHttpResponse resp = client.execute(post);
            try {
                logger.info("HTTP-request-" + resp.getStatusLine().getStatusCode());
                if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    logger.info("http连接失败.");
                    return null;
                }
                HttpEntity entity = resp.getEntity();
                if (null == entity) {
                    return null;
                }
                result = EntityUtils.toString(entity);
                response = JSONObject.fromObject(result);
                EntityUtils.consume(entity);
            } finally {
                resp.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }


    public static String httpPostWithJson(String url, String json) {
        String returnValue = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();
            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);
            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler);
            //调接口获取返回值时，必须用此方法
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // 第五步：处理返回值
        return returnValue;
    }


}