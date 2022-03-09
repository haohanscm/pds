package com.haohan.platform.service.sys.modules.xiaodian.api.service.order;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryAddressService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.SaleRulesService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderPayRecordService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/9/10
 */
@Service
public class GoodsOrderApiService {

    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderDetailService goodsOrderDetailService;
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SaleRulesService saleRulesService;
    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;

//    //订单生成
//    public BaseResp addGoodsOrder(BaseResp baseResp, GoodsOrderApiReq goodsOrderApiReq) throws Exception{
//        String merchantId = goodsOrderApiReq.getMerchantId();
////        String shopId = goodsOrderApiReq.getShopId();
//        String uid = goodsOrderApiReq.getUid();
//        String orderId = goodsOrderApiReq.getOrderId();
//        String jsAddressId = goodsOrderApiReq.getJsAddressId();
//        String goodsDetails = goodsOrderApiReq.getGoodsDetails();
//        String appid = goodsOrderApiReq.getAppid();
//        String orderInfo = goodsOrderApiReq.getOrderInfo();
//
//        Merchant merchant = merchantService.get(merchantId);
//        MerchantAppManage merchantAppManage = merchantAppManageService.fetchByAppId(appid);
//        if (null == merchantAppManage){
//            baseResp.error();
//            baseResp.setMsg("应用未启用");
//            return baseResp;
//        }
//        String jsAppId = merchantAppManage.getJisuAppId();
//
//        GoodsOrder goodsOrder = new GoodsOrder();
//        BeanUtils.copyProperties(goodsOrderApiReq,goodsOrder);
//        goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.DOING.getCode());
//        goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_PAY.getCode());
//        goodsOrder.setMerchantName(merchant.getMerchantName());
////        goodsOrder.setShopName(shop.getName());
//        goodsOrder.setOrderTime(new Date());
//        goodsOrder.setOrderInfo(StringEscapeUtils.unescapeHtml3(orderInfo));
//
//        DeliveryAddress deliveryAddress = deliveryAddressService.fetchByJsAppIdAndjsAddressId(jsAppId,jsAddressId);
//        if (null == deliveryAddress){
//            baseResp.error();
//            baseResp.setMsg("下单地址错误,请删除地址后重新添加");
//            return baseResp;
//        }
//        List<GoodsOrderDetail> detailList = JacksonUtils.readListValue(StringEscapeUtils.unescapeHtml3(goodsDetails),GoodsOrderDetail.class);
//        goodsOrder.setAddressId(deliveryAddress.getId());
//        for (GoodsOrderDetail detail : detailList){
//            Goods goods = goodsService.get(detail.getGoodsId());
//            SaleRules saleRules = saleRulesService.findByGoodsId(detail.getGoodsId());
//
//            //是否启用售卖规则
//            if (StringUtils.equals(goods.getSaleRule(), ICommonConstant.YesNoType.yes.getCode())) {
//                //验证规则
//                if (null != saleRules) {
//                    //1 检查是否允许在该区域售卖
//                    boolean isAllowSale = wxShopCommonApiService.checkGoodsAreas(saleRules, deliveryAddress);
//                    if (!isAllowSale) {
//                        baseResp.error();
//                        baseResp.setMsg("下单失败,下单地址超出售卖区域");
//                        return baseResp;
//                    }
//                    //2 检查是否未达到最大购买次数
//                    boolean notToMaxTimes = wxShopCommonApiService.checkMaxLimitBuy(saleRules, uid, detail.getGoodsId(), detail.getGoodsNum());
//                    if (!notToMaxTimes) {
//                        baseResp.error();
//                        baseResp.setMsg("下单失败,已达到最大购买次数");
//                        return baseResp;
//                    }
//                    //3 检查购买数量是否大于起售数量
//                    boolean isOverMinLimitBuy = wxShopCommonApiService.checkIsOverMinLimit(saleRules, detail.getGoodsNum());
//                    if (!isOverMinLimitBuy) {
//                        baseResp.error();
//                        baseResp.setMsg("下单失败,商品数量少于起售数量");
//                        return baseResp;
//                    }
//                    //4 检查售卖时间是否合法
//                    //boolean checkSaleTime = wxShopCommonApiService.checkSaleTime(detail.getGoodsId(),goodsOrder.getOrderTime());
//                }
//            }
//
//            detail.setOrderId(orderId);
//            detail.setMerchantId(merchantId);
//            goodsOrderDetailService.save(detail);
//        }
//
//        goodsOrderService.save(goodsOrder);
//        baseResp.success();
//        return baseResp;
//    }

    //订单变更

    //订单取消
    public BaseResp orderCancel(BaseResp baseResp, GoodsOrder goodsOrder){
        if (!(IOrderServiceConstant.OrderStatus.NOT_CONFIRM.getCode().equals(goodsOrder.getOrderStatus())
                || IOrderServiceConstant.OrderStatus.WAITE_PAY.getCode().equals(goodsOrder.getOrderStatus()))){
            baseResp.error();
            baseResp.setMsg("该订单不支持此操作");
            return baseResp;
        }

        goodsOrderService.updateOrderStatus(goodsOrder.getOrderId(), IOrderServiceConstant.OrderStatus.CLOSED);
        baseResp.success();
        return baseResp;
    }

    //订单删除

    //订单分配

    //订单关闭
    public void orderClose(String orderId){
        OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(orderId);
        if(null != orderPayRecord){
            String status = orderPayRecord.getPayStatus();
            if(status.equals(IBankServiceConstant.PayStatus.NOPAY.getCode()) || status.equals(IBankServiceConstant.PayStatus.DOING))
            orderPayRecordService.updatePayStatus(orderId, IBankServiceConstant.PayStatus.CLOSED);
        }
        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        if (null != goodsOrder){
            String  status = goodsOrder.getOrderStatus();
            if (IOrderServiceConstant.OrderStatus.NOT_CONFIRM.getCode().equals(status)
                    || IOrderServiceConstant.OrderStatus.WAITE_PAY.getCode().equals(status)){
                goodsOrderService.updatePayStatus(orderId, IBankServiceConstant.PayStatus.CLOSED);
                goodsOrderService.updateOrderStatus(orderId, IOrderServiceConstant.OrderStatus.CLOSED);
            }
        }
    }


    //支付成功更新商品订单
    public void paySuccess(OrderPayRecord orderPayRecord, GoodsOrder goodsOrder){
        goodsOrder.setPayId(orderPayRecord.getRequestId());
        goodsOrder.setAppid(orderPayRecord.getAppid());
        goodsOrder.setPayTime(new Date());
        goodsOrder.setPayType(orderPayRecord.getPayType());
        goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
        goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_SHIP.getCode());
        goodsOrderService.save(goodsOrder);
    }


}
