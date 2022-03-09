package com.haohan.platform.service.sys.modules.xiaodian.core.order.impl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.AbstractOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.IOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.BaseOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req.TerminalGoodsOrderReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zgw on 2018/9/26.
 */
@Service
public class TerminalAppOrderService extends AbstractOrderService implements IOrderService {
    @Autowired
    private UPassportService uPassportService;

    @Override
    public BaseResp createOrder(BaseResp baseResp , BaseOrderReq baseOrderReq, GoodsOrder goodsOrder) {
        try {
            baseResp = super.createOrder(baseResp, baseOrderReq,goodsOrder);
            if (!baseResp.isSuccess()){
                return baseResp;
            }
            TerminalGoodsOrderReq terminalGoodsOrderReq = (TerminalGoodsOrderReq) baseOrderReq;
            String uid = terminalGoodsOrderReq.getUid();
            UPassport uPassport = uPassportService.get(uid);
            if (null == uPassport){
                baseResp.error();
                baseResp.setMsg("下单用户不合法");
                return baseResp;
            }
            goodsOrder.setUid(uid);
            goodsOrder.setUserName(uPassport.getLoginName());
            goodsOrder.setOrderId(CommonUtils.genId("005"));
            goodsOrder.setPartnerNum("005");
            goodsOrder.setOrderFrom(IOrderServiceConstant.OrderFrom.terminal.getCode());
            goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.WAITE_PAY.getCode());
            goodsOrder.setPayStatus(IBankServiceConstant.PayStatus.NOPAY.getCode());
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
