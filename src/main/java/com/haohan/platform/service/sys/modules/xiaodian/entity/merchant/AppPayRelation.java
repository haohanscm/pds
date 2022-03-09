package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import org.hibernate.validator.constraints.Length;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

/**
 * app支付账户Entity
 * @author yu.shen
 * @version 2019-01-15
 */
public class AppPayRelation extends DataEntity<AppPayRelation> {
	
	private static final long serialVersionUID = 1L;
	private String partnerId;		// 商家账户号
	private String appId;		// 微信appid
	private String status;		// 状态

	private String appName;		// 微信appName
	
	public AppPayRelation() {
		super();
	}

	public AppPayRelation(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家账户号长度必须介于 0 和 64 之间")
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
	@Length(min=0, max=64, message="微信appid长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}