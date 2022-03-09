package com.haohan.platform.service.sys.modules.xiaodian.entity.order;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 购物车Entity
 * @author haohan
 * @version 2017-12-07
 */
public class Cart extends DataEntity<Cart> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String sessionId;		// 用户sessionID
	private String shopId;		// 店铺ID
	private String goodsId;		// 商品ID
	private String goodsSn;		// 商品编号
	private String productId;		// 产品ID
	private String goodsName;		// 商品名称
	private BigDecimal marketPrice;		// 商品市场价
	private BigDecimal goodsPrice;		// 商品销售价
	private String goodsUnit;		// 商品单位
	private String goodsNum;		// 购买数量
	private String goodsAttrIds;		// 商品属性ID集合
	private Integer isReal;		// 是否实物
	private String extAttr;		// 商品扩展属性
	private String parentId;		// 父商品ID
	private String cartGoodsType;		// 购物类型
	private String activityId;		// 活动ID
	private String isShipping;		// 是否需要配送
	
	public Cart() {
		super();
	}

	public Cart(String id){
		super(id);
	}

	@Length(min=0, max=64, message="用户ID长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="用户sessionID长度必须介于 0 和 64 之间")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="商品ID长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="商品编号长度必须介于 0 和 64 之间")
	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	
	@Length(min=0, max=64, message="产品ID长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=100, message="商品名称长度必须介于 0 和 100 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	@Length(min=0, max=50, message="商品单位长度必须介于 0 和 50 之间")
	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	
	@Length(min=0, max=5, message="购买数量长度必须介于 0 和 5 之间")
	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	@Length(min=0, max=64, message="商品属性ID集合长度必须介于 0 和 64 之间")
	public String getGoodsAttrIds() {
		return goodsAttrIds;
	}

	public void setGoodsAttrIds(String goodsAttrIds) {
		this.goodsAttrIds = goodsAttrIds;
	}
	
	public Integer getIsReal() {
		return isReal;
	}

	public void setIsReal(Integer isReal) {
		this.isReal = isReal;
	}
	
	public String getExtAttr() {
		return extAttr;
	}

	public void setExtAttr(String extAttr) {
		this.extAttr = extAttr;
	}
	
	@Length(min=0, max=500, message="父商品ID长度必须介于 0 和 500 之间")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Length(min=0, max=1, message="购物类型长度必须介于 0 和 1 之间")
	public String getCartGoodsType() {
		return cartGoodsType;
	}

	public void setCartGoodsType(String cartGoodsType) {
		this.cartGoodsType = cartGoodsType;
	}
	
	@Length(min=0, max=64, message="活动ID长度必须介于 0 和 64 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=1, message="是否需要配送长度必须介于 0 和 1 之间")
	public String getIsShipping() {
		return isShipping;
	}

	public void setIsShipping(String isShipping) {
		this.isShipping = isShipping;
	}
	
}