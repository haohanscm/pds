package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 采购商  采购单 请求参数
 *
 * @author dy
 * @date 2018/12/29
 */
public class PdsBuyerOrderApiReq extends PdsBaseApiReq implements Serializable {

    @NotNull(message = "请选择日期")
    private Date deliveryDate;
    @NotBlank(message = "请选择批次")
    private String buySeq;
    private String merchantId;
    private String status;

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
