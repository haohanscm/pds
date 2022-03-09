package com.haohan.platform.service.sys.modules.xiaodian.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;

/**
 * Created by zgw on 2017/12/10.
 */
public interface IMerchantApiService {


    /**
     * 商家注册
     *
     * @param merchant
     * @return BaseResp
     */
    BaseResp reg(Merchant merchant);


    /**
     * 新增商家店铺
     *
     * @param shop
     * @return BaseResp
     */
    BaseResp addShop(Shop shop);


    /**
     * 商家入驻开户
     *
     * @param merchantPayInfo
     * @return BaseResp
     */
    BaseResp bankAccountReg(MerchantPayInfo merchantPayInfo);


    /**
     * 商家支付
     * @param payRecord
     * @return BaseResp
     */
    BaseResp pay(OrderPayRecord payRecord);


    /**
     * 商家订单撤销
     * @param orderCancel
     * @return BaseResp
     */
    BaseResp orderCancel(OrderCancel orderCancel);


    /**
     * 商家退款
     *
     * @param refundManage
     * @return BaseResp
     */
    BaseResp refund(RefundManage refundManage);


    /**
     * 交易状态查询
     * @param query
     * @return BaseResp
     */
    BaseResp orderQuery(OrderQuery query);



    /**
     * 输入订单号和金额退款
     * @param orderAmount
     * @return
     */
    BaseResp refundByOrderIdAndAmount(GoodsOrder goodsOrder, String orderAmount);



}
