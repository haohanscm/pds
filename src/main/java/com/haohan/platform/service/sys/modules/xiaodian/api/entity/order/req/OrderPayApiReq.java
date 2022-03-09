package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/9/10
 */
public class OrderPayApiReq {
    @NotBlank(message = "orderId can not be null")
    private String orderId;
    @NotBlank(message = "payChannel can not be null")
    private String payChannel;
    @NotBlank(message = "payType can not be null")
    private String payType;
//    private String orderType;
    @NotNull(message = "orderAmount can not be null")
    private BigDecimal orderAmount;
    private String partnerId;

    //可选参数
    private String authCode;
    private String openid;
    private String appid;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

//    public String getOrderType() {
//        return orderType;
//    }
//
//    public void setOrderType(String orderType) {
//        this.orderType = orderType;
//    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
