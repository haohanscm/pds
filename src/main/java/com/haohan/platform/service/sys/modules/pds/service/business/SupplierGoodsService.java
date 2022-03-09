package com.haohan.platform.service.sys.modules.pds.service.business;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.supplier.SupplyPriceResp;
import com.haohan.platform.service.sys.modules.pds.dao.business.SupplierGoodsDao;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoodsExt;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商货物管理Service
 *
 * @author haohan
 * @version 2018-11-14
 */
@Service
@Transactional(readOnly = true)
public class SupplierGoodsService extends CrudService<SupplierGoodsDao, SupplierGoods> {

    public SupplierGoods get(String id) {
        return super.get(id);
    }

    public List<SupplierGoods> findList(SupplierGoods supplierGoods) {
        return super.findList(supplierGoods);
    }

    public SupplierGoods fetchByModelId(String pmId,String supplierId,String goodsModelId){
        SupplierGoods supplierGoods = new SupplierGoods();
        supplierGoods.setPmId(pmId);
        supplierGoods.setSupplierId(supplierId);
        supplierGoods.setGoodsModelId(goodsModelId);
        List<SupplierGoods> list = super.findList(supplierGoods);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 用于列表展示  关联查询名称
     * @param supplierGoods
     * @return
     */
    public List<SupplierGoods> findJoinList(SupplierGoods supplierGoods) {
        return dao.findJoinList(supplierGoods);
    }

    public Page<SupplierGoods> findPage(Page<SupplierGoods> page, SupplierGoods supplierGoods) {
        return super.findPage(page, supplierGoods);
    }

    @Transactional(readOnly = false)
    public void save(SupplierGoods supplierGoods) {
        super.save(supplierGoods);
    }

    @Transactional(readOnly = false)
    public void delete(SupplierGoods supplierGoods) {
        super.delete(supplierGoods);
    }

    /**
     * 供应商关联商品信息
     * @param supplierGoodsExt
     * @return
     */
    public List<SupplierGoodsExt> findExtList(SupplierGoodsExt supplierGoodsExt) {
        return dao.findExtList(supplierGoodsExt);
    }

    /**
     * 供应商关联商品分类信息
     * @param supplierGoods
     * @return
     */
    public List<SupplierGoods> findCategoryList(SupplierGoods supplierGoods){
        return dao.findCategoryList(supplierGoods);
    }

    /**
     * 查询平台商品的供应商供应价
     *
     * @param goods pmId/goodsModelId
     * @return 供应商商家为平台商时 无对应的采购商采购价时 返回商品的市场价
     */
    public List<SupplyPriceResp> querySupplyPriceList(SupplierGoods goods) {
        // 供应商商品启用状态;供应商商家为平台商时该商品上架状态/采购商商品上下架状态;供应商状态为启用  4个状态都是yes_no共用传入
        goods.setStatus(ICommonConstant.YesNoType.yes.getCode());
        return dao.querySupplyPriceList(goods);
    }

    /**
     * 批量生成供应商和平台商家 商品的映射关系
     * @param goods
     * @return
     */
    public List<SupplierGoods> relationGoodsList(SupplierGoods goods){
        return dao.relationGoodsList(goods);
    }
}