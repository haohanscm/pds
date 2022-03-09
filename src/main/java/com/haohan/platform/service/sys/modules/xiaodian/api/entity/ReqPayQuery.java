/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ReqPayQuery
 * Author:   Lenovo
 * Date:     2018/5/29 19:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/5/29
 * @since 1.0.0
 */
public class ReqPayQuery extends BaseParams implements Serializable {

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("shop_id")
    private String shopId;

    @JsonProperty("trans_id")
    private String transId;

    @JsonProperty("req_id")
    private String reqId;

    @JsonProperty("wallet_order_id")
    private String walletOrderId;

    @JsonProperty("order_id")
    private String orderId;

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

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getWalletOrderId() {
        return walletOrderId;
    }

    public void setWalletOrderId(String walletOrderId) {
        this.walletOrderId = walletOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
