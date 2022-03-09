package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.auth;


import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.AuthAccount;

import java.io.Serializable;

/**
 * 商户后台 管理信息请求参数
 * Created by dy on 2018/10/9.
 */
public class ReqAuthAccount implements Serializable {

    private String authAccountId;		// 认证信息id
    private String authType;		// 认证类型
    private String merchantId;		// 商家id
    private String idCardName;		// 姓名
    private String idCardNumber;		// 身份证号
    private String status;		// 账号状态
    private String idCardFront;		// 身份证头像面
    private String idCardBack;		// 身份证国徽面
    private String handIdCard;		// 手持身份证
    private String companyName;		// 企业/组织名称
    private String shorthand;		// 企业/组织简称
    private String businessLicenseNo;		// 营业执照编号
    private String businessLicenseImg;		// 营业执照照片
    private String licenseExpireDate;		// 营业执照有效期
    private String businessProvince;		// 归属省
    private String businessCity;		// 归属市
    private String businessArea;		// 归属区
    private String businessAddress;		// 经营地址
    private String accountLicenseNo;		// 开户许可证编号
    private String accountLicenseImg;		// 开户许可证照片
    private String settlementType;		// 结算账号公私类型;对公/对私
    private String settlementCardno;		// 结算卡卡号
    private String settlementBank;		// 结算卡开户行
    private String settlementProvice;		// 开户行省
    private String settlementCity;		// 开户行市
    private String settlementArea;		// 开户行区
    private String bankbranchName;		// 支行名称
    private String bankbranchNo;		// 支行行号
    private String settlementBankno;		// 清算行号
    private String adminTelephone;		// 管理员手机号
    private String adminEmail;		// 管理员邮箱
    private String bankcardFront;		// 银行卡正面
    private String bankcardBack;		// 银行卡反面
    private String supplyImgGroup;		// 补充资料图片组编号

    public void transToAuthAccount(AuthAccount authAccount){
        authAccount.setId(this.authAccountId);
        authAccount.setAuthType(this.authType);
        authAccount.setMerchantId(this.merchantId);
        authAccount.setIdCardName(this.idCardName);
        authAccount.setIdCardNumber(this.idCardNumber);
        authAccount.setStatus(this.status);
        authAccount.setIdCardFront(this.idCardFront);
        authAccount.setIdCardBack(this.idCardBack);
        authAccount.setHandIdCard(this.handIdCard);
        authAccount.setCompanyName(this.companyName);
        authAccount.setShorthand(this.shorthand);
        authAccount.setBusinessLicenseNo(this.businessLicenseNo);
        authAccount.setBusinessLicenseImg(this.businessLicenseImg);
        authAccount.setLicenseExpireDate(this.licenseExpireDate);
        authAccount.setBusinessProvince(this.businessProvince);
        authAccount.setBusinessCity(this.businessCity);
        authAccount.setBusinessArea(this.businessArea);
        authAccount.setBusinessAddress(this.businessAddress);
        authAccount.setAccountLicenseNo(this.accountLicenseNo);
        authAccount.setAccountLicenseImg(this.accountLicenseImg);
        authAccount.setSettlementType(this.settlementType);
        authAccount.setSettlementCardno(this.settlementCardno);
        authAccount.setSettlementBank(this.settlementBank);
        authAccount.setSettlementProvice(this.settlementProvice);
        authAccount.setSettlementCity(this.settlementCity);
        authAccount.setSettlementArea(this.settlementArea);
        authAccount.setBankbranchName(this.bankbranchName);
        authAccount.setBankbranchNo(this.bankbranchNo);
        authAccount.setSettlementBankno(this.settlementBankno);
        authAccount.setAdminTelephone(this.adminTelephone);
        authAccount.setAdminEmail(this.adminEmail);
        authAccount.setBankcardFront(this.bankcardFront);
        authAccount.setBankcardBack(this.bankcardBack);
        authAccount.setSupplyImgGroup(this.supplyImgGroup);
    }

    public String getAuthAccountId() {
        return authAccountId;
    }

    public void setAuthAccountId(String authAccountId) {
        this.authAccountId = authAccountId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getHandIdCard() {
        return handIdCard;
    }

    public void setHandIdCard(String handIdCard) {
        this.handIdCard = handIdCard;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShorthand() {
        return shorthand;
    }

    public void setShorthand(String shorthand) {
        this.shorthand = shorthand;
    }

    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getLicenseExpireDate() {
        return licenseExpireDate;
    }

    public void setLicenseExpireDate(String licenseExpireDate) {
        this.licenseExpireDate = licenseExpireDate;
    }

    public String getBusinessProvince() {
        return businessProvince;
    }

    public void setBusinessProvince(String businessProvince) {
        this.businessProvince = businessProvince;
    }

    public String getBusinessCity() {
        return businessCity;
    }

    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getAccountLicenseNo() {
        return accountLicenseNo;
    }

    public void setAccountLicenseNo(String accountLicenseNo) {
        this.accountLicenseNo = accountLicenseNo;
    }

    public String getAccountLicenseImg() {
        return accountLicenseImg;
    }

    public void setAccountLicenseImg(String accountLicenseImg) {
        this.accountLicenseImg = accountLicenseImg;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getSettlementCardno() {
        return settlementCardno;
    }

    public void setSettlementCardno(String settlementCardno) {
        this.settlementCardno = settlementCardno;
    }

    public String getSettlementBank() {
        return settlementBank;
    }

    public void setSettlementBank(String settlementBank) {
        this.settlementBank = settlementBank;
    }

    public String getSettlementProvice() {
        return settlementProvice;
    }

    public void setSettlementProvice(String settlementProvice) {
        this.settlementProvice = settlementProvice;
    }

    public String getSettlementCity() {
        return settlementCity;
    }

    public void setSettlementCity(String settlementCity) {
        this.settlementCity = settlementCity;
    }

    public String getSettlementArea() {
        return settlementArea;
    }

    public void setSettlementArea(String settlementArea) {
        this.settlementArea = settlementArea;
    }

    public String getBankbranchName() {
        return bankbranchName;
    }

    public void setBankbranchName(String bankbranchName) {
        this.bankbranchName = bankbranchName;
    }

    public String getBankbranchNo() {
        return bankbranchNo;
    }

    public void setBankbranchNo(String bankbranchNo) {
        this.bankbranchNo = bankbranchNo;
    }

    public String getSettlementBankno() {
        return settlementBankno;
    }

    public void setSettlementBankno(String settlementBankno) {
        this.settlementBankno = settlementBankno;
    }

    public String getAdminTelephone() {
        return adminTelephone;
    }

    public void setAdminTelephone(String adminTelephone) {
        this.adminTelephone = adminTelephone;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getBankcardFront() {
        return bankcardFront;
    }

    public void setBankcardFront(String bankcardFront) {
        this.bankcardFront = bankcardFront;
    }

    public String getBankcardBack() {
        return bankcardBack;
    }

    public void setBankcardBack(String bankcardBack) {
        this.bankcardBack = bankcardBack;
    }

    public String getSupplyImgGroup() {
        return supplyImgGroup;
    }

    public void setSupplyImgGroup(String supplyImgGroup) {
        this.supplyImgGroup = supplyImgGroup;
    }
}
