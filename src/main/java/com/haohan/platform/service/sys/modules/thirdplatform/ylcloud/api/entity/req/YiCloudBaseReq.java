package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/11/22
 */
public class YiCloudBaseReq implements Serializable {
    @JsonProperty(value = "client_id")
    private String client_id;        //开发者应用ID,在易联云平台创建应用获得
    @JsonProperty(value = "access_token")
    private String access_token;     //访问令牌
    @JsonProperty(value = "sign")
    private String sign;            //签名 md5(client_id+timestamp+client_secret)
    @JsonProperty(value = "time_stamp")
    private Integer timestamp;
    @JsonProperty(value = "id")
    private String id;              //UUID4

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
