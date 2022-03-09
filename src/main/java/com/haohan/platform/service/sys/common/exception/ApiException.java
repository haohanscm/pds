package com.haohan.platform.service.sys.common.exception;

import com.haohan.framework.entity.BaseStatus;

/**
 * Created by zgw on 2018/9/26.
 */
public class ApiException extends RuntimeException {

    /**
     * 结果码
     */
    protected Integer code;
    /**
     * 结果说明
     */
    protected String msg;

    /**
     * 扩展数据
     */
//    protected Serializable ext;
    public ApiException(BaseStatus baseStatus) {
        this.setCode(baseStatus.getCode());
        this.setMsg(baseStatus.getMsg());
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
}
