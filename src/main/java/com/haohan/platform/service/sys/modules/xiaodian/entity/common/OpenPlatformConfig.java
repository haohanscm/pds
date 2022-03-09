package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 开放平台管理Entity
 * @author haohan
 * @version 2017-08-06
 */
public class OpenPlatformConfig extends DataEntity<OpenPlatformConfig> {
	
	private static final long serialVersionUID = 1L;
	private String appId;		// 应用ID
    private String appName;		//应用名称
	private Integer appType;		// 应用类型
	private String appSecret;		// 应用密钥
	private String callbackUrl;		// 回调地址
	private String accessToken;		// 访问token
	private String status;		// 状态
	private String flushToken;		// 刷新token
	private Integer autoCreate;		// 自动账号生成
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String memo;		// 备注

	public OpenPlatformConfig() {
		super();
	}

	public OpenPlatformConfig(String id){
		super(id);
	}

	@Length(min=0, max=100, message="应用ID长度必须介于 0 和 100 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	
	@Length(min=0, max=50, message="应用密钥长度必须介于 0 和 50 之间")
	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	@Length(min=0, max=300, message="回调地址长度必须介于 0 和 50 之间")
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	
	@Length(min=0, max=50, message="访问token长度必须介于 0 和 50 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="刷新token长度必须介于 0 和 50 之间")
	public String getFlushToken() {
		return flushToken;
	}

	public void setFlushToken(String flushToken) {
		this.flushToken = flushToken;
	}
	
	public Integer getAutoCreate() {
		return autoCreate;
	}

	public void setAutoCreate(Integer autoCreate) {
		this.autoCreate = autoCreate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}