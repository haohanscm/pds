package com.haohan.platform.service.sys.modules.pds.dao.business;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.supplier.SupplyPriceResp;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoodsExt;

import java.util.List;

/**
 * 供应商货物管理DAO接口
 *
 * @author haohan
 * @version 2018-11-14
 */
@MyBatisDao
public interface SupplierGoodsDao extends CrudDao<SupplierGoods> {

    List<SupplierGoodsExt> findExtList(SupplierGoodsExt supplierGoodsExt);

    List<SupplierGoods> findCategoryList(SupplierGoods supplierGoods);

    List<SupplierGoods> findJoinList(SupplierGoods supplierGoods);

    List<SupplyPriceResp> querySupplyPriceList(SupplierGoods goods);

    List<SupplierGoods> relationGoodsList(SupplierGoods goods);
}