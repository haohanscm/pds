package com.haohan.platform.service.sys.modules.pds.api.entity.req.common;

import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsBaseApiReq;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author shenyu
 * @create 2018/12/17
 */
public class PdsTextPrintApiReq extends PdsBaseApiReq {
    @NotBlank(message = "请选择打印机编号")
    private String machineCode;
    @NotBlank(message = "订单编号不能为空")
    private String orderId;
    @NotBlank(message = "打印内容不能为空")
    private String content;

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
