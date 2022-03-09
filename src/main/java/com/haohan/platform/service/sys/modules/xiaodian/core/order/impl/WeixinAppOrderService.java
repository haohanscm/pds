package com.haohan.platform.service.sys.modules.xiaodian.core.order.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.AbstractOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.IOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.WxAppGoodsOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.WxOrderDetailReq;
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
public class WeixinAppOrderService extends AbstractOrderService implements IOrderService {
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private OrderDetailApiService orderDetailApiService;
    @Autowired
    private UPassportService uPassportService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsModelService goodsModelService;

    @Override
    public BaseResp createOrder(BaseResp baseResp, BaseOrderReq baseOrderReq, GoodsOrder goodsOrder) {
        try {
            baseResp = super.createOrder(baseResp, baseOrderReq, goodsOrder);
            if (!baseResp.isSuccess()){
                return baseResp;
            }
            WxAppGoodsOrderReq wxAppGoodsOrderReq = (WxAppGoodsOrderReq) baseOrderReq;
            String uid = wxAppGoodsOrderReq.getUid();
            String partnerNum = wxAppGoodsOrderReq.getPartnerNum();
            String orderId = wxAppGoodsOrderReq.getOrderId();
            UPassport uPassport = uPassportService.get(uid);
            if (null == uPassport){
                baseResp.error();
                baseResp.setMsg("下单用户不合法");
                return baseResp;
            }

            goodsOrder.setOrderId(CommonUtils.genId(wxAppGoodsOrderReq.getOrderType()));
            goodsOrder.setUid(uid);
            goodsOrder.setUserName(uPassport.getLoginName());
            goodsOrder.setAppid(wxAppGoodsOrderReq.getAppid());
            goodsOrder.setDeliveryType(wxAppGoodsOrderReq.getDeliveryType());
            goodsOrder.setAddressId(wxAppGoodsOrderReq.getAddressId());
            goodsOrder.setOrderFrom(IOrderServiceConstant.OrderFrom.wx_app.getCode());
            goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_PAY.getCode());
            goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.NOPAY.getCode());

            List<WxOrderDetailReq> detailList = wxAppGoodsOrderReq.getOrderDetails();
            for (WxOrderDetailReq detailReq:detailList){
                baseResp = orderDetailApiService.check(baseResp,detailReq,goodsOrder);
                if (!baseResp.isSuccess()){
                    return baseResp;
                }
                GoodsOrderDetail goodsOrderDetail = new GoodsOrderDetail();
                BeanUtils.copyProperties(goodsOrderDetail,detailReq);
                String goodsId = detailReq.getGoodsId();
                String modelId = detailReq.getModelId();

                Goods goods;
                GoodsModel goodsModel;


                goods = goodsService.get(goodsId);
                goodsModel = goodsModelService.get(modelId);

                if (null == goods){
                    baseResp.error();
                    baseResp.setMsg("商品"+detailReq.getGoodsName()+"信息有误");
                    return baseResp;
                }
                if (null == goodsModel){
                    baseResp.error();
                    baseResp.setMsg("商品"+detailReq.getGoodsName()+"规格信息有误");
                    return baseResp;
                }
                goodsOrderDetail.setGoodsId(goods.getId());
                goodsOrderDetail.setGoodsName(goods.getGoodsName());
                goodsOrderDetail.setModelId(goodsModel.getId());
                goodsOrderDetail.setModelName(goodsModel.getModelName());
                goodsOrderDetail.fromOrder(goodsOrder);
                baseResp = orderDetailApiService.save(baseResp,goodsOrderDetail);
                if (!baseResp.isSuccess()){
                    return baseResp;
                }
            }
            goodsOrderService.save(goodsOrder);
            baseResp.setExt(goodsOrder.getOrderId());
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
