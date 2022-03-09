package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 请求 结算金额
 *
 * @author dy
 * @date 2019/2/15
 */
public class PdsAdminSettlementRecordAmountReq {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmID长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "companyId不能为空")
    @Length(min = 0, max = 64, message = "结算商家ID长度必须介于 0 和 64 之间")
    private String companyId;
    @NotBlank(message = "companyType不能为空")
    @Length(min = 0, max = 5, message = "结算商家类型长度必须介于 0 和 5 之间")
    private String companyType;
    @NotNull(message = "请选择开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @NotNull(message = "请选择结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
