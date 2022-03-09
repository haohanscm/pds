package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsDateSeqBaseApiReq extends PdsBaseApiReq {
    @NotNull(groups = DefaultGroup.class, message = "lack of parameter deliveryTime")
    private Date deliveryTime;
    @NotBlank(groups = DefaultGroup.class, message = "lack of parameter buySeq")
    private String buySeq;

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }
}
