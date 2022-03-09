package com.haohan.platform.service.sys.modules.xiaodian.entity.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 商品库分类管理Entity
 * @author dy
 * @version 2018-10-17
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCategory extends TreeEntity<ProductCategory> {
	
	private static final long serialVersionUID = 1L;
//	private ProductCategory parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
//	private Integer sort;		// 排序
	private String categoryDesc;		// 分类描述
	private String aggregationType;		// 聚合平台类型
	private String generalCategorySn;		// 分类通用编号
    private String logo;        // logo图片地址

	private Integer goodsCount; // 分类商品数
    private String tempId; // 临时存放id
	
	public ProductCategory() {
		super();
	}

	public ProductCategory(String id){
		super(id);
	}

	@JsonBackReference
	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
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
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=500, message="分类描述长度必须介于 0 和 500 之间")
	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	public void setParentId(String parentId){
		if(null == parent){
			parent = new ProductCategory();
		}
		parent.setId(parentId);
	}

    public String getParentName() {
        return parent != null && parent.getName() != null ? parent.getName() : "";
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getGeneralCategorySn() {
        return generalCategorySn;
    }

    public void setGeneralCategorySn(String generalCategorySn) {
        this.generalCategorySn = generalCategorySn;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}