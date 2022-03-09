package com.haohan.platform.service.sys.modules.xiaodian.entity;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 品牌Entity
 * @author haohan
 * @version 2017-08-05
 */
public class Brand extends DataEntity<Brand> {
	
	private static final long serialVersionUID = 1L;
	private String industry;		// 行业
	private String brand;		// 品牌名称
	private String logo;		// 公司logo
	private String description;		// 品牌描述
	private String website;		// 品牌网址
	private Integer sort;		// 排序
	private Integer status;		// 状态
	
	public Brand() {
		super();
	}

	public Brand(String id){
		super(id);
	}

	@Length(min=0, max=100, message="行业长度必须介于 0 和 100 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=100, message="品牌名称长度必须介于 0 和 100 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=0, max=100, message="公司logo长度必须介于 0 和 100 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Length(min=0, max=500, message="品牌描述长度必须介于 0 和 500 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=100, message="品牌网址长度必须介于 0 和 100 之间")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}