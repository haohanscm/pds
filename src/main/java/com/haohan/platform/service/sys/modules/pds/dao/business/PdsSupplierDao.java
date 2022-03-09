package com.haohan.platform.service.sys.modules.pds.dao.business;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;

import java.util.List;

/**
 * 供应商管理DAO接口
 *
 * @author haohan
 * @version 2018-10-26
 */
@MyBatisDao
public interface PdsSupplierDao extends CrudDao<PdsSupplier> {

    List<PdsSupplier> findJoinList(PdsSupplier pdsSupplier);

    List<PdsSupplier> findMerchantList(PdsSupplier supplier);

    List<PdsSupplier> findListWithSupplyPrice(SupplierGoods supplierGoods);
}