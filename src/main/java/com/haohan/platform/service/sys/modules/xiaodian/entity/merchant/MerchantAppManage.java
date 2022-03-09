package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 商家应用Entity
 * @author haohan
 * @version 2017-12-25
 */
public class MerchantAppManage extends DataEntity<MerchantAppManage> {
	
	private static final long serialVersionUID = 1L;
	private String appId;		// 应用appid
	private String appName;		// 应用名称
	private String merchantId;		// 商家ID
	private String merchantName;		// 商家名称
	private String adminId;		// 管理员ID
	private String adminName;		// 管理员名称
	private String templateId;		// 模板ID
	private String templateName;		// 模板名称
	private String status;		// 状态   字典:merchant_app_status
	private String jisuAppId; //即速AppId
	private String ext;  //扩展信息字段
	private Date onlineTime;		// 上线时间
	private Date beginOnlineTime;		// 开始 上线时间
	private Date endOnlineTime;		// 结束 上线时间
	
	public MerchantAppManage() {
		super();
	}

	public MerchantAppManage(String id){
		super(id);
	}

	public String getJisuAppId() {
		return jisuAppId;
	}

	public void setJisuAppId(String jisuAppId) {
		this.jisuAppId = jisuAppId;
	}

	@Length(min=0, max=32, message="应用appid长度必须介于 0 和 32 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=64, message="应用名称长度必须介于 0 和 64 之间")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@Length(min=0, max=32, message="商家ID长度必须介于 0 和 32 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商家名称长度必须介于 0 和 64 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=32, message="管理员ID长度必须介于 0 和 32 之间")
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	@Length(min=0, max=64, message="管理员名称长度必须介于 0 和 64 之间")
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	@Length(min=0, max=32, message="模板ID长度必须介于 0 和 32 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=64, message="模板名称长度必须介于 0 和 64 之间")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	public Date getBeginOnlineTime() {
		return beginOnlineTime;
	}

	public void setBeginOnlineTime(Date beginOnlineTime) {
		this.beginOnlineTime = beginOnlineTime;
	}
	
	public Date getEndOnlineTime() {
		return endOnlineTime;
	}

	public void setEndOnlineTime(Date endOnlineTime) {
		this.endOnlineTime = endOnlineTime;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public MerchantAppExtInfo fromExt(){

		return (MerchantAppExtInfo) JsonMapper.fromJsonString(this.ext,MerchantAppExtInfo.class);
	}
		
}