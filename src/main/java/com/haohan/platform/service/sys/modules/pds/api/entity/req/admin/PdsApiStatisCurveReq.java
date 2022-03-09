package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsApiStatisCurveReq {
    private String pmId;        //平台商家ID
    private String period;      //查询时间段
    private String status;
    private Date startDate;
    private Date endDate;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
