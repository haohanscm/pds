package com.haohan.platform.service.sys.modules.xiaodian.exception;

/**
 * @author shenyu
 * @create 2018/12/10
 */
public class StorageOperationException extends Exception {
    public StorageOperationException(String message) {
        super(message);
    }

    public StorageOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
