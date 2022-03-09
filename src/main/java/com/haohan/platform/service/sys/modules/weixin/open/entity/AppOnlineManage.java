package com.haohan.platform.service.sys.modules.weixin.open.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.weixin.open.api.inf.IWxOpenAppService;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 应用上线管理Entity
 * @author haohan
 * @version 2017-12-26
 */
public class AppOnlineManage extends DataEntity<AppOnlineManage> {
	
	private static final long serialVersionUID = 1L;
	private String appId;		// 应用ID
	private String appName;		// 应用名称
	private String merchantId;		// 商家ID
	private String merchantName;		// 商家名称
	private String stepName;		// 步骤名称
	private String stepNo;		// 步骤序号
	private String reqParams;		// 请求值
	private String respParams;		// 返回值
	private String status;		// 状态
	private String channel;		// 渠道
	private String opType;		// 操作类型
	private String reqMethod;		// 请求方法
	private String reqUrl;		// 请求地址
	private Date reqTime;		// 请求时间
	private Date respTime;		// 返回时间
	private Date beginReqTime;		// 开始 请求时间
	private Date endReqTime;		// 结束 请求时间
	
	public AppOnlineManage() {
		super();
	}



	public AppOnlineManage(String id){
		super(id);
	}

	public AppOnlineManage(IWxOpenAppService.WxOpen_App_Api api) {
		this.setStepName(api.getStepName());
		this.setStepNo(api.getStepNo());
		this.setChannel(api.getChannel());
		this.setOpType(api.getOpType());
		this.setReqMethod(api.getMethod());
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
	
	@Length(min=0, max=64, message="步骤名称长度必须介于 0 和 64 之间")
	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
	@Length(min=0, max=64, message="步骤序号长度必须介于 0 和 64 之间")
	public String getStepNo() {
		return stepNo;
	}

	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}
	
	@Length(min=0, max=2000, message="请求值长度必须介于 0 和 1000 之间")
	public String getReqParams() {
		return reqParams;
	}

	public void setReqParams(String reqParams) {
		this.reqParams = reqParams;
	}
	
	@Length(min=0, max=1000, message="返回值长度必须介于 0 和 1000 之间")
	public String getRespParams() {
		return respParams;
	}

	public void setRespParams(String respParams) {
		this.respParams = respParams;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="渠道长度必须介于 0 和 64 之间")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@Length(min=0, max=64, message="操作类型长度必须介于 0 和 64 之间")
	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	@Length(min=0, max=32, message="请求方法长度必须介于 0 和 32 之间")
	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}
	
	@Length(min=0, max=1000, message="请求地址长度必须介于 0 和 1000 之间")
	public String getReqUrl() {
		return reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRespTime() {
		return respTime;
	}

	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}
	
	public Date getBeginReqTime() {
		return beginReqTime;
	}

	public void setBeginReqTime(Date beginReqTime) {
		this.beginReqTime = beginReqTime;
	}
	
	public Date getEndReqTime() {
		return endReqTime;
	}

	public void setEndReqTime(Date endReqTime) {
		this.endReqTime = endReqTime;
	}

	public AppOnlineManage config(IWxOpenAppService.WxOpen_App_Api api){
		AppOnlineManage onlineManage = new AppOnlineManage();
        onlineManage.setStepName(api.getStepName());
        onlineManage.setStepNo(api.getStepNo());
        onlineManage.setChannel(api.getChannel());
        onlineManage.setOpType(api.getOpType());
		return onlineManage;
	}

}