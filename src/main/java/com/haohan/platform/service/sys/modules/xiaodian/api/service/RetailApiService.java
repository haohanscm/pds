package com.haohan.platform.service.sys.modules.xiaodian.api.service;

import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsPriceRule;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsCategoryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsPriceRuleService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2018/6/22.
 */
@Service
public class RetailApiService {

    @Autowired
    @Lazy(true)
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    @Lazy(true)
    private GoodsService goodsService;
    @Autowired
    @Lazy(true)
    private GoodsPriceRuleService goodsPriceRuleService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;


    /**
     * 获取零售店铺商品品类列表     目前 获取的 带商品的子类
     * @param  shopId 必需参数
//     * @param  categoryType 必需参数 categoryType :0 商品品类 （对应商品列表）; 1 品类分类
     * @return
     */
    public ArrayList<Map<String,String>> fetchGoodsCategoryList(String shopId){
            GoodsCategory goodsCategory = new GoodsCategory();
            goodsCategory.setShopId(shopId);
            // 需要查询商品数量
            goodsCategory.setGoodsCount("-1");
            List<GoodsCategory> categoryList = goodsCategoryService.findJoinList(goodsCategory);

            ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
            if(categoryList.size()>0){
                Map<String,String> obj;
                for (GoodsCategory c :categoryList){
                    // 无商品的分类不返回
                    if(StringUtils.toInteger(c.getGoodsCount())<1){
                        continue;
                    }
                    obj = new LinkedHashMap<>();
                    obj.put("id",c.getId());
                    obj.put("name",c.getName());
                    obj.put("log",c.getLogo());
                    obj.put("categoryType",c.getCategoryType());
                    result.add(obj);
                }
            }
            return result;
    }

    /**
     * 根据商品分类 获取零售店铺商品列表
     * @param categoryId 必需参数 categoryId
     * @return
     */
    public ArrayList<Map<String,String>> fetchGoodsList(String categoryId){
        Goods goods = new Goods();
        goods.setGoodsCategoryId(categoryId);
        // 已上架商品
        goods.setIsMarketable(1);
        List<Goods> goodsList = goodsService.findList(goods);

        ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
        Map<String, String> obj;
        for (Goods g:goodsList){
            obj = new LinkedHashMap<>();
            obj.put("id",g.getId());
            // 商品名称/价格/库存
            if(!infoChoice(obj,g)){
                continue;
            }
            // 图片组 只传第1张
            String url = "";
            if(StringUtils.isNotEmpty(g.getThumbUrl())){
                String[] urlArray = StringUtils.split(g.getThumbUrl(),"|");
                url = urlArray[0];
            }
            obj.put("image",url);
            result.add(obj);
        }
        return result;
    }

    /**
     * 根据菜品id 获取零售店铺商品信息
     * @param goodsId 必需参数
     * @return
     */
    public LinkedHashMap<String,String> fetchGoodsInfo(String goodsId){
        // 返回 数据 :  商品名称/价格/图片  /商品唯一编号/商品描述/概要描述/商品品牌ID/是否上架/库存数量
        LinkedHashMap<String,String> obj = new LinkedHashMap<>();

        Goods goods = goodsService.get(goodsId);
        if(goods==null){
            return null;
        }
        // 商品名称/价格/库存
        infoChoice(obj,goods);
        //  概要描述/商品品牌ID/是否上架
        obj.put("simpleDesc",goods.getSimpleDesc());
        obj.put("brandId",goods.getBrandId());
        // 图片组 "|"分隔  TODO 启用photoGroup 后修改
        obj.put("images",goods.getThumbUrl());
        return obj;
    }

    // 选择加入的信息 商品名称/价格/库存/商品唯一编号/商品描述
    private boolean infoChoice(Map<String,String> infoMap,Goods goods){
        // 信息添加
        infoMap.put("name",goods.getGoodsName());
        infoMap.put("storage",StringUtils.toString(goods.getStorage(),"0"));
        infoMap.put("productSn",goods.getGoodsSn());
        infoMap.put("productDesc",goods.getDetailDesc());
        infoMap.put("isMarketable",StringUtils.toString(goods.getIsMarketable(),"0"));
        GoodsPriceRule priceRule = new GoodsPriceRule();
        priceRule.setGoodsId(goods.getId());
        priceRule.setStatus(1);
        // 默认一个商品只有1条定价规则启用
        List<GoodsPriceRule> priceRuleList = goodsPriceRuleService.findList(priceRule);
        if(priceRuleList.size()>0 && priceRuleList.get(0).getMarketPrice()!= null){
            // 零售定价
            infoMap.put("price",priceRuleList.get(0).getMarketPrice().toString());
        }else{
            // 没有价格的商品不计入
            return false;
        }
        return true;
    }

}
