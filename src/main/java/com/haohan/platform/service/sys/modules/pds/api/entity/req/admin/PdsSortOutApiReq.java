package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.beanvalidator.EditGroup;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author shenyu
 * @create 2018/12/14
 */
public class PdsSortOutApiReq extends PdsBaseApiReq {
    @NotBlank(groups = EditGroup.class, message = "missing param tradeId")
    private String tradeId;
    @NotNull(groups = EditGroup.class, message = "missing param sortOutNum")
    private BigDecimal sortOutNum;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public BigDecimal getSortOutNum() {
        return sortOutNum;
    }

    public void setSortOutNum(BigDecimal sortOutNum) {
        this.sortOutNum = sortOutNum;
    }
}
