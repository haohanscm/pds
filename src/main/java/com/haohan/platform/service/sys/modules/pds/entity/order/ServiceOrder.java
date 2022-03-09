package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 售后单Entity
 *
 * @author haohan
 * @version 2018-10-20
 */
public class ServiceOrder extends DataEntity<ServiceOrder> {

    private static final long serialVersionUID = 1L;
    private String pmId;        //平台商家
    private String serviceId;    //售后单编号
    private String tradeId;        // 交易单号
    private String deliveryId;        // 配送编号
    private String buyId;        // 采购单编号
    private String buyerId;        // 采购商ID
    private String supplierId;        // 供应商ID
    private String dealResult;        // 处理结果
    private String feedbackInfo;        // 反馈图文
    private BigDecimal refundAmount;        // 退款金额
    private Date deliveryTime;        // 配送时间
    private String serviceCategory;        // 售后分类
    private String stage;        // 阶段
    private String linkMan;        // 联系人
    private String status;        // 状态
    private String linkPhone;        // 联系电话


    //中间属性
    private String buyerName;
    private String supplierName;
    private List<String> feedbackImgList;
    @JsonIgnore
    private String[] feedbackImgIds;
    private String feedbackNote;
    private String pmName;        // 平台商家名称

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public ServiceOrder() {
        super();
    }

    public ServiceOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "交易单号长度必须介于 0 和 64 之间")
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Length(min = 0, max = 64, message = "配送编号长度必须介于 0 和 64 之间")
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Length(min = 0, max = 64, message = "采购商ID长度必须介于 0 和 64 之间")
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Length(min = 0, max = 64, message = "供应商ID长度必须介于 0 和 64 之间")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Length(min = 0, max = 64, message = "处理结果长度必须介于 0 和 64 之间")
    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getFeedbackInfo() {
        return feedbackInfo;
    }

    public void setFeedbackInfo(String feedbackInfo) {
        this.feedbackInfo = feedbackInfo;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Length(min = 0, max = 2, message = "售后分类长度必须介于 0 和 2 之间")
    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    @Length(min = 0, max = 5, message = "阶段长度必须介于 0 和 5 之间")
    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Length(min = 0, max = 64, message = "联系人长度必须介于 0 和 64 之间")
    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    @Length(min = 0, max = 2, message = "状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 64, message = "联系电话长度必须介于 0 和 64 之间")
    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    @Length(min = 0, max = 64, message = "采购单编号长度必须介于 0 和 64 之间")
    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String[] getFeedbackImgIds() {
        return feedbackImgIds;
    }

    public void setFeedbackImgIds(String[] feedbackImgIds) {
        this.feedbackImgIds = feedbackImgIds;
    }

    public String getFeedbackNote() {
        return feedbackNote;
    }

    public void setFeedbackNote(String feedbackNote) {
        this.feedbackNote = feedbackNote;
    }

    public List<String> getFeedbackImgList() {
        return feedbackImgList;
    }

    public void setFeedbackImgList(List<String> feedbackImgList) {
        this.feedbackImgList = feedbackImgList;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public void parseFeedbackInfo() {
        String info = this.getFeedbackInfo();
        if (StringUtils.isNotEmpty(info)) {
            String[] arry = info.split("@_@");
            if (ArrayUtils.isNotEmpty(arry)) {
                String imagIds = arry[0];
                String note = arry[1];
                String[] ids = imagIds.split(",");
                this.setFeedbackImgIds(ids);
                this.feedbackNote = note;
            }
        }

    }
}