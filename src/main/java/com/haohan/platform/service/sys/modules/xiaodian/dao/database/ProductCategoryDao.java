package com.haohan.platform.service.sys.modules.xiaodian.dao.database;

import com.haohan.platform.service.sys.common.persistence.TreeDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库分类管理DAO接口
 *
 * @author dy
 * @version 2018-10-17
 */
@MyBatisDao
public interface ProductCategoryDao extends TreeDao<ProductCategory> {

    /**
     * 结果 带父分类名称 parent.name
     *
     * @param productCategory
     * @return
     */
    List<ProductCategory> findJoinList(ProductCategory productCategory);

    /**
     * 查询ids中分类及其所有父级分类
     *
     * @param productCategory
     * @return
     */
    List<ProductCategory> findListByIdsWithParent(ProductCategory productCategory);

    /**
     * 删除分类及其子分类
     *
     * @param productCategory
     */
    void deleteWithChildren(ProductCategory productCategory);

    /**
     * 查找分类sn为空的
     *
     * @return
     */
    List<ProductCategory> findEmptySnList();

    /**
     * 带分类下商品数 goodsCount
     *
     * @param productCategory 只需 id
     * @return
     */
    ProductCategory getWith(ProductCategory productCategory);

    /**
     * 查询分类 在公共库和 店铺零售的对应关系 零售分类id存入tempId
     * 一级分类排在前
     * 按generalCategorySn 和 名称匹配
     * 按generalCategorySn 匹配的排在前
     * @param shopId
     * @param parentId        父分类的id  传"0" 只查询一级分类
     * @param excludeParentId 排除的父分类的id  传"0" 不查询一级分类
     * @return 零售店铺的分类id 存入 tempId
     */
    List<ProductCategory> findRelation(@Param("shopId") String shopId, @Param("parentId") String parentId, @Param("excludeParentId") String excludeParentId);
}