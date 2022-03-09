package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.DefaultGroup;
import com.haohan.platform.service.sys.common.beanvalidator.DeleteGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsOfferOrderApiReq extends PdsBaseApiReq {
    @NotBlank(groups = DefaultGroup.class, message = "missing param supplierId")
    private String supplierId;
    @NotBlank(groups = DeleteGroup.class, message = "missing param offerOrderId")
    private String offerOrderId;        // 报价单号
    private Date dealTime;        //成交时间
    private String shipStatus;    //发货状态
    @NotNull(groups = DefaultGroup.class, message = "missing param prepareDate")
    private Date prepareDate;    //备货日期
    private String buySeq;      //采购批次
    private String status;      //状态

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getOfferOrderId() {
        return offerOrderId;
    }

    public void setOfferOrderId(String offerOrderId) {
        this.offerOrderId = offerOrderId;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public Date getPrepareDate() {
        return prepareDate;
    }

    public void setPrepareDate(Date prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
