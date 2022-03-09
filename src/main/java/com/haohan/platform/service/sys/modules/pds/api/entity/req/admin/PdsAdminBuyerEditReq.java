package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求 采购商编辑
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminBuyerEditReq {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmId长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "采购商全称不能为空")
    @Length(min = 0, max = 64, message = "采购商全称长度必须介于 0 和 64 之间")
    private String buyerName;
    @NotBlank(message = "联系人不能为空")
    @Length(min = 0, max = 64, message = "联系人长度必须介于 0 和 64 之间")
    private String contact;
    @NotBlank(message = "电话不能为空")
    @Length(min = 0, max = 15, message = "电话长度必须介于 0 和 15 之间")
    private String telephone;
    @NotBlank(message = "采购商地址不能为空")
    @Length(min = 0, max = 64, message = "采购商地址长度必须介于 0 和 64 之间")
    private String address;

    private String id;
    @Length(min = 0, max = 64, message = "通行证ID长度必须介于 0 和 64 之间")
    private String uid;        // 通行证ID
    @Length(min = 0, max = 64, message = "采购商简称长度必须介于 0 和 64 之间")
    private String shortName;
    @Length(min = 0, max = 64, message = "微信长度必须介于 0 和 64 之间")
    private String wechatId;
    @Length(min = 0, max = 64, message = "操作员长度必须介于 0 和 64 之间")
    private String operator;
    @Length(min = 0, max = 64, message = "账期长度必须介于 0 和 64 之间")
    private String payPeriod;
    @Length(min = 0, max = 5, message = "是否需确认订单长度必须介于 0 和 5 之间")
    private String needConfirmation;
    @Length(min = 0, max = 5, message = "是否需开启消息推送长度必须介于 0 和 5 之间")
    private String needPush;
    @Length(min = 0, max = 5, message = "是否启用长度必须介于 0 和 5 之间")
    private String status;
    @Length(min = 0, max = 5, message = "排序长度必须介于 0 和 5 之间")
    private String sort;
    @Length(min = 0, max = 500, message = "备注长度必须介于 0 和 500 之间")
    String remarks;

    // 默认项

    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 5, message = "采购商类型长度必须介于 0 和 5 之间")
    private String buyerType;  // 采购商类型:老板/员工/运营
    private String merchantName;

    /**
     * 参数转换
     *
     * @param buyer
     */
    public void transToBuyer(PdsBuyer buyer) {
        buyer.setId(this.id);
        buyer.setPmId(this.pmId);
        if (null != this.uid) {
            buyer.setPassportId(this.uid);
        }
        if (null != this.merchantId) {
            buyer.setMerchantId(this.merchantId);
        }
        if (null != this.merchantName) {
            buyer.setMerchantName(this.merchantName);
        }
        if (null != this.buyerName) {
            buyer.setBuyerName(this.buyerName);
        }
        if (null != this.shortName) {
            buyer.setShortName(this.shortName);
        }
        if (null != this.contact) {
            buyer.setContact(this.contact);
        }
        if (null != this.telephone) {
            buyer.setTelephone(this.telephone);
        }
        if (null != this.wechatId) {
            buyer.setWechatId(this.wechatId);
        }
        if (null != this.operator) {
            buyer.setOperator(this.operator);
        }
        if (null != this.payPeriod) {
            buyer.setPayPeriod(this.payPeriod);
        }
        if (null != this.address) {
            buyer.setAddress(this.address);
        }
        if (null != this.needConfirmation) {
            buyer.setNeedConfirmation(this.needConfirmation);
        }
        if (null != this.buyerType) {
            buyer.setBuyerType(this.buyerType);
        }
        if (null != this.needPush) {
            buyer.setNeedPush(this.needPush);
        }
        if (null != this.status) {
            buyer.setStatus(this.status);
        }
        if (null != this.sort) {
            Integer num = StringUtils.toInteger(this.sort);
            if (num <= 0) {
                this.sort = "100";
            }
            buyer.setSort(this.sort);
        }
        if (null != this.remarks) {
            buyer.setRemarks(this.remarks);
        }
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeedConfirmation() {
        return needConfirmation;
    }

    public void setNeedConfirmation(String needConfirmation) {
        this.needConfirmation = needConfirmation;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
