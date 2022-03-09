package com.haohan.platform.service.sys.modules.pds.core.settlement;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SettlementRecord;

/**
 * 结算记录
 *
 * @author dy
 * @date 2019/2/15
 */
public interface ISettlementRecordCoreService {

    /**
     * 根据结算公司类型 计算 采购商/供应商 结算金额
     *
     * @param settlementRecord
     * @return settlementAmount paymentSn
     */
    BaseResp countPayment(SettlementRecord settlementRecord);

    /**
     * 根据货款单号 修改货款单状态为已结算
     *
     * @param settlementRecord
     * @param msg
     * @return
     */
    BaseResp paymentSettlement(SettlementRecord settlementRecord, String msg);

    /**
     * 获取结算公司列表
     *
     * @param settlementRecord pmId companyType
     * @return merchantId merchantName
     */
    BaseResp fetchCompanyList(SettlementRecord settlementRecord);


}
