package com.haohan.platform.service.sys.modules.xiaodian.dao.database;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductAttrValue;

import java.util.List;

/**
 * 商品库属性值管理DAO接口
 * @author dy
 * @version 2018-10-22
 */
@MyBatisDao
public interface ProductAttrValueDao extends CrudDao<ProductAttrValue> {

    // 带属性名attrName
    List<ProductAttrValue> findJoinList(ProductAttrValue productAttrValue);

    // 根据规格值ids 查询 结果带属性名 参数存入id 必需
    List<ProductAttrValue> findByValueIds(ProductAttrValue productAttrValue);

    // 获取属性名:属性值 的对应关系
    List<String> fetchNameValue(ProductAttrValue productAttrValue);
}