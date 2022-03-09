package com.haohan.platform.service.sys.modules.thirdplatform.mshpay.service.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderPayRecord;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderQuery;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.RefundManage;

/**
 * @author shenyu
 * @create 2018/7/20
 */
public interface IMshPayService {

    public OrderPayRecord csbPay(OrderPayRecord orderPayRecord);

    public OrderPayRecord bscPay(OrderPayRecord orderPayRecord);

    public OrderPayRecord jsPay(OrderPayRecord orderPayRecord);

    public BaseResp orderQuery(OrderQuery orderQuery);

    public BaseResp orderRefund(RefundManage refundManage);
}
