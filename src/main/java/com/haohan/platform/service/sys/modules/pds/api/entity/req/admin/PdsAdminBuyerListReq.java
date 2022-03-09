package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求采购商列表
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminBuyerListReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    private int pageNo;
    private int pageSize;
    @Length(min = 0, max = 64, message = "通行证ID长度必须介于 0 和 64 之间")
    private String uid;
    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 64, message = "商家名称长度必须介于 0 和 64 之间")
    private String merchantName;
    @Length(min = 0, max = 64, message = "全称长度必须介于 0 和 64 之间")
    private String buyerName;
    @Length(min = 0, max = 64, message = "采购商简称长度必须介于 0 和 64 之间")
    private String shortName;
    @Length(min = 0, max = 15, message = "联系电话长度必须介于 0 和 15 之间")
    private String telephone;
    @Length(min = 0, max = 5, message = "采购商类型长度必须介于 0 和 5 之间")
    private String buyerType;
    @Length(min = 0, max = 5, message = "是否需确认订单长度必须介于 0 和 5 之间")
    private String needConfirmation;
    @Length(min = 0, max = 5, message = "是否需开启消息推送长度必须介于 0 和 5 之间")
    private String needPush;
    @Length(min = 0, max = 5, message = "是否绑定微信长度必须介于 0 和 5 之间")
    private String bindStatus;
    @Length(min = 0, max = 5, message = "是否启用长度必须介于 0 和 5 之间")
    private String status;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public String getNeedConfirmation() {
        return needConfirmation;
    }

    public void setNeedConfirmation(String needConfirmation) {
        this.needConfirmation = needConfirmation;
    }

    public String getNeedPush() {
        return needPush;
    }

    public void setNeedPush(String needPush) {
        this.needPush = needPush;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
