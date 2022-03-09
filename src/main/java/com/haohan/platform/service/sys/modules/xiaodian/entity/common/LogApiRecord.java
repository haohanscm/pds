package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 接口日志记录Entity
 * @author haohan
 * @version 2018-05-29
 */
public class LogApiRecord extends DataEntity<LogApiRecord> {
	
	private static final long serialVersionUID = 1L;
	private String platform;		// 平台端
	private String product;		// 产品线
	private String version;		// 版本
	private String sessionId;		// 会话ID
	private String reqId;		// 请求ID
	private Date reqTime;		// 请求时间
	private String apiName;		// 接口名称
	private String httpMethod;		// HTTP方法
	private String reqUri;		// 请求URI
	private String reqParams;		// 请求参数
	private String respParams;		// 返回参数
	private String costTime;		// 耗时毫秒
	private Date respTime;		// 返回时间

	private Date beginReqTime;		// 开始 创建时间
	private Date endReqTime;		// 结束 创建时间
	
	public LogApiRecord() {
		super();
	}

	public LogApiRecord(String id){
		super(id);
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

	@Length(min=0, max=64, message="平台端长度必须介于 0 和 64 之间")
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	@Length(min=0, max=64, message="产品线长度必须介于 0 和 64 之间")
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	@Length(min=0, max=64, message="版本长度必须介于 0 和 64 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=64, message="会话ID长度必须介于 0 和 64 之间")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Length(min=0, max=64, message="请求ID长度必须介于 0 和 64 之间")
	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
	@Length(min=0, max=64, message="请求时间长度必须介于 0 和 64 之间")
	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	
	@Length(min=0, max=64, message="接口名称长度必须介于 0 和 64 之间")
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
	@Length(min=0, max=64, message="HTTP方法长度必须介于 0 和 64 之间")
	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	@Length(min=0, max=500, message="请求URI长度必须介于 0 和 500 之间")
	public String getReqUri() {
		return reqUri;
	}

	public void setReqUri(String reqUri) {
		this.reqUri = reqUri;
	}
	
	public String getReqParams() {
		return reqParams;
	}

	public void setReqParams(String reqParams) {
		this.reqParams = reqParams;
	}
	
	public String getRespParams() {
		return respParams;
	}

	public void setRespParams(String respParams) {
		this.respParams = respParams;
	}
	
	@Length(min=0, max=64, message="耗时毫秒长度必须介于 0 和 64 之间")
	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRespTime() {
		return respTime;
	}

	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}
	
}