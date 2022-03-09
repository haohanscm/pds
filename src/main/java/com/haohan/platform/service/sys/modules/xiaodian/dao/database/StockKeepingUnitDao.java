package com.haohan.platform.service.sys.modules.xiaodian.dao.database;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.database.RespSku;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;

import java.util.List;

/**
 * 库存商品管理DAO接口
 * @author dy
 * @version 2018-10-20
 */
@MyBatisDao
public interface StockKeepingUnitDao extends CrudDao<StockKeepingUnit> {

    /**
     * sku 详情 带spu信息
     * @param stockGoodsId
     * @return
     */
    RespSku getSkuInfo(String stockGoodsId);

    /**
     *  sku列表 带attrInfo
     * @param stockKeepingUnit
     * @return
     */
    List<StockKeepingUnit> findListWithAttrInfo(StockKeepingUnit stockKeepingUnit);

    List<StockKeepingUnit> findEmptySnSpuList();
}