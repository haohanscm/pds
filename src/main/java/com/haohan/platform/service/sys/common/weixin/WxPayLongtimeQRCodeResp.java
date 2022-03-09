package com.haohan.platform.service.sys.common.weixin;

import com.haohan.framework.dto.api.ApiRespData;

public class WxPayLongtimeQRCodeResp extends ApiRespData {
    private static final long serialVersionUID = 1L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public WxPayLongtimeQRCodeResp() {
        super();
    }

    public WxPayLongtimeQRCodeResp(String code) {
        super();
        this.code = code;
    }

}
