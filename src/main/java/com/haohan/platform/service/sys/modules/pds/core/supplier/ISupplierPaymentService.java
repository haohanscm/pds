package com.haohan.platform.service.sys.modules.pds.core.supplier;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;

/**
 * Created by zgw on 2018/10/20.
 */
public interface ISupplierPaymentService {
    // 查看账款列表
    BaseResp queryPaymentList(SupplierPayment supplierPayment, Page page);

    // 查看账款统计
    BaseResp totalPayment(SupplierPayment supplierPayment);

    // 生成供应商货款记录
    BaseResp paymentRecord(SupplierPayment supplierPayment);

}
