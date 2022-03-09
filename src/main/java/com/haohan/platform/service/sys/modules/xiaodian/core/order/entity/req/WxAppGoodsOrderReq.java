package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2019/1/2
 */
public class WxAppGoodsOrderReq extends BaseOrderReq<WxOrderDetailReq> {
    @NotBlank(message = "appid can not be null")
    private String appid;               //微信appid
    private String addressId;           //收货地址地址id
    @NotBlank(message = "uid can not be null")
    private String uid;            //下单用户
    private String jsAddressId;         //即速地址id
    private String deliveryType;        //配送方式
    private BigDecimal shippingFee;     //配送费
    private String orderId;             //订单编号

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getJsAddressId() {
        return jsAddressId;
    }

    public void setJsAddressId(String jsAddressId) {
        this.jsAddressId = jsAddressId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
