package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.service.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.response.BillDownLoadResponse;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.NotifyParams;
import com.haohan.platform.service.sys.modules.thirdplatform.smartpay.entity.ReqParams;
import com.haohan.platform.service.sys.modules.xiaodian.api.constant.IBankServiceConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.*;

/**
 * Created by zgw on 2018/3/21.
 */
public interface IXmBankPayService extends IBankServiceConstant {


    /**
     * 商户开户
     * @param merchantPayInfo
     * @return
     */
     MerchantPayInfo merchantReg(MerchantPayInfo merchantPayInfo);


    /**
     * 查询商户状态
     * @param params
     * @return
     */
     MerchantPayInfo merchantQuery(ReqParams params);


    /**
     * 商户开户通知
     * @param params
     * @return
     */
    MerchantPayInfo merchantRegNotify(NotifyParams params);


    /**
     * 对账单下载
     * @param params
     * @return
     */
    BillDownLoadResponse downloadBill(ReqParams params);


    /**
     *
     * C扫B支付(生成二维码)
     * @param request
     * @return
     */
    OrderPayRecord csbPay(OrderPayRecord request);


    /**
     *
     * B扫C支付(扫码设备支持)
     * @param request
     * @return
     */
    OrderPayRecord bscPay(OrderPayRecord request);



    /**
     *
     * H5支付(浏览器调用钱包APP支付)
     * @param request
     * @return
     */
    OrderPayRecord h5Pay(OrderPayRecord request);


    /**
     *
     * JS支付(微信公众号和支付宝生活号)
     * @param request
     * @return
     */
    OrderPayRecord jsPay(OrderPayRecord request);


    /**
     *
     * app支付(支持调用微信App支付)
     * @param request
     * @return
     */
    OrderPayRecord appPay(OrderPayRecord request);


    /**
     * 支付状态查询
     * @param query
     * @return
     */
     BaseResp payStatusQuery(OrderQuery query);

    /**
     * 退款请求
     * @param refundManage
     * @return
     */
     BaseResp refund(RefundManage refundManage);


    /**
     * 取消订单
     * @param orderCancel
     * @return
     */
    BaseResp orderCancel(OrderCancel orderCancel);
}
