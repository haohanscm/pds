package com.haohan.platform.service.sys.modules.pds.entity.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台商品定价管理Entity
 * @author haohan
 * @version 2018-12-08
 */
public class PdsPlatformGoodsPrice extends DataEntity<PdsPlatformGoodsPrice> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;		// 平台商家ID
	private String merchantId;		// 采购商商家ID
	private String buyerId;		// 采购商ID
	private String categoryId;		// 商品分类ID
	private String goodsId;		// 商品spuID
	private String modelId;		// 商品skuID
	private String categoryName;		// 商品分类名称
	private String goodsName;		// 商品名称
	private String modelName;		// 规格名称
	private String unit;		// 单位
	private BigDecimal price;		// 采购价
	private Date startDate;		// 起始时间
	private Date endDate;		// 截止时间
	private String status;		// 上下架 状态
	private Date beginStartDate;		// 开始 起始时间
	private Date endStartDate;		// 结束 起始时间
	private Date beginEndDate;		// 开始 截止时间
	private Date endEndDate;		// 结束 截止时间

	private BigDecimal marketPrice; //市场价
    private BigDecimal rate; // 上浮比例
    private String shopId; // 平台商家店铺id
	private Date queryDate;	//查询日期

    private String pmName;		// 平台商家名称 
    private String merchantName;		// 采购商商家名称 
    private String buyerName;		// 采购商名称
	private String newBuyerId;		//copy:新采购商ID
	private String newMerchantId;	//copy:新采购商merchantId
    private String goodsModelSn;   // 商品规格唯一编号 sku唯一
	
	public PdsPlatformGoodsPrice() {
		super();
	}

	public PdsPlatformGoodsPrice(String id){
		super(id);
	}

	@Length(min=0, max=64, message="平台商家ID长度必须介于 0 和 64 之间")
	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	
	@Length(min=0, max=64, message="采购商商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="采购商ID长度必须介于 0 和 64 之间")
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	@Length(min=0, max=64, message="商品分类ID长度必须介于 0 和 64 之间")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Length(min=0, max=64, message="商品spuID长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="商品skuID长度必须介于 0 和 64 之间")
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Length(min=0, max=64, message="商品分类名称长度必须介于 0 和 64 之间")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=64, message="规格名称长度必须介于 0 和 64 之间")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Length(min=0, max=64, message="单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginStartDate() {
		return beginStartDate;
	}

	public void setBeginStartDate(Date beginStartDate) {
		this.beginStartDate = beginStartDate;
	}
	
	public Date getEndStartDate() {
		return endStartDate;
	}

	public void setEndStartDate(Date endStartDate) {
		this.endStartDate = endStartDate;
	}
		
	public Date getBeginEndDate() {
		return beginEndDate;
	}

	public void setBeginEndDate(Date beginEndDate) {
		this.beginEndDate = beginEndDate;
	}
	
	public Date getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(Date endEndDate) {
		this.endEndDate = endEndDate;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

	public String getNewBuyerId() {
		return newBuyerId;
	}

	public void setNewBuyerId(String newBuyerId) {
		this.newBuyerId = newBuyerId;
	}

	public String getNewMerchantId() {
		return newMerchantId;
	}

	public void setNewMerchantId(String newMerchantId) {
		this.newMerchantId = newMerchantId;
	}

    public String getGoodsModelSn() {
        return goodsModelSn;
    }

    public void setGoodsModelSn(String goodsModelSn) {
        this.goodsModelSn = goodsModelSn;
    }
}