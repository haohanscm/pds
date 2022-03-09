package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 商家应用扩展信息管理Entity
 * @author haohan
 * @version 2018-02-05
 */
public class MerchantAppExt extends TreeEntity<MerchantAppExt> {
	
	private static final long serialVersionUID = 1L;
	private String appId;		// 应用ID
	private String templateId;		// 模板ID
	private String merchantId;		// 商家ID
	private String fieldName;		// 字段名称
	private String fieldCode;		// 字段Code
	private String fieldValue;		// 字段值
	private String fieldType;      //  字段类型
    private String isNeed; 		   //是否必填

	private String rootNodeNum;    //根节点数量



	public MerchantAppExt() {
		super();
	}

	public MerchantAppExt(String id){
		super(id);
	}

	@JsonBackReference
	public MerchantAppExt getParent() {
		return parent;
	}

	public void setParent(MerchantAppExt parent) {
		this.parent = parent;
	}

	public String getRootNodeNum() {
		return rootNodeNum;
	}

	public void setRootNodeNum(String rootNodeNum) {
		this.rootNodeNum = rootNodeNum;
	}

	@Length(min=0, max=64, message="应用ID长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=64, message="模板ID长度必须介于 0 和 64 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
	
	@Length(min=0, max=1000, message="字段值长度必须介于 0 和 1000 之间")
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getIsNeed() {
		return isNeed;
	}

	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}
}