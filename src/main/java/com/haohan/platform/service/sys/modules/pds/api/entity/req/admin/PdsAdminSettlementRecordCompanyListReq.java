package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求 结算公司(采购商/供应商 商家)
 *
 * @author dy
 * @date 2019/2/22
 */
public class PdsAdminSettlementRecordCompanyListReq {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmID长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "companyType不能为空")
    @Length(min = 0, max = 5, message = "结算商家类型长度必须介于 0 和 5 之间")
    private String companyType;

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
}
