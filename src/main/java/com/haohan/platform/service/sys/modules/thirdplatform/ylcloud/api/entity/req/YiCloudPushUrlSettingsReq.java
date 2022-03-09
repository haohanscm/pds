package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

/**
 * @author shenyu
 * @create 2018/11/26
 */
public class YiCloudPushUrlSettingsReq extends YiCloudBaseReq{
    private String cmd;
    private String url;
    private String status;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
