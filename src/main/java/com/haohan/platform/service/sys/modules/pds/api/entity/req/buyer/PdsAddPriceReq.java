package com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dy
 * @date 2019/8/21
 */
public class PdsAddPriceReq {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmId长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "buyerMerchantId不能为空")
    @Length(min = 0, max = 64, message = "buyerMerchantId长度必须介于 0 和 64 之间")
    private String merchantId;
    @NotNull(message = "请选择开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotNull(message = "请选择结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotBlank(message = "modelId不能为空")
    @Length(min = 0, max = 64, message = "modelId长度必须介于 0 和 64 之间")
    private String modelId;

    @NotNull(message = "price商品定价不能为空")
    private BigDecimal price;

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

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
