package com.haohan.platform.service.sys.modules.xiaodian.api.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author dy
 * @date 2020/7/23
 */
public class MerchantAppReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "商家id不能为空")
    @Length(max = 64, message = "商家id最大长度64字符")
    private String merchantId;

    @NotBlank(message = "appId不能为空")
    @Length(max = 64, message = "appId最大长度64字符")
    private String appId;

    @Length(max = 64, message = "shopId最大长度64字符")
    private String shopId;

    /**
     * 体验用户微信号, 多个使用逗号连接
     */
    @Length(max = 64, message = "体验用户微信号长度最大64字符")
    private String testers;

    // 发布测试时补充参数

    @Length(max = 64, message = "商家账户长度最大64字符")
    private String partnerId;

    @Length(max = 64, message = "应用版本号长度最大64字符")
    private String versionNo;

    @Length(max = 64, message = "应用版本描述长度最大64字符")
    private String versionDesc;

    @Length(max = 64, message = "小程序管理员微信号长度最大64字符")
    private String adminId;

    @Length(max = 64, message = "小程序管理员名称长度最大64字符")
    private String adminName;


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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTesters() {
        return testers;
    }

    public void setTesters(String testers) {
        this.testers = testers;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
