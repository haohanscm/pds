package com.haohan.platform.service.sys.modules.pds.core.supplier.impl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.supplier.SupplierGoodsRelationReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.resp.supplier.SupplyPriceResp;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierGoodsCoreService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoodsExt;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.business.SupplierGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsCategoryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SupplierGoodsCoreServiceImpl implements ISupplierGoodsCoreService {

    @Autowired
    private SupplierGoodsService supplierGoodsService;
    @Autowired
    @Lazy(true)
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    @Lazy(true)
    private GoodsService goodsService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService supplierService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;

    @Override
    public BaseResp queryGoodsList(SupplierGoodsExt supplierGoodsExt, Page page) {
        BaseResp baseResp = BaseResp.newError();
        supplierGoodsExt.setPage(page);
        List<SupplierGoodsExt> list = supplierGoodsService.findExtList(supplierGoodsExt);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        ApiRespPage respPage = new ApiRespPage(page);
        respPage.setList(list);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    @Override
    public BaseResp modifyGoods(SupplierGoods supplierGoods) {
        BaseResp baseResp = BaseResp.newError();
        // ???????????????????????????
        if (StringUtils.isNotEmpty(supplierGoods.getCategoryId())) {
            // ??????
            Set<String> goodsIdSet = new HashSet<>();
            // ??????????????????
            Goods goods;
            List<GoodsCategory> list = goodsCategoryService.fetchWithChildrenForIds(supplierGoods.getCategoryId(), supplierGoods.getShopId());
            for (GoodsCategory c : list) {
                // ????????????
                if (!StringUtils.equals("0", c.getParentId())) {
                    goods = new Goods();
                    goods.setShopId(supplierGoods.getShopId());
                    goods.setGoodsCategoryId(c.getId());
                    List<Goods> goodsList = goodsService.findList(goods);
                    for (Goods g : goodsList) {
                        goodsIdSet.add(g.getId());
                    }
                }
            }
            // ??????????????????
            for (String s : goodsIdSet) {
                supplierGoods.setGoodsId(s);
                modifyGoodsSingle(supplierGoods);
            }
        } else if (StringUtils.isNotEmpty(supplierGoods.getGoodsId())) {
            modifyGoodsSingle(supplierGoods);
        } else {
            baseResp.setMsg("????????????");
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(supplierGoods);
        return baseResp;
    }

    @Override
    public BaseResp queryCategoryStatusList(SupplierGoods goods) {
        BaseResp baseResp = BaseResp.newError();
        List<SupplierGoods> list = supplierGoodsService.findCategoryList(goods);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        ApiRespPage respPage = new ApiRespPage();
        int count = list.size();
        respPage.setPageNo(1);
        respPage.setPageSize(count);
        respPage.setCount(count);
        respPage.setTotalPage(1);
        respPage.setList(list);
        baseResp.success();
        baseResp.setExt(respPage);
        return baseResp;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param goods
     * @return
     */
    @Override
    public BaseResp querySupplyPriceList(SupplierGoods goods) {
        BaseResp baseResp = BaseResp.newError();
        List<SupplyPriceResp> list = supplierGoodsService.querySupplyPriceList(goods);
        if (Collections3.isEmpty(list)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(new ArrayList<>(list));
        return baseResp;
    }

    /**
     * ???????????????????????????????????? ?????????????????????
     * ????????????????????????
     * ??????: 1.????????????/????????????/???????????????;
     * 2. ????????????/?????????????????????????????????/???????????????.
     * ???????????????????????????
     *
     * @param relationReq pmId/supplierId ??????
     * @return
     */
    @Override
    public BaseResp relationGoods(SupplierGoodsRelationReq relationReq) {
        BaseResp baseResp = BaseResp.newError();
        String supplierId = relationReq.getSupplierId();
        PdsSupplier supplier = supplierService.get(supplierId);
        if (null == supplier) {
            baseResp.setMsg("???????????????");
            return baseResp;
        }
        String pmId = relationReq.getPmId();
        String smId = supplier.getMerchantId();
        String pmShopId = "";
        String smShopId = "";
        // ??????shopId  ?????????????????????????????????
        Shop shop = new Shop();
        shop.setMerchantId(pmId);
        shop.setShopLevel(IShopConstant.ShopLevelType.pds.getCode());
        List<Shop> shopList = shopService.fetchByMerchantIdEnable(shop);
        if (!Collections3.isEmpty(shopList)) {
            pmShopId = shopList.get(0).getId();
        }
        shop.setMerchantId(smId);
        shopList = shopService.fetchByMerchantIdEnable(shop);
        if (!Collections3.isEmpty(shopList)) {
            smShopId = shopList.get(0).getId();
        }
        if (StringUtils.isAnyEmpty(pmShopId, smShopId)) {
            baseResp.setMsg("???????????????????????????");
            return baseResp;
        }
        relationReq.setSupplierMerchantId(smId);
        relationReq.setPmShopId(pmShopId);
        relationReq.setSupplierShopId(smShopId);
        SupplierGoods supplierGoods = new SupplierGoods();
        supplierGoods.setPmId(pmId);
        supplierGoods.setSupplierId(supplierId);
        supplierGoods.setStatus(ICommonConstant.YesNoType.yes.getCode());
        supplierGoods.setSupplierMerchantId(smId);
        supplierGoods.setShopId(pmShopId);
        supplierGoods.setSupplierShopId(smShopId);
        List<SupplierGoods> list = supplierGoodsService.relationGoodsList(supplierGoods);
        int num = 0;
        String status = ICommonConstant.YesNoType.yes.getCode();
        for (SupplierGoods g : list) {
            g.setStatus(status);
            supplierGoodsService.save(g);
            num++;
        }
        if (num > 0) {
            baseResp.success();
            baseResp.setMsg("????????????:" + num + "?????????????????????");
        } else {
            baseResp.setMsg("???????????????????????????????????????");
        }
        return baseResp;
    }

    /**
     * ?????????????????? ????????????
     *
     * @param supplierGoods
     * @return
     */
    private int modifyGoodsSingle(SupplierGoods supplierGoods) {
        // ?????????
        SupplierGoods goods = new SupplierGoods();
        goods.setSupplierId(supplierGoods.getSupplierId());
        goods.setGoodsId(supplierGoods.getGoodsId());
        List<SupplierGoods> list = supplierGoodsService.findList(goods);
        boolean flag = false;
        int i = 0;
        if (Collections3.isEmpty(list)) {
            flag = true;
        } else {
            goods = list.get(0);
            supplierGoods.setId(goods.getId());
            // ??????????????? ??????
            if (!StringUtils.equals(goods.getStatus(), supplierGoods.getStatus())) {
                flag = true;
            }
        }
        // ???????????????
        goods.setStatus(supplierGoods.getStatus());
        if (flag) {
            supplierGoodsService.save(goods);
            i = 1;
        }
        return i;
    }
}
