package com.haohan.platform.service.sys.modules.pds.api.entity.req.delivery;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsShipBuyerApiReq extends PdsBaseApiReq {
    @NotNull(message = "请先选择日期")
    private Date deliveryDate;
    @NotBlank(message = "请选择批次")
    private String buySeq;
    @NotBlank(message = "uid不能为空")
    private String uid;
    private String driverId;
    private String buyerId;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
