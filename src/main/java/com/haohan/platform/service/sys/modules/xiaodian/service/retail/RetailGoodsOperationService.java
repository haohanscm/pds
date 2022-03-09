package com.haohan.platform.service.sys.modules.xiaodian.service.retail;

import com.google.common.collect.Lists;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.utils.excel.ImportExcel;
import com.haohan.platform.service.sys.modules.sys.entity.Dict;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoImport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsPriceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 零售商品库 商品操作
 * Created by dy on 2018/10/31.
 */
@Service
@Transactional(readOnly = true)
public class RetailGoodsOperationService {

    @Autowired
    @Lazy(true)
    private GoodsPriceRuleService goodsPriceRuleService;
    @Autowired
    @Lazy(true)
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    @Lazy(true)
    private GoodsService goodsService;
    @Autowired

    /**
     * 商品导入
     *
     * @param file 导入的Excel文件
     */
    @Transactional(readOnly = false)
    public String goodsImport(MultipartFile file) {
        int successNum = 0;
        int failureNum = 0;
        // 初始值：excel中表头行号  索引从1开始
        int rowNum = 2;
        // 导入失败 提示信息
        StringBuilder failureMsg = new StringBuilder();
        // 导入失败的单元格
        List<int[]> errorCells = Lists.newArrayList();
        try {
            // 参数headerNum 的 索引从0开始
            ImportExcel excel = new ImportExcel(file, rowNum - 1, 0);
            List<GoodsInfoImport> list = excel.getBeanList(GoodsInfoImport.class, fetchHeaderMapper(), errorCells);
            // 店铺ID, 商家ID 映射
            GoodsCategory parent = new GoodsCategory();
            parent.setId("0");
            GoodsCategory categoryRoot = new GoodsCategory();
            categoryRoot.setParent(parent);
            Map<String, String> shopMap = Collections3.extractToMap(goodsCategoryService.findList(categoryRoot), "shopId", "merchantId");
            // 数据处理
            GoodsCategory category;
            GoodsPriceRule goodsPriceRule;
            for (GoodsInfoImport goods : list) {
                rowNum++;
                // 缺少必传字段时 不导入
                String shopId = goods.getShopId();
                String categoryName = goods.getGoodsCategoryId();
                String goodsName = goods.getGoodsName();
                String marketPrice = goods.getMarketPrice() != null ? goods.getMarketPrice().toString() : "";
                boolean flag = true;
                if (StringUtils.isNoneEmpty(shopId, categoryName, goodsName, marketPrice)) {
                    // shopId 判断 不存在时不导入
                    if (!shopMap.containsKey(shopId)) {
                        failureNum++;
                        failureMsg.append("<br/>第 " + rowNum + " 行导入失败：该店铺不存在或未启用；");
                    } else {
                        goods.setMerchantId(shopMap.get(shopId));
                        // 商品品类处理
                        category = new GoodsCategory();
                        category.setShopId(shopId);
                        category.setName(categoryName);
                        category.setCategoryType(ICommonConstant.YesNoType.yes.getCode());
                        List<GoodsCategory> categoryList = goodsCategoryService.findList(category);
                        // 商品品类设置
                        if (categoryList.size() > 0) {
                            goods.setGoodsCategoryId(categoryList.get(0).getId());
                        } else {
                            failureMsg.append("<br/>第 " + rowNum + " 行，" + goodsName + "的商品品类信息导入失败，请手动添加；");
                        }
                        // 设置是否上架 默认是
                        if (goods.getIsMarketable() == null || (goods.getIsMarketable() != null && 0 != goods.getIsMarketable())) {
                            goods.setIsMarketable(1);
                        }
                        // 设置库存数量 默认0
                        if (null == goods.getStorage()) {
                            goods.setStorage(0);
                        }
                        successNum++;
                        goodsService.save(goods);
                        // 定价规则
                        goodsPriceRule = new GoodsPriceRule();
                        goods.fetchGoodsPriceRule(goodsPriceRule);
                        goodsPriceRuleService.save(goodsPriceRule);
                    }
                } else {
                    flag = false;
                }
                if (!flag) {
                    failureNum++;
                    failureMsg.append("<br/>第 " + rowNum + " 行导入失败：该商品缺少必要信息；");
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条信息，导入信息如下：");
            }
            if (errorCells.size() > 0) {
                failureMsg.append("<br/>共有 " + errorCells.size() + " 个单元格信息导入失败：<br/>" + JacksonUtils.toJson(errorCells));
            }
            failureMsg.insert(0, "已成功导入 " + successNum + " 条信息");
        } catch (Exception e) {
            failureMsg.append("导入信息失败！");
            failureMsg.append("<br/>第 " + rowNum + " 行导入商品导致失败，导入中断！");
        }
        return failureMsg.toString();
    }

    /**
     * Excel表头和实体类属性的映射   暂不支持商品扩展属性导入
     * @return
     */
    public Map<String, String> fetchHeaderMapper() {
        Map<String, String> headerMapper = new LinkedHashMap<>();
        headerMapper.put("店铺ID", "shopId");    // 必要
        headerMapper.put("商品品类名称", "goodsCategoryId"); // 必要
        headerMapper.put("商品名称", "goodsName");  // 必要
        headerMapper.put("商品描述", "detailDesc");
//        headerMapper.put("商品唯一编号", "goodsSn");
//        headerMapper.put("概要描述", "simpleDesc");
//        headerMapper.put("商品品牌", "brandId");
//        headerMapper.put("售卖规则", "saleRule");  // 字典 未启用
//        headerMapper.put("图片组编号", "photoGroupNum");  // 未启用
//        headerMapper.put("缩略图地址", "thumbUrl");  // 暂不支持
        headerMapper.put("是否上架", "isMarketable");  // 字典 0 -否, 1 -是
        headerMapper.put("库存数量", "storage");
        headerMapper.put("零售定价", "marketPrice"); // 单位:元 必要
        headerMapper.put("批发定价", "wholesalePrice"); // 单位:元
        headerMapper.put("vip定价", "vipPrice"); // 单位:元
        headerMapper.put("市场价/销售价", "ruleDesc"); // 描述
        headerMapper.put("计量单位", "unit");
        headerMapper.put("备注", "remarks");
        // 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
        List<Dict> headerList = DictUtils.getDictList("retail_goods_import");
        if (!Collections3.isEmpty(headerList)) {
            headerMapper.putAll(Collections3.extractToMap(headerList, "value", "label"));
        }
        return headerMapper;
    }

    /**
     * Excel表头和批注的映射
     * @return
     */
    public Map<String, String> fetchCommentMapper() {
        Map<String, String> commentMapper = new LinkedHashMap<>();
        commentMapper.put("店铺ID", "必填项");
        commentMapper.put("商品品类名称", "必填项");
        commentMapper.put("商品名称", "必填项");
        commentMapper.put("零售定价", "必填项 单位:元");
        commentMapper.put("是否上架", "填写值: 0 -否,1 -是");
        commentMapper.put("库存数量", "值为整数");
        commentMapper.put("批发定价", "单位:元");
        commentMapper.put("vip定价", "单位:元");
        commentMapper.put("市场价/销售价", "描述");
        // 从字典中查询替换  value:表头列名, label:实体类属性名, description:表头批注
        List<Dict> list = DictUtils.getDictList("retail_goods_import");
        if (!Collections3.isEmpty(list)) {
            commentMapper.putAll(Collections3.extractToMap(list, "value", "description"));
        }
        return commentMapper;
    }




}
