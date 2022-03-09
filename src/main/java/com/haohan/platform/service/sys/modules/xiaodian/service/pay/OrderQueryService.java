package com.haohan.platform.service.sys.modules.xiaodian.service.pay;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.constant.XmBankEnums;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.inf.IXmBankPayService;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.constant.MshPayConstant;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.service.inf.IMshPayService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.pay.OrderQueryDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易状态查询Service
 *
 * @author haohan
 * @version 2017-12-12
 */
@Service
@Transactional(readOnly = true)
public class OrderQueryService extends CrudService<OrderQueryDao, OrderQuery> {

    @Autowired
    private OrderPayRecordService orderPayRecordService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private RefundManageService refundManageService;

    @Resource
    private IXmBankPayService xmBankPayService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Autowired
    private PayNotifyService payNotifyService;
    @Resource
    private IMshPayService mshPayService;

    public OrderQuery get(String id) {
        return super.get(id);
    }

    public List<OrderQuery> findList(OrderQuery orderQuery) {
        return super.findList(orderQuery);
    }

    public Page<OrderQuery> findPage(Page<OrderQuery> page, OrderQuery orderQuery) {
        return super.findPage(page, orderQuery);
    }

    @Transactional(readOnly = false)
    public void save(OrderQuery orderQuery) {
        super.save(orderQuery);
    }

    @Transactional(readOnly = false)
    public void delete(OrderQuery orderQuery) {
        super.delete(orderQuery);
    }

    @Transactional(readOnly = false)
    public OrderQuery checkPayStatus(String orderId, XmBankEnums.TransType type) {

        if (StringUtils.isBlank(orderId)) {
            return null;
        }

        OrderQuery query = new OrderQuery();

        OrderPayRecord orderPayRecord = orderPayRecordService.fetchByOrderId(orderId);
        GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
        RefundManage refundManage = null;

        switch (type) {
            case consume: {
                if (null != orderPayRecord) {
                    query.setMerchantId(orderPayRecord.getMerchantId());
                    query.setPartnerId(orderPayRecord.getPartnerId());
                    query.setRequestId(orderPayRecord.getRequestId());
                    query.setOrderId(orderPayRecord.getOrderId());
                    query.setOrderTime(orderPayRecord.getOrderTime());
                    query.setTransType(XmBankEnums.TransType.consume.getCode());
                    query.setThirdOrdNo(orderPayRecord.getTransId());
                }
                break;
            }

            case refund: {
                refundManage = refundManageService.fetchByOrderId(orderId);
                if (null != refundManage) {
                    query.setMerchantId(refundManage.getMerchantId());
                    query.setPartnerId(refundManage.getPartnerId());
                    query.setRequestId(refundManage.getRequestId());
                    query.setOrderId(refundManage.getOrderId());
                    query.setOrderTime(refundManage.getRefundApplyTime());
                    query.setTransType(XmBankEnums.TransType.refund.getCode());
                    query.setThirdOrdNo(orderPayRecord.getTransId());
                }
                break;
            }

        }
        //先查询Notify
        PayNotify notify = payNotifyService.fetchByOrderId(orderId);
        MerchantPayInfo payInfo = merchantPayInfoService.fetchByPartnerId(query.getPartnerId());
        if(null != notify){
            if (XmBankEnums.TransType.consume.getCode().equals(query.getTransType())){
                query.setPayResult(IBankServiceConstant.PayStatus.SUCCESS.getCode());
                query.setRespCode(notify.getRespCode());
                query.setRemarks(notify.getRemarks());
            }
            if (XmBankEnums.TransType.refund.getCode().equals(query.getTransType())) {
                if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.XMBANK.getCode())) {
                    xmBankPayService.payStatusQuery(query);
                }
                if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.MSHPAY.getCode())){
                    mshPayService.orderQuery(query);
                }
            }
        }else{
            if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.XMBANK.getCode())) {
                xmBankPayService.payStatusQuery(query);
            }
            if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.MSHPAY.getCode())){
                mshPayService.orderQuery(query);
            }
        }
        if (null != orderPayRecord) {
            orderPayRecord.setPayStatus(query.getPayResult());
            if(null != goodsOrder) {
                goodsOrder.setPayStatus(query.getPayResult());
                goodsOrderService.save(goodsOrder);
            }
            orderPayRecordService.save(orderPayRecord);
        }
        if (null != refundManage) {
            if (IBankServiceConstant.TransStatus.SUCCESS.getCode().equals(query.getPayResult())||MshPayConstant.SUCCESS.getCode().toString().equals(query.getPayResult())){
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDED.getCode());
            }else {
                refundManage.setStatus(IBankServiceConstant.RefundStatus.REFUNDFAIL.getCode());
            }
            refundManageService.save(refundManage);
        }

        if (null != query) {
            super.save(query);
            return query;
        }
        return null;

    }
}