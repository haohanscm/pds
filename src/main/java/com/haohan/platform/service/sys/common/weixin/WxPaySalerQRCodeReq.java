package com.haohan.platform.service.sys.common.weixin;

import com.haohan.framework.dto.api.ApiReq;

public class WxPaySalerQRCodeReq extends ApiReq {

    private static final long serialVersionUID = 1L;

    private String salerUid;

    private Long productTypeId;

    /**
     * 二维码时间 单位：秒
     */
    private Integer timeScope;

    public String getSalerUid() {
        return salerUid;
    }

    public void setSalerUid(String salerUid) {
        this.salerUid = salerUid;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getTimeScope() {
        return timeScope;
    }

    public void setTimeScope(Integer timeScope) {
        this.timeScope = timeScope;
    }

}
