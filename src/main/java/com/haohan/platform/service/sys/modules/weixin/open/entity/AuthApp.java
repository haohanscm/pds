package com.haohan.platform.service.sys.modules.weixin.open.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 授权应用管理Entity
 * @author haohan
 * @version 2018-01-05
 */
public class AuthApp extends DataEntity<AuthApp> {
	
	private static final long serialVersionUID = 1L;
	private String appId;		// 应用ID
	private String appSecret;		// App秘钥
	private String accessToken;		// 访问token
	private String flushToken;		// 刷新token
	private String authCode;		// 授权值列表
	private String appName;		// 应用名称
	private String appIcon;		// 应用头像地址
	private String serviceType;		// 服务类型
	private String verifyType;		// 效验类型
	private String originalAppid;		// 原appId
	private String principalName;		// 主体名称
	private String weixinId;		// 微信号
	private String qrcode;		// 二维码地址
	private String expiresin;		// 有效期
	private Date authTime;		// 授权时间
	private String authInfo;		// 授权信息
	private String status;		// 状态
	private Date beginAuthTime;		// 开始 授权时间
	private Date endAuthTime;		// 结束 授权时间

	private String authAppId;  //授权AppId
	private String authAppName; //授权AppName
	
	public AuthApp() {
		super();
	}

	public AuthApp(String id){
		super(id);
	}

	@Length(min=0, max=64, message="应用ID长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=200, message="App秘钥长度必须介于 0 和 64 之间")
	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	@Length(min=0, max=200, message="访问token长度必须介于 0 和 64 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Length(min=0, max=200, message="刷新token长度必须介于 0 和 64 之间")
	public String getFlushToken() {
		return flushToken;
	}

	public void setFlushToken(String flushToken) {
		this.flushToken = flushToken;
	}
	
	@Length(min=0, max=300, message="授权值列表长度必须介于 0 和 300 之间")
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	@Length(min=0, max=64, message="应用名称长度必须介于 0 和 64 之间")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@Length(min=0, max=200, message="应用头像地址长度必须介于 0 和 200 之间")
	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}
	
	@Length(min=0, max=64, message="服务类型长度必须介于 0 和 64 之间")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Length(min=0, max=64, message="效验类型长度必须介于 0 和 64 之间")
	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}
	
	@Length(min=0, max=64, message="原appId长度必须介于 0 和 64 之间")
	public String getOriginalAppid() {
		return originalAppid;
	}

	public void setOriginalAppid(String originalAppid) {
		this.originalAppid = originalAppid;
	}
	
	@Length(min=0, max=64, message="主体名称长度必须介于 0 和 64 之间")
	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	
	@Length(min=0, max=64, message="微信号长度必须介于 0 和 64 之间")
	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
	
	@Length(min=0, max=200, message="二维码地址长度必须介于 0 和 200 之间")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	@Length(min=0, max=64, message="有效期长度必须介于 0 和 64 之间")
	public String getExpiresin() {
		return expiresin;
	}

	public void setExpiresin(String expiresin) {
		this.expiresin = expiresin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}
	
	public String getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}
	
	@Length(min=0, max=32, message="状态长度必须介于 0 和 32 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginAuthTime() {
		return beginAuthTime;
	}

	public void setBeginAuthTime(Date beginAuthTime) {
		this.beginAuthTime = beginAuthTime;
	}
	
	public Date getEndAuthTime() {
		return endAuthTime;
	}

	public void setEndAuthTime(Date endAuthTime) {
		this.endAuthTime = endAuthTime;
	}

	public String getAuthAppId() {
		return authAppId;
	}

	public void setAuthAppId(String authAppId) {
		this.authAppId = authAppId;
	}

	public String getAuthAppName() {
		return authAppName;
	}

	public void setAuthAppName(String authAppName) {
		this.authAppName = authAppName;
	}
}