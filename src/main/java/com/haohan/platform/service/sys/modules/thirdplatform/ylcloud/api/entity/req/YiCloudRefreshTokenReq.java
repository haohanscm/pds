package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shenyu
 * @create 2018/11/22
 */
public class YiCloudRefreshTokenReq extends YiCloudBaseReq {
    private String scope;
    @JsonProperty(value = "grant_type")
    private String grant_type;
    @JsonProperty(value = "refresh_token")
    private String refresh_token;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
