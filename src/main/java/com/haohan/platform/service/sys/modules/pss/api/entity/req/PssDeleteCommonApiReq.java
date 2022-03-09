package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssDeleteCommonApiReq extends PssBaseApiReq{
    @NotBlank(message = "missing param id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
