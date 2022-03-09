package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsBuyShipFeeApiReq extends PdsBaseApiReq {
    @NotBlank(groups = DefaultGroup.class, message = "missing param buyId")
    private String buyId;
    @NotNull(groups = DefaultGroup.class, message = "missing param shipFee")
    private BigDecimal shipFee;

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }
}
