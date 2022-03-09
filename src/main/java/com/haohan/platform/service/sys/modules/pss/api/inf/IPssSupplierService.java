package com.haohan.platform.service.sys.modules.pss.api.inf;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssPageApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssSupplierPaymentApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssSupplierQueryReq;
import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;
import com.haohan.platform.service.sys.modules.pss.entity.info.SupplierPayrecord;
import org.springframework.stereotype.Service;

/**
 * @author shenyu
 * @create 2018/11/27
 */
@Service
public interface IPssSupplierService {

    //供应商列表(分页)
    BaseResp findSupPage(PssSupplierQueryReq pssPageApiReq, Page page);

    //供应商列表(不分页)
    BaseResp findList(String merchantId);

    //保存供应商(新增,修改)
    BaseResp saveSupplier(String merchantId, Supplier supplier);

    //删除供应商
    BaseResp delSupplier(String merchantId,String id);

    //供应商还款
    BaseResp repayment(SupplierPayrecord supplierPayrecord);

    //还款记录列表(分页)
    BaseResp findRepaymentPage(PssSupplierPaymentApiReq paymentApiReq,Page page);


}
