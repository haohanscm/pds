package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 请求采购商货款列表
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminBuyerPaymentListReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    private int pageNo;
    private int pageSize;

    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 64, message = "商家名称长度必须介于 0 和 64 之间")
    private String merchantName;
    @Length(min = 0, max = 64, message = "采购商货款编号长度必须介于 0 和 64 之间")
    private String buyerPaymentId;
    @Length(min = 0, max = 64, message = "采购编号长度必须介于 0 和 64 之间")
    private String buyId;
    @Length(min = 0, max = 64, message = "采购商长度必须介于 0 和 64 之间")
    private String buyerId;
    @Length(min = 0, max = 128, message = "售后单编号必须介于 0 和 128 之间")
    private String serviceId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginBuyDate;        // 开始 采购单的送货日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endBuyDate;        // 结束 采购单的送货日期
    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    private String status;  // 状态:已结/未结

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBuyerPaymentId() {
        return buyerPaymentId;
    }

    public void setBuyerPaymentId(String buyerPaymentId) {
        this.buyerPaymentId = buyerPaymentId;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getBeginBuyDate() {
        return beginBuyDate;
    }

    public void setBeginBuyDate(Date beginBuyDate) {
        this.beginBuyDate = beginBuyDate;
    }

    public Date getEndBuyDate() {
        return endBuyDate;
    }

    public void setEndBuyDate(Date endBuyDate) {
        this.endBuyDate = endBuyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
