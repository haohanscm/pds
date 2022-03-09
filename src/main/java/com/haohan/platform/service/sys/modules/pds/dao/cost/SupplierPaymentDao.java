package com.haohan.platform.service.sys.modules.pds.dao.cost;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;

import java.util.List;

/**
 * 供应商货款统计DAO接口
 *
 * @author haohan
 * @version 2018-11-06
 */
@MyBatisDao
public interface SupplierPaymentDao extends CrudDao<SupplierPayment> {

    // 修改货款状态 批量
    int updateStatusBatch(SupplierPayment payment);

    List<SupplierPayment> findJoinList(SupplierPayment supplierPayment);
}