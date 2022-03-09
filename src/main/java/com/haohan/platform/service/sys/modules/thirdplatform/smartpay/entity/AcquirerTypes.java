package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zgw on 2018/3/22.
 */
public class AcquirerTypes implements Serializable{

    @JsonProperty("acquirerTypes")
    List<AcquirerType> acquirerTypeList;

    public List<AcquirerType> getAcquirerTypeList() {
        return acquirerTypeList;
    }

    public void setAcquirerTypeList(List<AcquirerType> acquirerTypeList) {
        this.acquirerTypeList = acquirerTypeList;
    }
}
