package com.haohan.platform.service.sys.modules.pds.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author dy
 * @date 2018/12/14.
 */
public class PdsBuyerReq implements Serializable {

    @NotBlank(message = "pmId不能为空")
    private String pmId; // 平台商家id
    private String buyerId;        // 采购商

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
