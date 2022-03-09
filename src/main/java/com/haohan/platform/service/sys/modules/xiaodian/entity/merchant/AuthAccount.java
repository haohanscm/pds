package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 账号信息认证管理Entity
 * @author dy
 * @version 2018-10-15
 */
public class AuthAccount extends DataEntity<AuthAccount> {
	
	private static final long serialVersionUID = 1L;
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
	private String settlementType;		// 结算账号公私类型; 对公/对私
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
	
	public AuthAccount() {
		super();
	}

	public AuthAccount(String id){
		super(id);
	}

	@Length(min=0, max=1, message="认证类型长度必须介于 0 和 1 之间")
	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=10, message="姓名长度必须介于 0 和 10 之间")
	public String getIdCardName() {
		return idCardName;
	}

	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}
	
	@Length(min=0, max=20, message="身份证号长度必须介于 0 和 20 之间")
	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	
	@Length(min=0, max=1, message="账号状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=500, message="身份证头像面长度必须介于 0 和 500 之间")
	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}
	
	@Length(min=0, max=500, message="身份证国徽面长度必须介于 0 和 500 之间")
	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}
	
	@Length(min=0, max=500, message="手持身份证长度必须介于 0 和 500 之间")
	public String getHandIdCard() {
		return handIdCard;
	}

	public void setHandIdCard(String handIdCard) {
		this.handIdCard = handIdCard;
	}
	
	@Length(min=0, max=64, message="企业/组织名称长度必须介于 0 和 64 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=64, message="企业/组织简称长度必须介于 0 和 64 之间")
	public String getShorthand() {
		return shorthand;
	}

	public void setShorthand(String shorthand) {
		this.shorthand = shorthand;
	}
	
	@Length(min=0, max=64, message="营业执照编号长度必须介于 0 和 64 之间")
	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}
	
	@Length(min=0, max=500, message="营业执照照片长度必须介于 0 和 500 之间")
	public String getBusinessLicenseImg() {
		return businessLicenseImg;
	}

	public void setBusinessLicenseImg(String businessLicenseImg) {
		this.businessLicenseImg = businessLicenseImg;
	}
	
	@Length(min=0, max=64, message="营业执照有效期长度必须介于 0 和 64 之间")
	public String getLicenseExpireDate() {
		return licenseExpireDate;
	}

	public void setLicenseExpireDate(String licenseExpireDate) {
		this.licenseExpireDate = licenseExpireDate;
	}
	
	@Length(min=0, max=10, message="归属省长度必须介于 0 和 10 之间")
	public String getBusinessProvince() {
		return businessProvince;
	}

	public void setBusinessProvince(String businessProvince) {
		this.businessProvince = businessProvince;
	}
	
	@Length(min=0, max=10, message="归属市长度必须介于 0 和 10 之间")
	public String getBusinessCity() {
		return businessCity;
	}

	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}
	
	@Length(min=0, max=10, message="归属区长度必须介于 0 和 10 之间")
	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	
	@Length(min=0, max=64, message="经营地址长度必须介于 0 和 64 之间")
	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	
	@Length(min=0, max=64, message="开户许可证编号长度必须介于 0 和 64 之间")
	public String getAccountLicenseNo() {
		return accountLicenseNo;
	}

	public void setAccountLicenseNo(String accountLicenseNo) {
		this.accountLicenseNo = accountLicenseNo;
	}
	
	@Length(min=0, max=500, message="开户许可证照片长度必须介于 0 和 500 之间")
	public String getAccountLicenseImg() {
		return accountLicenseImg;
	}

	public void setAccountLicenseImg(String accountLicenseImg) {
		this.accountLicenseImg = accountLicenseImg;
	}
	
	@Length(min=0, max=1, message="结算账号公私类型; 对公/对私长度必须介于 0 和 1 之间")
	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	
	@Length(min=0, max=64, message="结算卡卡号长度必须介于 0 和 64 之间")
	public String getSettlementCardno() {
		return settlementCardno;
	}

	public void setSettlementCardno(String settlementCardno) {
		this.settlementCardno = settlementCardno;
	}
	
	@Length(min=0, max=64, message="结算卡开户行长度必须介于 0 和 64 之间")
	public String getSettlementBank() {
		return settlementBank;
	}

	public void setSettlementBank(String settlementBank) {
		this.settlementBank = settlementBank;
	}
	
	@Length(min=0, max=10, message="开户行省长度必须介于 0 和 10 之间")
	public String getSettlementProvice() {
		return settlementProvice;
	}

	public void setSettlementProvice(String settlementProvice) {
		this.settlementProvice = settlementProvice;
	}
	
	@Length(min=0, max=10, message="开户行市长度必须介于 0 和 10 之间")
	public String getSettlementCity() {
		return settlementCity;
	}

	public void setSettlementCity(String settlementCity) {
		this.settlementCity = settlementCity;
	}
	
	@Length(min=0, max=10, message="开户行区长度必须介于 0 和 10 之间")
	public String getSettlementArea() {
		return settlementArea;
	}

	public void setSettlementArea(String settlementArea) {
		this.settlementArea = settlementArea;
	}
	
	@Length(min=0, max=64, message="支行名称长度必须介于 0 和 64 之间")
	public String getBankbranchName() {
		return bankbranchName;
	}

	public void setBankbranchName(String bankbranchName) {
		this.bankbranchName = bankbranchName;
	}
	
	@Length(min=0, max=64, message="支行行号长度必须介于 0 和 64 之间")
	public String getBankbranchNo() {
		return bankbranchNo;
	}

	public void setBankbranchNo(String bankbranchNo) {
		this.bankbranchNo = bankbranchNo;
	}
	
	@Length(min=0, max=64, message="清算行号长度必须介于 0 和 64 之间")
	public String getSettlementBankno() {
		return settlementBankno;
	}

	public void setSettlementBankno(String settlementBankno) {
		this.settlementBankno = settlementBankno;
	}
	
	@Length(min=0, max=11, message="管理员手机号长度必须介于 0 和 11 之间")
	public String getAdminTelephone() {
		return adminTelephone;
	}

	public void setAdminTelephone(String adminTelephone) {
		this.adminTelephone = adminTelephone;
	}
	
	@Length(min=0, max=64, message="管理员邮箱长度必须介于 0 和 64 之间")
	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	
	@Length(min=0, max=500, message="银行卡正面长度必须介于 0 和 500 之间")
	public String getBankcardFront() {
		return bankcardFront;
	}

	public void setBankcardFront(String bankcardFront) {
		this.bankcardFront = bankcardFront;
	}
	
	@Length(min=0, max=500, message="银行卡反面长度必须介于 0 和 500 之间")
	public String getBankcardBack() {
		return bankcardBack;
	}

	public void setBankcardBack(String bankcardBack) {
		this.bankcardBack = bankcardBack;
	}
	
	@Length(min=0, max=64, message="补充资料图片组编号长度必须介于 0 和 64 之间")
	public String getSupplyImgGroup() {
		return supplyImgGroup;
	}

	public void setSupplyImgGroup(String supplyImgGroup) {
		this.supplyImgGroup = supplyImgGroup;
	}
	
}