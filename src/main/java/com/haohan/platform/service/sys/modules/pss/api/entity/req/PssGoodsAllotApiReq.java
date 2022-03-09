package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssGoodsAllotApiReq extends PssPageApiReq {
//    private String merchantId;		// 商家ID
    private String allotNum;		// 调拨单据编号
    private Integer num;		// 数量
    private BigDecimal totalAmount;		// 总金额
    private String allotoutType;		// 调出类型
    private String allotoutId;		// 调出ID
    private String allotinId;		// 调入ID
    private String allotinType;		// 调入类型
    private String oprateNode;		// 操作备注
    private String operator;		// 操作员
    private Date bizTime;			//操作时间
    private String auditStatus;		// 审核状态
    private String orderStatus;		// 订单状态

//    @Override
//    public String getMerchantId() {
//        return merchantId;
//    }
//
//    @Override
//    public void setMerchantId(String merchantId) {
//        this.merchantId = merchantId;
//    }

    public String getAllotNum() {
        return allotNum;
    }

    public void setAllotNum(String allotNum) {
        this.allotNum = allotNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAllotoutType() {
        return allotoutType;
    }

    public void setAllotoutType(String allotoutType) {
        this.allotoutType = allotoutType;
    }

    public String getAllotoutId() {
        return allotoutId;
    }

    public void setAllotoutId(String allotoutId) {
        this.allotoutId = allotoutId;
    }

    public String getAllotinId() {
        return allotinId;
    }

    public void setAllotinId(String allotinId) {
        this.allotinId = allotinId;
    }

    public String getAllotinType() {
        return allotinType;
    }

    public void setAllotinType(String allotinType) {
        this.allotinType = allotinType;
    }

    public String getOprateNode() {
        return oprateNode;
    }

    public void setOprateNode(String oprateNode) {
        this.oprateNode = oprateNode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getBizTime() {
        return bizTime;
    }

    public void setBizTime(Date bizTime) {
        this.bizTime = bizTime;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
