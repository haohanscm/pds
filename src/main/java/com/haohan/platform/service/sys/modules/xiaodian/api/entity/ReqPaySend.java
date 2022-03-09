package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayParamsExt;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgw on 2018/5/28.
 */
public class ReqPaySend extends BaseParams implements Serializable {

    @JsonProperty("pay_channel")
    private String payChannel;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("pay_type")
    private String payType;

    @JsonProperty("shop_id")
    private String shopId;

    @JsonProperty("open_id")
    private String openId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_type")
    private String orderType;

    @JsonProperty("order_desc")
    private String orderDesc;

    @JsonProperty("order_info")
    private OrderPayParamsExt orderInfo;

    @JsonProperty("order_detail")
    private List<GoodsOrderDetail> orderDetail;

    @JsonProperty("order_amount")
    private String orderAmount;

    @JsonProperty("buyer_id")
    private String buyerId;

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("auth_code")
    private String authCode;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("goods_name")
    private String goodsName;

    @JsonProperty("notify_url")
    private String partnerNotifyUrl;

    private String appKey;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public OrderPayParamsExt getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderPayParamsExt orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<GoodsOrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<GoodsOrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getPartnerNotifyUrl() {
        return partnerNotifyUrl;
    }

    public void setPartnerNotifyUrl(String partnerNotifyUrl) {
        this.partnerNotifyUrl = partnerNotifyUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
