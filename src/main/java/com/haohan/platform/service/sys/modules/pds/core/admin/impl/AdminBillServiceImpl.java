package com.haohan.platform.service.sys.modules.pds.core.admin.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminBuyerPaymentBatchCreateReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSettlementRecordEditReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSupplierPaymentCreateReq;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.admin.IPdsAdminBillService;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.core.settlement.ISettlementRecordCoreService;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierPaymentService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.cost.BuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.service.cost.SettlementRecordService;
import com.haohan.platform.service.sys.modules.pds.service.cost.SupplierPaymentService;
import com.haohan.platform.service.sys.modules.pds.service.order.BuyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author dy
 * @date 2019/02/13
 */
@Service
public class AdminBillServiceImpl implements IPdsAdminBillService {

    @Autowired
    @Lazy(true)
    private BuyerPaymentService buyerPaymentService;
    @Autowired
    @Lazy(true)
    private IBuyerPaymentService buyerPaymentCoreService;
    @Autowired
    @Lazy(true)
    private SupplierPaymentService supplierPaymentService;
    @Autowired
    @Lazy(true)
    private ISupplierPaymentService supplierPaymentCoreService;
    @Autowired
    @Lazy(true)
    private SettlementRecordService settlementRecordService;
    @Autowired
    @Lazy(true)
    private ISettlementRecordCoreService settlementRecordCoreService;
    @Autowired
    @Lazy(true)
    private BuyOrderService buyOrderService;
    @Autowired
    @Lazy(true)
    private PdsBuyerService buyerService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService supplierService;

    @Override
    public BaseResp findBuyerPaymentList(BuyerPayment buyerPayment) {
        BaseResp baseResp = BaseResp.newError();
        List<BuyerPayment> list = buyerPaymentService.findJoinList(buyerPayment);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = buyerPayment.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp createBuyerPaymentBatch(PdsAdminBuyerPaymentBatchCreateReq createReq) {
        BaseResp baseResp = BaseResp.newError();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setPmId(createReq.getPmId());
        buyOrder.setBuySeq(createReq.getBuySeq());
        buyOrder.setDeliveryTime(createReq.getDeliveryDate());
        List<BuyOrder> list = buyOrderService.findList(buyOrder);
        if (Collections3.isEmpty(list)) {
            baseResp.setMsg("当前时间批次无采购单");
        } else {
            baseResp = buyerPaymentCoreService.paymentRecordBatch(list, successMsg, errorMsg);
            HashMap<String, String> result = new HashMap<>(8);
            result.put("successMsg", successMsg.toString());
            result.put("errorMsg", errorMsg.toString());
            baseResp.setExt(result);
        }
        return baseResp;
    }

    @Override
    public BaseResp findSupplierPaymentList(SupplierPayment supplierPayment) {
        BaseResp baseResp = BaseResp.newError();
        List<SupplierPayment> list = supplierPaymentService.findJoinList(supplierPayment);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = supplierPayment.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp createSupplierPayment(PdsAdminSupplierPaymentCreateReq createReq) {
        SupplierPayment supplierPayment = new SupplierPayment();
        supplierPayment.setPmId(createReq.getPmId());
        supplierPayment.setSupplyDate(createReq.getSupplyDate());
        supplierPayment.setSupplierId(createReq.getSupplierId());
        BaseResp baseResp = supplierPaymentCoreService.paymentRecord(supplierPayment);
        return baseResp;
    }

    @Override
    public BaseResp findSettlementRecordList(SettlementRecord settlementRecord) {
        BaseResp baseResp = BaseResp.newError();
        List<SettlementRecord> list = settlementRecordService.findJoinList(settlementRecord);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        Page page = settlementRecord.getPage();
        page.setList(list);
        ApiRespPage respPage = new ApiRespPage();
        respPage.fetchFromPage(page);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp settlementRecordEdit(PdsAdminSettlementRecordEditReq editReq) {
        BaseResp baseResp = BaseResp.newError();
        SettlementRecord settlementRecord = null;
        // 修改
        if (StringUtils.isNotEmpty(editReq.getId())) {
            settlementRecord = settlementRecordService.get(editReq.getId());
            if (null == settlementRecord) {
                baseResp.setMsg("结算记录id有误");
                return baseResp;
            }
        }
        String type = editReq.getCompanyType();
        String companyName = null;
        String companyId = editReq.getCompanyId();
        String pmId = editReq.getPmId();
        // 验证采购商/供应商
        if (StringUtils.equals(type, IPdsConstant.CompanyType.buyer.getCode())) {
            PdsBuyer buyer = new PdsBuyer();
            buyer.setPmId(pmId);
            buyer.setMerchantId(companyId);
            List<PdsBuyer> list = buyerService.findList(buyer);
            if (!Collections3.isEmpty(list)) {
                companyName = list.get(0).getMerchantName();
            }
        } else if (StringUtils.equals(type, IPdsConstant.CompanyType.supplier.getCode())) {
            PdsSupplier supplier = new PdsSupplier();
            supplier.setPmId(pmId);
            supplier.setMerchantId(companyId);
            List<PdsSupplier> list = supplierService.findList(supplier);
            if (!Collections3.isEmpty(list)) {
                companyName = list.get(0).getMerchantName();
            }
        }
        if (null == companyName) {
            baseResp.setMsg("companyId有误");
            return baseResp;
        }
        // 新增
        if (null == settlementRecord) {
            editReq.setId(null);
            settlementRecord = new SettlementRecord();
        }
        editReq.transToSettlement(settlementRecord);
        // 覆盖公司名称
        settlementRecord.setCompanyName(companyName);
        settlementRecordService.save(settlementRecord);
        String msg = "保存结算记录成功";
        // 根据货款单号 修改货款单状态为已结算
        settlementRecordCoreService.paymentSettlement(settlementRecord, msg);
        baseResp.success();
        baseResp.setExt(msg);
        return baseResp;
    }

    @Override
    public BaseResp querySettlementAmount(SettlementRecord settlementRecord) {
        return settlementRecordCoreService.countPayment(settlementRecord);
    }

    @Override
    public BaseResp fetchCompanyList(SettlementRecord settlementRecord) {
        return settlementRecordCoreService.fetchCompanyList(settlementRecord);
    }


}
