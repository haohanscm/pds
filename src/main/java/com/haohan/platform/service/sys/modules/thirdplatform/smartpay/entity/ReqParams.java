package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity;

import java.io.Serializable;

/**
 * Created by zgw on 2018/3/21.
 */
public class ReqParams implements Serializable{

    private String coopMchtId;

    private String acquirerType;

    private String custId;

    public String getCoopMchtId() {
        return coopMchtId;
    }

    public void setCoopMchtId(String coopMchtId) {
        this.coopMchtId = coopMchtId;
    }

    public String getAcquirerType() {
        return acquirerType;
    }

    public void setAcquirerType(String acquirerType) {
        this.acquirerType = acquirerType;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
