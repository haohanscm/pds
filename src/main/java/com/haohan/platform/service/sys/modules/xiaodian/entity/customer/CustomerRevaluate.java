package com.haohan.platform.service.sys.modules.xiaodian.entity.customer;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 客户服务评价Entity
 * @author haohan
 * @version 2018-04-22
 */
public class CustomerRevaluate extends DataEntity<CustomerRevaluate> {
	
	private static final long serialVersionUID = 1L;
	private String appId;		// 应用ID
	private String appName;		// 应用名称
	private String merchantId;		// 商家ID
	private String merchantName;		// 商家名称
	private String serviceType;		// 服务类型
	private String evaluateUid;		// 评分人ID
	private String evaluateName;		// 评分人姓名
	
	public CustomerRevaluate() {
		super();
	}

	public CustomerRevaluate(String id){
		super(id);
	}

	@Length(min=0, max=64, message="应用ID长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=255, message="服务类型长度必须介于 0 和 255 之间")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Length(min=0, max=64, message="评分人ID长度必须介于 0 和 64 之间")
	public String getEvaluateUid() {
		return evaluateUid;
	}

	public void setEvaluateUid(String evaluateUid) {
		this.evaluateUid = evaluateUid;
	}
	
	@Length(min=0, max=64, message="评分人姓名长度必须介于 0 和 64 之间")
	public String getEvaluateName() {
		return evaluateName;
	}

	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}
	
}