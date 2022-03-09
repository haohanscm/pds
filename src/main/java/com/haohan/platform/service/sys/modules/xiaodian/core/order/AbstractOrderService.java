package com.haohan.platform.service.sys.modules.xiaodian.core.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderDetailReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by zgw on 2018/9/26.
 */
public  abstract  class  AbstractOrderService  implements IOrderService{
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ShopService shopService;

    public BaseResp createOrder(BaseResp baseResp , BaseOrderReq baseOrderReq, GoodsOrder goodsOrder){
        String merchantId = baseOrderReq.getMerchantId();
        goodsOrder = baseOrderReq.copyToGoodsOrder(goodsOrder);

        Merchant merchant = merchantService.get(merchantId);
        if (null != merchant){
            goodsOrder.setMerchantName(merchant.getMerchantName());
        }else {
            baseResp.error();
            baseResp.setMsg("merchantId不正确");
            return baseResp;
        }

        Shop shop = shopService.get(baseOrderReq.getShopId());
        if (null != shop){
            goodsOrder.setShopName(shop.getName());
        }

        goodsOrder.setOrderMarks(baseOrderReq.getOrderRemark());
        goodsOrder.setOrderTime(new Date());
        baseResp.success();
        return baseResp;
    }

}
