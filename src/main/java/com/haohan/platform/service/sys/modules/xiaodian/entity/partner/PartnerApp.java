package com.haohan.platform.service.sys.modules.xiaodian.entity.partner;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 厂商应用管理Entity
 * @author haohan
 * @version 2018-05-29
 */
public class PartnerApp extends DataEntity<PartnerApp> {
	
	private static final long serialVersionUID = 1L;
	private String partnerName;		// 厂商名称
	private String partnerNum;      //渠道编号
	private String appKey;		// 应用KEY
	private String appSecret;		// 应用秘钥
	private String partnerDesc;		// 厂商说明
	private String contactUser;		// 厂商联系人
	private String contactPhone;		// 联系电话
	private String partnerAddress;		// 厂商地址
	private String partnerType;		// 厂商类型
	private String status;		// 状态
    private String notifyUrl;   //回调地址

	
	public PartnerApp() {
		super();
	}

	public PartnerApp(String id){
		super(id);
	}

	public String getPartnerNum() {
		return partnerNum;
	}

	public void setPartnerNum(String partnerNum) {
		this.partnerNum = partnerNum;
	}

	@Length(min=0, max=64, message="厂商名称长度必须介于 0 和 64 之间")
	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	
	@Length(min=0, max=64, message="应用KEY长度必须介于 0 和 64 之间")
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	@Length(min=0, max=64, message="应用秘钥长度必须介于 0 和 64 之间")
	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	@Length(min=0, max=500, message="厂商说明长度必须介于 0 和 500 之间")
	public String getPartnerDesc() {
		return partnerDesc;
	}

	public void setPartnerDesc(String partnerDesc) {
		this.partnerDesc = partnerDesc;
	}
	
	@Length(min=0, max=64, message="厂商联系人长度必须介于 0 和 64 之间")
	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}
	
	@Length(min=0, max=64, message="联系电话长度必须介于 0 和 64 之间")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Length(min=0, max=200, message="厂商地址长度必须介于 0 和 200 之间")
	public String getPartnerAddress() {
		return partnerAddress;
	}

	public void setPartnerAddress(String partnerAddress) {
		this.partnerAddress = partnerAddress;
	}
	
	@Length(min=0, max=64, message="厂商类型长度必须介于 0 和 64 之间")
	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}