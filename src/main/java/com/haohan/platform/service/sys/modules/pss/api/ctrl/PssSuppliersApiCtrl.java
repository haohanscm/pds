package com.haohan.platform.service.sys.modules.pss.api.ctrl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.api.entity.req.*;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssSupplierService;
import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;
import com.haohan.platform.service.sys.modules.pss.entity.info.SupplierPayrecord;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 供应商管理controller
 * Created by zgw on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/pss/api/supplier")
public class PssSuppliersApiCtrl extends BaseController {
    @Resource
    private IPssSupplierService mercSupplierServiceImpl;

    //供应商列表展示
    @RequestMapping(value = "findPage")
    @ResponseBody
    public BaseResp findPage(@Validated PssSupplierQueryReq supplierReq, BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        Integer pageNo = supplierReq.getPageNo();
        Integer pageSize = supplierReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);
        baseResp = mercSupplierServiceImpl.findSupPage(supplierReq,reqPage);
        return baseResp;
    }

    @RequestMapping(value = "fetchList")
    @ResponseBody
    public BaseResp fetchList(@Validated PssBaseApiReq baseApiReq,BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        baseResp = mercSupplierServiceImpl.findList(baseApiReq.getMerchantId());
        return baseResp;
    }

    //新增供应商
    @RequestMapping(value = "save")
    @ResponseBody
    public BaseResp save(@Validated Supplier supplier,BindingResult bindingResult, HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        String merchantId = supplier.getMerchantId();

        baseResp = mercSupplierServiceImpl.saveSupplier(merchantId,supplier);
        return baseResp;
    }

    //删除供应商
    @RequestMapping(value = "delete")
    @ResponseBody
    public BaseResp delete(@Validated PssDeleteCommonApiReq commonApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }

        baseResp = mercSupplierServiceImpl.delSupplier(commonApiReq.getMerchantId(),commonApiReq.getId());
        return baseResp;
    }

    //供应商还款
    @RequestMapping(value = "repayment")
    @ResponseBody
    public BaseResp repayment(@Validated SupplierPayrecord supplierPayrecord,BindingResult bindingResult ,HttpServletRequest request) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        supplierPayrecord.setPayTime(new Date());
        baseResp= mercSupplierServiceImpl.repayment(supplierPayrecord);
        return baseResp;
    }

    //还款记录列表(分页)
    @RequestMapping(value = "repayment/history")
    @ResponseBody
    public BaseResp repaymentRecordList(@Validated PssSupplierPaymentApiReq paymentApiReq,BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult,baseResp)){
            return baseResp;
        }
        Integer pageNo = paymentApiReq.getPageNo();
        Integer pageSize = paymentApiReq.getPageSize();
        Page reqPage = new Page();
        reqPage.setPageNo(null == pageNo ? 1 : pageNo);
        reqPage.setPageSize(null == pageSize ? 30 : pageSize);

        baseResp = mercSupplierServiceImpl.findRepaymentPage(paymentApiReq,reqPage);
        return baseResp;
    }



}
