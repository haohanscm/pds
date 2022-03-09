package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.haohan.platform.service.sys.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;


/**
 * 商品分类Entity
 * @author haohan
 * @version 2017-08-06
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
public class GoodsCategory extends TreeEntity<GoodsCategory> {
	
	private static final long serialVersionUID = 1L;
	private String shopId;		// 店铺ID
	private String merchantId;  // 商家Id
	private String industry;		// 行业名称
	private String keywords;		// 关键词
//	private GoodsCategory parent;		// 父级编号
//	private String parentIds;		// 所有父级编号
//	private String name;		// 名称
//	private Integer sort;		// 排序
	private String description;		// 描述
	private String categoryType;		// 分类类型  修改为是否展示 0否 1是
	private String logo;        // logo图片地址
	private String categorySn;  // 第三方分类编号   即速分类ID
	private String parentSn;      // 第三方父分类编号  即速父级ID
    private String goodsType;// 商品类型 0实体商品，1虚拟商品
    private String generalCategorySn;		// 分类通用编号 公共库编号

    private String merchantName;
    private String shopName;
    private String goodsCount; // 分类下的商品数量

	private String rootNodeNum; // 根节点数
	private String jisuappId;  // 即速应用id
	private String updateJisu; // 是否同步即速应用


	
	public GoodsCategory() {
		super();
	}

	public GoodsCategory(String id){
		super(id);
	}

	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
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
	public GoodsCategory getParent() {
		return parent;
	}

	public void setParent(GoodsCategory parent) {
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
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=50, message="分类类型长度必须介于 0 和 50 之间")
	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	@Length(min=0, max=255, message="logo图片地址长度必须介于 0 和 255 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

	public String getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getRootNodeNum() {
		return rootNodeNum;
	}

	public void setRootNodeNum(String rootNodeNum) {
		this.rootNodeNum = rootNodeNum;
	}

    public String getCategorySn() {
        return categorySn;
    }

    public void setCategorySn(String categorySn) {
        this.categorySn = categorySn;
    }

	public String getParentSn() {
		return parentSn;
	}

	public void setParentSn(String parentSn) {
		this.parentSn = parentSn;
	}

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getJisuappId() {
        return jisuappId;
    }

    public void setJisuappId(String jisuappId) {
        this.jisuappId = jisuappId;
    }

	public String getUpdateJisu() {
		return updateJisu;
	}

	public void setUpdateJisu(String updateJisu) {
		this.updateJisu = updateJisu;
	}

    public String getGeneralCategorySn() {
        return generalCategorySn;
    }

    public void setGeneralCategorySn(String generalCategorySn) {
        this.generalCategorySn = generalCategorySn;
    }
}