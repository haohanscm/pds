package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/9/17
 */
public class DeliveryReplaceReq implements Serializable {
    private String orgDeliveryManId;
    private String newDeliveryManId;
    private Date startDate;
    private Date endDate;

    public String getOrgDeliveryManId() {
        return orgDeliveryManId;
    }

    public void setOrgDeliveryManId(String orgDeliveryManId) {
        this.orgDeliveryManId = orgDeliveryManId;
    }

    public String getNewDeliveryManId() {
        return newDeliveryManId;
    }

    public void setNewDeliveryManId(String newDeliveryManId) {
        this.newDeliveryManId = newDeliveryManId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
