package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/29
 */
public class PssSupplierPaymentApiReq extends PssPageApiReq {
    @NotBlank(message = "supplierId不能为空")
    private String supplierId;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
