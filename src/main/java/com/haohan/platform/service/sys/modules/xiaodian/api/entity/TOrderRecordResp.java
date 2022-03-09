/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TOrderRecord
 * Author:   Lenovo
 * Date:     2018/6/23 11:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayParamsExt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author shenyu
 * @create 2018/6/23
 * @since 1.0.0
 */
public class TOrderRecordResp extends BaseParams implements Serializable {
    private String partnerId;

    private String shopId;

    private String shopName;

    private String merchantId;

    private String merchantName;

    private String orderId;

    private String requestId;

    private String orderName;

    private String orderType;

    private BigDecimal orderAmount;

    private String payChannel;		// 支付渠道

    private String payType;		// 支付方式

    private String payStatus;

    private Date orderTime;

    private OrderPayParamsExt buyerInfo;   //orderInfo封装实体

    private List<GoodsOrderDetail> orderDetails;    //购买商品列表

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public OrderPayParamsExt getBuyerInfo() {
        return buyerInfo;
    }

    public void setBuyerInfo(OrderPayParamsExt buyerInfo) {
        this.buyerInfo = buyerInfo;
    }

    public List<GoodsOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<GoodsOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String toJson(){

        return JacksonUtils.toJson(this);
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
