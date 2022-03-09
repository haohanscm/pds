package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author shenyu
 * @create 2019/2/14
 */
public class PdsBuyOrderDetailApiReq extends PdsBaseApiReq {
    @NotBlank(groups = DeleteGroup.class, message = "missing param detailId")
    private String detailId;
    private Date deliveryDate;
    private String buySeq;
    @NotBlank(groups = DefaultGroup.class, message = "missing param buyId")
    private String buyId;


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

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }
}
