package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity;

import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;

/**
 * @author shenyu
 * @create 2019/1/11
 */
public class OrderPayMsgEntity extends MQEntity {
    private String orderId;
    private Integer retryTimes;
    private XmBankEnums.TransType transType= XmBankEnums.TransType.consume;

    public XmBankEnums.TransType getTransType() {
        return transType;
    }

    public void setTransType(XmBankEnums.TransType transType) {
        this.transType = transType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }
}
