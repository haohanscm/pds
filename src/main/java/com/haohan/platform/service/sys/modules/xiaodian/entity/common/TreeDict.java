package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 树形字典Entity
 * @author haohan
 * @version 2017-12-11
 */
public class TreeDict extends TreeEntity<TreeDict> {
	
	private static final long serialVersionUID = 1L;
	private TreeDict parent;		// 父级编号
	private String parentIds;		// 所有父级编号
	private String name;		// 名称
	private String code;		// 编码
	private String type;		// 类别
	private String attr;		// 属性

	public TreeDict() {
		super();
	}

	public TreeDict(String id){
		super(id);
	}

	@JsonBackReference
	public TreeDict getParent() {
		return parent;
	}

	public void setParent(TreeDict parent) {
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
	
	@Length(min=0, max=100, message="编码长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="类别长度必须介于 0 和 100 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="属性长度必须介于 0 和 100 之间")
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}
	

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}