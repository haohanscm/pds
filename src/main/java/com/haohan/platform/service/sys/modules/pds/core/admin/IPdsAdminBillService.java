package com.haohan.platform.service.sys.modules.pds.core.admin;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminBuyerPaymentBatchCreateReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSettlementRecordEditReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.admin.PdsAdminSupplierPaymentCreateReq;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;

/**
 * 平台商家 账单管理:采购商货款/供应商货款/结算记录
 *
 * @author dy
 * @date 2019/2/13
 */
public interface IPdsAdminBillService {

    /**
     * 获取采购商货款列表
     *
     * @param buyerPayment
     * @return
     */
    BaseResp findBuyerPaymentList(BuyerPayment buyerPayment);

    /**
     * 生成采购商货款 批量
     *
     * @param createReq
     * @return
     */
    BaseResp createBuyerPaymentBatch(PdsAdminBuyerPaymentBatchCreateReq createReq);

    /**
     * 获取供应商货款列表
     *
     * @param supplierPayment
     * @return
     */
    BaseResp findSupplierPaymentList(SupplierPayment supplierPayment);

    /**
     * 生成采购商货款
     *
     * @param createReq
     * @return
     */
    BaseResp createSupplierPayment(PdsAdminSupplierPaymentCreateReq createReq);

    /**
     * 获取结算记录列表
     *
     * @param settlementRecord
     * @return
     */
    BaseResp findSettlementRecordList(SettlementRecord settlementRecord);

    /**
     * 生成结算记录
     *
     * @param editReq
     * @return
     */
    BaseResp settlementRecordEdit(PdsAdminSettlementRecordEditReq editReq);

    /**
     * 查询结算金额
     *
     * @param settlementRecord
     * @return
     */
    BaseResp querySettlementAmount(SettlementRecord settlementRecord);

    /**
     * 查询 结算公司(采购商/供应商 商家)
     *
     * @param settlementRecord
     * @return
     */
    BaseResp fetchCompanyList(SettlementRecord settlementRecord);
}
