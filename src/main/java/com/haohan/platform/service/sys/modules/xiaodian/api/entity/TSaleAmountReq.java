/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TSaleAmountReq
 * Author:   Lenovo
 * Date:     2018/6/14 20:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;

/**
 * @author Lenovo
 * @create 2018/6/14
 * @since 1.0.0
 */
public class TSaleAmountReq extends BaseParams implements Serializable {

    private String shopId;

    private String partnerId;

    private String period;

    private String payType;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
