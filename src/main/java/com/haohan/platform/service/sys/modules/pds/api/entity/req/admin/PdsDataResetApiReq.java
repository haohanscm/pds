package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsDataResetApiReq extends PdsBaseApiReq {
    @NotNull(groups = DefaultGroup.class, message = "missing param deliveryDate")
    private Date deliveryDate;
    @NotBlank(groups = DefaultGroup.class, message = "missing param buySeq")
    private String buySeq;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }
}
