package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2019/1/3
 */
public class WebGoodsOrderApiReq extends BaseOrderReq<BaseOrderDetailReq> {
    private String addressId;
    private String deliveryType;
    @NotBlank(message = "uid can not be null")
    private String uid;
    private String orderId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
