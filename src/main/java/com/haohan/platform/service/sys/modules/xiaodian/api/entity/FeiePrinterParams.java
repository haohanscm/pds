package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;

public class FeiePrinterParams extends BaseParams implements Serializable {

    private String shopId;  // 店铺ID
    private String printerId;  // 打印机Id
    private String useable;  // 启用状态
    private String name; // 打印机名称
    private String times; // 打印次数
    private String orderId; // orderPayRecord 订单id

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPrinterId() {
        return printerId;
    }

    public void setPrinterId(String printerId) {
        this.printerId = printerId;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
