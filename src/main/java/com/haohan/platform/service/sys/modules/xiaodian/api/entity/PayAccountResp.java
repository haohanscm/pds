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
 * 账户查询请求返回
 */
public class PayAccountResp extends BaseParams implements Serializable {

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("shop_id")
    private String shopId;

    @JsonProperty("pay_url")
    private String payUrl;//请求地址

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

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

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
}
