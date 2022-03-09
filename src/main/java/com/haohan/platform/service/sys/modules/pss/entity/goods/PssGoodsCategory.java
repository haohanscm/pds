package com.haohan.platform.service.sys.modules.pss.entity.goods;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 进销存 商品总分类Entity
 * @author haohan
 * @version 2018-09-07
 */
public class PssGoodsCategory extends TreeEntity<PssGoodsCategory> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String industry;		// 行业名称
	private String keywords;		// 关键词
//	private PssGoodsCategory parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
//	private String sort;		// 排序
	private String categoryDesc;		// 描述

	public PssGoodsCategory() {
		super();
	}

	public PssGoodsCategory(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=100, message="行业名称长度必须介于 0 和 100 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=100, message="关键词长度必须介于 0 和 100 之间")
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	@JsonBackReference
	public PssGoodsCategory getParent() {
		return parent;
	}

	public void setParent(PssGoodsCategory parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=500, message="所有父级编号长度必须介于 0 和 500 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}