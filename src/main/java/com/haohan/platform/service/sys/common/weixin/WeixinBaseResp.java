package com.haohan.platform.service.sys.common.weixin;

import java.io.Serializable;

public class WeixinBaseResp implements Serializable {
    private static final long serialVersionUID = 4647539800282253316L;

    private Integer errcode = 0;
    private String errmsg = "ok";

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
