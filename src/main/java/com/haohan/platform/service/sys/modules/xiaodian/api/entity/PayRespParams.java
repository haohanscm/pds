package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;

import java.io.Serializable;

/**
 * Created by zgw on 2017/12/31.
 */

public class PayRespParams implements Serializable{

    @JsonProperty("timeStamp")
    private String timeStamp;

    @JsonProperty("nonceStr")
    private String nonceStr;

    @JsonProperty("package")
    private String packAge;

    @JsonProperty("paySign")
    private String paySign;

    public PayRespParams() {
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackAge() {
        return packAge;
    }

    public void setPackAge(String packAge) {
        this.packAge = packAge;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public PayRespParams(String timeStamp, String nonceStr, String packAge, String paySign) {
        this.timeStamp = timeStamp;
        this.nonceStr = nonceStr;
        this.packAge = packAge;
        this.paySign = paySign;
    }

    public String toJson(){
        return JsonMapper.toJsonString(this);
    }
}
