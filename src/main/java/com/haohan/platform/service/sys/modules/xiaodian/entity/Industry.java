package com.haohan.platform.service.sys.modules.xiaodian.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 行业分类管理Entity
 * @author haohan
 * @version 2017-08-05
 */
public class Industry extends TreeEntity<Industry> {
	
	private static final long serialVersionUID = 1L;
//	private Industry parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
	private String description;		// 描述
//	private String sort;		// 排序
	
	public Industry() {
		super();
	}

	public Industry(String id){
		super(id);
	}

	@JsonBackReference
	public Industry getParent() {
		return parent;
	}

	public void setParent(Industry parent) {
		this.parent = parent;
	}
	
//	@Length(min=0, max=500, message="所有父级编号长度必须介于 0 和 500 之间")
//	public String getParentIds() {
//		return parentIds;
//	}
//
//	public void setParentIds(String parentIds) {
//		this.parentIds = parentIds;
//	}
//
//	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
	
	@Length(min=0, max=500, message="描述长度必须介于 0 和 500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

//	public String getParentId() {
//		return parent != null && parent.getId() != null ? parent.getId() : "0";
//	}
}