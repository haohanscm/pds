package com.haohan.platform.service.sys.modules.pds.exception;

import com.haohan.framework.entity.BaseStatus;
import com.haohan.platform.service.sys.common.exception.ServiceException;

/**
 * @author shenyu
 * @create 2018/11/9
 */
public class PdsSummaryOperationException extends ServiceException {
    public PdsSummaryOperationException() {
    }

    public PdsSummaryOperationException(String message) {
        super(message);
    }

    public PdsSummaryOperationException(BaseStatus baseStatus) {
        super(baseStatus);
    }
}
