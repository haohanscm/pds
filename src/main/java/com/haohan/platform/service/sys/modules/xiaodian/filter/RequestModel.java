package com.haohan.platform.service.sys.modules.xiaodian.filter;

import java.util.Date;

/**
 * Created by zgw on 2018/4/6.
 */
public class RequestModel {


    private static final ThreadLocal<RequestModel> REQUEST_MODEL = new ThreadLocal<>();
    /**平台**/
    private String platform;
    /**版本**/
    private String version;
    /**产品**/
    private String product;
    /**开始时间**/
    private Long startMillis;
    /**当前请求用户sessionId**/
    private String sessionId;

    private String reqUri;//请求地址

    private String reqParams;//Json串

    private Object respParams;//返回值

    private String httpMethod;//HTTP-POST

    private Date reqTime;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(Long startMillis) {
        this.startMillis = startMillis;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getReqParams() {
        return reqParams;
    }

    public void setReqParams(String reqParams) {
        this.reqParams = reqParams;
    }

    public static RequestModel getRequestModel() {
        return REQUEST_MODEL.get();
    }

    public static void setRequestModel(RequestModel request){
        REQUEST_MODEL.set(request);
    }

    public static void removeRequestModel(){
        REQUEST_MODEL.remove();
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public Object getRespParams() {
        return respParams;
    }

    public void setRespParams(Object respParams) {
        this.respParams = respParams;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }
}
