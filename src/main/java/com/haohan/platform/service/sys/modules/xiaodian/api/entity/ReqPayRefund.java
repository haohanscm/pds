/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ReqPayRefund
 * Author:   Lenovo
 * Date:     2018/5/29 19:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Lenovo
 * @create 2018/5/29
 * @since 1.0.0
 */
public class ReqPayRefund extends BaseParams implements Serializable {

    @JsonProperty("shop_id")
    private String shopId;

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("order_amount")
    private BigDecimal orderAmount;

    @JsonProperty("refund_amount")
    private BigDecimal refundAmount;

    @JsonProperty("org_trans_id")
    private String orgTransId;

    @JsonProperty("org_req_id")
    private String orgReqId;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrgTransId() {
        return orgTransId;
    }

    public void setOrgTransId(String orgTransId) {
        this.orgTransId = orgTransId;
    }

    public String getOrgReqId() {
        return orgReqId;
    }

    public void setOrgReqId(String orgReqId) {
        this.orgReqId = orgReqId;
    }
}
