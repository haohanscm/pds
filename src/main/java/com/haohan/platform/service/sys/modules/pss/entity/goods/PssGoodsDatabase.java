package com.haohan.platform.service.sys.modules.pss.entity.goods;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 *  进销存 商品数据库管理Entity
 * @author haohan
 * @version 2018-09-05
 */
public class PssGoodsDatabase extends DataEntity<PssGoodsDatabase> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String goodsName;		// 商品名称
	private String goodsCode;		// 商品编码   唯一标识 后台生成 pss0001
	private String goodsCategory;		// 商品类型
	private String advicePrice;		// 参考进价
	private String unit;		// 单位
	private String attr;		// 规格   为公共商品库中sku的规格详情
	private String brand;		// 品牌   为公共商品库中spu 的 trademark 商标
	private String yieldly;		// 生产地  公共商品库中spu 的manufactor 厂家/制造商
	private String photos;		// 图片样例
	private String scanCode; //  扫码条码
	private String goodsDesc; //    商品描述
	private Integer sort; //   排序

    private String categoryName; // 分类名称
	
	public PssGoodsDatabase() {
		super();
	}

	public PssGoodsDatabase(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=64, message="商品编码长度必须介于 0 和 64 之间")
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@Length(min=0, max=200, message="商品类型长度必须介于 0 和 200 之间")
	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	
	@Length(min=0, max=10, message="参考进价长度必须介于 0 和 10 之间")
	public String getAdvicePrice() {
		return advicePrice;
	}

	public void setAdvicePrice(String advicePrice) {
		this.advicePrice = advicePrice;
	}
	
	@Length(min=0, max=64, message="单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="规格长度必须介于 0 和 64 之间")
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	@Length(min=0, max=64, message="品牌长度必须介于 0 和 64 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=0, max=64, message="生产地长度必须介于 0 和 64 之间")
	public String getYieldly() {
		return yieldly;
	}

	public void setYieldly(String yieldly) {
		this.yieldly = yieldly;
	}
	
	@Length(min=0, max=200, message="图片样例长度必须介于 0 和 200 之间")
	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
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
}