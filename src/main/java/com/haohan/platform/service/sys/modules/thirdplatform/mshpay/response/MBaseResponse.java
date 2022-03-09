package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.response;

import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.constant.MshPayConstant;

/**
 * @author shenyu
 * @create 2018/7/27
 */
public class MBaseResponse {
    private Integer code;
    private String msg;
    private Object data;

    public MBaseResponse(){}

    public MBaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static MBaseResponse error(String errorMsg){
        MBaseResponse errorResp =  new MBaseResponse(MshPayConstant.ERROR.getCode(),errorMsg);
        return errorResp;
    }

    public static MBaseResponse success(){
        MBaseResponse success = new MBaseResponse(MshPayConstant.SUCCESS.getCode(),MshPayConstant.SUCCESS.getMsg());
        return success;
    }

    public boolean isSuccess() {
        return MshPayConstant.SUCCESS.getCode().equals(code);
    }

    public String toJson() {
        return JacksonUtils.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
