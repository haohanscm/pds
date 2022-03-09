package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/10/6
 */
public class MercSaleOrderResp extends DataEntity implements Serializable {
    private String merchantId;  //商家id
    private String orderId;		// 订单编号
    private String payId;		// 支付流水号
    private String shopId;		// 店铺ID
    private String shopName;		// 店铺名称
    private String orderStatus;		// 订单状态
    private String orderFrom;		// 订单来源
    private String orderType;       //订单类型
    private String orderDesc;		//订单描述
    private String orderMarks;		// 订单备注
    private String appid;           //微信appid
    private String uid;		// 用户ID
    private String userName;		// 用户名称
    private BigDecimal insureFee;		// 保价费用
    private BigDecimal shippingFee;		// 配送费用
    private Date shippingTime;		// 发货时间
    private String payStatus;		// 支付状态
    private Date payTime;		// 支付时间
    private String payType;     //支付方式
    private String orderInfo;   //收货信息
    private BigDecimal orderAmount;		//订单金额
    private Date orderTime;			//下单时间
    private String deliveryType;      //配送方式
    private String addressId;
    private List<MercGoodsOrderDetailResp> goodsList;       //商品列表

//    //查询字段
//    private Date beginTime;
//    private Date endTime;
//
//    public Date getBeginTime() {
//        return beginTime;
//    }
//
//    public void setBeginTime(Date beginTime) {
//        this.beginTime = beginTime;
//    }

//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderMarks() {
        return orderMarks;
    }

    public void setOrderMarks(String orderMarks) {
        this.orderMarks = orderMarks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getInsureFee() {
        return insureFee;
    }

    public void setInsureFee(BigDecimal insureFee) {
        this.insureFee = insureFee;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<MercGoodsOrderDetailResp> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<MercGoodsOrderDetailResp> goodsList) {
        this.goodsList = goodsList;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
