package com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.goods;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.RespPage;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.common.MerchantCommonService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsCategoryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家 商品管理
 * Created by dy on 2018/9/27.
 */
@Service
@Transactional(readOnly = true)
public class MerchantGoodsService implements IGoodsConstant {

    @Autowired
    @Lazy(true)
    private GoodsService goodsService;
    @Autowired
    @Lazy(true)
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;
    @Autowired
    @Lazy(true)
    private MerchantCommonService merchantCommonService;

    /**
     * 零售商品基础信息的列表  分页
     * 带商品规格
     * @param goods
     * @param pageNo
     * @param pageSize
     * @return
     */
    public RespPage fetchGoodsList(Goods goods, int pageNo, int pageSize) {
        RespPage respPage = new RespPage<>();
        Page page = goodsService.findPageWithModel(new Page<>(pageNo, pageSize), goods);
        if (Collections3.isEmpty(page.getList())) {
            return respPage;
        }
        // 获取属性
        respPage.fetchFromPage(page);
        return respPage;
    }


    /**
     * 零售商品基础信息修改  包括 价格/规格
     *
     * @param goods
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp goodsBaseModify(GoodsInfoExt goods) {
        BaseResp baseResp = BaseResp.newError();
        String shopId = goods.getShopId();
        // 根据shopId 判断是否即速应用店铺
        Shop shop = shopService.get(shopId);
        if (null == shop) {
            baseResp.setMsg("找不到对应店铺");
            return baseResp;
        } else if (StringUtils.isEmpty(shop.getCode()) || !StringUtils.equals(shop.getIsUpdateJisu(), ICommonConstant.YesNoType.yes.getCode())) {
            goods.setGoodsFrom(IGoodsConstant.GoodsFromType.platform.getCode());
        } else {
            goods.setGoodsFrom(IGoodsConstant.GoodsFromType.jisuapp.getCode());
        }
        goods.setMerchantId(shop.getMerchantId());

        // 新增或修改
        Goods oldGoods = null;
        if (!StringUtils.isEmpty(goods.getGoodsSn())) {
            // 从数据库查询的 属性   主键id 分类id 扩展标记
            oldGoods = goodsService.findByGoodsSn(goods.getGoodsSn());
            // 验证  不能跨店铺修改
            if (null != oldGoods && !StringUtils.equals(oldGoods.getShopId(), shopId)) {
                oldGoods = null;
                goods.setGoodsSn(null);
            }
        }
        // 分类id sn
        String oldCategoryId;
        String categorySn;
        if (null != oldGoods) {
            goods.setId(oldGoods.getId());
            goods.setThirdGoodsSn(oldGoods.getThirdGoodsSn());
            // 扩展项标记
//            goods.setServiceSelection(oldGoods.getServiceSelection());
//            goods.setDeliveryRule(oldGoods.getDeliveryRule());
//            goods.setSaleRule(oldGoods.getSaleRule());
//            goods.setGoodsGift(oldGoods.getGoodsGift());
            // 分类id
            oldCategoryId = oldGoods.getGoodsCategoryId();
            categorySn = oldGoods.getCategorySn();
        } else {
            // 扩展项标记 默认
            goods.setServiceSelection(ICommonConstant.YesNoType.no.getCode());
            goods.setDeliveryRule(ICommonConstant.YesNoType.no.getCode());
            goods.setSaleRule(ICommonConstant.YesNoType.no.getCode());
            goods.setGoodsGift(ICommonConstant.YesNoType.no.getCode());
            // 分类id
            oldCategoryId = "";
            categorySn = "";
        }
        // 保存  包括 价格/规格
        goodsService.saveGoods(goods, "base");
        // 即速新增/修改商品 新增时thirdGoodsSn为即速商品编号
        if (StringUtils.isNotEmpty(shop.getCode())) {
            // 启用同步时
            if (StringUtils.equals(shop.getIsUpdateJisu(), ICommonConstant.YesNoType.yes.getCode())) {
                // 分类处理
                // 判断是否修改  设置即速分类sn
                if (!StringUtils.equals(oldCategoryId, goods.getGoodsCategoryId())) {
                    if (StringUtils.isNotEmpty(goods.getGoodsCategoryId())) {
                        categorySn = goodsCategoryService.getCategorySns(goods.getGoodsCategoryId());
                    } else {
                        categorySn = "";
                    }
                }
                goods.setCategorySn(categorySn);
                // 更新规格类型名称信息
                goods.transToModelInfo();
//                GoodsAsyncUtils.getInstance().saveGoodsJisu(shop.getCode(), goods);
            }
        }
        HashMap<String, String> result = new HashMap<>(8);
        result.put("goodsSn", goods.getGoodsSn());
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * 零售商品扩展信息修改  包括 服务选项/赠品/售卖规则/配送规则  包括标记字段
     *
     * @param goods
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp goodsExtModify(GoodsInfoExt goods) {
        BaseResp baseResp = BaseResp.newError();
        String goodsSn = goods.getGoodsSn();
        // 修改扩展属性   修改前goods必需存在
        Goods oldGoods = null;
        if (!StringUtils.isEmpty(goodsSn)) {
            // 从数据库查询的 属性   主键id 分类id 扩展标记
            oldGoods = goodsService.findByGoodsSn(goodsSn);
        }
        if (null == oldGoods) {
            baseResp.setMsg("goodsSn错误");
            return baseResp;
        }
        // goods 修改
        goods.setId(oldGoods.getId());
        goods.setStorage(oldGoods.getStorage());
        goods.setIsMarketable(oldGoods.getIsMarketable());
        // 保存 扩展项 包括标记
        goodsService.saveGoods(goods, "ext");
        HashMap<String, String> result = new HashMap<>();
        result.put("goodsSn", goods.getGoodsSn());
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * 根据merchantId 和 多个商品sn 批量删除商品
     *
     * @param goods merchantId/多个商品sn 逗号连接
     * @return
     */
    @Transactional(readOnly = false)
    public int goodsDelete(Goods goods) {
        if (StringUtils.isEmpty(goods.getGoodsSn())) {
            return 0;
        }
        // 批量删除
        return goodsService.deleteByGoodsSn(goods);
    }

    /**
     * 根据 商品编号 获取 商品详情 包括扩展信息
     *
     * @param goodsSn
     * @return
     */
    public GoodsInfoExt getGoodsInfoExt(String goodsSn) {
        GoodsInfoExt goods = goodsService.getGoodsInfoBySn(goodsSn);
        if (null != goods && null != goods.getDetailDesc()) {
            goods.setDetailDesc(StringEscapeUtils.unescapeHtml4(goods.getDetailDesc()));
        }
        return goods;
    }

    /**
     * 商品权重  排序修改
     *
     * @param goods goodsSn  sort
     * @return
     */
    @Transactional(readOnly = false)
    public boolean goodsSortModify(Goods goods) {
        Goods oldGoods = null;
        if (!StringUtils.isEmpty(goods.getGoodsSn())) {
            // 从数据库查询的 属性   主键id
            oldGoods = goodsService.findByGoodsSn(goods.getGoodsSn());
        }
        if (null == oldGoods) {
            return false;
        }
        // 未改变时
        if (StringUtils.equals(goods.getSort(), oldGoods.getSort())) {
            return true;
        }
        // 是否即速应用商品
        if (!StringUtils.equals(oldGoods.getUpdateJisu(), ICommonConstant.YesNoType.no.getCode())) {
            if (StringUtils.isNoneEmpty(oldGoods.getJisuappId(), oldGoods.getThirdGoodsSn(), goods.getSort())) {
                // 即速修改
//                GoodsAsyncUtils.getInstance().modifyGoodsWeightJisu(oldGoods.getJisuappId(), oldGoods.getThirdGoodsSn(), goods.getSort());
            }
        }
        // 绑定id 修改sort
        goods.setId(oldGoods.getId());
        goodsService.save(goods);
        return true;
    }

    /**
     * 商品上下架
     *
     * @param goods
     * @return
     */
    @Transactional(readOnly = false)
    public boolean goodsStatusModify(Goods goods) {
        Goods oldGoods = null;
        if (!StringUtils.isEmpty(goods.getGoodsSn())) {
            // 从数据库查询的 属性   主键id
            oldGoods = goodsService.findByGoodsSn(goods.getGoodsSn());
        }
        if (null == oldGoods) {
            return false;
        }
        // 未改变时
        if ((goods.getIsMarketable().equals(oldGoods.getIsMarketable()))) {
            return true;
        }

        // 绑定id 修改上下架
        goods.setId(oldGoods.getId());
        goodsService.save(goods);
        return true;
    }

    /**
     * 商品库存  修改
     *
     * @param goods goodsSn  storage
     * @return
     */
    @Transactional(readOnly = false)
    public boolean goodsStorageModify(Goods goods) {
        Goods oldGoods = null;
        if (!StringUtils.isEmpty(goods.getGoodsSn())) {
            // 从数据库查询的 属性   主键id
            oldGoods = goodsService.findByGoodsSn(goods.getGoodsSn());
        }
        if (null == oldGoods) {
            return false;
        }
        // 未改变时
        if ((goods.getStorage().equals(oldGoods.getStorage()))) {
            return true;
        }
        // 库存修改 影响商品状态
        String status = oldGoods.getGoodsStatus();
        if (StringUtils.equals(ICommonConstant.YesNoType.yes.getCode(), StringUtils.toString(goods.getIsMarketable(), "1"))) {
            if (Integer.valueOf(0).equals(goods.getStorage())) {
                status = GoodsStatus.takeout.getCode();
            } else if (Integer.valueOf(0).equals(oldGoods.getStorage())) {
                status = GoodsStatus.sale.getCode();
            }
        }
        goods.setGoodsStatus(status);
        // 绑定id 修改sort
        goods.setId(oldGoods.getId());
        goodsService.save(goods);
        return true;
    }

    /**
     * 获取商品分类列表
     *
     * @param goodsCategory
     * @return
     */
    public ArrayList<GoodsCategory> fetchCategoryList(GoodsCategory goodsCategory) {
        List<GoodsCategory> list = goodsCategoryService.findList(goodsCategory);
        return new ArrayList<>(list);
    }

    /**
     * 分类删除  根据多个categorySn  逗号连接
     *
     * @param goodsCategory
     * @return
     */
    @Transactional(readOnly = false)
    public int categoryDelete(GoodsCategory goodsCategory) {
        if (StringUtils.isEmpty(goodsCategory.getId())) {
            return 0;
        }

        return goodsCategoryService.deleteByIds(goodsCategory);
    }


    /**
     * 零售商品分类修改/新增
     *
     * @param category
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp goodsCategoryModify(GoodsCategory category) {
        BaseResp baseResp = BaseResp.newError();
        // 根据shopId 查询店铺  是否即速应用店铺
        Shop shop = shopService.get(category.getShopId());
        if(null == shop){
            baseResp.setMsg("shopId错误");
            return baseResp;
        }
        GoodsCategory oldCategory = null;
        // 新增或修改
        if (StringUtils.isNotEmpty(category.getId())) {
            // 根据id查询分类
            oldCategory = goodsCategoryService.get(category.getId());
            // 验证  不能跨店铺修改
            if (null != oldCategory && !StringUtils.equals(oldCategory.getShopId(), category.getShopId())) {
                oldCategory = null;
                category.setCategorySn(null);
            }
        }
        if (null != oldCategory) {
            // 分类修改
            category.setCategorySn(oldCategory.getCategorySn());
            // 父级未修改
            if (StringUtils.equals(oldCategory.getParentId(), category.getParentId())) {
                category.setParentSn(oldCategory.getParentSn());
            } else {
                GoodsCategory parent;
                if (StringUtils.isEmpty(category.getParentId())) {
                    parent = null;
                } else {
                    parent = goodsCategoryService.get(category.getParentId());
                }
                if (null != parent) {
                    category.setParent(parent);
                    category.setParentSn(parent.getCategorySn());
                } else {
                    category.setParentSn("0");
                }
            }

        } else {
            // 分类新增  id parentId  categorySn parentSn
            category.setCategorySn(null);
            // 找到parent
            GoodsCategory parentCategory = null;
            // 是否一级分类
            if (StringUtils.isNotEmpty(category.getParentId()) && !StringUtils.equals("0", category.getParentId())) {
                //  根据sn查询分类
                parentCategory = goodsCategoryService.get(category.getParentId());
                category.setParentSn(parentCategory.getCategorySn());
            } else {
                category.setParentSn("0");
            }
            category.setParent(parentCategory);
        }

        // 保存并更新子节点
        category.setMerchantId(shop.getMerchantId());
        goodsCategoryService.save(category);
        HashMap<String, String> result = new HashMap<>(8);
        result.put("categoryId", category.getId());
        baseResp.success();
        baseResp.setExt(result);
        return baseResp;
    }

    /**
     * 保存上传的图片
     *
     * @param files      图片文件
     * @param merchantId 商家id
     * @param type       文件类型
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp savePhoto(MultipartFile files, String merchantId, String type) {
        return merchantCommonService.savePhoto(files, merchantId, type);
    }

}
