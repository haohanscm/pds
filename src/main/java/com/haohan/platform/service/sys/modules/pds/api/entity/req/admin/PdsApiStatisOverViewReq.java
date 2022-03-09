package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsApiStatisOverViewReq {
    private String pmId;      //平台商家ID
    private Date startTime;  //查询开始时间
    private Date endTime;    //查询结束时间
    private String status;   //状态可选
    private Date deliveryDate;  //送货日期

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
