package com.haohan.platform.service.sys.modules.xiaodian.service.retail;

import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.TreeService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.dao.retail.GoodsCategoryDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsPriceRule;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类Service
 * @author haohan
 * @version 2017-08-06
 */
@Service
@Transactional(readOnly = true)
public class GoodsCategoryService extends TreeService<GoodsCategoryDao, GoodsCategory> {
    @Autowired
    @Lazy(true)
    private GoodsService goodsService;
    @Autowired
    private ShopService shopService;
    @Autowired
    @Lazy(true)
    private GoodsPriceRuleService goodsPriceRuleService;

	public GoodsCategory get(String id) {
		return super.get(id);
	}

	// 通过分类id的列表(","连接)找到对应的分类  结果带jisuappId/updateJisu
	public List<GoodsCategory> getForIds(String ids){
	    return dao.getForIds(ids);
    }

    // 通过分类id的列表(","连接)找到对应的分类及子分类
	public List<GoodsCategory> fetchWithChildrenForIds(String ids, String shopId){
	    GoodsCategory goodsCategory = new GoodsCategory(ids);
	    goodsCategory.setShopId(shopId);
	    return dao.fetchWithChildrenForIds(goodsCategory);
    }

	public List<GoodsCategory> findList(GoodsCategory goodsCategory) {
		return super.findList(goodsCategory);
	}

	// 带 shopName/merchantName
	public Page<GoodsCategory> findPage(Page<GoodsCategory> page, GoodsCategory goodsCategory) {
        goodsCategory.setPage(page);
        page.setList(dao.findJoinList(goodsCategory));
		return page;
	}

    /**
     * 根据不同的shopId分页查询  返回的是shopId 的分页结果
     * @param page
     * @param goodsCategory
     * @return
     */
    public Page<GoodsCategory> fetchShopIdPage(Page<GoodsCategory> page, GoodsCategory goodsCategory) {
        goodsCategory.setPage(page);
        page.setList(dao.fetchShopId(goodsCategory));
        return page;
    }

    /**
     * 分类列表   带 shopName/merchantName
     * @param goodsCategory
     * @return
     */
    public List<GoodsCategory> findJoinList(GoodsCategory goodsCategory){
	    return dao.findJoinList(goodsCategory);
    }
	
	@Transactional(readOnly = false)
	public void save(GoodsCategory goodsCategory) {
		super.save(goodsCategory);
	}

    /**
     * 保存分类  不更新子分类节点
     * @param goodsCategory
     */
    @Transactional(readOnly = false)
    public void saveCategory(GoodsCategory goodsCategory) {
        if (goodsCategory.getIsNewRecord()){
            goodsCategory.preInsert();
            dao.insert(goodsCategory);
        }else{
            goodsCategory.preUpdate();
            dao.update(goodsCategory);
        }
    }

    /**
     * 删除 分类下的子分类  及 对应商品 基础信息和价格
     * @param goodsCategory
     */
	@Transactional(readOnly = false)
	public void deleteByCategory(GoodsCategory goodsCategory) {
//	    // 当前节点分类id
//	    String categoryId = goodsCategory.getId();
//	    GoodsCategory parent = new GoodsCategory();
//	    parent.setParentIds(categoryId);
//	    List<GoodsCategory> goodsCategoryList = super.findList(parent);
//        // 删除节点及子节点对应的商品
//	    for(GoodsCategory c:goodsCategoryList){
//            goodsService.deleteForCategory(c.getId());
//        }
//        goodsService.deleteForCategory(categoryId);
		// 删除当前节点及子节点 (sql中删除)
		dao.deleteChildren(goodsCategory);
        dao.delete(goodsCategory);
	}

    @Transactional(readOnly = false)
	public void delete(GoodsCategory goodsCategory){
        super.delete(goodsCategory);
    }

    /**
     * 删除子分类, 不能删除当前分类
     * @param goodsCategory
     */
    @Transactional(readOnly = false)
    public void deleteChildren(GoodsCategory goodsCategory){
        dao.deleteChildren(goodsCategory);
    }

	// 分类id、名称键值对
	public Map<String,String> fetchNameMap(GoodsCategory goodsCategory){
        return Collections3.extractToMap(dao.findJoinList(goodsCategory),"id","name");
    }

    // 已有分类中的店铺id/name 键值对
    public Map<String,String> fetchShopMap(){
        // 不在店铺列表的 shopId(模板) 键值都为 shopId
        Map<String, String> shopMap = Collections3.extractToMap(super.fetchRootList(new GoodsCategory()), "shopId", "shopId");
        // 已有店铺的shopId/shopName 键值对
        Map<String, String> map = Collections3.extractToMap(shopService.findList(new Shop()), "id", "name");
        for(String key :shopMap.keySet()){
            if(map.containsKey(key)){
                shopMap.put(key,map.get(key));
            }
        }
        return shopMap;
    }


//    // 按模板添加店铺菜品及分类
//    @Transactional(readOnly = false)
//    public void shopModel(GoodsCategory entity){
//        // 模板shopId 存放在remarks中
//        String modelShopId = entity.getRemarks();
//        String merchantId = entity.getMerchantId();
//        String shopId = entity.getShopId();
//        String name = entity.getName();
//        entity = new GoodsCategory();
//        entity.setShopId(modelShopId);
////        logger.debug("model shop id:"+ modelShopId);
//        // 根据modelShopId 查找模板的所有Category记录
//        List<GoodsCategory> modelCategoryList = dao.findList(entity);
//        // 替换新旧id  <旧，新>
//        HashMap<String,String> categoryMap = new HashMap<>();
//        String oldParentId;
//        String newParentId;
//        String oldId;
//
//        // 修改为新的category记录
//        for(GoodsCategory r:modelCategoryList){
//            r.setShopId(shopId);
//            oldId = r.getId();
//            // 替换为新对象 新id
//            r.preInsert();
//            // 添加需替换的 id对
//            categoryMap.put(oldId,r.getId());
//            // 修改 品类分类名称
//            if(StringUtils.equals("1",r.getCategoryType())){
//                r.setName(name);
//            }
//        }
//        // 修改parentIds
//        for (GoodsCategory r : modelCategoryList){
//            oldParentId = r.getParentId();
//            // 若parentId 为旧值则修改
//            if(categoryMap.containsKey(oldParentId)){
//                newParentId = categoryMap.get(oldParentId);
//                // 修改parentId
//                r.getParent().setId(newParentId);
//            }
//            // 修改 parentIds
//            String[] parentIds = r.getParentIds().split(",");
//            for (int i=0;i<parentIds.length;i++) {
//                if(categoryMap.containsKey(parentIds[i])){
//                    // 修改parent_Ids
//                    parentIds[i] = categoryMap.get(parentIds[i]);
//                }
//            }
//            // 逗号结尾
//            r.setParentIds(StringUtils.join(parentIds,",")+",");
//        }
//        // 保存category实体
//        for (GoodsCategory r : modelCategoryList){
//            dao.insert(r);
//        }
//        // 根据modelShopId 查找所有Goods实体
//        Goods goods = new Goods();
//        goods.setShopId(modelShopId);
//        List<Goods> goodsList = goodsService.findList(goods);
//        // 替换新旧id  <旧，新>
//        HashMap<String,String> goodsMap = new HashMap<>();
//        String oldCategory;
//        for (Goods d:goodsList){
//            d.setShopId(shopId);
//            d.setMerchantId(merchantId);
//            oldCategory = d.getGoodsCategoryId();
//            // 商品分类的多选处理
//            String[] categoryArray = oldCategory.split(",");
//            for (int i = 0; i < categoryArray.length; i++) {
//                if (categoryMap.containsKey(categoryArray[i])) {
//                    // 修改parent_Ids
//                    categoryArray[i] = categoryMap.get(categoryArray[i]);
//                }
//            }
//            d.setGoodsCategoryId(StringUtils.join(categoryArray,","));
//            oldId = d.getId();
//            // 保存Goods实体
//            d.setIsNewRecord(true);
//            d.setId(IdGen.uuid());
//            goodsMap.put(oldId,d.getId());
//            goodsService.save(d);
//        }
//        // 根据modelShopId 查找所有GoodsPriceRule实体
//        GoodsPriceRule goodsPriceRule = new GoodsPriceRule();
//        goodsPriceRule.setShopId(modelShopId);
//        List<GoodsPriceRule> infoList = goodsPriceRuleService.findList(goodsPriceRule);
//        String oldObjId;
//        for(GoodsPriceRule i:infoList){
//            i.setShopId(shopId);
//            i.setMerchantId(merchantId);
//            // 替换绑定对象的Id
//            oldObjId = i.getGoodsId();
//            if(StringUtils.isNotEmpty(oldObjId)){
//                if(goodsMap.containsKey(oldObjId)){
//                    i.setGoodsId(goodsMap.get(oldObjId));
//                }
//            }
//            // 保存GoodsPriceRule实体
//            i.setIsNewRecord(true);
//            i.setId(IdGen.uuid());
//            goodsPriceRuleService.save(i);
//        }
//        // TODO  查找并替换 goods 扩展信息
//
//    }


    /**
     * 根据分类编号 查询商品分类列表
     * @param categorySns  多个商品分类编号  逗号连接
     * @return
     */
    public List<GoodsCategory> findByCategorySns(String categorySns) {
        if(StringUtils.isEmpty(categorySns)){
            return new ArrayList<>();
        }
	    GoodsCategory goodsCategory = new GoodsCategory();
	    goodsCategory.setCategorySn(categorySns);
        return dao.findList(goodsCategory);
    }

    /**
     * 获取分类id的列表  逗号连接
     * @param categorySn 多个sn列表  逗号连接
     * @return
     */
    public String getCategoryIds(String categorySn) {
        GoodsCategory category = new GoodsCategory();
        category.setCategorySn(categorySn);
        category = dao.getCategoryIds(category);
        if(null == category || StringUtils.isEmpty(category.getId())){
            return null;
        }
        return category.getId();
    }

    /**
     * 获取分类sn的列表  逗号连接
     *
     * @param ids 多个id列表  逗号连接
     * @return
     */
    public String getCategorySns(String ids) {
        String result = "";
        if (StringUtils.isEmpty(ids)) {
            return result;
        }
        List<GoodsCategory> list = dao.getForIds(ids);
        if (Collections3.isEmpty(list)) {
            return result;
        }
        return Collections3.extractToString(list, "categorySn", ",");
    }



    public GoodsCategory getCategoryBySn(String categorySn) {
        GoodsCategory category = new GoodsCategory();
        category.setCategorySn(categorySn);
        List<GoodsCategory> categoryList = findList(category);
        return CollectionUtils.isNotEmpty(categoryList)?categoryList.get(0):null;

    }

    // 根据 多个分类id 、商家 删除分类
    public int deleteByIds(GoodsCategory goodsCategory) {
        return dao.deleteByIds(goodsCategory);
    }
}