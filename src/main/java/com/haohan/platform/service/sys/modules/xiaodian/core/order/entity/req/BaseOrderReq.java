package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req;

import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/9/26
 */
public class BaseOrderReq<D extends BaseOrderDetailReq>{
    @NotBlank(message = "merchantId can not be null")
    private String merchantId;          //商家id
    @NotBlank(message = "shopId can not be null")
    private String shopId;              //店铺id
    private String orderFrom;             //订单来源
    @NotBlank(message = "orderDesc can not be null")
    private String orderDesc;            //订单描述
    @NotNull(message = "orderAmount can not be null")
    private BigDecimal orderAmount;     //订单金额
    @NotBlank(message = "partnerNum can not be null")
    private String partnerNum;          //渠道编号
    @NotNull(message = "orderType can not be null")
    private String orderType;            //订单类型
    private String orderRemark;          //订单备注
    @Valid
    @NotEmpty(message = "orderDetails can not be null")
    private List<D> orderDetails;        //订单明细


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<D> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<D> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public GoodsOrder copyToGoodsOrder(GoodsOrder goodsOrder){
        if (null != goodsOrder){
            goodsOrder.setMerchantId(this.merchantId);
            goodsOrder.setShopId(this.shopId);
            goodsOrder.setOrderFrom(this.orderFrom);
            goodsOrder.setOrderDesc(this.orderDesc);
            goodsOrder.setOrderAmount(this.orderAmount);
            goodsOrder.setPartnerNum(this.partnerNum);
            goodsOrder.setOrderType(this.orderType);
        }
        return goodsOrder;
    }
}
