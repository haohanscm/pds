package com.haohan.platform.service.sys.modules.xiaodian.dao.delivery;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.GoodsGifts;

/**
 * 赠品管理DAO接口
 * @author haohan
 * @version 2018-08-31
 */
@MyBatisDao
public interface GoodsGiftsDao extends CrudDao<GoodsGifts> {

    void deleteByGoodsId(GoodsGifts goodsGifts);
}