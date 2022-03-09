package com.haohan.platform.service.sys.modules.weixin.open.api.entity;

import me.chanjar.weixin.open.util.json.WxOpenGsonBuilder;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2019/2/25
 */
public class WxOpenBaseResp implements Serializable {
    private Integer errcode;
    private String errmsg;

    public WxOpenBaseResp() {
        super();
    }

    public Boolean isSuccess(){
        if (null != errcode && 0 != errcode){
            return false;
        }else
            return true;
    }

    public static WxOpenBaseResp fromJson(String json) {
        return WxOpenGsonBuilder.create().fromJson(json, WxOpenBaseResp.class);
    }

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

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
