package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 楼栋管理Entity
 * @author yu.shen
 * @version 2018-08-31
 */
public class BuildingsManage extends TreeEntity<BuildingsManage> {
	
	private static final long serialVersionUID = 1L;
	private String communityNo;		// 小区编号
	private String communityName;		// 小区名称
	private String placeType;		// 位置类型
	private String status;		// 状态

	public BuildingsManage() {
		super();
	}

	public BuildingsManage(String id){
		super(id);
	}

	@JsonBackReference
	public BuildingsManage getParent() {
		return parent;
	}

	public void setParent(BuildingsManage parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=500, message="所有父级编号长度必须介于 0 和 500 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=64, message="小区编号长度必须介于 0 和 64 之间")
	public String getCommunityNo() {
		return communityNo;
	}

	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
	
	@Length(min=0, max=64, message="小区名称长度必须介于 0 和 64 之间")
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	
	@Length(min=0, max=64, message="位置名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="位置类型长度必须介于 0 和 64 之间")
	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}