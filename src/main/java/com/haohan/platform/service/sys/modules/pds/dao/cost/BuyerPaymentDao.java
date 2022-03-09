package com.haohan.platform.service.sys.modules.pds.dao.cost;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin.PdsRangeAmountResultResp;
import com.haohan.platform.service.sys.modules.pds.entity.cost.BuyerPayment;

import java.util.List;

/**
 * 采购商货款统计DAO接口
 *
 * @author haohan
 * @version 2018-10-20
 */
@MyBatisDao
public interface BuyerPaymentDao extends CrudDao<BuyerPayment> {

    /**
     * 货款统计 按采购商及状态分组 buyerId/status
     *
     * @param payment
     * @return
     */
    List<BuyerPayment> countPayment(BuyerPayment payment);

    /**
     * 修改货款状态 批量
     *
     * @param payment
     * @return
     */
    int updateStatusBatch(BuyerPayment payment);

    List<BuyerPayment> findJoinList(BuyerPayment buyerPayment);

    /**
     * 按时间统计 金额
     *
     * @param payment
     * @return
     */
    List<PdsRangeAmountResultResp> rangeAmount(BuyerPayment payment);

}