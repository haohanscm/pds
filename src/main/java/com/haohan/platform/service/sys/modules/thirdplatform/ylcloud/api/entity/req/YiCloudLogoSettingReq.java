package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

/**
 * @author shenyu
 * @create 2018/11/26
 */
public class YiCloudLogoSettingReq extends YiCloudBaseReq {
    private String machine_code;
    private String img_url;

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
