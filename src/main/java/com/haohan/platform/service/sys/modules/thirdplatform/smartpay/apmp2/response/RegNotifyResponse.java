package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response;

import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.AcquirerType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgw on 2018/3/29.
 */
public class RegNotifyResponse implements Serializable{

    private String coopMchtId;
    private String bankMchtId;
    private String mchtName;

    private List<AcquirerType> acquirerTypes;

    public String getCoopMchtId() {
        return coopMchtId;
    }

    public void setCoopMchtId(String coopMchtId) {
        this.coopMchtId = coopMchtId;
    }

    public String getBankMchtId() {
        return bankMchtId;
    }

    public void setBankMchtId(String bankMchtId) {
        this.bankMchtId = bankMchtId;
    }

    public String getMchtName() {
        return mchtName;
    }

    public void setMchtName(String mchtName) {
        this.mchtName = mchtName;
    }

    public List<AcquirerType> getAcquirerTypes() {
        return acquirerTypes;
    }

    public void setAcquirerTypes(List<AcquirerType> acquirerTypes) {
        this.acquirerTypes = acquirerTypes;
    }
}
