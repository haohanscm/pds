package com.haohan.platform.service.sys.modules.xiaodian.entity.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 标准商品库管理Entity
 * @author dy
 * @version 2018-10-17
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardProductUnit extends DataEntity<StandardProductUnit> {
	
	private static final long serialVersionUID = 1L;
	private String goodsName;		// 商品名称
	private String goodsCategoryId;		// 商品分类id
	private String detailDesc;		// 商品描述
	private String generalSn;		// 商品通用编号
	private String thumbUrl;		// 缩略图地址
	private String industry;		// 行业
	private String brand;		// 品牌
	private String manufacturer;		// 厂家/制造商
	private String photoGroupNum;		// 图片组编号
	private Integer sort;		// 排序
    private String aggregationType;		// 聚合平台类型

	private String categoryName; // 分类名称

	public StandardProductUnit() {
		super();
	}

	public StandardProductUnit(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=500, message="商品分类id长度必须介于 0 和 500 之间")
	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	
	public String getDetailDesc() {
		return detailDesc;
	}

	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}
	
	@Length(min=0, max=64, message="商品通用编号长度必须介于 0 和 64 之间")
	public String getGeneralSn() {
		return generalSn;
	}

	public void setGeneralSn(String generalSn) {
		this.generalSn = generalSn;
	}
	
	@Length(min=0, max=500, message="缩略图地址长度必须介于 0 和 500 之间")
	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	@Length(min=0, max=64, message="行业长度必须介于 0 和 64 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=64, message="品牌长度必须介于 0 和 64 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=0, max=64, message="厂家/制造商长度必须介于 0 和 64 之间")
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@Length(min=0, max=64, message="图片组编号长度必须介于 0 和 64 之间")
	public String getPhotoGroupNum() {
		return photoGroupNum;
	}

	public void setPhotoGroupNum(String photoGroupNum) {
		this.photoGroupNum = photoGroupNum;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }
}