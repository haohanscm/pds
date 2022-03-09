package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import java.io.Serializable;

/**
 * Created by zgw on 2018/6/11.
 */
public class TLoginReq extends BaseParams implements Serializable{

    private String account;

    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
