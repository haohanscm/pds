package com.haohan.platform.service.sys.modules.xiaodian.dao.database;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductAttrName;

import java.util.List;

/**
 * 商品库属性名管理DAO接口
 * @author dy
 * @version 2018-10-22
 */
@MyBatisDao
public interface ProductAttrNameDao extends CrudDao<ProductAttrName> {

    // 根据规格名ids 查询  参数存入id 必需
    List<ProductAttrName> findByNameIds(ProductAttrName productAttrName);
}