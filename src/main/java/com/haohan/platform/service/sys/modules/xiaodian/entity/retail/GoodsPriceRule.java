package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 定价规则管理Entity
 * @author haohan
 * @version 2017-12-07
 */
public class GoodsPriceRule extends DataEntity<GoodsPriceRule> {
	
	private static final long serialVersionUID = 1L;
	private String ruleName;		// 规则名称
	private String shopId;		// 店铺ID
	private String merchantId;		// 商家ID
	private String goodsId;		// 商品ID
	private String ruleDesc;		// 市场价/销售价
	private BigDecimal wholesalePrice;		// 批发定价,单位元
	private BigDecimal vipPrice;		// vip定价,单位元
	private BigDecimal marketPrice;		// 零售定价,单位元
	private BigDecimal virtualPrice;		// 虚拟价格,单位元
	private String unit;		// 计量单位
	private Integer status;		// 状态   未使用

	private String goodsName;  // 商品名称
	private String shopName;
	private String merchantName;
	
	public GoodsPriceRule() {
		super();
	}

	public GoodsPriceRule(String id){
		super(id);
	}

	@Length(min=0, max=64, message="规则名称长度必须介于 0 和 64 之间")
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
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
	
	@Length(min=0, max=64, message="商品ID长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="市场价/销售价长度必须介于 0 和 64 之间")
	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getVirtualPrice() {
		return virtualPrice;
	}

	public void setVirtualPrice(BigDecimal virtualPrice) {
		this.virtualPrice = virtualPrice;
	}
	
	public BigDecimal getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}
	
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	@Length(min=0, max=64, message="计量单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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
}