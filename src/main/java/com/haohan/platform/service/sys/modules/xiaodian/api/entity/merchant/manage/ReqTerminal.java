package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.modules.xiaodian.entity.teiminal.TerminalManage;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户后台 设备 返回参数
 * Created by dy on 2018/10/9.
 */
public class ReqTerminal implements Serializable {

    private int pageNo;
    private int pageSize;

    private String terminalId;  // 设备id
    private Integer terminalNo;		// 设备编号
    private String terminalType;		// 设备类型
    private String name;		// 设备名称
    private String alias;		// 设备别名
    private String snCode;		// SN码
    private String producer;		// 制造厂商
    private String imeiCode;		// IMEI
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date purchaseTime;		// 购货时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date sellTime;		// 出库时间
    private String merchantId;		// 商家id
    private String shopId;		// 店铺id
    private String shopName;	//店铺名称
    private String status;		// 设备状态
    private String remark;		// 设备备注

    public void transToTerminal(TerminalManage terminalManage){
        terminalManage.setId(this.terminalId);
        terminalManage.setTerminalNo(this.terminalNo);
        terminalManage.setTerminalType(this.terminalType);
        terminalManage.setName(this.name);
        terminalManage.setAlias(this.alias);
        terminalManage.setSnCode(this.snCode);
        terminalManage.setProducer(this.producer);
        terminalManage.setImeiCode(this.imeiCode);
        terminalManage.setPurchaseTime(this.purchaseTime);
        terminalManage.setSellTime(this.sellTime);
        terminalManage.setMerchantId(this.merchantId);
        terminalManage.setShopId(this.shopId);
        terminalManage.setShopName(this.shopName);
        terminalManage.setStatus(this.status);
        terminalManage.setRemark(this.remark);
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

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(Integer terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getImeiCode() {
        return imeiCode;
    }

    public void setImeiCode(String imeiCode) {
        this.imeiCode = imeiCode;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
