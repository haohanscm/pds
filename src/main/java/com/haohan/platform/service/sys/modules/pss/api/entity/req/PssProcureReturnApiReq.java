package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/12/28
 */
public class PssProcureReturnApiReq extends PssPageApiReq {
    private String returnId;        //id
    private String returnNum;		// 退货编号
    private Integer goodsNum;		// 商品数量
    private String payType;		// 结账方式
    private String supplierId;		// 供应商ID
    private String operator;		// 操作员
    private Date bizTime;		// 业务时间
    private String returnStatus;		// 退货状态

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(String returnNum) {
        this.returnNum = returnNum;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }
}
