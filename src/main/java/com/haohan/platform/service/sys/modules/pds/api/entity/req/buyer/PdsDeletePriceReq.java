package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author dy
 * @date 2019/8/21
 */
public class PdsDeletePriceReq {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmId长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "priceId不能为空")
    @Length(min = 0, max = 64, message = "priceId长度必须介于 0 和 64 之间")
    private String priceId;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }
}
