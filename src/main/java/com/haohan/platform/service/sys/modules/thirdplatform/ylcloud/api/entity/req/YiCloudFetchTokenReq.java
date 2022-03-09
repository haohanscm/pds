package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shenyu
 * @create 2018/11/22
 */
public class YiCloudFetchTokenReq extends YiCloudBaseReq{
    private String scope;
    @JsonProperty(value = "grant_type")
    private String grant_type;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
