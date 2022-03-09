package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.framework.utils.JacksonUtils;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/11/22
 */
public class YiCloudBaseResp implements Serializable {
    @JsonProperty(value = "error")
    private String code;
    @JsonProperty(value = "error_description")
    private String msg;
    @JsonProperty(value = "body")
    private Object body;

    public static final String SUCCESS_CODE = "0";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public boolean isSuccess(){
        return SUCCESS_CODE.equals(this.getCode());
    }

    @Override
    public String toString() {
        return JacksonUtils.toJson(this);
    }
}
