package com.haohan.platform.service.sys.common.weixin;

import com.haohan.framework.dto.api.ApiReq;

public class WxPaySalerQRCodeMoreInfoReq extends ApiReq {

    private static final long serialVersionUID = 1L;
    /**** 卡号 ***/
    private String vipCard;

    /****** 销售人员Id *******/
    private String salerUid;

    /****** 产品父类Id *******/
    private String pId;//

    /****** 二维码内容 *******/
    private String qrcode;

    /******* 最初的卡号 *****/
    private String originalCard;

    private String key;
    /**
     * 二维码时间 单位：秒
     */
    private Integer timeScope;

    private Long productTypeId;

    public String getVipCard() {
        return vipCard;
    }

    public void setVipCard(String vipCard) {
        this.vipCard = vipCard;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getOriginalCard() {
        return originalCard;
    }

    public void setOriginalCard(String originalCard) {
        this.originalCard = originalCard;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getTimeScope() {
        return timeScope;
    }

    public void setTimeScope(Integer timeScope) {
        this.timeScope = timeScope;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getSalerUid() {
        return salerUid;
    }

    public void setSalerUid(String salerUid) {
        this.salerUid = salerUid;
    }

}
