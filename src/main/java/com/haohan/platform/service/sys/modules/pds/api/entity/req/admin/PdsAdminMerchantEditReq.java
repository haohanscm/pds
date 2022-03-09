package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求 商家 编辑
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminMerchantEditReq {

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmId长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "名称不能为空")
    @Length(min = 0, max = 50, message = "名称长度必须介于 0 和 50 之间")
    private String merchantName;
    @NotBlank(message = "商家地址不能为空")
    @Length(min = 0, max = 500, message = "商家地址长度必须介于 0 和 500 之间")
    private String address;
    @NotBlank(message = "联系人名称不能为空")
    @Length(min = 0, max = 50, message = "联系人长度必须介于 0 和 50 之间")
    private String contact;
    @NotBlank(message = "电话不能为空")
    @Length(min = 0, max = 50, message = "电话长度必须介于 0 和 50 之间")
    private String telephone;
    @NotBlank(message = "状态不能为空")
    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    private String status;

    @Length(min = 0, max = 64, message = "商家id长度必须介于 0 和 64 之间")
    private String id;
    @Length(min = 0, max = 64, message = "归属用户id长度必须介于 0 和 64 之间")
    private String uid;        // 归属用户
    @Length(min = 0, max = 5, message = "商家的店铺类型长度必须介于 0 和 5 之间")
    private String merchantType;        //商家的店铺类型  0:餐饮   1:零售
    @Length(min = 0, max = 64, message = "区域长度必须介于 0 和 64 之间")
    private String area;        // 区域
    @Length(min = 0, max = 50, message = "行业名称长度必须介于 0 和 50 之间")
    private String industry;        // 行业名称
    @Length(min = 0, max = 50, message = "规模长度必须介于 0 和 50 之间")
    private String scale;        // 规模
    @Length(min = 0, max = 1000, message = "业务介绍长度必须介于 0 和 1000 之间")
    private String bizDesc;        // 业务介绍
    @Length(min = 0, max = 64, message = "业务专管员长度必须介于 0 和 64 之间")
    private String user;        // 业务专管员
    @Length(min = 0, max = 64, message = "渠道编号长度必须介于 0 和 64 之间")
    private String partnerNum;    //渠道编号
    @Length(min = 0, max = 5, message = "采购配送商家类型长度必须介于 0 和 5 之间")
    private String pdsType;  // 采购配送商家类型  0 普通商家  1 平台商家
    @Length(min = 0, max = 5, message = "产品类型长度必须介于 0 和 5 之间")
    private String prodType;    //产品类型
    @Length(min = 0, max = 5, message = "是否启用自动分单长度必须介于 0 和 5 之间")
    private String isAutomaticOrder;        //是否启用自动分单
    @Length(min = 0, max = 5, message = "产品线类型长度必须介于 0 和 5 之间")
    private String productLine;  // 产品线类型
    @Length(min = 0, max = 500, message = "备注长度必须介于 0 和 500 之间")
    String remarks;    // 备注

    /**
     * 参数转换
     *
     * @param merchant
     */
    public void transToMerchant(Merchant merchant) {
        merchant.setParentId(this.pmId);
        merchant.setId(this.id);

        if (null != this.uid) {
            merchant.setUpassport(new UPassport(this.uid));
        }
        if (null != this.merchantName) {
            merchant.setMerchantName(this.merchantName);
        }
        if (null != this.merchantType) {
            merchant.setMerchantType(this.merchantType);
        }
        if (StringUtils.isNotEmpty(this.area)) {
            merchant.setArea(new Area(this.area));
        }
        if (null != this.address) {
            merchant.setAddress(this.address);
        }
        if (null != this.contact) {
            merchant.setContact(this.contact);
        }
        if (null != this.telephone) {
            merchant.setTelephone(this.telephone);
        }
        if (null != this.industry) {
            merchant.setIndustry(this.industry);
        }
        if (null != this.scale) {
            merchant.setScale(this.scale);
        }
        if (null != this.bizDesc) {
            merchant.setBizDesc(this.bizDesc);
        }
        if (StringUtils.isNotEmpty(this.user)) {
            merchant.setUser(new User(this.user));
        }
        if (null != this.partnerNum) {
            merchant.setPartnerNum(this.partnerNum);
        }
        if (null != this.status) {
            merchant.setStatus(StringUtils.toInteger(this.status));
        }
        if (null != this.pdsType) {
            merchant.setPdsType(this.pdsType);
        }
        if (null != this.prodType) {
            merchant.setProdType(this.prodType);
        }
        if (null != this.isAutomaticOrder) {
            merchant.setIsAutomaticOrder(this.isAutomaticOrder);
        }
        if (null != this.remarks) {
            merchant.setRemarks(this.remarks);
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getBizDesc() {
        return bizDesc;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPartnerNum() {
        return partnerNum;
    }

    public void setPartnerNum(String partnerNum) {
        this.partnerNum = partnerNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPdsType() {
        return pdsType;
    }

    public void setPdsType(String pdsType) {
        this.pdsType = pdsType;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getIsAutomaticOrder() {
        return isAutomaticOrder;
    }

    public void setIsAutomaticOrder(String isAutomaticOrder) {
        this.isAutomaticOrder = isAutomaticOrder;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
