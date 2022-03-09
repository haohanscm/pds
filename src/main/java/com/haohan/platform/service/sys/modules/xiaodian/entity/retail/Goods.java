package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品管理Entity
 * @author haohan
 * @version 2017-08-06
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord", "jisuappId"})
public class Goods extends DataEntity<Goods> {
	
	private static final long serialVersionUID = 1L;
	private String shopId;		// 店铺ID
	private String merchantId;		// 商家ID
	private String goodsCategoryId;		// 商品分类
	private String goodsSn;		// 商品唯一编号
	private String goodsName;		// 商品名称
	private String detailDesc;		// 商品描述
	private String simpleDesc;		// 概要描述 关键词
	private String brandId;		// 商品品牌ID
	private String saleRule;		// 售卖规则标记 yes_no
	private String photoGroupNum;		// 图片组编号
	private String thumbUrl;		// 缩略图地址
	private Integer isMarketable;		// 是否上架
	private Integer storage;		// 库存数量
    private String serviceSelection; // 服务选项标记  yes_no
    private String deliveryRule; // 配送规则标记  yes_no
    private String goodsGift; // 赠品标记  yes_no
    private String goodsModel; // 商品规格标记  yes_no
	private String goodsStatus; // 商品状态  IGoodsConstant.GoodsStatus  0 出售中 1 仓库中 2 已售罄
	private String goodsFrom; // 商品来源
	private String sort; // 商品排序
    private String goodsType;// 商品类型 0实体商品，1虚拟商品
    private String scanCode;//扫码购编码
	private String thirdGoodsSn;  // 第三方编号 即速商品id
	private String generalSn;  // 通用编号 公共商品库通用编号

	private String shopName;
	private String merchantName;
	private String categoryName; // 分类名称
    private String categorySn; // 分类编号
    private String jisuappId;  // 即速应用id
	private String updateJisu; // 是否同步即速应用

    //价格
    private BigDecimal marketPrice;		// 零售定价,单位元
    private String unit;		// 计量单位

    private String salecFlag;  //是否c端销售

    // 重写的分页查询
    private int pageStart;
    private int pageSize;
    private String buyerMerchantId;
    private String buyerUid;
    private Date deliveryDate;

	public Goods() {
		super();
	}

	public Goods(String id){
		super(id);
	}

	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=500, message="商品分类长度必须介于 0 和 500 之间")
	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	
	@Length(min=0, max=64, message="商品唯一编号长度必须介于 0 和 64 之间")
	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	
	@Length(min=0, max=128, message="商品名称长度必须介于 0 和 128 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public String getDetailDesc() {
		return detailDesc;
	}

	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}
	
	@Length(min=0, max=255, message="概要描述长度必须介于 0 和 255 之间")
	public String getSimpleDesc() {
		return simpleDesc;
	}

	public void setSimpleDesc(String simpleDesc) {
		this.simpleDesc = simpleDesc;
	}
	
	@Length(min=0, max=64, message="商品品牌ID长度必须介于 0 和 64 之间")
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	

	@Length(min=0, max=64, message="图片组编号长度必须介于 0 和 64 之间")
	public String getPhotoGroupNum() {
		return photoGroupNum;
	}

	public void setPhotoGroupNum(String photoGroupNum) {
		this.photoGroupNum = photoGroupNum;
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

    @Length(min=0, max=1, message="是否上架长度必须介于 0 和 1 之间")
	public Integer getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}
	
	public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

    @Length(min=0, max=1, message="启用售卖规则长度必须介于 0 和 1 之间")
    public String getSaleRule() {
        return saleRule;
    }

    public void setSaleRule(String saleRule) {
        this.saleRule = saleRule;
    }

    @Length(min=0, max=1, message="启用服务选项长度必须介于 0 和 1 之间")
    public String getServiceSelection() {
        return serviceSelection;
    }

    public void setServiceSelection(String serviceSelection) {
        this.serviceSelection = serviceSelection;
    }

    @Length(min=0, max=1, message="启用配送规则长度必须介于 0 和 1 之间")
    public String getDeliveryRule() {
        return deliveryRule;
    }

    public void setDeliveryRule(String deliveryRule) {
        this.deliveryRule = deliveryRule;
    }

    @Length(min=0, max=1, message="启用赠品长度必须介于 0 和 1 之间")
    public String getGoodsGift() {
        return goodsGift;
    }

    public void setGoodsGift(String goodsGift) {
        this.goodsGift = goodsGift;
    }

    @Length(min=0, max=1, message="启用规格长度必须介于 0 和 1 之间")
	public String getGoodsModel() {
		return goodsModel;
	}

	public void setGoodsModel(String goodsModel) {
		this.goodsModel = goodsModel;
	}

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Length(min=0, max=1, message="商品状态长度必须介于 0 和 1 之间")
    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(String goodsFrom) {
        this.goodsFrom = goodsFrom;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCategorySn() {
        return categorySn;
    }

    public void setCategorySn(String categorySn) {
        this.categorySn = categorySn;
    }

    public String getJisuappId() {
        return jisuappId;
    }

    public void setJisuappId(String jisuappId) {
        this.jisuappId = jisuappId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public String getThirdGoodsSn() {
        return thirdGoodsSn;
    }

    public void setThirdGoodsSn(String thirdGoodsSn) {
        this.thirdGoodsSn = thirdGoodsSn;
    }

    public String getUpdateJisu() {
        return updateJisu;
    }

    public void setUpdateJisu(String updateJisu) {
        this.updateJisu = updateJisu;
    }

    public String getGeneralSn() {
        return generalSn;
    }

    public void setGeneralSn(String generalSn) {
        this.generalSn = generalSn;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getBuyerMerchantId() {
        return buyerMerchantId;
    }

    public void setBuyerMerchantId(String buyerMerchantId) {
        this.buyerMerchantId = buyerMerchantId;
    }

    public String getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(String buyerUid) {
        this.buyerUid = buyerUid;
    }
    public String getSalecFlag() {
        return salecFlag;
    }

    public void setSalecFlag(String salecFlag) {
        this.salecFlag = salecFlag;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}