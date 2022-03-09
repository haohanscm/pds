package com.haohan.platform.service.sys.modules.pds.entity.business;

import org.hibernate.validator.constraints.Length;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

/**
 * 消息管理Entity
 * @author haohan
 * @version 2018-12-03
 */
public class MessageManage extends DataEntity<MessageManage> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;		// 平台商家ID
	private String messageNo;		// 消息编号
	private String messageType;		// 消息类型
	
	public MessageManage() {
		super();
	}

	public MessageManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="平台商家ID长度必须介于 0 和 64 之间")
	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	
	@Length(min=0, max=64, message="消息编号长度必须介于 0 和 64 之间")
	public String getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}
	
	@Length(min=0, max=64, message="消息类型长度必须介于 0 和 64 之间")
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
}