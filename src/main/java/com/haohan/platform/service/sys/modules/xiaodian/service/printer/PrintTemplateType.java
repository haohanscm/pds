package com.haohan.platform.service.sys.modules.xiaodian.service.printer;

/**
 * 打印模板类型
 * @author shenyu
 * @create 2019/3/25
 */
public enum  PrintTemplateType {
    NEW_ORDER("0"),
    CANCEL_ORDER("1"),
    UPDATE_ORDER("2");

    private String value;

    PrintTemplateType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
