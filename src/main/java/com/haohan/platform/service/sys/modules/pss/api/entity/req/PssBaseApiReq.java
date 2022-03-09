package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssBaseApiReq {
    @NotBlank(message = "missing param merchantId")
    private String merchantId;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
