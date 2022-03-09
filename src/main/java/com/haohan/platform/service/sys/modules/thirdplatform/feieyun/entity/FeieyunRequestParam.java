package com.haohan.platform.service.sys.modules.thirdplatform.feieyun.entity;


import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;

import java.io.Serializable;

/**
 * Created by dy on 2018/8/1.
 */
public class FeieyunRequestParam implements Serializable{

    private static final long serialVersionUID = 1L;
    private String printerContent; // 打印机编号(必填) # 打印机识别码(必填) # 备注名称(选填) # 流量卡号码(选填)，多台打印机请换行（\n）添加新打印机信息，每次最多100台。
    private String sn; // 打印机编号
    private String content; // 打印内容,不能超过5000字节
    private String times; // 打印次数，默认为1
    private String snList; // 打印机编号，多台打印机请用减号“-”连接起来。
    private String name; // 打印机备注名称
    private String phoneNum; // 打印机流量卡号码(飞鹅)
    private String orderId; // 订单ID，由接口Open_printMsg返回。
    private String date; // 查询日期，格式YY-MM-DD，如：2016-09-20

    // 设置添加打印机时参数
    public String fetchPrinterContent(FeiePrinter feiePrinter){
        String content = "";
        String sn = feiePrinter.getPrinterSn();
        String key = feiePrinter.getPrinterKey();
        String name = feiePrinter.getPrinterName();
        if(!StringUtils.isAnyEmpty(sn, key)){
            content = sn + "#" + key;
            if(StringUtils.isEmpty(name)){
                content = "#" + name;
            }
        }
        this.printerContent = content;
        return content;
    }

    public String getPrinterContent() {
        return printerContent;
    }

    public void setPrinterContent(String printerContent) {
        this.printerContent = printerContent;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getSnList() {
        return snList;
    }

    public void setSnList(String snList) {
        this.snList = snList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
