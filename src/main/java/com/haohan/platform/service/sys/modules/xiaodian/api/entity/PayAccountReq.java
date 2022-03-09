/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ReqPayQuery
 * Author:   Lenovo
 * Date:     2018/5/29 19:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 账户查询请求
 */
public class PayAccountReq extends BaseParams implements Serializable {

    @JsonProperty("app_id")
    private String appId;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


}
