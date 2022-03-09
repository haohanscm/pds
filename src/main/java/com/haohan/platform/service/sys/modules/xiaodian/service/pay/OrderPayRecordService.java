package com.haohan.platform.service.sys.modules.xiaodian.service.pay;

import com.haohan.framework.utils.DateUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.thirdplatform.mshpay.service.impl.MshPayServiceImpl;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.inf.IXmBankPayService;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.BankServiceType;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.pay.OrderPayRecordDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.MerchantPayInfo;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单支付Service
 * @author haohan
 * @version 2017-12-10
 */
@Service
@Transactional(readOnly = true)
public class OrderPayRecordService extends CrudService<OrderPayRecordDao, OrderPayRecord> {


    @Resource
    private IXmBankPayService xmBankPayService;
    @Autowired
    private MerchantPayInfoService merchantPayInfoService;
    @Autowired
    @Lazy(true)
    private MshPayServiceImpl mshPayService;

    public OrderPayRecord get(String id) {
        return super.get(id);
    }

    public List<OrderPayRecord> findList(OrderPayRecord orderPayRecord) {
        return super.findList(orderPayRecord);
    }

    public Page<OrderPayRecord> findPage(Page<OrderPayRecord> page, OrderPayRecord orderPayRecord) {
        return super.findPage(page, orderPayRecord);
    }


    @Transactional(readOnly = false)
    public void save(OrderPayRecord orderPayRecord) {
        super.save(orderPayRecord);
    }

    @Transactional(readOnly = false)
    public void delete(OrderPayRecord orderPayRecord) {
        super.delete(orderPayRecord);
    }

    public OrderPayRecord callBankService(OrderPayRecord orderPayRecord) {

//        if(StringUtils.equalsAnyIgnoreCase("true", DictUtils.getDictValue("测试","xiaodian_pay_test",""))) {
//            orderPayRecord.setRespCode("00");
//            orderPayRecord.setRespDesc("测试支付成功");
//            return orderPayRecord;
//        }
        MerchantPayInfo payInfo = merchantPayInfoService.fetchByPartnerId(orderPayRecord.getPartnerId());

        if(0 == BigDecimal.ZERO.compareTo(orderPayRecord.getOrderAmount())){
            orderPayRecord.setRespCode("00");
            orderPayRecord.setRespDesc("测试支付成功");
            return orderPayRecord;
        }

        switch (BankServiceType.valueOfServiceType(orderPayRecord.getPayType())) {

            case AliPayQrCode:
            case WexinQrCode:
                if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.XMBANK.getCode())){
                    return xmBankPayService.csbPay(orderPayRecord);
                }
                if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.MSHPAY.getCode())){
                    return mshPayService.csbPay(orderPayRecord);
                }
            case AliAuthPay :
            case WexinAuthPay:

                if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.XMBANK.getCode())){
                    return xmBankPayService.bscPay(orderPayRecord);
                }
                if (payInfo.getBankChannel().equals(IMerchantConstant.BankChannel.MSHPAY.getCode())){
                    return mshPayService.bscPay(orderPayRecord);
                }
            case WexinMpPay :
            case AliServicePay:
            case WexinAppPay:
                return xmBankPayService.jsPay(orderPayRecord);
            case CashPay:
                cashPay(orderPayRecord);
                return orderPayRecord;
        }
        orderPayRecord.setRespCode("900");
        orderPayRecord.setRespDesc("接口不支持");
        return orderPayRecord;
    }


    public OrderPayRecord fetchByOrderId(String orderId){
        OrderPayRecord order = new OrderPayRecord();
        order.setOrderId(orderId);
        List<OrderPayRecord> list =findList(order);
        return CollectionUtils.isEmpty(list)?null:list.get(0);
    }

    @Transactional(readOnly = false)
    public void updatePayStatus(String orderId,IBankServiceConstant.PayStatus payStatus){
        OrderPayRecord payRecord = fetchByOrderId(orderId);
        if (null != payRecord){
            payRecord.setPayStatus(payStatus.getCode());
            super.save(payRecord);
        }
    }

    public BigDecimal sumSaleAmount(OrderPayRecord orderPayRecord){
        BigDecimal result = dao.sumSaleAmount(orderPayRecord);
        return null == result?BigDecimal.ZERO:result;
    }

    public OrderPayRecord cashPay(OrderPayRecord orderPayRecord){
        orderPayRecord.setRespCode(IBankServiceConstant.BankServiceStatus.SUCCESS.getCode());
        orderPayRecord.setPayStatus(IBankServiceConstant.PayStatus.SUCCESS.getCode());
        orderPayRecord.setRespTime(new Date());
        Map<String , Object> respMap = new HashMap<>();
        respMap.put("orderId",orderPayRecord.getOrderId());
        respMap.put("reqId",orderPayRecord.getRequestId());
        respMap.put("responseCode",orderPayRecord.getRespCode());
        respMap.put("transResult",IBankServiceConstant.TransStatus.SUCCESS.getCode());
        respMap.put("transTime",DateUtils.getWsTime());
        respMap.put("acquirerType",orderPayRecord.getPayChannel());
        orderPayRecord.setPayInfo(JacksonUtils.toJson(respMap));
        return orderPayRecord;
    }
}