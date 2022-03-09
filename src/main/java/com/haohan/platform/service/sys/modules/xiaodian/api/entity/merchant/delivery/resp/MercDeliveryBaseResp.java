package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.resp;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/9/28
 */
public class MercDeliveryBaseResp implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
