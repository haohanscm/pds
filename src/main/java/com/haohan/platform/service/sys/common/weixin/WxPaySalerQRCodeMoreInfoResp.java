package com.haohan.platform.service.sys.common.weixin;

import com.haohan.framework.dto.api.ApiRespData;

public class WxPaySalerQRCodeMoreInfoResp extends ApiRespData {
    private static final long serialVersionUID = 1L;
    /**
     * 缓存中的Key
     */
    private String key;
    /**
     * 缓存中key的时间
     */
    private Long keyTimeScope;
    /**
     * 真正二维码内容
     */
    private WxSalesQrcode qrcode;
    /**
     * 二维码的时间
     */
    private Long qrcodeTimeScope;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getKeyTimeScope() {
        return keyTimeScope;
    }

    public void setKeyTimeScope(Long keyTimeScope) {
        this.keyTimeScope = keyTimeScope;
    }

    public WxSalesQrcode getQrcode() {
        return qrcode;
    }

    public void setQrcode(WxSalesQrcode qrcode) {
        this.qrcode = qrcode;
    }

    public Long getQrcodeTimeScope() {
        return qrcodeTimeScope;
    }

    public void setQrcodeTimeScope(Long qrcodeTimeScope) {
        this.qrcodeTimeScope = qrcodeTimeScope;
    }

}
