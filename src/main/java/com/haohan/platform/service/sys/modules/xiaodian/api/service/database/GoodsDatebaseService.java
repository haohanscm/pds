package com.haohan.platform.service.sys.modules.xiaodian.api.service.database;

import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.database.RespSku;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.database.RespSpu;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StandardProductUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.CommonGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.ProductCategoryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.StandardProductUnitService;
import com.haohan.platform.service.sys.modules.xiaodian.service.database.StockKeepingUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公共商品库 商品管理
 * Created by dy on 2018/10/17.
 */
@Service
@Transactional(readOnly = true)
public class GoodsDatebaseService {

    @Autowired
    @Lazy(true)
    private StandardProductUnitService standardProductUnitService;
    @Autowired
    @Lazy(true)
    private StockKeepingUnitService stockKeepingUnitService;
    @Autowired
    @Lazy(true)
    private CommonGoodsService commonGoodsService;
    @Autowired
    @Lazy(true)
    private ProductCategoryService productCategoryService;


    /**
     * 标准商品 spu 列表
     * @param standardProductUnit
     * @param pageNo
     * @param pageSize
     * @return
     */
    public ApiRespPage<StandardProductUnit> fetchStandardProductList(StandardProductUnit standardProductUnit, int pageNo, int pageSize) {
        Page<StandardProductUnit> page = new Page<>(pageNo, pageSize);
        standardProductUnit.setPage(page);
        page.setList(standardProductUnitService.findJoinList(standardProductUnit));
        ApiRespPage<StandardProductUnit> respPage = new ApiRespPage<>();
        if (Collections3.isEmpty(page.getList())) {
            return respPage;
        }
        // 获取属性
        respPage.fetchFromPage(page);
        return respPage;
    }

    /**
     * 标准商品 spu 详情  带skuList
     * @param goodsId
     * @return
     */
    public RespSpu getStandardGoods(String goodsId) {
        RespSpu respSpu = new RespSpu();
        StandardProductUnit spu = standardProductUnitService.get(goodsId);
        if(null == spu){
            return null;
        }
        BeanUtils.copyProperties(spu, respSpu);
        List<StockKeepingUnit> skuList = stockKeepingUnitService.findList(spu.getId());
        respSpu.setSkuList(skuList);
        return respSpu;
    }

    /**
     * 库存商品 sku 详情 带spu信息
     * @param stockGoodsId
     * @return
     */
    public RespSku getStockGoods(String stockGoodsId) {
        RespSku respSku = stockKeepingUnitService.getSkuInfo(stockGoodsId);
        return respSku;
    }

    /**
     * 库存商品列表  sku列表
     * @param stockKeepingUnit
     * @param pageNo
     * @param pageSize
     * @return
     */
    public ApiRespPage<StockKeepingUnit> fetchStockGoodsList(StockKeepingUnit stockKeepingUnit, int pageNo, int pageSize) {
        Page<StockKeepingUnit> page = new Page<>(pageNo, pageSize);
        stockKeepingUnit.setPage(page);
        page.setList(stockKeepingUnitService.findList(stockKeepingUnit));
        ApiRespPage<StockKeepingUnit> respPage = new ApiRespPage<>();
        if (Collections3.isEmpty(page.getList())) {
            return respPage;
        }
        // 获取属性
        respPage.fetchFromPage(page);
        return respPage;
    }

    /**
     * 根据公共商品库 spu复制生成零售商品
     * @param goodsId
     * @return
     */
    public GoodsInfoExt copyToRetailGoods(String goodsId) {
        StandardProductUnit spu = standardProductUnitService.get(goodsId);
        return commonGoodsService.transToRetailGoods(spu);
    }

    /**
     * 商品分类列表
     * @param category
     * @return
     */
    public ApiRespPage<ProductCategory> fetchCategoryList(ProductCategory category) {
        ApiRespPage<ProductCategory> page = new ApiRespPage<>();
        List<ProductCategory> list = productCategoryService.findList(category);
        if (Collections3.isEmpty(list)) {
            return page;
        }
        page.setList(list);
        page.setCount(list.size());
        return page;
    }


}
