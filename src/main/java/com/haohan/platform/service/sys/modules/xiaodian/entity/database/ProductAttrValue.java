package com.haohan.platform.service.sys.modules.xiaodian.entity.database;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 商品库属性值管理Entity
 * @author dy
 * @version 2018-10-22
 */
public class ProductAttrValue extends DataEntity<ProductAttrValue> {
	
	private static final long serialVersionUID = 1L;
	private String attrValue;		// 属性值
	private String attrNameId;		// 属性名id
	private String spuId;		// 标准商品id   只在spu商品私有属性时关联
	private Integer sort;		// 排序

    private String attrName;  // 属性名
	
	public ProductAttrValue() {
		super();
	}

	public ProductAttrValue(String id){
		super(id);
	}

	@Length(min=0, max=64, message="属性值长度必须介于 0 和 64 之间")
	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	
	@Length(min=0, max=64, message="属性名id长度必须介于 0 和 64 之间")
	public String getAttrNameId() {
		return attrNameId;
	}

	public void setAttrNameId(String attrNameId) {
		this.attrNameId = attrNameId;
	}
	
	@Length(min=0, max=64, message="标准商品id长度必须介于 0 和 64 之间")
	public String getSpuId() {
		return spuId;
	}

	public void setSpuId(String spuId) {
		this.spuId = spuId;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
	
}