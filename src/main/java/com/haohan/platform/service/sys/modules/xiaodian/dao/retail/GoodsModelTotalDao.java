package com.haohan.platform.service.sys.modules.xiaodian.dao.retail;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModelTotal;

/**
 * 零售商品规格名称管理DAO接口
 * @author haohan
 * @version 2018-09-27
 */
@MyBatisDao
public interface GoodsModelTotalDao extends CrudDao<GoodsModelTotal> {

    void deleteByGoodsId(GoodsModelTotal goodsModelTotal);
}