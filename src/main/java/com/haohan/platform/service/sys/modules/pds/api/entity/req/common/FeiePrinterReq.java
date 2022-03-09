package com.haohan.platform.service.sys.modules.pds.api.entity.req.common;


import com.haohan.platform.service.sys.modules.xiaodian.entity.printer.FeiePrinter;

import java.io.Serializable;

/**
 * 商户后台 飞鹅打印机 请求参数
 * Created by dy on 2018/10/12.
 */
public class FeiePrinterReq implements Serializable {

    private int pageNo;
    private int pageSize;
    private String shopId;
    private String printerType;        // 打印机类型
    private String printerSn;        // 打印机编号
    private String printerKey;        // 打印机秘钥
    private String printerName;        // 打印机名
    private String template;        // 打印模板,小票样式
    private String useable;     // 打印机是否启用,0 -否 1-是
    private String category;    // 可打印分类  对应菜品/商品的分类名称
    private String times;       // 打印次数
    private String remarks;  // 小票打印备注

    private String printerId;  // 打印机id

    private String pmId; // 平台商家id

//    private String status;		// 云打印状态,是否添加至飞鹅云,0 -否 1-是


    // 属性复制到feiePrinter
    public void transToPrinter(FeiePrinter feiePrinter) {
        feiePrinter.setId(this.printerId);
        feiePrinter.setShopId(this.shopId);
        feiePrinter.setPrinterType(this.printerType);
        feiePrinter.setPrinterSn(this.printerSn);
        feiePrinter.setPrinterKey(this.printerKey);
        feiePrinter.setPrinterName(this.printerName);
        feiePrinter.setTemplate(this.template);
        feiePrinter.setUseable(this.useable);
        feiePrinter.setCategory(this.category);
        feiePrinter.setTimes(this.times);
        feiePrinter.setRemarks(this.remarks);
    }

    public String getPrinterId() {
        return printerId;
    }

    public void setPrinterId(String printerId) {
        this.printerId = printerId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPrinterType() {
        return printerType;
    }

    public void setPrinterType(String printerType) {
        this.printerType = printerType;
    }

    public String getPrinterSn() {
        return printerSn;
    }

    public void setPrinterSn(String printerSn) {
        this.printerSn = printerSn;
    }

    public String getPrinterKey() {
        return printerKey;
    }

    public void setPrinterKey(String printerKey) {
        this.printerKey = printerKey;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }
}
