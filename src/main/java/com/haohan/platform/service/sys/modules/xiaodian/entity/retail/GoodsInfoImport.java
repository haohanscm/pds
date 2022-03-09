package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import java.math.BigDecimal;

public class GoodsInfoImport extends Goods {

    private String ruleDesc;		// 市场价/销售价
    private BigDecimal wholesalePrice;		// 批发定价,单位元
    private BigDecimal vipPrice;		// vip定价,单位元
    private BigDecimal marketPrice;		// 零售定价,单位元
    private BigDecimal virtualPrice;		// 虚拟价格,单位元
    private String unit;		// 计量单位

    public GoodsInfoImport(){
        super();
    }

    // 获取定价规则
    public void fetchGoodsPriceRule(GoodsPriceRule goodsPriceRule){
        goodsPriceRule.setRuleName("简单价格");
        goodsPriceRule.setShopId(this.getShopId());
        goodsPriceRule.setMerchantId(this.getMerchantId());
        goodsPriceRule.setGoodsId(this.getId());
        goodsPriceRule.setRuleDesc(this.ruleDesc);
        goodsPriceRule.setWholesalePrice(this.wholesalePrice);
        goodsPriceRule.setVipPrice(this.vipPrice);
        goodsPriceRule.setMarketPrice(this.marketPrice);
        goodsPriceRule.setVirtualPrice(this.virtualPrice);
        goodsPriceRule.setUnit(this.unit);
        goodsPriceRule.setStatus(1);
    }

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(BigDecimal virtualPrice) {
        this.virtualPrice = virtualPrice;
    }
}
