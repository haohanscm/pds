package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 店铺模板扩展信息管理Entity
 * @author haohan
 * @version 2018-02-05
 */
public class ShopTemplateExtInfo extends TreeEntity<ShopTemplateExtInfo> {
	
	private static final long serialVersionUID = 1L;
	private String templateId;		// 模板ID
	private String fieldName;		// 字段名称
	private String fieldCode;		// 字段Code
	private String fieldType;		// 类型
	private String isNeed;		// 是否必填
	private String defaultValue;		// 默认值
	private String fieldDesc;		// 功能说明

	private String rootNodeNum;    //根节点数量

	public ShopTemplateExtInfo() {
		super();
	}

	public ShopTemplateExtInfo(String id){
		super(id);
	}

	public String getRootNodeNum() {
		return rootNodeNum;
	}

	public void setRootNodeNum(String rootNodeNum) {
		this.rootNodeNum = rootNodeNum;
	}

	@JsonBackReference
	public ShopTemplateExtInfo getParent() {
		return parent;
	}

	public void setParent(ShopTemplateExtInfo parent) {
		this.parent = parent;
	}

	@Length(min=0, max=64, message="模板ID长度必须介于 0 和 64 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=64, message="字段名称长度必须介于 0 和 64 之间")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Length(min=0, max=64, message="字段Code长度必须介于 0 和 64 之间")
	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	
	@Length(min=0, max=64, message="类型长度必须介于 0 和 64 之间")
	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	@Length(min=0, max=5, message="是否必填长度必须介于 0 和 5 之间")
	public String getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}
	
	@Length(min=0, max=1000, message="默认值长度必须介于 0 和 1000 之间")
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Length(min=0, max=200, message="功能说明长度必须介于 0 和 200 之间")
	public String getFieldDesc() {
		return fieldDesc;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
}