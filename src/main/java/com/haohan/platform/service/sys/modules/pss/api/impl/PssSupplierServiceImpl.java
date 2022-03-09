package com.haohan.platform.service.sys.modules.pss.api.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseList;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssPageApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssSupplierPaymentApiReq;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.PssSupplierQueryReq;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssSupplierService;
import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;
import com.haohan.platform.service.sys.modules.pss.entity.info.SupplierPayrecord;
import com.haohan.platform.service.sys.modules.pss.service.info.SupplierPayrecordService;
import com.haohan.platform.service.sys.modules.pss.service.info.SupplierService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenyu
 * @create 2018/11/27
 */
@Service
public class PssSupplierServiceImpl implements IPssSupplierService {
    @Resource
    private SupplierService supplierService;
    @Resource
    private SupplierPayrecordService supplierPayrecordService;

    @Override
    public BaseResp findSupPage(PssSupplierQueryReq pageApiReq, Page page) {
        BaseList<Supplier> baseList = new BaseList<>();

        Supplier supplier = new Supplier();
        pageApiReq.transToSupplier(supplier);
        supplierService.findPage(page,supplier);

        if (CollectionUtils.isEmpty(page.getList())){
            baseList.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseList;
        }

        baseList.setTotalPage(page.getTotalPage());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.success();
        return baseList;
    }

    @Override
    public BaseResp findList(String merchantId) {
        BaseResp baseResp = BaseResp.newError();

        Supplier supplier = new Supplier();
        supplier.setMerchantId(merchantId);
        List<Supplier> supList = supplierService.findList(supplier);

        if (CollectionUtils.isEmpty(supList)){
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }

        baseResp.setExt(supList.stream().collect(Collectors.toCollection(ArrayList::new)));
        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp saveSupplier(String merchantId, Supplier supplier) {
        BaseResp baseResp = BaseResp.newError();

        supplier.setMerchantId(merchantId);
        supplierService.save(supplier);

        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp delSupplier(String merchantId, String id) {
        BaseResp baseResp = BaseResp.newError();

        Supplier supplier = supplierService.get(id);
        if (null == supplier){
            baseResp.setMsg("未找到供应商信息");
            return baseResp;
        }
        supplierService.delete(supplier);

        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp repayment(SupplierPayrecord supplierPayrecord) {
        BaseResp baseResp = BaseResp.newError();

//        supplierPayrecord.setMerchantId(merchantId);
        supplierPayrecord.setPayTime(new Date());
        supplierPayrecordService.save(supplierPayrecord);

        baseResp.success();
        return baseResp;
    }

    @Override
    public BaseResp findRepaymentPage(PssSupplierPaymentApiReq paymentApiReq,Page page) {
        BaseList<SupplierPayrecord> baseList = new BaseList<>();
        SupplierPayrecord supplierPayrecord = new SupplierPayrecord();
        supplierPayrecord.setMerchantId(paymentApiReq.getMerchantId());
        supplierPayrecord.setSupplierId(paymentApiReq.getSupplierId());

        supplierPayrecordService.findPage(page,supplierPayrecord);
        if (CollectionUtils.isEmpty(page.getList())){
            baseList.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseList;
        }

        baseList.setTotalPage(page.getTotalPage());
        baseList.setCurPage(page.getPageNo());
        baseList.setTotalRows(new Long(page.getCount()).intValue());
        baseList.setPageSize(page.getPageSize());
        baseList.setList(page.getList());
        baseList.success();
        return baseList;
    }
}
