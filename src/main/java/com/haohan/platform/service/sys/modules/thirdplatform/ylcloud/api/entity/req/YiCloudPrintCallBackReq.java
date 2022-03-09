package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/11/26
 */
public class YiCloudPrintCallBackReq implements Serializable {
    private String machine_code;
    private String order_id;
    private String state;
    private String print_time;
    private String origin_id;
    private String push_time;
    private String sign;

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPrint_time() {
        return print_time;
    }

    public void setPrint_time(String print_time) {
        this.print_time = print_time;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }
}
