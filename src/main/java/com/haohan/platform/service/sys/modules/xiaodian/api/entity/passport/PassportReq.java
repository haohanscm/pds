package com.haohan.platform.service.sys.modules.xiaodian.api.entity.passport;

import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;

/**
 * Created by zgw on 2018/12/3.
 */
public class PassportReq extends UPassport{

    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
