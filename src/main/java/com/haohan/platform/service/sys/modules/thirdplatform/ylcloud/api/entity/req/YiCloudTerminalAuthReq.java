package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shenyu
 * @create 2018/11/22
 */
public class YiCloudTerminalAuthReq extends YiCloudBaseReq {
    @JsonProperty(value = "machine_code")
    private String machine_code;         //易联云打印机终端号
    private String msign;               //易联云终端密钥
    private Integer phone;               //手机号(可选)
    private String print_name;           //自定义打印机名称(可选)

    public String getMsign() {
        return msign;
    }

    public void setMsign(String msign) {
        this.msign = msign;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public String getPrint_name() {
        return print_name;
    }

    public void setPrint_name(String print_name) {
        this.print_name = print_name;
    }
}
