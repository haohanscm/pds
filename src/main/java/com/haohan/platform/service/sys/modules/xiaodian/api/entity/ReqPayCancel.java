/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PayCancel
 * Author:   Lenovo
 * Date:     2018/5/29 18:41
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
public class ReqPayCancel extends BaseParams implements Serializable {


    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("shop_id")
    private String shopId;

    @JsonProperty("order_id")
    private String orderId;

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
