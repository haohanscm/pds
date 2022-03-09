package com.haohan.platform.service.sys.modules.pds.core.supplier;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.supplier.SupplierGoodsRelationReq;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoodsExt;

/**
 * 供应商  零售商品
 * Created by dy on 2018/10/26.
 */
public interface ISupplierGoodsCoreService {

    /**
     * 供应商零售商品列表(spu) TODO 功能修改
     */
    BaseResp queryGoodsList(SupplierGoodsExt supplierGoodsExt, Page page);

    /**
     * 供应商商品关联修改  TODO 功能修改
     *
     * @param supplierGoods
     * @return
     */
    BaseResp modifyGoods(SupplierGoods supplierGoods);

    /**
     * 供应商 商品分类列表查询 带关联状态  TODO 功能修改
     *
     * @param goods
     * @return
     */
    BaseResp queryCategoryStatusList(SupplierGoods goods);

    /**
     * 查询平台商品的供应商供应价
     *
     * @param goods
     * @return
     */
    BaseResp querySupplyPriceList(SupplierGoods goods);

    /**
     * 批量生成供应商和平台商家 商品的映射关系
     *
     * @param relationReq
     * @return
     */
    BaseResp relationGoods(SupplierGoodsRelationReq relationReq);

}
