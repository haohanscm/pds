package com.haohan.platform.service.sys.modules.pds.dao.cost;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.entity.cost.GoodsLoss;

import java.util.List;

/**
 * 商品损耗管理DAO接口
 *
 * @author haohan
 * @version 2018-12-03
 */
@MyBatisDao
public interface GoodsLossDao extends CrudDao<GoodsLoss> {

    List<GoodsLoss> findJoinList(GoodsLoss goodsLoss);
}