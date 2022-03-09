package com.haohan.platform.service.sys.modules.xiaodian.api.service.order;

import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IOrderServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.req.OrderRefundApplyReq;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.RefundManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.RefundManageService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/10/8
 */
@Service
public class RefundOrderApiService {
    @Autowired
    private RefundManageService refundManageService;

    //创建退款交易记录
    public void formRefundRecord(OrderRefundApplyReq refundApplyReq , GoodsOrder goodsOrder, OrderPayRecord orderPayRecord){
        String requestId = CommonUtils.genId(orderPayRecord.getPayType());

        RefundManage refundManage = new RefundManage();
        refundManage.setMerchantId(goodsOrder.getMerchantId());
        refundManage.setMerchantName(goodsOrder.getMerchantName());
        refundManage.setRequestId(requestId);
        refundManage.setPartnerId(orderPayRecord.getPartnerId());
        refundManage.setOrderId(goodsOrder.getOrderId());
        refundManage.setOrgReqId(orderPayRecord.getRequestId());
        refundManage.setOrgTransId(orderPayRecord.getTransId());
        refundManage.setOrderAmount(goodsOrder.getOrderAmount());
        refundManage.setPayAmount(orderPayRecord.getOrderAmount());
        refundManage.setRefundAmount(refundApplyReq.getRefundAmount());
        refundManage.setBusType("3");
        refundManage.setRefundCause(refundApplyReq.getCause());
        refundManage.setRefundApplyTime(new Date());
        refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDAPPLY.getCode());
        refundManage.setRespDesc("发起退款申请");
        goodsOrder.setOrderStatus(IOrderServiceConstant.OrderStatus.REFUNDING.getCode());

        refundManageService.save(refundManage);
    }

    //商家同意退款

    //商家拒绝退款


}
