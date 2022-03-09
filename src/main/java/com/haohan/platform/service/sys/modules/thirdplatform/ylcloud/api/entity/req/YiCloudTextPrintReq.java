package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

/**
 * @author shenyu
 * @create 2018/11/23
 */
public class YiCloudTextPrintReq extends YiCloudBaseReq {
    private String content;     //打印内容
    private String origin_id;   //订单号,32字符以内,同一个client_id下唯一
    private String machine_code;    //打印机终端号

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }
}
