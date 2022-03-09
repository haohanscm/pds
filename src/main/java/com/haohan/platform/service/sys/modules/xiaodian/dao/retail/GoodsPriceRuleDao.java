package com.haohan.platform.service.sys.modules.xiaodian.dao.retail;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsPriceRule;

import java.util.List;

/**
 * 定价规则管理DAO接口
 * @author haohan
 * @version 2017-12-07
 */
@MyBatisDao
public interface GoodsPriceRuleDao extends CrudDao<GoodsPriceRule> {

    // 删除 同一商品id的所有 GoodsPriceRule
    int deleteGoodsId(GoodsPriceRule goodsPriceRule);
    // 根据商品分类id 删除对应分类及子父类拥有的商品的GoodsPriceRule;分类id用goodsId传入
    int deleteForCategory(GoodsPriceRule goodsPriceRule);

    List<GoodsPriceRule> findJoinList(GoodsPriceRule goodsPriceRule);

}