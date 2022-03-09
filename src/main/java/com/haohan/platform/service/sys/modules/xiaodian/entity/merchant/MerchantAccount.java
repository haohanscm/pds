package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 管理不同渠道的商户账号Entity
 * @author yu.shen
 * @version 2018-06-11
 */
public class MerchantAccount extends DataEntity<MerchantAccount> {
	
	private static final long serialVersionUID = 1L;
	private String partnerId;		// 商户账号
	private String appKey;		// 渠道appKey
	private String sid;		// 会话Id
	private String password;		// 密码
	private String lastLoginArea;		// 上次登录地区
	private Date lastLoginTime;		// 上次登录时间
	private String lastLoginIp;		// 上次登录ip
	private String mobile;		// 手机号
	private String merchantId; //商家ID
	private String authCode;   //账户权限
	private String shopId;		//店铺id
	private String uid;        //用户通行证id
	private String agentManageId;  // 合作商管理id
	private String step;		//记录注册步骤   为0表示注册完成

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public MerchantAccount() {
		super();
	}

	public MerchantAccount(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商户账号长度必须介于 0 和 64 之间")
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	@Length(min=0, max=255, message="渠道appKey长度必须介于 0 和 255 之间")
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	@Length(min=0, max=64, message="会话Id长度必须介于 0 和 64 之间")
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	@Length(min=0, max=64, message="密码长度必须介于 0 和 64 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=64, message="上次登录地区长度必须介于 0 和 64 之间")
	public String getLastLoginArea() {
		return lastLoginArea;
	}

	public void setLastLoginArea(String lastLoginArea) {
		this.lastLoginArea = lastLoginArea;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@Length(min=0, max=64, message="上次登录ip长度必须介于 0 和 64 之间")
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	@Length(min=0, max=64, message="手机号长度必须介于 0 和 64 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

    public String getAgentManageId() {
        return agentManageId;
    }

    public void setAgentManageId(String agentManageId) {
        this.agentManageId = agentManageId;
    }
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}