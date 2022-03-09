package com.haohan.platform.service.sys.common.exception;

import com.haohan.framework.entity.BaseStatus;

/**
 * @author shenyu
 * @create 2019/3/30
 */
public class ServiceException extends RuntimeException {
    protected Integer code;
    protected String errorMsg;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(BaseStatus baseStatus) {
        this.code = baseStatus.getCode();
        this.errorMsg = baseStatus.getMsg();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
