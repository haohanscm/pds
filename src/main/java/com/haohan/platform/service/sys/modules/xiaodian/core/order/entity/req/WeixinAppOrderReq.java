package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req;

import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryRules;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.GoodsGifts;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.SaleRules;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ServiceSelection;

import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/9/30
 */
@Deprecated
public class WeixinAppOrderReq {
    private String orderInfo;           //订单收货信息
    private String appid;               //微信appid
    private String addressId;           //地址id
    private String memberId;            //下单用户
    private String memberName;          //下单用户名称

    private DeliveryRules deliveryRules;    //配送规则
    private SaleRules saleRules;            //售卖规则
    private ServiceSelection serviceSelection;  //服务选项
    private GoodsGifts goodsGifts;          //赠品

    private String merchantId;  //商家id
    private String orderId;     //订单id
    private String orderFrom;   //订单来源
    private String orderDesc;   //订单描述
    private BigDecimal orderAmount;     //订单金额
    private String partnerNum;  //渠道编号
    private String orderType;   //订单类型
    private String orderRemark; //订单备注
    private String orderDetails;        //订单明细


    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public DeliveryRules getDeliveryRules() {
        return deliveryRules;
    }

    public void setDeliveryRules(DeliveryRules deliveryRules) {
        this.deliveryRules = deliveryRules;
    }

    public SaleRules getSaleRules() {
        return saleRules;
    }

    public void setSaleRules(SaleRules saleRules) {
        this.saleRules = saleRules;
    }

    public ServiceSelection getServiceSelection() {
        return serviceSelection;
    }

    public void setServiceSelection(ServiceSelection serviceSelection) {
        this.serviceSelection = serviceSelection;
    }

    public GoodsGifts getGoodsGifts() {
        return goodsGifts;
    }

    public void setGoodsGifts(GoodsGifts goodsGifts) {
        this.goodsGifts = goodsGifts;
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

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPartnerNum() {
        return partnerNum;
    }

    public void setPartnerNum(String partnerNum) {
        this.partnerNum = partnerNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }
}
