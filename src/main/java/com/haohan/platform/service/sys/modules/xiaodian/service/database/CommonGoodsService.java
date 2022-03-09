package com.haohan.platform.service.sys.modules.xiaodian.service.database;

import com.google.common.collect.Lists;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.service.BaseService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.utils.excel.ExportExcel;
import com.haohan.platform.service.sys.common.utils.excel.ImportExcel;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.*;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsCategoryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 公共商品库 商品操作
 * Created by dy on 2018/10/20.
 */
@Service
@Transactional(readOnly = true)
public class CommonGoodsService extends BaseService {

    @Autowired
    @Lazy(true)
    private StandardProductUnitService standardProductUnitService;
    @Autowired
    @Lazy(true)
    private StockKeepingUnitService stockKeepingUnitService;
    @Autowired
    @Lazy(true)
    private ProductAttrNameService productAttrNameService;
    @Autowired
    @Lazy(true)
    private ProductAttrValueService productAttrValueService;
    @Autowired
    @Lazy(true)
    private ProductCategoryService productCategoryService;
    @Autowired
    @Lazy(true)
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    @Lazy(true)
    private GoodsService goodsService;


    /**
     * 根据spuId
     * 转换 商品库标准商品为零售商品
     * 未选择分类
     * @param spu 需完整属性
     * @return
     */
    public GoodsInfoExt transToRetailGoods(StandardProductUnit spu) {
        String spuId = spu.getId();
        if (null == spu || StringUtils.isEmpty(spuId)) {
            return null;
        }
        GoodsInfoExt goodsInfoExt = new GoodsInfoExt();
        // 属性设置
        goodsInfoExt.init();
        // 规格处理
        // 所有sku
        List<StockKeepingUnit> skuList = stockKeepingUnitService.findList(spuId);
        if(Collections3.isEmpty(skuList)){
            // 无规格不转换
            return null;
        }
        transToModel(skuList, goodsInfoExt);
        // 启用规格标记
        if (!Collections3.isEmpty(goodsInfoExt.getGoodsModelList())) {
            goodsInfoExt.setGoodsModel(ICommonConstant.YesNoType.yes.getCode());
        }

        // 基础属性复制
        transToGoodsInfoExt(spu, goodsInfoExt);
        // 默认 零售商品 的 价格/单位/扫码条码 同第一条规格
        if (!Collections3.isEmpty(goodsInfoExt.getGoodsModelList())) {
            GoodsModel goodsModel = goodsInfoExt.getGoodsModelList().get(0);
            goodsInfoExt.setMarketPrice(goodsModel.getModelPrice());
            goodsInfoExt.setUnit(goodsModel.getModelUnit());
            goodsInfoExt.setStorage(goodsModel.getModelStorage());
            goodsInfoExt.setScanCode(goodsModel.getModelCode());
        }
        return goodsInfoExt;
    }

    /**
     * 根据spuId查询
     * 转换 库存商品 为 零售商品的规格信息/列表
     *
     * @param skuList      商品库库存商品
     * @param goodsInfoExt 零售商品
     * @return
     */
    public GoodsInfoExt transToModel(List<StockKeepingUnit> skuList, GoodsInfoExt goodsInfoExt) {
        // 零售商品规格列表
        List<GoodsModel> goodsModelList = new ArrayList<>();
        GoodsModel goodsModel;
        // sku 的属性名Ids,属性值Ids,
        List<String> attrNameIds = new ArrayList<>();
        List<String> attrValueIds = new ArrayList<>();
        // 处理每个sku
        for (StockKeepingUnit sku : skuList) {
            try {
                attrNameIds.addAll(Arrays.asList(StringUtils.split(sku.getAttrNameIds(), ",")));
                attrValueIds.addAll(Arrays.asList(StringUtils.split(sku.getAttrValueIds(), ",")));
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("sku 处理错误", e.getMessage());
            }
            // sku 属性复制给 GoodsModel
            goodsModel = new GoodsModel();
            transToGoodsModel(sku, goodsModel);
            goodsModelList.add(goodsModel);
        }

        // 属性名/属性值  处理
        // 零售商品规格信息
        Map<String, JisuModelInfo> modelInfo = new HashMap<>(8);
        // 属性值 列表查询
        List<ProductAttrValue> attrValueList = productAttrValueService.findByValueIds(StringUtils.join(attrValueIds, ","));
        // 存放所有 属性值  attrValueId: subModelId
        Map<String, String> valueMap = new HashMap<>(8);
        // 属性名 列表查询
        List<ProductAttrName> attrNameList = productAttrNameService.findByNameIds(StringUtils.join(attrNameIds, ","));
        // 存放所有 属性名   attrName: nameId
        Map<String, String> nameMap = new HashMap<>(8);
        // nameMap 设置
        for (ProductAttrName name : attrNameList) {
            nameMap.put(name.getAttrName(), StringUtils.toString(name.getSort(), "111"));
        }
        // subModelId 默认从30开始 增加
        int subModelIdIncr = 30;
        // 属性名/属性值 map及规格信息处理
        for (ProductAttrValue attrValue : attrValueList) {
            // 规格信息modelInfo 中 key
            String nameId = nameMap.get(attrValue.getAttrName());
            // 规格类型id  对应零售商品规格subModelId
            String subModelId;
            JisuModelInfo jisuModelInfo;
            // 规格类型id起始值 默认从30开始
            subModelId = StringUtils.toString(subModelIdIncr++, "30");
            // 生成 规格信息modelInfo
            if (modelInfo.containsKey(nameId)) {
                jisuModelInfo = modelInfo.get(nameId);
                jisuModelInfo.getSubModelId().add(subModelId);
                jisuModelInfo.getSubModelName().add(attrValue.getAttrValue());
            } else {
                jisuModelInfo = new JisuModelInfo();
                jisuModelInfo.setName(attrValue.getAttrName());
                jisuModelInfo.setId(nameId);
                jisuModelInfo.setSubModelId(new ArrayList<>());
                jisuModelInfo.setSubModelName(new ArrayList<>());
                jisuModelInfo.getSubModelId().add(subModelId);
                jisuModelInfo.getSubModelName().add(attrValue.getAttrValue());
                modelInfo.put(nameId, jisuModelInfo);
            }
            valueMap.put(attrValue.getId(), subModelId);
        }
        // 存入 modelInfo
        goodsInfoExt.setModelInfo(modelInfo);
        // goodsModelList中 规格组合修改
        List<String> idsList;
        for (GoodsModel g : goodsModelList) {
            idsList = new ArrayList<>();
            String[] ids = StringUtils.split(g.getModel(), ",");
            for (int i = 0; i < ids.length; i++) {
                if (valueMap.containsKey(ids[i])) {
                    idsList.add(valueMap.get(ids[i]));
                }
            }
            g.setModel(StringUtils.join(idsList, ","));
        }
        // 存入 goodsModelList
        goodsInfoExt.setGoodsModelList(goodsModelList);
        return goodsInfoExt;
    }

    /**
     * sku 属性复制给 GoodsModel
     * 价格/单位/图片/条码/重量/体积/库存
     * 规格名(拼接)/规格组合
     *
     * @param sku
     * @param goodsModel
     * @return
     */
    public GoodsModel transToGoodsModel(StockKeepingUnit sku, GoodsModel goodsModel) {
        goodsModel.setModelPrice(sku.getSalePrice());
        goodsModel.setModelUnit(sku.getUnit());
        goodsModel.setModelUrl(sku.getAttrPhoto());
        goodsModel.setModelCode(sku.getScanCode());
        goodsModel.setWeight(sku.getWeight());
        goodsModel.setVolume(sku.getVolume());
        goodsModel.setModelStorage(StringUtils.toInteger(sku.getStock()));
        // 规格名(拼接)/规格组合
        goodsModel.setModelName(sku.getAttrDetail());
        goodsModel.setModel(sku.getAttrValueIds());
        // 通用编号
        goodsModel.setModelGeneralSn(sku.getStockGoodsSn());
        return goodsModel;
    }

    /**
     * spu 复制属性给零售商品  名称/描述/通用编号/图片/图片组编号/排序
     *
     * @param spu
     * @param goodsInfoExt
     * @return
     */
    public GoodsInfoExt transToGoodsInfoExt(StandardProductUnit spu, GoodsInfoExt goodsInfoExt) {
        goodsInfoExt.setGoodsName(spu.getGoodsName());
        goodsInfoExt.setDetailDesc(spu.getDetailDesc());
        goodsInfoExt.setGeneralSn(spu.getGeneralSn());
        goodsInfoExt.setThumbUrl(spu.getThumbUrl());
        goodsInfoExt.setPhotoGroupNum(spu.getPhotoGroupNum());
        goodsInfoExt.setSort(StringUtils.toString(spu.getSort(), "10"));
        goodsInfoExt.setGoodsFrom(IGoodsConstant.GoodsFromType.platform.getCode());
        goodsInfoExt.setGoodsType(IGoodsConstant.GoodsType.physical.getCode());
        return goodsInfoExt;
    }

    /**
     * Excel导入信息   实体类属性未做验证
     * 一行信息 转为 一个实体类对象
     * headerMapper  Excel表头和实体类属性的映射  ex: "店铺ID":"shopId"
     *
     * @param file excel 文件
     * @param type 导入类型   category   spu   sku  skuExt
     * @return
     */
    @Transactional(readOnly = false)
    public String fileImport(MultipartFile file, String type) {
        int successNum = 0;
        int failureNum = 0;
        // 初始值：excel中表头行号  索引从1开始
        int rowNum = 2;
        // 导入失败 提示信息
        StringBuilder failureMsg = new StringBuilder();
        // 导入失败的单元格
        List<int[]> errorCells = Lists.newArrayList();
        Map<String, String> headerMapper;
        int[] resultNum = new int[]{successNum, failureNum, rowNum};
        try {
            // 参数headerNum 的 索引从0开始
            ImportExcel excel = new ImportExcel(file, rowNum - 1, 0);
            // excel每行数据转换为 entity
            // 每行数据处理 及保存
            if (StringUtils.equals("category", type)) {
                headerMapper = productCategoryService.fetchHeaderMapper();
                List<ProductCategory> list = excel.getBeanList(ProductCategory.class, headerMapper, errorCells);
                resultNum = categoryTransAndSave(list, successNum, failureNum, rowNum, failureMsg);
            } else if (StringUtils.equals("spu", type)) {
                headerMapper = standardProductUnitService.fetchHeaderMapper();
                List<StandardProductUnit> list = excel.getBeanList(StandardProductUnit.class, headerMapper, errorCells);
                resultNum = spuTransAndSave(list, successNum, failureNum, rowNum, failureMsg);
            } else if (StringUtils.equals("sku", type)) {
                headerMapper = stockKeepingUnitService.fetchHeaderMapper();
                List<StockKeepingUnit> list = excel.getBeanList(StockKeepingUnit.class, headerMapper, errorCells);
                resultNum = skuTransAndSave(list, successNum, failureNum, rowNum, failureMsg);
                // 生成 规格商品 stockGoodsSn
                genAllSkuSn();
            } else if (StringUtils.equals("skuExt", type)) {
                // todo 未修改完成
                headerMapper = stockKeepingUnitService.fetchHeaderMapper();
                List<SkuExtImport> list = excel.getBeanList(SkuExtImport.class, headerMapper, errorCells);
                resultNum = skuExtTransAndSave(list, successNum, failureNum, rowNum, failureMsg);
            } else {
                failureMsg.append("导入信息失败！导入类型有误!");
                return failureMsg.toString();
            }
            successNum = resultNum[0];
            failureNum = resultNum[1];
            rowNum = resultNum[2];
            // 导入成功失败记录
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条信息，导入信息如下：");
            }
            if (errorCells.size() > 0) {
                failureMsg.append("<br/>共有 " + errorCells.size() + " 个单元格信息导入失败：<br/>" + JacksonUtils.toJson(errorCells));
            }
            failureMsg.insert(0, "已成功导入 " + successNum + " 条信息");
        } catch (Exception e) {
            failureMsg.append("导入信息失败！");
            failureMsg.append("<br/>第 " + rowNum + " 行信息导入导致失败，导入中断！");
        }
        return failureMsg.toString();
    }

    // 暂未使用
    private int[] skuExtTransAndSave(List<SkuExtImport> list, int successNum, int failureNum, int rowNum, StringBuilder failureMsg) {

        return new int[]{successNum, failureNum, rowNum};
    }

    /**
     * 商品库  库存商品  sku excel导入时 转换并保存
     * 同一spu商品   已存在相同名称的spu时不再导入
     *
     * @param list       从excel中读取的分类信息
     * @param successNum 成功转换的行数
     * @param failureNum 转换失败的行数
     * @param rowNum     当前处理的行号
     * @param failureMsg 处理失败的信息
     */
    private int[] skuTransAndSave(List<StockKeepingUnit> list, int successNum, int failureNum, int rowNum, StringBuilder failureMsg) {
        // 已存在的spu
        List<StandardProductUnit> existSpuList = standardProductUnitService.findList(new StandardProductUnit());
        Map<String, String> existSpuMap = Collections3.extractToMap(existSpuList, "goodsName", "id");
        // 已存在的属性名
        List<ProductAttrName> existNameList = productAttrNameService.findList(new ProductAttrName());
        Map<String, String> existNameMap = Collections3.extractToMap(existNameList, "attrName", "id");
        // 已存在的属性值
        List<ProductAttrValue> existValueList = productAttrValueService.findList(new ProductAttrValue());
        Map<String, String> existValueMap = Collections3.extractToMap(existValueList, "attrValue", "id");
        // 新增的属性值列表
        List<ProductAttrValue> valueSaveList = new ArrayList<>();
        // 已存在的sku
        List<StockKeepingUnit> existSkuList = stockKeepingUnitService.findListWithAttrInfo(new StockKeepingUnit());
        // 按attrInfo区分不同sku  map键 ex:  spuName + attrInfo
        Map<String, StockKeepingUnit> existSkuMap = new HashMap<>();
        for (StockKeepingUnit sku : existSkuList) {
            String key = StringUtils.defaultString(sku.getGoodsName()) + sku.getAttrInfo();
            existSkuMap.put(key, sku);
        }
        // 需保存sku
        List<StockKeepingUnit> saveList = new ArrayList<>();
        // 处理导入的sku
        String attrInfo;
        String goodsName;
        for (StockKeepingUnit sku : list) {
            // 当前处理行号
            rowNum++;
            // 缺少必传字段时 不导入; 同一spu商品 已存在相同名称的spu时不再导入
            attrInfo = sku.getAttrInfo();
            goodsName = StringUtils.defaultString(sku.getGoodsName());
            String skuMapKey = goodsName + attrInfo;
            if (StringUtils.isEmpty(attrInfo) || existSkuMap.containsKey(skuMapKey)) {
                failureNum++;
                failureMsg.append("<br/>第 " + rowNum + " 行导入失败：未填写sku商品规格属性信息或已存在该规格属性信息；");
                continue;
            }
            // 处理 sku的spuId
            if (existSpuMap.containsKey(goodsName)) {
                sku.setSpuId(existSpuMap.get(goodsName));
            } else {
                failureNum++;
                failureMsg.append("<br/>第 " + rowNum + " 行导入失败:未找到sku商品所属的spu商品；");
                continue;
            }
            // 处理 attrInfo  保存 attrNameIds/attrValueIds/attrDetail
            try {
                // attrInfo   ex:   尺码:XL,颜色:蓝
                String[] nameValueList = StringUtils.split(attrInfo, ",");
                // 保存属性名/属性值 用于关联id
                List<String> nameList = new ArrayList<>();
                List<String> valueList = new ArrayList<>();
                // 关联的id
                Set<String> nameIdSet = new HashSet<>();
                Set<String> valueIdSet = new HashSet<>();
                // 处理成功标记
                boolean flag = true;
                if (null == nameValueList) {
                    flag = false;
                } else {
                    for (String nv : nameValueList) {
                        String[] attr = StringUtils.split(nv, ":");
                        if (null == attr || attr.length != 2) {
                            flag = false;
                            break;
                        } else {
                            nameList.add(attr[0]);
                            valueList.add(attr[1]);
                        }
                    }
                }
                // 当属性名 找不到时,所有属性都不导入
                for (String name : nameList) {
                    if (!existNameMap.containsKey(name)) {
                        flag = false;
                        break;
                    }
                    nameIdSet.add(existNameMap.get(name));
                }
                if (!flag) {
                    failureNum++;
                    failureMsg.append("<br/>第 " + rowNum + " 行导入失败：sku商品的规格属性信息有误；");
                    continue;
                }
                // attrValueIds获取
                int i = 0; // 索引
                for (String value : valueList) {
                    if (existValueMap.containsKey(value)) {
                        valueIdSet.add(existValueMap.get(value));
                    } else {
                        // 新增属性值记录
                        ProductAttrValue productAttrValue = new ProductAttrValue();
                        productAttrValue.setId(IdGen.genByT(ProductAttrValue.class));
                        productAttrValue.setIsNewRecord(true);
                        productAttrValue.setAttrNameId(existNameMap.get(nameList.get(i)));
                        productAttrValue.setAttrValue(value);
                        productAttrValue.setSort(10);
                        valueIdSet.add(productAttrValue.getId());
                        valueSaveList.add(productAttrValue);
                        existValueMap.put(value, productAttrValue.getId());
                    }
                    i++;
                }
                // 保存attrNameIds
                sku.setAttrNameIds(StringUtils.join(nameIdSet.toArray(), ","));
                // 保存attrValueIds
                sku.setAttrValueIds(StringUtils.join(valueIdSet.toArray(), ","));
                // 保存attrDetail
                sku.setAttrDetail(StringUtils.join(valueList, ","));

                // map加入当前导入sku
                successNum++;
                sku.setId(IdGen.genByT(StockKeepingUnit.class));
                sku.setIsNewRecord(true);
                existSkuMap.put(skuMapKey, sku);
                saveList.add(sku);
            } catch (Exception e) {
                failureNum++;
                failureMsg.append("<br/>第 " + rowNum + " 行导入失败：商品解析有误；");
            }
        }
        // 保存属性值
        for (ProductAttrValue value : valueSaveList) {
            productAttrValueService.save(value);
        }
        // 保存sku
        for (StockKeepingUnit sku : saveList) {
            stockKeepingUnitService.save(sku);
        }
        return new int[]{successNum, failureNum, rowNum};
    }

    /**
     * 商品库  标准商品 spu excel导入时 转换并保存
     *
     * @param list       从excel中读取的分类信息
     * @param successNum 成功转换的行数
     * @param failureNum 转换失败的行数
     * @param rowNum     当前处理的行号
     * @param failureMsg 处理失败的信息
     */
    private int[] spuTransAndSave(List<StandardProductUnit> list, int successNum, int failureNum, int rowNum, StringBuilder failureMsg) {
        // 已存在的分类
        List<ProductCategory> existCategoryList = productCategoryService.findList(new ProductCategory());
        Map<String, String> existCategoryMap = Collections3.extractToMap(existCategoryList, "name", "id");
        // 已存在的spu
        List<StandardProductUnit> existSpuList = standardProductUnitService.findList(new StandardProductUnit());
        // 按spu名称区分不同spu
        Map<String, String> existSpuMap = Collections3.extractToMap(existSpuList, "goodsName", "id");
        // 需保存spu
        List<StandardProductUnit> saveList = new ArrayList<>();
        // 处理导入的spu
        String goodsName;
        String categoryName;
        for (StandardProductUnit spu : list) {
            // 当前处理行号
            rowNum++;
            // 缺少必传字段时 不导入;已存在相同名称的spu时不再导入
            goodsName = spu.getGoodsName();
            if (StringUtils.isEmpty(goodsName) || existSpuMap.containsKey(goodsName)) {
                failureNum++;
                failureMsg.append("<br/>第 " + rowNum + " 行导入失败：未填写spu商品名称或已存在该spu商品名称；");
                continue;
            }
            // 处理 spu分类
            categoryName = spu.getCategoryName();
            if (existCategoryMap.containsKey(categoryName)) {
                spu.setGoodsCategoryId(existCategoryMap.get(categoryName));
            } else {
                failureMsg.append("<br/>第 " + rowNum + " 行商品,商品分类设置失败,请手动选择；");
            }
            // map加入当前导入spu
            successNum++;
            spu.setId(IdGen.genByT(StandardProductUnit.class));
            spu.setIsNewRecord(true);
            existSpuMap.put(goodsName, spu.getId());
            saveList.add(spu);
        }
        // 保存spu
        for (StandardProductUnit spu : saveList) {
            standardProductUnitService.save(spu);
        }
        return new int[]{successNum, failureNum, rowNum};
    }

    /**
     * 商品库 分类信息 excel导入时 转换并保存
     *
     * @param list       从excel中读取的分类信息
     * @param successNum 成功转换的行数
     * @param failureNum 转换失败的行数
     * @param rowNum     当前处理的行号
     * @param failureMsg 处理失败的信息
     */
    private int[] categoryTransAndSave(List<ProductCategory> list, int successNum, int failureNum, int rowNum, StringBuilder failureMsg) {
        // 已存在的分类
        List<ProductCategory> existCategoryList = productCategoryService.findList(new ProductCategory());
        // 按分类名称区分不同分类
        Map<String, ProductCategory> existCategoryMap = new HashMap<>(existCategoryList.size());
        for (ProductCategory category : existCategoryList) {
            existCategoryMap.put(category.getName(), category);
        }
        // 需保存分类
        List<ProductCategory> saveList = new ArrayList<>(list.size());
        // 未处理完的分类  父级分类未处理
        List<ProductCategory> waitList = new ArrayList<>(list.size());

        // 处理导入的分类
        String name;
        String parentName;
        for (ProductCategory category : list) {
            // 当前处理行号
            rowNum++;
            // 缺少必传字段时 不导入;已存在相同名称的分类时不再导入
            name = category.getName();
            if (StringUtils.isEmpty(name) || existCategoryMap.containsKey(name)) {
                failureNum++;
                failureMsg.append("<br/>第 " + rowNum + " 行导入失败：未填写分类名称或已存在该分类名称；");
                continue;
            }
            // 处理父级分类  headerMapper中存放名称在parentIds中
            parentName = category.getParentIds();
            if (StringUtils.isEmpty(parentName)) {
                category.setParent(null);
                category.setParentIds(null);
                saveList.add(category);
            } else if (existCategoryMap.containsKey(parentName)) {
                category.setParent(existCategoryMap.get(parentName));
                category.setParentIds(null);
                saveList.add(category);
            } else {
                // 找不到父分类时
                waitList.add(category);
            }
            // map加入当前导入分类
            successNum++;
            category.setId(IdGen.genByT(ProductCategory.class));
            category.setIsNewRecord(true);
            existCategoryMap.put(name, category);
        }
        // 处理 还需处理的分类  (父级分类未处理)
        for (ProductCategory category : waitList) {
            parentName = category.getParentIds();
            // 找不到父级分类时,该分类作为一级分类保存
            if (existCategoryMap.containsKey(parentName)) {
                category.setParent(existCategoryMap.get(parentName));
            } else {
                category.setParent(null);
            }
            category.setParentIds(null);
            saveList.add(category);
        }
        // 分类保存
        for (ProductCategory category : saveList) {
            productCategoryService.save(category);
        }
        return new int[]{successNum, failureNum, rowNum};
    }

    /**
     * 导出Excel文件
     *
     * @param list          导出信息 列表
     * @param headerMapper  Excel表头和实体类属性的映射  ex: "店铺ID":"shopId"
     * @param commentMapper Excel表头和批注的映射   ex: "店铺ID":"必填项"
     * @param name          用于生成文件名  如: name = "商品库sku商品"  fileName = name+ "导出" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
     * @param response      用于输出到客户端
     * @param <E>
     * @return
     */
    public <E> BaseResp exportFile(List<E> list, Map<String, String> headerMapper, Map<String, String> commentMapper, String name, HttpServletResponse response) {
        BaseResp baseResp = BaseResp.newError();
        try {
            String fileName = name + "导出" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            new ExportExcel(name, headerMapper, commentMapper).setBeanList(list, headerMapper).write(response, fileName).dispose();
            baseResp.success();
        } catch (Exception e) {
            baseResp.setMsg("导出" + name + "失败！失败信息：" + e.getMessage());
        }
        return baseResp;
    }

    // 商品分类导出
    public BaseResp categoryExportFile(List<ProductCategory> list, HttpServletResponse response) {
        String name = "商品库商品分类";
        Map<String, String> headerMapper = productCategoryService.fetchHeaderMapper();
        Map<String, String> commentMapper = productCategoryService.fetchCommentMapper();
        return exportFile(list, headerMapper, commentMapper, name, response);
    }

    // spu商品导出
    public BaseResp spuExportFile(List<StandardProductUnit> list, HttpServletResponse response) {
        String name = "商品库spu商品";
        Map<String, String> headerMapper = standardProductUnitService.fetchHeaderMapper();
        Map<String, String> commentMapper = standardProductUnitService.fetchCommentMapper();
        return exportFile(list, headerMapper, commentMapper, name, response);
    }

    // sku商品导出
    public BaseResp skuExportFile(List<StockKeepingUnit> list, HttpServletResponse response) {
        String name = "商品库sku商品";
        Map<String, String> headerMapper = stockKeepingUnitService.fetchHeaderMapper();
        Map<String, String> commentMapper = stockKeepingUnitService.fetchCommentMapper();
        return exportFile(list, headerMapper, commentMapper, name, response);
    }


    /**
     * spu商品转换并保存
     * 所有spu商品 放在 同一个零售商品分类下
     * @param goodsCategory 必需 shopId/merchantId/id
     * @param spuList  需完整属性
     */
    @Transactional(readOnly = false)
    public void retailGoodsSave(GoodsCategory goodsCategory, List<StandardProductUnit> spuList) {
        for (StandardProductUnit spu : spuList) {
            // 存在相同 generalSn 的 已上架商品不再导入   上下架状态 0:下架,1:上架
            Goods query = new Goods();
            query.setShopId(goodsCategory.getShopId());
            query.setIsMarketable(1);
            query.setGeneralSn(spu.getGeneralSn());
            List<Goods> list = goodsService.findList(query);
            if(!Collections3.isEmpty(list)){
                continue;
            }
            // 商品库标准商品为零售商品
            GoodsInfoExt goodsInfoExt = transToRetailGoods(spu);
            if(null == goodsInfoExt){
                continue;
            }
            // 设置分类及关联商家店铺
            goodsInfoExt.setShopId(goodsCategory.getShopId());
            goodsInfoExt.setMerchantId(goodsCategory.getMerchantId());
            goodsInfoExt.setGoodsCategoryId(goodsCategory.getId());
            // 保存 零售商品
            goodsService.saveGoods(goodsInfoExt, "base");
        }
    }

    /**
     * 建立 公共商品库分类和 零售商品分类的id映射
     * 所有零售商品分类为新增
     * @param categoryList  使用 id
     * @param shopId
     * @param merchantId
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, String> transCategoryMap(List<ProductCategory> categoryList, String shopId, String merchantId) {
        List<String> categoryIdList = new ArrayList<>(categoryList.size());
        for (ProductCategory p : categoryList) {
            categoryIdList.add(p.getId());
        }
        return transCategoryMap(shopId, merchantId, categoryIdList);
//        int size = categoryList.size();
//        int mapSize = size * 4 / 3 + 1;
//        // productCategoryId : goodsCategoryId
//        Map<String, String> resultMap = new HashMap<>(mapSize);
//        // 新增的零售商品分类列表
//        List<GoodsCategory> goodsCategoryList = new ArrayList<>(size);
//        Map<String, GoodsCategory> goodsCategoryMap = new HashMap<>(mapSize);
//
////        Map<String, ProductCategory> productCategoryMap = new HashMap<>(mapSize);
//        for (ProductCategory p : categoryList) {
////            productCategoryMap.put(p.getId(), p);
//            // 新增的零售分类
//            GoodsCategory goodsCategory = new GoodsCategory();
//            goodsCategory.setShopId(shopId);
//            goodsCategory.setMerchantId(merchantId);
//            goodsCategory.setName(p.getName());
//            goodsCategory.setSort(p.getSort());
//            // 设置通用分类sn
//            goodsCategory.setGeneralCategorySn(p.getGeneralCategorySn());
//            goodsCategory.setId(IdGen.uuid());
//            goodsCategory.setIsNewRecord(true);
//            goodsCategoryList.add(goodsCategory);
//            resultMap.put(p.getId(), goodsCategory.getId());
//            goodsCategoryMap.put(goodsCategory.getId(), goodsCategory);
//        }
//        // 关联父分类
//        GoodsCategory category;
//        for (ProductCategory p : categoryList) {
//            String parentId = p.getParentId();
//            if (StringUtils.isNotEmpty(parentId) && !StringUtils.equals("0", parentId)) {
//                category = goodsCategoryMap.get(resultMap.get(p.getId()));
//                category.setParent(new GoodsCategory(resultMap.get(parentId)));
//            }
//        }
//        for (GoodsCategory g : goodsCategoryList) {
//            goodsCategoryService.save(g);
//        }
//        return resultMap;
    }

    /**
     * 建立 公共商品库分类和 零售商品分类的id映射
     * 查询 商家的零售分类  通过generalCategorySn 或 名称匹配
     * @param shopId
     * @param merchantId
     * @param categoryIdList  公共库分类Id 列表
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, String> transCategoryMap(String shopId, String merchantId, List<String> categoryIdList) {
        int size = categoryIdList.size();
        int mapSize = size * 4 / 3 + 1;
        // productCategoryId : goodsCategoryId
        Map<String, String> resultMap = new HashMap<>(mapSize);
        // 新增的零售商品分类列表
        List<GoodsCategory> goodsCategoryList = new ArrayList<>(size);

        // 需要的 公共库分类及其父分类
        // 结果中一级分类排在前
        List<ProductCategory> categoryList = productCategoryService.findListByIdsWithParent(StringUtils.join(categoryIdList,","));
        // 查询映射关系 一级分类
        List<ProductCategory> mapList = productCategoryService.findRelation(shopId, "0", "");
        for(ProductCategory p:mapList){
            if(!resultMap.containsKey(p.getId())){
                resultMap.put(p.getId(), p.getTempId());
            }
        }
        // 查询映射关系 二级分类
        mapList = productCategoryService.findRelation(shopId, "", "0");
        for(ProductCategory p:mapList){
            if(!resultMap.containsKey(p.getId())){
                resultMap.put(p.getId(), p.getTempId());
            }
        }

        // 若零售店铺无对应分类则新增分类
        for (ProductCategory p : categoryList) {
            // 已有分类映射关系 不需新增
            if(resultMap.containsKey(p.getId())){
                continue;
            }
            // 新增的零售分类
            GoodsCategory goodsCategory = new GoodsCategory();
            goodsCategory.setShopId(shopId);
            goodsCategory.setMerchantId(merchantId);
            goodsCategory.setName(p.getName());
            goodsCategory.setSort(p.getSort());
            goodsCategory.setLogo(p.getLogo());
            goodsCategory.setDescription(p.getCategoryDesc());
            goodsCategory.setCategoryType(ICommonConstant.YesNoType.yes.getCode());
            // 设置通用分类sn
            goodsCategory.setGeneralCategorySn(p.getGeneralCategorySn());
            // 设置父级分类
            String parentId = p.getParentId();
            if(StringUtils.equals("0", parentId)){
                goodsCategory.setParent(new GoodsCategory("0"));
            } else {
                // 关联父分类
                goodsCategory.setParent(new GoodsCategory(resultMap.get(parentId)));
            }
            goodsCategory.setId(IdGen.genByT(GoodsCategory.class));
            goodsCategory.setIsNewRecord(true);
            goodsCategoryList.add(goodsCategory);
            resultMap.put(p.getId(), goodsCategory.getId());
        }
        for (GoodsCategory g : goodsCategoryList) {
            goodsCategoryService.save(g);
        }
        return resultMap;
    }

    /**
     * 公共商品库商品 导入 零售商品库
     * 按分类导入
     * 一个公共库分类下的spu 全导入 一个零售分类下
     * @param retailCategoryId 零售商品分类id (非父节点)
     * @param categoryId       公共商品分类id (非父节点)
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp importToRetailGoods(String retailCategoryId, String categoryId) {
        BaseResp baseResp = BaseResp.newError();
        GoodsCategory goodsCategory = goodsCategoryService.get(retailCategoryId);
        ProductCategory productCategory = productCategoryService.get(categoryId);
        if (null == goodsCategory || null == productCategory) {
            baseResp.setMsg("分类选择有误, 不能选择父级分类");
            return baseResp;
        }
        // spu列表
        StandardProductUnit standardProductUnit = new StandardProductUnit();
        standardProductUnit.setGoodsCategoryId(categoryId);
        List<StandardProductUnit> spuList = standardProductUnitService.findList(standardProductUnit);
        if (Collections3.isEmpty(spuList)) {
            baseResp.setMsg("公共商品库分类下无商品");
            return baseResp;
        }
        // spu商品转换并保存
        retailGoodsSave(goodsCategory, spuList);
        baseResp.success();
        return baseResp;
    }

    /**
     * 公共商品库商品 导入 零售商品库
     * 按分类导入 所有零售分类都为新增
     * 选择多个公共库分类id 下所有spu 导入零售库
     * @param categoryList 公共商品库分类列表,需带父级分类,不然在零售商品中无父级
     * @param shopId
     * @param merchantId
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp importToRetailGoods(List<ProductCategory> categoryList, String shopId, String merchantId) {
        BaseResp baseResp = BaseResp.newError();
        // 获取分类映射
        Map<String, String> categoryMap = transCategoryMap(categoryList, shopId, merchantId);

        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setShopId(shopId);
        goodsCategory.setMerchantId(merchantId);
        try {
            // spu列表
            StandardProductUnit standardProductUnit;
            List<StandardProductUnit> spuList;
            for (ProductCategory p : categoryList) {
                standardProductUnit = new StandardProductUnit();
                standardProductUnit.setGoodsCategoryId(p.getId());
                spuList = standardProductUnitService.findList(standardProductUnit);
                if (!Collections3.isEmpty(spuList)) {
                    goodsCategory.setId(categoryMap.get(p.getId()));
                    retailGoodsSave(goodsCategory, spuList);
                }
            }
            baseResp.success();
        } catch (Exception e) {
            baseResp.setMsg("操作中断");
        }
        return baseResp;
    }

    /**
     * 公共商品库商品 导入 零售商品库
     * 按spu导入
     * 选择多个spu 导入零售库
     * @param shopId
     * @param merchantId
     * @param spuIdList
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp importToRetailGoods(String shopId, String merchantId, List<String> spuIdList) {
        BaseResp baseResp = BaseResp.newError();
        // 获取分类映射
        String ids = StringUtils.join(spuIdList, ",");
        List<StandardProductUnit> spuList = standardProductUnitService.findListByIds(ids);
        if(Collections3.isEmpty(spuList)){
            baseResp.setMsg("选择的公共库商品无效");
            return baseResp;
        }
        // 分类处理
        Set<String> categoryIdSet = new HashSet<>(spuList.size());
        for(StandardProductUnit spu : spuList){
            if(StringUtils.isNotEmpty(spu.getGoodsCategoryId())){
                categoryIdSet.add(spu.getGoodsCategoryId());
            }
        }
        List<String> categoryIdList = new ArrayList<>(categoryIdSet);
        // 分类id映射
        Map<String, String> categoryIdMap = transCategoryMap(shopId, merchantId, categoryIdList);
        // spu 商品转换
        for (StandardProductUnit spu : spuList) {
            // 商品库标准商品为零售商品
            GoodsInfoExt goodsInfoExt = transToRetailGoods(spu);
            if(null == goodsInfoExt){
                continue;
            }
            // 设置分类及关联商家店铺
            goodsInfoExt.setShopId(shopId);
            goodsInfoExt.setMerchantId(merchantId);
            goodsInfoExt.setGoodsCategoryId(categoryIdMap.get(spu.getGoodsCategoryId()));
            // 保存 零售商品
            goodsService.saveGoods(goodsInfoExt, "base");
        }
        baseResp.success();
        return baseResp;
    }

    /**
     * 公共商品库商品 导入 零售商品库
     * 单个spu 导入 所选 零售商品分类下
     *
     * @param goodsCategory 零售商品分类 (非父节点)
     * @param spuId         公共商品spu id
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp importToRetailGoods(GoodsCategory goodsCategory, String spuId) {
        BaseResp baseResp = BaseResp.newError();
        GoodsCategory category = goodsCategoryService.get(goodsCategory.getId());
        if (null == category) {
            baseResp.setMsg("分类选择有误");
            return baseResp;
        }
        // spu
        StandardProductUnit standardProductUnit = standardProductUnitService.get(spuId);
        if (null == standardProductUnit) {
            baseResp.setMsg("公共商品库spu有误");
            return baseResp;
        }
        // spu商品转换并保存
        // 商品库标准商品为零售商品
        GoodsInfoExt goodsInfoExt = transToRetailGoods(standardProductUnit);
        if(null == goodsInfoExt){
            baseResp.setMsg("无sku信息");
            return baseResp;
        }
        // 设置分类及关联商家店铺
        goodsInfoExt.setShopId(goodsCategory.getShopId());
        goodsInfoExt.setMerchantId(goodsCategory.getMerchantId());
        goodsInfoExt.setGoodsCategoryId(goodsCategory.getId());
        // 保存 零售商品
        goodsService.saveGoods(goodsInfoExt, "base");
        baseResp.success();
        return baseResp;
    }

    /**
     * 生成所有库存商品的商品编号
     *
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp genAllSkuSn() {
        BaseResp baseResp = BaseResp.newError();
        // 查询所有库存商品中 sku商品编号为空的
        List<StockKeepingUnit> spuIdList = stockKeepingUnitService.findEmptySnSpuList();
        if (Collections3.isEmpty(spuIdList)) {
            baseResp.setMsg("未找到需生成sku商品编号的sku商品");
            return baseResp;
        }
        List<StockKeepingUnit> skuList;
        // spu 商品编号
        String generalSn;
        int curNum;
        int successNum = 0;
        for (StockKeepingUnit s : spuIdList) {
            skuList = stockKeepingUnitService.findList(s.getSpuId());
            if (Collections3.isEmpty(skuList)) {
                continue;
            }
            int maxNum = 0;
            generalSn = s.getGeneralSn();
            // 获取最大编号数
            for (StockKeepingUnit sku : skuList) {
                if (StringUtils.isEmpty(sku.getStockGoodsSn())) {
                    continue;
                }
                curNum = stockKeepingUnitService.fetchSkuSnNum(sku.getStockGoodsSn());
                maxNum = (curNum > maxNum) ? curNum : maxNum;
            }
            for (StockKeepingUnit sku : skuList) {
                if (StringUtils.isEmpty(sku.getStockGoodsSn())) {
                    sku.setStockGoodsSn(stockKeepingUnitService.createSkuSn(generalSn, ++maxNum));
                    stockKeepingUnitService.save(sku);
                    successNum++;
                }
            }
        }
        if (successNum > 0) {
            baseResp.success();
            baseResp.setMsg("共成功生成:" + successNum + "个sku商品编号");
        }
        return baseResp;
    }

    /**
     * 生成所有商品分类的分类编号
     *
     * @return
     */
    @Transactional(readOnly = false)
    public BaseResp genAllCategorySn() {
        BaseResp baseResp = BaseResp.newError();
        // 所有商品分类中 分类编号为空的
        List<ProductCategory> categoryList = productCategoryService.findEmptySnList();
        if (Collections3.isEmpty(categoryList)) {
            baseResp.setMsg("未找到需生成分类编号的商品分类");
            return baseResp;
        }
        int successNum = 0;
        for(ProductCategory p:categoryList){
            if(StringUtils.isEmpty(p.getGeneralCategorySn())){
                productCategoryService.save(p);
                successNum++;
            }
        }
        if (successNum > 0) {
            baseResp.success();
            baseResp.setMsg("共成功生成:" + successNum + "个商品分类");
        }
        return baseResp;
    }
}
