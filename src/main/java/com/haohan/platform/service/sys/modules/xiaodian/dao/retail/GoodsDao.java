package com.haohan.platform.service.sys.modules.xiaodian.dao.retail;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;

import java.util.List;

/**
 * 商品管理DAO接口
 * @author haohan
 * @version 2017-08-06
 */
@MyBatisDao
public interface GoodsDao extends CrudDao<Goods> {

    // 根据商品分类id 删除对应分类及子父类拥有的商品 未删除扩展信息
    int deleteForCategory(Goods goods);

    // 商品列表 带 shopName/merchantName/categoryName/categorySn/jisuappId/marketPrice/unit
    List<Goods> findJoinList(Goods goods);

    // 修改库存
    int modifyStorage(Goods entity);

    // 带categoryName/categorySn
    Goods getExt(Goods goods);

    // 根据merchantId 和 多个商品sn 批量删除商品
    int deleteByGoodsSn(Goods goods);

    // 用于查询goodsSn 目前的生成数字
    List<Goods> findGoodsSnList(Goods goods);

    // 查询商品基础数据 和规格信息
    List<GoodsInfoExt> findWithModelPds(Goods goods);

    int findGoodsCount(Goods goods);

    List<GoodsInfoExt> findListWithBase(Goods goods);

    // 自定义分页 查询商品列表 带规格属性
//    List findWithModelInfo(Goods goods);

    // 获取商品详情
//    GoodsInfoExt getGoodsInfoExt(Goods goods);
}