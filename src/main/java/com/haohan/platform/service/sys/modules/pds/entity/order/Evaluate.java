package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 评价管理Entity
 *
 * @author haohan
 * @version 2018-12-03
 */
public class Evaluate extends DataEntity<Evaluate> {

    private static final long serialVersionUID = 1L;
    private String pmId;        // 平台商家ID
    private String orderId;        // 交易单号
    private String buyOrderId;        // 采购单号
    private String deliveryNo;        // 配送编号
    private String supplierId;        // 供应商
    private String buyerId;        // 采购商
    private String goodsId;        // 商品ID
    private String evaluationDetail;        // 评价图文
    private String evaluationDesc;        // 评价说明
    private String evaluationDate;        // 评价时间
    private String status;        // 状态
    private String evaluationUser;        // 用户

    public Evaluate() {
        super();
    }

    public Evaluate(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "平台商家ID长度必须介于 0 和 64 之间")
    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    @Length(min = 0, max = 64, message = "交易单号长度必须介于 0 和 64 之间")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Length(min = 0, max = 64, message = "采购单号长度必须介于 0 和 64 之间")
    public String getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(String buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    @Length(min = 0, max = 64, message = "配送编号长度必须介于 0 和 64 之间")
    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    @Length(min = 0, max = 64, message = "供应商长度必须介于 0 和 64 之间")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Length(min = 0, max = 64, message = "采购商长度必须介于 0 和 64 之间")
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Length(min = 0, max = 64, message = "商品ID长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Length(min = 0, max = 2000, message = "评价图文长度必须介于 0 和 2000 之间")
    public String getEvaluationDetail() {
        return evaluationDetail;
    }

    public void setEvaluationDetail(String evaluationDetail) {
        this.evaluationDetail = evaluationDetail;
    }

    @Length(min = 0, max = 64, message = "评价说明长度必须介于 0 和 64 之间")
    public String getEvaluationDesc() {
        return evaluationDesc;
    }

    public void setEvaluationDesc(String evaluationDesc) {
        this.evaluationDesc = evaluationDesc;
    }

    @Length(min = 0, max = 64, message = "评价时间长度必须介于 0 和 64 之间")
    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 64, message = "用户长度必须介于 0 和 64 之间")
    public String getEvaluationUser() {
        return evaluationUser;
    }

    public void setEvaluationUser(String evaluationUser) {
        this.evaluationUser = evaluationUser;
    }

}