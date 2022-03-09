package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author dy
 * @create 2018/12/26
 */
public class PdsRangeAmountReq implements Serializable {
    @NotNull(message = "请选择日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotNull(message = "请选择日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @NotBlank(message = "请选择平台商家")
    private String pmId;
    private String merchantId;
    private String buyerId;

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

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
}
