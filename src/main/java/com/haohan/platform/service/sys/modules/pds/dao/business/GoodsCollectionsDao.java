package com.haohan.platform.service.sys.modules.pds.dao.business;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.buyer.PdsTopNGoodsResp;
import com.haohan.platform.service.sys.modules.pds.entity.business.GoodsCollections;

import java.util.List;

/**
 * 商品收藏DAO接口
 *
 * @author yu
 * @version 2018-12-13
 */
@MyBatisDao
public interface GoodsCollectionsDao extends CrudDao<GoodsCollections> {

    List<PdsTopNGoodsResp> selectCollectPage(GoodsCollections goodsCollections);

    int countPageRows(GoodsCollections goodsCollections);
}