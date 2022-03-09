package com.haohan.platform.service.sys.modules.pds.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采购商管理Entity
 *
 * @author haohan
 * @version 2018-10-19
 */
@JsonIgnoreProperties({"createDate", "isNewRecord", "updateDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsBuyer extends DataEntity<PdsBuyer> {

    private static final long serialVersionUID = 1L;
    private String pmId;        //平台商家
    @JsonProperty("uid")
    private String passportId;        // 通行证ID
    private String merchantId;        // 商家ID
    private String merchantName;  // 商家名称
    private String buyerName;        // 全称
    private String shortName;        // 采购商名称
    private String contact;        // 联系人
    private String telephone;        // 联系电话
    private String wechatId;        // 微信
    private String operator;        // 操作员
    private String payPeriod;        // 账期
    private String payDay;        // 账期日
    private String address;        // 采购商地址
    private String needConfirmation;        // 是否需确认订单
    private String buyerType;  // 采购商类型:老板/员工/运营
    private String needPush;        // 是否开启消息推送
    private String status;        // 是否启用 字典 yes_no
    private String sort;        // 排序

    //补充
    private String selfOrderAddress;    //自提地址

    private String pmName;        // 平台商家名称
    private String bindStatus;  // 是否绑定微信

    public PdsBuyer() {
        super();
    }

    public PdsBuyer(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "通行证ID长度必须介于 0 和 64 之间")
    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 64, message = "采购商全称长度必须介于 0 和 64 之间")
    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    @Length(min = 0, max = 64, message = "采购商简称长度必须介于 0 和 64 之间")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Length(min = 0, max = 64, message = "联系人长度必须介于 0 和 64 之间")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Length(min = 0, max = 15, message = "联系电话长度必须介于 0 和 15 之间")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Length(min = 0, max = 64, message = "微信长度必须介于 0 和 64 之间")
    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    @Length(min = 0, max = 64, message = "操作员长度必须介于 0 和 64 之间")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Length(min = 0, max = 64, message = "账期长度必须介于 0 和 64 之间")
    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    @Length(min = 0, max = 200, message = "供应商地址长度必须介于 0 和 200 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min = 0, max = 5, message = "是否需确认订单长度必须介于 0 和 5 之间")
    public String getNeedConfirmation() {
        return needConfirmation;
    }

    public void setNeedConfirmation(String needConfirmation) {
        this.needConfirmation = needConfirmation;
    }

    @Length(min = 0, max = 64, message = "商家名称长度必须介于 0 和 64 之间")
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSelfOrderAddress() {
        return selfOrderAddress;
    }

    public void setSelfOrderAddress(String selfOrderAddress) {
        this.selfOrderAddress = selfOrderAddress;
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

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    @Length(min = 0, max = 5, message = "采购商类型长度必须介于 0 和 5 之间")
    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    @Length(min = 0, max = 5, message = "是否需开启消息推送长度必须介于 0 和 5 之间")
    public String getNeedPush() {
        return needPush;
    }

    public void setNeedPush(String needPush) {
        this.needPush = needPush;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPayDay() {
        return payDay;
    }

    public void setPayDay(String payDay) {
        this.payDay = payDay;
    }
}