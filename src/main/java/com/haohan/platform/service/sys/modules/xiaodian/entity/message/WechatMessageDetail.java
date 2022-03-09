package com.haohan.platform.service.sys.modules.xiaodian.entity.message;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 消息模板管理Entity
 * @author haohan
 * @version 2018-04-26
 */
public class WechatMessageDetail extends DataEntity<WechatMessageDetail> {
	
	private static final long serialVersionUID = 1L;
	private WechatMessageTemplate msgTemplate;		// 消息ID 父类
	private String fieldName;		// 字段名称
	private String fieldValue;		// 字段值
	private String fileldCode;		// 字段code
	private String fieldColor;		// 字段颜色
	private String isBold;		// 是否加粗
	private String sort;		// 排序
	
	public WechatMessageDetail() {
		super();
	}

	public WechatMessageDetail(String id){
		super(id);
	}

	public WechatMessageDetail(WechatMessageTemplate msgTemplate){
		this.msgTemplate = msgTemplate;
	}

	@Length(min=0, max=64, message="消息ID长度必须介于 0 和 64 之间")
	public WechatMessageTemplate getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(WechatMessageTemplate msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
	
	@Length(min=0, max=64, message="字段名称长度必须介于 0 和 64 之间")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Length(min=0, max=64, message="字段值长度必须介于 0 和 64 之间")
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	@Length(min=0, max=64, message="字段code长度必须介于 0 和 64 之间")
	public String getFileldCode() {
		return fileldCode;
	}

	public void setFileldCode(String fileldCode) {
		this.fileldCode = fileldCode;
	}
	
	@Length(min=0, max=64, message="字段颜色长度必须介于 0 和 64 之间")
	public String getFieldColor() {
		return fieldColor;
	}

	public void setFieldColor(String fieldColor) {
		this.fieldColor = fieldColor;
	}
	
	@Length(min=0, max=5, message="是否加粗长度必须介于 0 和 5 之间")
	public String getIsBold() {
		return isBold;
	}

	public void setIsBold(String isBold) {
		this.isBold = isBold;
	}
	
	@Length(min=0, max=32, message="排序长度必须介于 0 和 32 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}