package com.haohan.platform.service.sys.common.service;

import com.haohan.platform.service.sys.common.mapper.JsonMapper;

/**
 * Created by zgw on 2017/7/26.
 */
public class AppResp {

    private Integer code;
    private String msg;
    private Object obj;

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

    public AppResp(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String success(Object obj) {
        this.code = 200;
        this.msg = "success";
        this.obj = obj;
        return JsonMapper.toJsonString(this);
    }

    public static String needPay(String msg) {

        return new JsonMapper().toJsonString(new AppResp(900, msg));
    }
}
