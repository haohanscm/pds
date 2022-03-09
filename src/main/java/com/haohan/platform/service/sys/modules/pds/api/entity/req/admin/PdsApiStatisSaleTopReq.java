package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/18
 */
public class PdsApiStatisSaleTopReq {
    private String pmId;      //平台商家ID
    private String status;
    private int limitNum;
    private Date startTime;
    private Date endTime;
    private String buyerId;

    private String opt;     //排序方式

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

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

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
