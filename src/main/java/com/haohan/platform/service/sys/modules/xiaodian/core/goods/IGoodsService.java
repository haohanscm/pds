package com.haohan.platform.service.sys.modules.xiaodian.core.goods;

/**
 * Created by zgw on 2018/9/30.
 * 商品渠道服务
 */
public interface IGoodsService  {


   enum SHOP_DATA_SYNC{
        ON,OFF
    }

    boolean checkSync(String shopId);






}
