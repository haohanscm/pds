package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/9/6
 */
public class GoodsOrderApiReq implements Serializable {
    @NotBlank(message = "appid不能为空")
    private String appid;         //微信appid
    @NotBlank(message = "merchantId不能为空")
    private String merchantId;		// 商户ID
    private String partnerNum;      //渠道编号
    @NotBlank(message = "orderId不能为空")
    private String orderId;		// 订单编号
    private String shopId;		// 店铺ID
    private String orderType;       //订单类型
    @NotNull(message = "orderDesc不能为空")
    private String orderDesc;		//订单描述
    private String orderMarks;		// 订单备注
    @NotBlank(message = "uid不能为空")
    private String uid;		    // 用户ID
    private String userName;    //用户名称
    private String jsAddressId;     //会员地址id
    private BigDecimal shippingFee;		// 配送费
    private String orderInfo;           //用户信息
    @NotNull(message = "orderAmount不能为空")
    private BigDecimal orderAmount;		//订单金额
    private BigDecimal reducedAmount;       //优惠金额

    //补充
    @NotBlank(message = "orderFrom不能为空")
    private String orderFrom;       //订单来源

    //商品明细
    @NotBlank(message = "goodsDetails不能为空")
    private String goodsDetails;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPartnerNum() {
        return partnerNum;
    }

    public void setPartnerNum(String partnerNum) {
        this.partnerNum = partnerNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
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

    public BigDecimal getReducedAmount() {
        return reducedAmount;
    }

    public void setReducedAmount(BigDecimal reducedAmount) {
        this.reducedAmount = reducedAmount;
    }

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getJsAddressId() {
        return jsAddressId;
    }

    public void setJsAddressId(String jsAddressId) {
        this.jsAddressId = jsAddressId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }
}
