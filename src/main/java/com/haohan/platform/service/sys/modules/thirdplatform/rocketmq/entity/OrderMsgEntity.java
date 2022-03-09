package com.haohan.platform.service.sys.modules.thirdplatform.rocketmq.entity;

/**
 * @author shenyu
 * @create 2019/1/11
 */
public class OrderMsgEntity extends MQEntity{
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
