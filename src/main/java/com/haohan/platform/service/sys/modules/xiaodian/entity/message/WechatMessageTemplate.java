package com.haohan.platform.service.sys.modules.xiaodian.entity.message;

import com.google.common.collect.Lists;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 消息模板管理Entity
 * @author haohan
 * @version 2018-04-26
 */
public class WechatMessageTemplate extends DataEntity<WechatMessageTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String templateId;		// 模板ID
	private String appId;		// 应用ID
	private String msgType;		// 消息类型
	private String goPage;		// 跳转页面
	private String msgDesc;		// 说明
	private String status;		// 状态
	private List<WechatMessageDetail> wechatMessageDetailList = Lists.newArrayList();		// 子表列表
	
	public WechatMessageTemplate() {
		super();
	}

	public WechatMessageTemplate(String id){
		super(id);
	}

	@Length(min=0, max=64, message="模板ID长度必须介于 0 和 64 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=64, message="应用ID长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=64, message="消息类型长度必须介于 0 和 64 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Length(min=0, max=200, message="跳转页面长度必须介于 0 和 200 之间")
	public String getGoPage() {
		return goPage;
	}

	public void setGoPage(String goPage) {
		this.goPage = goPage;
	}
	
	@Length(min=0, max=200, message="说明长度必须介于 0 和 200 之间")
	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<WechatMessageDetail> getWechatMessageDetailList() {
		return wechatMessageDetailList;
	}

	public void setWechatMessageDetailList(List<WechatMessageDetail> wechatMessageDetailList) {
		this.wechatMessageDetailList = wechatMessageDetailList;
	}
}