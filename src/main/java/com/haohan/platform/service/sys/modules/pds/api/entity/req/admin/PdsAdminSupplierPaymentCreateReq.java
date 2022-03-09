package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 请求 供应商货款生成
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminSupplierPaymentCreateReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    @NotBlank(message = "供应商Id不能为空")
    private String supplierId;
    @NotNull(message = "请选择供应日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date supplyDate;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }
}
