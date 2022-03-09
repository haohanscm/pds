package com.haohan.platform.service.sys.modules.xiaodian.dao.retail;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;

import java.util.List;

/**
 * 商品分类DAO接口
 * @author haohan
 * @version 2017-08-06
 */
@MyBatisDao
public interface GoodsCategoryDao extends TreeDao<GoodsCategory> {

    // 分类列表   带 shopName/merchantName
    List<GoodsCategory> findJoinList(GoodsCategory goodsCategory);

    // 通过分类id的列表(","连接)找到对应的分类
    List<GoodsCategory> getForIds(String ids);

    // 删除子分类, 不能删除当前分类
    int deleteChildren(GoodsCategory goodsCategory);

    // 根据不同的shopId分页查询  返回的是shopId 的分页结果
    List<GoodsCategory> fetchShopId(GoodsCategory goodsCategory);

    // 获取分类id列表 根据sn列表 逗号连接
    GoodsCategory getCategoryIds(GoodsCategory goodsCategory);

    // 根据 多个分类id 、商家 删除分类
    int deleteByIds(GoodsCategory goodsCategory);

    List<GoodsCategory> fetchWithChildrenForIds(GoodsCategory goodsCategory);
}