package com.haohan.platform.service.sys.modules.pds.api.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.*;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminBillService;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账单管理
 *
 * @author dy
 * @date 2019/2/13
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/admin/bill")
public class PdsAdminBillCtrl extends BaseController {

    @Autowired
    private IPdsAdminBillService pdsAdminBillService;

    /**
     * 获取采购商货款列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "buyerPayment/findList")
    @ResponseBody
    public BaseResp buyerPaymentList(@Validated PdsAdminBuyerPaymentListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        BuyerPayment buyerPayment = new BuyerPayment();
        buyerPayment.setPmId(listReq.getPmId());
        buyerPayment.setMerchantId(listReq.getMerchantId());
        buyerPayment.setMerchantName(listReq.getMerchantName());
        buyerPayment.setBuyerPaymentId(listReq.getBuyerPaymentId());
        buyerPayment.setBuyId(listReq.getBuyId());
        buyerPayment.setBuyerId(listReq.getBuyerId());
        buyerPayment.setServiceId(listReq.getServiceId());
        buyerPayment.setBeginBuyDate(listReq.getBeginBuyDate());
        buyerPayment.setEndBuyDate(listReq.getEndBuyDate());
        buyerPayment.setStatus(listReq.getStatus());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        buyerPayment.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminBillService.findBuyerPaymentList(buyerPayment);
        return baseResp;
    }

    /**
     * 批量计算生成采购商货款
     *
     * @param createReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "buyerPayment/batchCreate")
    @ResponseBody
    public BaseResp buyerPaymentBatchCreate(@Validated PdsAdminBuyerPaymentBatchCreateReq createReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminBillService.createBuyerPaymentBatch(createReq);
        return baseResp;
    }

    /**
     * 获取供应商货款列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "supplierPayment/findList")
    @ResponseBody
    public BaseResp supplierPaymentList(@Validated PdsAdminSupplierPaymentListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        SupplierPayment supplierPayment = new SupplierPayment();
        supplierPayment.setPmId(listReq.getPmId());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        supplierPayment.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminBillService.findSupplierPaymentList(supplierPayment);
        return baseResp;
    }

    /**
     * 计算生成 供应商货款
     *
     * @param createReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "supplierPayment/create")
    @ResponseBody
    public BaseResp supplierPaymentCreate(@Validated PdsAdminSupplierPaymentCreateReq createReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminBillService.createSupplierPayment(createReq);
        return baseResp;
    }

    /**
     * 获取 结算记录列表
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "settlementRecord/findList")
    @ResponseBody
    public BaseResp settlementRecordList(@Validated PdsAdminSettlementRecordListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        SettlementRecord settlementRecord = new SettlementRecord();
        settlementRecord.setPmId(listReq.getPmId());
        // 分页
        int pageNo = listReq.getPageNo();
        int pageSize = listReq.getPageSize();
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;
        settlementRecord.setPage(new Page<>(pageNo, pageSize));
        baseResp = pdsAdminBillService.findSettlementRecordList(settlementRecord);
        return baseResp;
    }

    /**
     * 新增/修改  结算记录
     *
     * @param editReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "settlementRecord/edit")
    @ResponseBody
    public BaseResp settlementRecordEdit(@Validated PdsAdminSettlementRecordEditReq editReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = pdsAdminBillService.settlementRecordEdit(editReq);
        return baseResp;
    }

    /**
     * 查询 结算金额
     *
     * @param amountReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "settlementRecord/queryAmount")
    @ResponseBody
    public BaseResp settlementQueryAmount(@Validated PdsAdminSettlementRecordAmountReq amountReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        SettlementRecord settlementRecord = new SettlementRecord();
        settlementRecord.setPmId(amountReq.getPmId());
        settlementRecord.setCompanyType(amountReq.getCompanyType());
        settlementRecord.setCompanyId(amountReq.getCompanyId());
        settlementRecord.setSettlementBeginDate(amountReq.getBeginDate());
        settlementRecord.setSettlementEndDate(amountReq.getEndDate());
        baseResp = pdsAdminBillService.querySettlementAmount(settlementRecord);
        return baseResp;
    }

    /**
     * 查询 结算公司(采购商/供应商 商家)
     *
     * @param listReq
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "settlementRecord/fetchCompanyList")
    @ResponseBody
    public BaseResp fetchCompanyList(@Validated PdsAdminSettlementRecordCompanyListReq listReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        SettlementRecord settlementRecord = new SettlementRecord();
        settlementRecord.setPmId(listReq.getPmId());
        settlementRecord.setCompanyType(listReq.getCompanyType());
        baseResp = pdsAdminBillService.fetchCompanyList(settlementRecord);
        return baseResp;
    }
}
