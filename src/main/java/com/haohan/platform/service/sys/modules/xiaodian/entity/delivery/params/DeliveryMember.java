package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;
import java.util.List;

/**
 * 配送 顾客信息
 */
public class DeliveryMember implements Serializable{

    private String address;   // 配送地址
    private String memberContact;   // 顾客联系方式
    private String memberName;   // 顾客名称
    private String orderRemark;   // 订单备注
    private String status;   // 配送状态
    private List<DeliveryProduct> productList;   // 产品列表

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemberContact() {
        return memberContact;
    }

    public void setMemberContact(String memberContact) {
        this.memberContact = memberContact;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DeliveryProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<DeliveryProduct> productList) {
        this.productList = productList;
    }
}
