package com.haohan.platform.service.sys.modules.xiaodian.core.order.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.GoodsOrderPdsService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.AbstractOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.IOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.*;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.service.OrderDetailApiService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zgw on 2018/9/26.
 */
@Service
public class WebOrderService extends AbstractOrderService implements IOrderService {
    @Autowired
    private UPassportService uPassportService;
    @Autowired
    private OrderDetailApiService orderDetailApiService;
    @Autowired
    private GoodsModelService goodsModelService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderPdsService goodsOrderPdsService;

    @Override
    public BaseResp createOrder(BaseResp baseResp, BaseOrderReq baseOrderReq, GoodsOrder goodsOrder) {
        try {
            baseResp = super.createOrder(baseResp, baseOrderReq,goodsOrder);
            if (!baseResp.isSuccess()){
                return baseResp;
            }


            baseResp = super.createOrder(baseResp, baseOrderReq, goodsOrder);
            if (!baseResp.isSuccess()){
                return baseResp;
            }
            WebGoodsOrderApiReq apiRequest = (WebGoodsOrderApiReq) baseOrderReq;
            String uid = apiRequest.getUid();
            UPassport uPassport = uPassportService.get(uid);
            if (null == uPassport){
                baseResp.error();
                baseResp.setMsg("用户不存在");
                return baseResp;
            }
            goodsOrder.setOrderId(CommonUtils.genId("001"));
            goodsOrder.setUid(uid);
            goodsOrder.setUserName(uPassport.getLoginName());
            goodsOrder.setDeliveryType(apiRequest.getDeliveryType());
            goodsOrder.setAddressId(apiRequest.getAddressId());
            goodsOrder.setOrderFrom(IOrderServiceConstant.OrderFrom.merchant_admin.getCode());
            goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_PAY.getCode());
            goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.NOPAY.getCode());

            List<BaseOrderDetailReq> detailList = apiRequest.getOrderDetails();
            for (BaseOrderDetailReq detailReq:detailList){
                baseResp = orderDetailApiService.check(baseResp,detailReq,goodsOrder);
                if (!baseResp.isSuccess()){
                    return baseResp;
                }
                GoodsOrderDetail goodsOrderDetail = new GoodsOrderDetail();
                BeanUtils.copyProperties(goodsOrderDetail,detailReq);
                Goods goods = goodsService.get(detailReq.getGoodsId());
                if (null == goods){
                    baseResp.error();
                    baseResp.setMsg("商品"+detailReq.getGoodsName()+"信息有误");
                    return baseResp;
                }
                goodsOrderDetail.setGoodsId(goods.getId());
                goodsOrderDetail.setGoodsName(goods.getGoodsName());
                GoodsModel goodsModel = goodsModelService.get(detailReq.getModelId());
                if (null == goodsModel){
                    baseResp.error();
                    baseResp.setMsg("商品"+detailReq.getGoodsName()+"规格信息有误");
                    return baseResp;
                }
                goodsOrderDetail.setModelId(goodsModel.getId());
                goodsOrderDetail.setModelName(goodsModel.getModelName());
                goodsOrderDetail.fromOrder(goodsOrder);
                baseResp = orderDetailApiService.save(baseResp,goodsOrderDetail);
                if (!baseResp.isSuccess()){
                    return baseResp;
                }
            }
//            baseResp = goodsOrderPdsService.convertToBuyOrder(goodsOrder);
//            if (!baseResp.isSuccess()){
//                return baseResp;
//            }
            goodsOrderService.save(goodsOrder);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.error();
            baseResp.setMsg("系统错误");
        } finally {
            return baseResp;
        }
    }

    @Override
    public BaseResp cancelOrder() {
        return null;
    }

    @Override
    public BaseResp deleteOrder() {
        return null;
    }
}
