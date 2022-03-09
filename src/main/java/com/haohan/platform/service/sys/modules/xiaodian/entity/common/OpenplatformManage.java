package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 开放平台应用资料管理Entity
 * @author haohan
 * @version 2018-02-01
 */
public class OpenplatformManage extends TreeEntity<OpenplatformManage> {
	
	private static final long serialVersionUID = 1L;
	private String industryCategory; //行业分类
	private String appName;		// 名称
	private String appType;		// 应用类型
	private String appId;		// 应用ID
	private String appSecrect;		// 应用秘钥
	private String regEmail;		// 注册邮箱
	private String regPassword;		// 密码
	private String regTelephone;		// 注册手机号
	private String regUser;		// 注册人姓名
	private String ghId;		// 原始ID
	private String serviceCategory;		// 服务类目
	private String status;		// 状态
	private Date openTime;		// 开通时间
	private Date beginOpenTime;		// 开始 开通时间
	private Date endOpenTime;		// 结束 开通时间
	
	public OpenplatformManage() {
		super();
	}

	public OpenplatformManage(String id){
		super(id);
	}

	@JsonBackReference
	public OpenplatformManage getParent() {
		return parent;
	}

	public void setParent(OpenplatformManage parent) {
		this.parent = parent;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@Length(min=0, max=64, message="应用类型长度必须介于 0 和 64 之间")
	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	@Length(min=0, max=128, message="应用ID长度必须介于 0 和 128 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=128, message="应用秘钥长度必须介于 0 和 128 之间")
	public String getAppSecrect() {
		return appSecrect;
	}

	public void setAppSecrect(String appSecrect) {
		this.appSecrect = appSecrect;
	}
	
	@Length(min=0, max=128, message="注册邮箱长度必须介于 0 和 128 之间")
	public String getRegEmail() {
		return regEmail;
	}

	public void setRegEmail(String regEmail) {
		this.regEmail = regEmail;
	}
	
	@Length(min=0, max=64, message="密码长度必须介于 0 和 64 之间")
	public String getRegPassword() {
		return regPassword;
	}

	public void setRegPassword(String regPassword) {
		this.regPassword = regPassword;
	}
	
	@Length(min=0, max=64, message="注册手机号长度必须介于 0 和 64 之间")
	public String getRegTelephone() {
		return regTelephone;
	}

	public void setRegTelephone(String regTelephone) {
		this.regTelephone = regTelephone;
	}
	
	@Length(min=0, max=64, message="注册人姓名长度必须介于 0 和 64 之间")
	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	
	@Length(min=0, max=128, message="原始ID长度必须介于 0 和 128 之间")
	public String getGhId() {
		return ghId;
	}

	public void setGhId(String ghId) {
		this.ghId = ghId;
	}
	
	@Length(min=0, max=500, message="服务类目长度必须介于 0 和 500 之间")
	public String getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getBeginOpenTime() {
		return beginOpenTime;
	}

	public void setBeginOpenTime(Date beginOpenTime) {
		this.beginOpenTime = beginOpenTime;
	}
	
	public Date getEndOpenTime() {
		return endOpenTime;
	}

	public void setEndOpenTime(Date endOpenTime) {
		this.endOpenTime = endOpenTime;
	}
		
}