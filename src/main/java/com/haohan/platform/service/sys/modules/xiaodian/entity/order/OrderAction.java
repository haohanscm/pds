package com.haohan.platform.service.sys.modules.xiaodian.entity.order;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 订单操作Entity
 *
 * @author haohan
 * @version 2017-08-06
 */
public class OrderAction extends DataEntity<OrderAction> {

    private static final long serialVersionUID = 1L;
    private String orderId;        // 操作交易号
    private String actionUser;        // 操作人员
    private Integer orderStatus;        // 订单状态
    private Integer shippingStatus;        // 发货状态
    private String payStatus;        // 支付状态

    public OrderAction() {
        super();
    }

    public OrderAction(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "操作交易号长度必须介于 0 和 64 之间")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Length(min = 0, max = 64, message = "操作人员长度必须介于 0 和 64 之间")
    public String getActionUser() {
        return actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}