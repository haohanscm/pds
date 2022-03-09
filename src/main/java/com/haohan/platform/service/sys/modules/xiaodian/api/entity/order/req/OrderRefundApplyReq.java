package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/10/8
 */
public class OrderRefundApplyReq implements Serializable {
    private String merchantId;
    private String appid;
    private String orderId;
    private BigDecimal refundAmount;
    private String cause;
    private String bankChannle;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getBankChannle() {
        return bankChannle;
    }

    public void setBankChannle(String bankChannle) {
        this.bankChannle = bankChannle;
    }
}
