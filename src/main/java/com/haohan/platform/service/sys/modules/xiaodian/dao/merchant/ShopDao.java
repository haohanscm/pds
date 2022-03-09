package com.haohan.platform.service.sys.modules.xiaodian.dao.merchant;

import com.haohan.platform.service.sys.common.persistence.CrudDao;
import com.haohan.platform.service.sys.common.persistence.annotation.MyBatisDao;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.TShopDto;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;

import java.util.List;

/**
 * 店铺管理DAO接口
 * @author haohan
 * @version 2017-12-15
 */
@MyBatisDao
public interface ShopDao extends CrudDao<Shop> {

    List<Shop> fetchJisuApp(Shop shop);

    // 店铺信息 部分修改
    int modifyShopInfo(Shop shop);

    List<Shop> findJoinList(Shop shop);

    List<Shop> fetchByName(Shop shop);

    List<Shop> findListWithPmId(Shop shop);

    List<TShopDto> selectSimpleShop(Shop shop);
}