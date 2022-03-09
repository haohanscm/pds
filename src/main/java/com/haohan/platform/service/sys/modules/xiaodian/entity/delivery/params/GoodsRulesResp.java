package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/9/5
 */
public class GoodsRulesResp implements Serializable {
    private GoodsSaleRulesResp saleRules;
    private GoodsServiceSelectionResp serviceSelection;
    private GoodsGiftsResp goodsGifts;
    private GoodsDeliveryRulesResp deliveryRules;

    public GoodsSaleRulesResp getSaleRules() {
        return saleRules;
    }

    public void setSaleRules(GoodsSaleRulesResp saleRules) {
        this.saleRules = saleRules;
    }

    public GoodsServiceSelectionResp getServiceSelection() {
        return serviceSelection;
    }

    public void setServiceSelection(GoodsServiceSelectionResp serviceSelection) {
        this.serviceSelection = serviceSelection;
    }

    public GoodsGiftsResp getGoodsGifts() {
        return goodsGifts;
    }

    public void setGoodsGifts(GoodsGiftsResp goodsGifts) {
        this.goodsGifts = goodsGifts;
    }

    public GoodsDeliveryRulesResp getDeliveryRules() {
        return deliveryRules;
    }

    public void setDeliveryRules(GoodsDeliveryRulesResp deliveryRules) {
        this.deliveryRules = deliveryRules;
    }
}
