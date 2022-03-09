package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.haohan.platform.service.sys.common.mapper.JsonMapper;

import java.io.Serializable;

/**
 * Created by zgw on 2017/12/10.
 */
public class ApiResp implements Serializable{

    private Integer code;
    private String msg;
    private String data;

    public ApiResp() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ApiResp(Integer code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String success(String data) {
        this.code = 200;
        this.msg = "success";
        this.data = data;
        return JsonMapper.toJsonString(this);
    }

    public String fail(String msg) {
        this.code = 900;
        this.msg = msg;
        return JsonMapper.toJsonString(this);
    }

    public String toJson(){
        return JsonMapper.toJsonString(this);
    }
}
