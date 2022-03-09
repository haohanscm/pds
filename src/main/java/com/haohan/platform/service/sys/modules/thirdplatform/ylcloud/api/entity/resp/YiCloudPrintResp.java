package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.resp;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/11/23
 */
public class YiCloudPrintResp implements Serializable {
    private String id;
    private String origin_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }
}
