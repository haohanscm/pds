package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

/**
 * @author shenyu
 * @create 2018/11/23
 */
public class YiCloudPicturePrintReq extends YiCloudBaseReq {
    private String picture_url;
    private String origin_id;

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }
}
