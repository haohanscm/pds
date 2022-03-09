package com.haohan.platform.service.sys.modules.pds.core.order;

/**
 * Created by zgw on 2018/10/20.
 */
public interface ITradeOderService {
    //生成交易单(所有状态初始化)
    void createTradeOrder();

    //供应商已备货
    void preparedGoods();

    //采购员揽货
    void takedGoods();

    //采购员已分拣
    void goodsSorted();

    //货物已装车
    void truckLoaded();

    //货物已送达
    void arrived();

    //货物已验收
    void buyerChecked();

}
