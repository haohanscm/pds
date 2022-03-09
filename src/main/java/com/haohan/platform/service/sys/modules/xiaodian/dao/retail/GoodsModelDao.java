package com.haohan.platform.service.sys.modules.xiaodian.dao.retail;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;

import java.util.List;

/**
 * 商品规格管理DAO接口
 *
 * @author haohan
 * @version 2018-09-11
 */
@MyBatisDao
public interface GoodsModelDao extends CrudDao<GoodsModel> {

    /**
     * 删除goodsId 的所有规格
     *
     * @param goodsModel
     */
    void deleteByGoodsId(GoodsModel goodsModel);

    /**
     * 联查goods表  结果带goodsName shopId goodsCategoryId
     *
     * @param goodsModel
     * @return
     */
    List<GoodsModel> findJoinList(GoodsModel goodsModel);

    /**
     * 联查goods表  结果带goodsName shopId goodsCategoryId
     *
     * @param id
     * @return
     */
    GoodsModel fetch(String id);

    /**
     * 联查goods表  结果带goodsName shopId goodsCategoryId
     *
     * @param goodsModel
     * @return
     */
    GoodsModel fetchWithShop(GoodsModel goodsModel);

    List<GoodsModel> findByGoodsIdWithDel(String goodsId);
}