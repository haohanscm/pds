package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssProcureReturnDetailApiReq extends PssPageApiReq {
    @NotBlank(message = "missing param returnId")
    private String returnId;

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }
}
