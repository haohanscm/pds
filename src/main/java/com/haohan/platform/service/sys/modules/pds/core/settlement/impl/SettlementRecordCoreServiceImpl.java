package com.haohan.platform.service.sys.modules.pds.core.settlement.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.settlement.ISettlementRecordCoreService;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.cost.BuyerPaymentService;
import com.haohan.platform.service.sys.modules.pds.service.cost.SupplierPaymentService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 结算记录
 *
 * @author dy
 * @date 2019/2/15
 */
@Service
public class SettlementRecordCoreServiceImpl implements ISettlementRecordCoreService {
    @Autowired
    @Lazy(true)
    private PdsBuyerService pdsBuyerService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService pdsSupplierService;
    @Autowired
    @Lazy(true)
    private BuyerPaymentService buyerPaymentService;
    @Autowired
    @Lazy(true)
    private SupplierPaymentService supplierPaymentService;

    @Override
    public BaseResp countPayment(SettlementRecord settlementRecord) {
        BaseResp baseResp = BaseResp.newError();
        String type = settlementRecord.getCompanyType();
        String merchantId = settlementRecord.getCompanyId();
        String pmId = settlementRecord.getPmId();
        Date begin = settlementRecord.getSettlementBeginDate();
        Date end = settlementRecord.getSettlementEndDate();
        if (null == IPdsConstant.CompanyType.getTypeByCode(type)
                || StringUtils.isAnyEmpty(merchantId, pmId)
                || null == begin || null == end) {
            baseResp.setMsg("参数有误");
            return baseResp;
        }
        // 结算金额
        BigDecimal settlementAmount = BigDecimal.ZERO;
        // 货款单号列表
        List<String> paymentSnList = new ArrayList<>();
        // 查询采购商
        if (StringUtils.equals(type, IPdsConstant.CompanyType.buyer.getCode())) {
            // 未结状态的货款
            BuyerPayment buyerPayment = new BuyerPayment();
            buyerPayment.setPmId(pmId);
            buyerPayment.setStatus(ICommonConstant.YesNoType.no.getCode());
            buyerPayment.setMerchantId(merchantId);
            buyerPayment.setBeginBuyDate(begin);
            buyerPayment.setEndBuyDate(end);
            List<BuyerPayment> buyerPaymentList = buyerPaymentService.findJoinList(buyerPayment);
            // 结算款 为 采购货款 + 售后货款
            for (BuyerPayment b : buyerPaymentList) {
                settlementAmount = settlementAmount.add(b.getBuyerPayment()).add(b.getAfterSalePayment());
                paymentSnList.add(b.getBuyerPaymentId());
            }
        } else if (StringUtils.equals(type, IPdsConstant.CompanyType.supplier.getCode())) {
            SupplierPayment supplierPayment = new SupplierPayment();
            supplierPayment.setPmId(pmId);
            supplierPayment.setStatus(ICommonConstant.YesNoType.no.getCode());
            supplierPayment.setMerchantId(merchantId);
            supplierPayment.setBeginSupplyDate(begin);
            supplierPayment.setEndSupplyDate(end);
            List<SupplierPayment> supplierPaymentList = supplierPaymentService.findJoinList(supplierPayment);
            // 结算款 为 供应货款 + 售后货款
            for (SupplierPayment s : supplierPaymentList) {
                settlementAmount = settlementAmount.add(s.getSupplierPayment()).add(s.getAfterSalePayment());
                paymentSnList.add(s.getSupplierPaymentId());
            }
        }
        // 货款单号列表不为空
        if (!Collections3.isEmpty(paymentSnList)) {
            baseResp.success();
            settlementRecord.setSettlementAmount(settlementAmount);
            settlementRecord.setPaymentSn(StringUtils.join(paymentSnList, ","));
            baseResp.setExt(settlementRecord);
        } else {
            baseResp.setMsg("无对应货款单信息");
        }
        return baseResp;
    }

    @Override
    public BaseResp paymentSettlement(SettlementRecord settlementRecord, String msg) {
        BaseResp baseResp = BaseResp.newError();
        String paymentSn = settlementRecord.getPaymentSn();
        String companyType = settlementRecord.getCompanyType();
        String pmId = settlementRecord.getPmId();
        if (StringUtils.isAnyEmpty(pmId, companyType, paymentSn)) {
            baseResp.setMsg("参数有误");
            return baseResp;
        }
        String[] paymentSnList = StringUtils.split(paymentSn, ",");
        int num = 0;
        if (null != paymentSnList && paymentSnList.length > 0) {
            // 修改货款单状态 根据货款单号 改状态
            if (StringUtils.equals(companyType, IPdsConstant.CompanyType.buyer.getCode())) {
                BuyerPayment buyerPayment = new BuyerPayment();
                buyerPayment.setStatus(ICommonConstant.YesNoType.no.getCode());
                buyerPayment.setFinalStatus(ICommonConstant.YesNoType.yes.getCode());
                buyerPayment.setPmId(pmId);
                buyerPayment.setBuyerPaymentId(paymentSn);
                // 状态改为已结算
                num = buyerPaymentService.updateStatusBatch(buyerPayment);
            } else if (StringUtils.equals(companyType, IPdsConstant.CompanyType.supplier.getCode())) {
                SupplierPayment supplierPayment = new SupplierPayment();
                supplierPayment.setStatus(ICommonConstant.YesNoType.no.getCode());
                supplierPayment.setFinalStatus(ICommonConstant.YesNoType.yes.getCode());
                supplierPayment.setPmId(pmId);
                supplierPayment.setSupplierPaymentId(paymentSn);
                // 状态改为已结算
                num = supplierPaymentService.updateStatusBatch(supplierPayment);
            }
        }
        if (num > 0) {
            msg += ";修改" + num + "个货款单状态";
        } else {
            msg += ";没有货款单可修改状态";
        }
        baseResp.success();
        baseResp.setExt(msg);
        return baseResp;
    }

    @Override
    public BaseResp fetchCompanyList(SettlementRecord settlementRecord) {
        BaseResp baseResp = BaseResp.newError();
        String type = settlementRecord.getCompanyType();
        String pmId = settlementRecord.getPmId();
        if (null == IPdsConstant.CompanyType.getTypeByCode(type) || StringUtils.isEmpty(pmId)) {
            return baseResp;
        }
        List list;
        // 查询 采购商/供应商 返回 merchantId / merchantName
        if (StringUtils.equals(type, IPdsConstant.CompanyType.buyer.getCode())) {
            list = pdsBuyerService.findMerchantList(pmId);
        } else if (StringUtils.equals(type, IPdsConstant.CompanyType.supplier.getCode())) {
            list = pdsSupplierService.findMerchantList(pmId);
        } else {
            list = null;
        }
        if (!Collections3.isEmpty(list)) {
            baseResp.success();
            baseResp.setExt(new ArrayList<>(list));
        } else {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        }
        return baseResp;
    }


}
