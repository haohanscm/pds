package com.haohan.platform.service.sys.modules.xiaodian.service.retail;

import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.service.CrudService;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.dao.retail.GoodsDao;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryRules;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.GoodsGifts;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.SaleRules;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ServiceSelection;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryRulesService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.GoodsGiftsService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.SaleRulesService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.ServiceSelectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 商品管理Service
 * @author haohan
 * @version 2017-08-06
 */
@Service
@Transactional(readOnly = true)
public class GoodsService extends CrudService<GoodsDao, Goods> {

    @Autowired
    @Lazy(true)
    private GoodsPriceRuleService goodsPriceRuleService;
    @Autowired
    @Lazy(true)
    private SaleRulesService saleRulesService;
    @Autowired
    @Lazy(true)
    private DeliveryRulesService deliveryRulesService;
    @Autowired
    @Lazy(true)
    private GoodsGiftsService goodsGiftsService;
    @Autowired
    @Lazy(true)
    private ServiceSelectionService serviceSelectionService;
    @Autowired
    @Lazy(true)
    private GoodsModelService goodsModelService;
    @Autowired
    @Lazy(true)
    private GoodsModelTotalService goodsModelTotalService;

	public Goods get(String id) {
		return super.get(id);
	}

	public List<Goods> findList(Goods goods) {
		return super.findList(goods);
	}

    /**
     * 商品列表 带 shopName/merchantName/categoryName/categorySn/jisuappId/marketPrice/unit
     * @param page 分页对象
     * @param goods
     * @return
     */
	public Page<Goods> findPage(Page<Goods> page, Goods goods) {
        goods.setPage(page);
        page.setList(dao.findJoinList(goods));
        return page;
	}

    /**
     * 商品列表 带 shopName/merchantName/categoryName/categorySn/jisuappId/marketPrice/unit
     * @param goods
     * @return
     */
    public List<Goods> findJoinList(Goods goods) {
        return dao.findJoinList(goods);
    }

    // 查询商品列表 带规格属性
//    public List<GoodsInfoExt> findListWithModel(Goods goods){
//        List<Goods> goodsList = dao.findJoinList(goods);
//        List<GoodsInfoExt> extList = new ArrayList<>(goodsList.size());
//        ConvertUtils.register(new DateConverter(), Date.class);
//        for(Goods g:goodsList){
//            GoodsInfoExt goodsInfoExt = new GoodsInfoExt();
//            // 复制 goods
//            BeanUtils.copyProperties(g, goodsInfoExt);
//            // 获取商品规格  需goodsId
//            fetchGoodsModel(goodsInfoExt);
//            extList.add(goodsInfoExt);
//        }
//        return extList;
//    }

//    // 商品基础信息和modelList 带pds采购商收藏商品状态,采购价 用于pds
    public ApiRespPage findWithModelPds(Goods goods) {
        ApiRespPage respPage = new ApiRespPage();
        // 获取分页信息
        Page page = goods.getPage();
        if(null == page){
            page = new Page(1,30);
        }
        goods.setPage(null);
        // 总记录数
        int count = dao.findGoodsCount(goods);
        page.setCount(count);
        int pageStart = (page.getPageNo() - 1) * page.getPageSize();
        if(pageStart > count){
            pageStart = 0;
            page.setPageNo(1);
        }
        int pageSize = page.getPageSize();
        goods.setPageStart(pageStart);
        goods.setPageSize(pageSize);
        page.setList(dao.findWithModelPds(goods));
        respPage.fetchFromPage(page);
        return respPage;
    }

    // 分页 查询商品列表 带规格属性 goodsModelList
    public Page<GoodsInfoExt> findPageWithModel(Page page, Goods goods) {
        goods.setPage(null);
        // 总记录数
        int count = dao.findGoodsCount(goods);
        page.setCount(count);
        int pageStart = (page.getPageNo() - 1) * page.getPageSize();
        if(pageStart > count){
            pageStart = 0;
            page.setPageNo(1);
        }
        int pageSize = page.getPageSize();
        goods.setPageStart(pageStart);
        goods.setPageSize(pageSize);
        page.setList(dao.findWithModelPds(goods));
        return page;
    }

	@Transactional(readOnly = false)
	public void save(Goods goods) {
        // 生成商品唯一编号
        if(StringUtils.isEmpty(goods.getGoodsSn())){
            goods.setGoodsSn(genGoodsSn());
        }
        // 处理上下架isMarketable与商品状态goodsStatus  默认出售中
        String goodsStatus = changeGoodsStatus(goods);
        goods.setGoodsStatus(goodsStatus);
		super.save(goods);
	}

    /**
     * 删除商品的所有信息
     * @param goods  根据 goodsId
     */
	@Transactional(readOnly = false)
	public void deleteGoodsInfoExt(Goods goods) {
	    if(null == goods || StringUtils.isEmpty(goods.getId())){
	        return;
        }
		super.delete(goods);
		// 同时删除定价规则
        goodsPriceRuleService.deleteGoodsId(goods.getId());
        // 删除规格
        goodsModelService.deleteByGoodsId(goods.getId());
        goodsModelTotalService.deleteByGoodsId(goods.getId());
        // 删除服务选项
//        serviceSelectionService.deleteByGoodsId(goods.getId());
        // 删除配送规则
//        deliveryRulesService.deleteByGoodsId(goods.getId());
        // 删除售卖规则
//        saleRulesService.deleteByGoodsId(goods.getId());
        // 删除赠品
//        goodsGiftsService.deleteByGoodsId(goods.getId());
	}

    /**
     * 根据商品分类id 删除子分类拥有的商品 删除扩展信息
     * @param categoryId  商品分类id
     */
	@Transactional(readOnly = false)
	public void deleteForCategory(String categoryId) {
	    Goods goods = new Goods();
	    goods.setGoodsCategoryId(categoryId);
		List<Goods> goodsList = super.findList(goods);
		for(Goods g:goodsList){
            deleteGoodsInfoExt(g);
        }
	}


//    @Transactional(readOnly = false)
//    public int changeStorage(String goodsId, int addNum) {
//        Goods goods = dao.get(goodsId);
//        goods.setStorage(goods.getStorage() + addNum);
//        return dao.modifyStorage(goods);
//    }

    /**
     * 找到商品列表  根据多个商品编号sns
     * @param sns  多个商品编号sn  通过逗号连接
     * @return
     */
    public List<Goods> findByGoodsSns(String sns) {
        if (StringUtils.isEmpty(sns)) {
            return new ArrayList<>();
        }
        Goods goods = new Goods();
        goods.setGoodsSn(sns);
        return dao.findJoinList(goods);
    }

    /**
     * 找到商品  带 shopName/merchantName/categoryName/categorySn/jisuappId/marketPrice/unit
     * @param goodsSn 商品编号
     * @return
     */
    public Goods findByGoodsSn(String goodsSn) {
        List<Goods> list = findByGoodsSns(goodsSn);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

    public Goods fetchByJsGoodsId(String jsGoodsId) {
        Goods goods = new Goods();
        goods.setThirdGoodsSn(jsGoodsId);
        List<Goods> list = dao.findList(goods);
        return Collections3.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 找到商品列表  根据多个商品第三方编号 thirdGoodsSns (即速)
     * @param thirdGoodsSns  多个商品第三方编号sn  通过逗号连接
     * @return
     */
    public List<Goods> findByThirdGoodsSns(String thirdGoodsSns) {
        if (StringUtils.isEmpty(thirdGoodsSns)) {
            return new ArrayList<>();
        }
        Goods goods = new Goods();
        goods.setThirdGoodsSn(thirdGoodsSns);
        return dao.findJoinList(goods);
    }

	// 商品id、名称键值对
	public Map<String,String> fetchNameMap(Goods goods){
		return Collections3.extractToMap(findList(goods),"id","goodsName");
	}

    /**
     * 根据 goodsId
     * 获取商品的规格详情/规格名称信息  goodsModelList/modelInfo
     * 属性会存入 goodsInfoExt
     * @param goodsInfoExt  必需goodsId/ 规格标识 /
     * @return
     */
    public GoodsInfoExt fetchGoodsModel(GoodsInfoExt goodsInfoExt) {
        String goodsId = goodsInfoExt.getId();
        if(StringUtils.isEmpty(goodsId)){
            return goodsInfoExt;
        }
        // 规格明细
        GoodsModel model = new GoodsModel();
        model.setGoodsId(goodsId);
        List<GoodsModel> goodsModelList = goodsModelService.findList(model);
        // 规格信息
        GoodsModelTotal modelInfo = new GoodsModelTotal();
        modelInfo.setGoodsId(goodsId);
        List<GoodsModelTotal> infoList = goodsModelTotalService.findList(modelInfo);
        // 规格名称 格式转换
        goodsInfoExt.setInfoList(infoList);
        goodsInfoExt.transToModelInfo();
        // 存入 规格详情
        goodsInfoExt.setGoodsModelList(goodsModelList);
        return goodsInfoExt;
    }

    /**
     * 获取商品详情(包含扩展信息)
     * 价格/售卖规则/配送规则/赠品  都是一一对应
     * 服务选项/商品规格  一对多
     * @param goods  商品信息  需带 分类名称及分类sn
     * @return null 或 goodsInfoExt
     */
    public GoodsInfoExt getGoodsInfoExt(Goods goods) {
        if (null == goods || StringUtils.isEmpty(goods.getId())) {
            return null;
        }
        String goodsId = goods.getId();
        GoodsInfoExt goodsInfoExt = new GoodsInfoExt();
//        ConvertUtils.register(new DateConverter(), Date.class);
        // 获取售卖规则
        if (StringUtils.equals(goods.getSaleRule(), ICommonConstant.YesNoType.yes.getCode())) {
            SaleRules saleRules = saleRulesService.findByGoodsId(goodsId);
            if (null != saleRules) {
                BeanUtils.copyProperties(saleRules, goodsInfoExt);
            }
        }
        // 配送规则
        if (StringUtils.equals(goods.getDeliveryRule(), ICommonConstant.YesNoType.yes.getCode())) {
            DeliveryRules deliveryRules = deliveryRulesService.fetchByGoodsId(goodsId);
            if (null != deliveryRules) {
                BeanUtils.copyProperties(deliveryRules, goodsInfoExt);
            }
        }
        // 赠品
        if (StringUtils.equals(goods.getGoodsGift(), ICommonConstant.YesNoType.yes.getCode())) {
            GoodsGifts goodsGifts = goodsGiftsService.findByGoodsId(goodsId);
            if (null != goodsGifts) {
                BeanUtils.copyProperties(goodsGifts, goodsInfoExt);
            }
        }
        // 服务选项
        if (StringUtils.equals(goods.getServiceSelection(), ICommonConstant.YesNoType.yes.getCode())) {
            ServiceSelection selection = new ServiceSelection();
            selection.setGoodsId(goodsId);
            List<ServiceSelection> serviceSelectionList = serviceSelectionService.findList(selection);
            goodsInfoExt.setServiceSelectionList(serviceSelectionList);
        }
        // 复制 good
        BeanUtils.copyProperties(goods, goodsInfoExt);
        // 获取价格
        GoodsPriceRule goodsPriceRule = goodsPriceRuleService.fetchByGoodsId(goodsId);
        goodsInfoExt.setMarketPrice(goodsPriceRule.getMarketPrice());
        goodsInfoExt.setVirtualPrice(goodsPriceRule.getVirtualPrice());
        goodsInfoExt.setVipPrice(goodsPriceRule.getVipPrice());
        goodsInfoExt.setUnit(goodsPriceRule.getUnit());
        // 获取商品规格  需goodsId
        fetchGoodsModel(goodsInfoExt);
        return goodsInfoExt;
    }

    /**
     * 获取商品信息及扩展信息 带分类名称和分类sn
     * @param id   商品id
     * @return
     */
    public GoodsInfoExt getGoodsInfoExt(String id) {
        return getGoodsInfoExt(getExt(id));
    }

    /**
     * 获取商品信息及扩展信息 带分类名称和分类sn
     * @param goodsSn  商品编号
     * @return
     */
    public GoodsInfoExt getGoodsInfoBySn(String goodsSn) {
        return getGoodsInfoExt(getExtBySn(goodsSn));
    }

    /**
     * 获取商品信息  带分类名称和分类sn
     * @param id  商品id
     * @return
     */
    public Goods getExt(String id){
        if(StringUtils.isEmpty(id)){
            return null;
        }
        Goods goods = new Goods(id);
        return dao.getExt(goods);
    }

    /**
     * 获取商品信息  带分类名称和分类sn
     * @param goodsSn  商品编号
     * @return
     */
    public Goods getExtBySn(String goodsSn){
        if(StringUtils.isEmpty(goodsSn)){
            return null;
        }
        Goods goods = new Goods();
        goods.setGoodsSn(goodsSn);
        return dao.getExt(goods);
    }

    /**
     * 商品扩展信息 保存 包括所有扩展信息
     * @param goodsInfoExt
     */
    @Transactional(readOnly = false)
    public void saveGoods(GoodsInfoExt goodsInfoExt){
        // 保存所有扩展项
        saveGoods(goodsInfoExt, "all");
    }

    /**
     * 商品扩展信息 保存  可选择保存的范围
     * @param saveType   all:全部保存   base:基础项(价格/规格)  ext:扩展项
      */
    @Transactional(readOnly = false)
    public void saveGoods(GoodsInfoExt goodsInfoExt, String saveType){
        Goods goods = new Goods();
        // 复制出零售商品属性
        BeanUtils.copyProperties(goodsInfoExt, goods);
        // 默认 启用规格
        String modelUse = goodsInfoExt.getGoodsModel();
        modelUse = (StringUtils.isEmpty(modelUse)) ? ICommonConstant.YesNoType.yes.getCode(): modelUse;
        goods.setGoodsModel(modelUse);
        // 生成商品唯一编号
        if(StringUtils.isEmpty(goodsInfoExt.getGoodsSn())){
            goods.setGoodsSn(genGoodsSn());
            // 源扩展商品保存goodsSn
            goodsInfoExt.setGoodsSn(goods.getGoodsSn());
        }
        // 处理上下架isMarketable与商品状态goodsStatus  默认出售中
        String goodsStatus = changeGoodsStatus(goods);
        goods.setGoodsStatus(goodsStatus);
        super.save(goods);
        // 新增时 id
        goodsInfoExt.setId(goods.getId());
        // 保存 基础项  价格/规格
        if(StringUtils.equalsAny(saveType,"all", "base")){
            // 价格 是否已有
            GoodsPriceRule goodsPriceRule = goodsPriceRuleService.fetchByGoodsId(goods.getId());
            if(null == goodsPriceRule){
                goodsPriceRule = new GoodsPriceRule();
            }
            goodsInfoExt.transfGoodsPriceRule(goodsPriceRule);
            goodsPriceRuleService.save(goodsPriceRule);
            // 商品规格 是否启用
            // 判断 转换并保存规格
            saveGoodsModel(goodsInfoExt);
        }
        // 保存 扩展项 售卖规则/配送规则/赠品/服务选项
        if(StringUtils.equalsAny(saveType,"all", "ext")){
            // 售卖规则 是否启用
            if(StringUtils.equals(goodsInfoExt.getSaleRule(), ICommonConstant.YesNoType.yes.getCode())){
                SaleRules saleRules = saleRulesService.findByGoodsId(goods.getId());
                if(null == saleRules){
                    saleRules = new SaleRules();
                }
                goodsInfoExt.transfSaleRule(saleRules);
                saleRulesService.save(saleRules);
            }
            // 配送规则 是否启用
            if(StringUtils.equals(goodsInfoExt.getDeliveryRule(), ICommonConstant.YesNoType.yes.getCode())){
                DeliveryRules deliveryRules = deliveryRulesService.fetchByGoodsId(goods.getId());
                if(null == deliveryRules){
                    deliveryRules = new DeliveryRules();
                }
                goodsInfoExt.transfDeliveryRules(deliveryRules);
                deliveryRulesService.save(deliveryRules);
            }
            // 赠品 是否启用
            if(StringUtils.equals(goodsInfoExt.getGoodsGift(), ICommonConstant.YesNoType.yes.getCode())){
                GoodsGifts goodsGifts = goodsGiftsService.findByGoodsId(goods.getId());
                if(null == goodsGifts){
                    goodsGifts = new GoodsGifts();
                }
                goodsInfoExt.transfGoodsGifts(goodsGifts);
                goodsGiftsService.save(goodsGifts);
            }
            // 判断 是否启用  并保存 服务选项
            saveServiceSelection(goodsInfoExt);
        }

    }

    /**
     * 保存服务选项
     * 服务选项须存在
     * @param goodsInfoExt
     */
    @Transactional(readOnly = false)
    public void saveServiceSelection(GoodsInfoExt goodsInfoExt) {
        if(StringUtils.equals(goodsInfoExt.getServiceSelection(), ICommonConstant.YesNoType.yes.getCode()) && !Collections3.isEmpty(goodsInfoExt.getServiceSelectionList())){
            List<ServiceSelection> serviceSelectionList = serviceSelectionService.findByGoodsId(goodsInfoExt.getId());
            // 当前保存的服务选项
            List<ServiceSelection>  saveList = new ArrayList<>();
            for (ServiceSelection s: goodsInfoExt.getServiceSelectionList()){
                // 无效项 剔除
//                if(null == s.getId()){
//                    continue;
//                }
                s.setGoodsId(goodsInfoExt.getId());
                s.setMerchantId(goodsInfoExt.getMerchantId());
                saveList.add(s);
            }
            if(!Collections3.isEmpty(serviceSelectionList)){
                // 原有的服务选项
                HashMap<String, ServiceSelection> idMap = new HashMap<>();
                for (ServiceSelection s:serviceSelectionList){
                    idMap.put(s.getId(), s);
                }
                for (ServiceSelection s: saveList){
                    if(idMap.containsKey(s.getId())){
                        idMap.remove(s.getId());
                    }else {
                        // 新增项
                        s.setId(null);
                    }
                }
                if(!idMap.isEmpty()){
                    for(ServiceSelection s:idMap.values()){
                        serviceSelectionService.delete(s);
                    }
                }
            }
            // 保存
            for (ServiceSelection s:saveList){
                serviceSelectionService.save(s);
            }
        }
    }

    /**
     * 保存规格
     * 判断商品规格是否启用
     * 未启用时生成默认规格  goodsModelSn  sku
     * @param goodsInfoExt  必需存在goodsSn  goodsId
     */
    @Transactional(readOnly = false)
    public void saveGoodsModel(GoodsInfoExt goodsInfoExt) {
        String goodsId = goodsInfoExt.getId();
        List<GoodsModel> goodsModelList = goodsModelService.findByGoodsIdWithDel(goodsId);
        // 当前保存的规格
        List<GoodsModel> saveList = new ArrayList<>();
        // 规格信息  数据库中已有的
        List<GoodsModelTotal> modelInfoList = goodsModelTotalService.findByGoodsId(goodsId);
        // 当前保存的规格信息
        List<GoodsModelTotal> saveInfoList = new ArrayList<>();
        // goodsModelSn 后缀数字 的最大值
        int maxNumber = 0;
        // 启用规格
        boolean usable = StringUtils.equals(goodsInfoExt.getGoodsModel(), ICommonConstant.YesNoType.yes.getCode());
        // 规格信息有效时(属性存在规格列表和规格信息)
        boolean right = !Collections3.isEmpty(goodsInfoExt.getGoodsModelList()) && (!Collections3.isEmpty(goodsInfoExt.getInfoList()) || !(null == goodsInfoExt.getModelInfo() || goodsInfoExt.getModelInfo().size() == 0));
        if (usable && right) {
            // 原有的 规格
            HashMap<String, GoodsModel> idMap = new HashMap<>(8);
            for (GoodsModel g : goodsModelList) {
                idMap.put(g.getId(), g);
                // 获取已存在的goodsModelSn 后缀数字 的最大值
                int temp = fetchModelSnNum(g.getGoodsModelSn());
                maxNumber = Math.max(temp, maxNumber);
            }
            // 当前需保存的规格
            for (GoodsModel g : goodsInfoExt.getGoodsModelList()) {
                // 无效项 剔除
                if (null == g.getModelPrice()) {
                    continue;
                }
                g.setGoodsId(goodsId);
                saveList.add(g);
                GoodsModel exist = idMap.get(g.getId());
                if (null != exist) {
                    g.setGoodsModelSn(exist.getGoodsModelSn());
                    g.setThirdModelSn(exist.getThirdModelSn());
                    idMap.remove(g.getId());
                }
            }
            // 商品规格处理,删除不使用项,并清除goodsModelSn
            Iterator ids = idMap.values().iterator();
            // 筛选 新增项和删除项
            for (GoodsModel g : saveList) {
                if (StringUtils.isEmpty(g.getId())) {
                    if (ids.hasNext()) {
                        GoodsModel exist = (GoodsModel) ids.next();
                        g.setId(exist.getId());
                        g.setGoodsModelSn(exist.getGoodsModelSn());
                        g.setThirdModelSn(exist.getThirdModelSn());
                        ids.remove();
                    } else {
                        // 新增项
                        g.setId(null);
                        g.setGoodsModelSn("");
                        g.setThirdModelSn("");
                    }
                }
            }
            // 删除项
            if (!idMap.isEmpty()) {
                for (GoodsModel g : idMap.values()) {
                    if(StringUtils.equals(GoodsModel.DEL_FLAG_DELETE, g.getDelFlag())){
                        continue;
                    }
                    g.setGoodsModelSn("");
                    g.setThirdModelSn("");
                    goodsModelService.delete(g);
                }
            }
            try {
                // 优先从infoList中获取 规格类型信息
                if (!Collections3.isEmpty(goodsInfoExt.getInfoList())) {
                    for (GoodsModelTotal gt : goodsInfoExt.getInfoList()) {
                        // 无效项 剔除
                        if (StringUtils.isAnyEmpty(gt.getModelId(), gt.getModelName(),
                                gt.getSubModelId(), gt.getSubModelName())) {
                            continue;
                        }
                        gt.setGoodsId(goodsId);
                        saveInfoList.add(gt);
                    }
                } else {
                    for (JisuModelInfo j : goodsInfoExt.getModelInfo().values()) {
                        // 无效项 剔除
                        int subModelIdSize = j.getSubModelId().size();
                        if (StringUtils.isEmpty(j.getId()) || Collections3.isEmpty(j.getSubModelId())
                                || Collections3.isEmpty(j.getSubModelName()) || subModelIdSize > j.getSubModelName().size()) {
                            continue;
                        }
                        for (int i = 0; i < subModelIdSize; i++) {
                            GoodsModelTotal modelInfo = new GoodsModelTotal();
                            modelInfo.setGoodsId(goodsId);
                            modelInfo.setModelId(j.getId());
                            modelInfo.setModelName(j.getName());
                            modelInfo.setSubModelId(j.getSubModelId().get(i));
                            modelInfo.setSubModelName(j.getSubModelName().get(i));
                            saveInfoList.add(modelInfo);
                        }
                    }
                }
                // 原有的 规格信息  使用id标识是否新增
                HashMap<String, GoodsModelTotal> totalIdMap = new HashMap<>(8);
                for (GoodsModelTotal g : modelInfoList) {
                    totalIdMap.put(g.getId(), g);
                }
                for (GoodsModelTotal g : saveInfoList) {
                    // id 相同项为修改
                    if (totalIdMap.containsKey(g.getId())) {
                        // 获取 id 用于修改
                        totalIdMap.remove(g.getId());
                    } else {
                        // 新增项
                        g.setId(null);
                    }
                }
                if (!totalIdMap.isEmpty()) {
                    for (GoodsModelTotal g : totalIdMap.values()) {
                        goodsModelTotalService.delete(g);
                    }
                }
            } catch (Exception e) {
            }
        } else {
            // 未启用规格 默认生成goodsModel
            GoodsModel defaultModel;
            if (!Collections3.isEmpty(goodsInfoExt.getGoodsModelList())) {
                defaultModel = goodsInfoExt.getGoodsModelList().get(0);
            } else {
                defaultModel = fetchDefaultModel(goodsInfoExt);
            }
            defaultModel.setGoodsId(goodsId);
            // 修改时
            if (!Collections3.isEmpty(goodsModelList)) {
                defaultModel.setId(goodsModelList.get(0).getId());
                defaultModel.setGoodsModelSn(goodsModelList.get(0).getGoodsModelSn());
                // 删除多余的项
                for (int i = 0; i < goodsModelList.size(); i++) {
                    if (i != 0) {
                        goodsModelList.get(i).setGoodsModelSn("");
                        goodsModelService.delete(goodsModelList.get(i));
                    }
                }
            }
            saveList.add(defaultModel);

            GoodsModelTotal defaultTotal = null;
            if (!Collections3.isEmpty(goodsInfoExt.getInfoList())) {
                defaultTotal = checkModelTotal(goodsInfoExt.getInfoList().get(0));
            }
            if (null == defaultTotal) {
                defaultTotal = fetchDefaultModelTotal(goodsInfoExt);
            }
            defaultTotal.setGoodsId(goodsId);
            // 修改时
            if (!Collections3.isEmpty(modelInfoList)) {
                defaultTotal.setId(modelInfoList.get(0).getId());
                // 删除多余的项
                for (int i = 0; i < modelInfoList.size(); i++) {
                    if (i != 0) {
                        goodsModelTotalService.delete(modelInfoList.get(i));
                    }
                }
            }
            saveInfoList.add(defaultTotal);
        }
        // 保存
        for (GoodsModelTotal g : saveInfoList) {
            goodsModelTotalService.save(g);
        }
        // 保存 规格
        for (GoodsModel g : saveList) {
            // 生成goodsModelSn maxNumber自增
            if (StringUtils.isEmpty(g.getGoodsModelSn())) {
                g.setGoodsModelSn(genGoodsModelSn(goodsInfoExt.getGoodsSn(), ++maxNumber));
            }
            goodsModelService.save(g);
        }
        // 规格信息更新
        goodsInfoExt.setInfoList(saveInfoList);
        goodsInfoExt.setGoodsModelList(saveList);
    }

    private GoodsModelTotal checkModelTotal(GoodsModelTotal total) {
        if(StringUtils.isAnyBlank(total.getGoodsId(), total.getModelId(), total.getModelName(), total.getSubModelId(), total.getSubModelName())){
            return null;
        }
        return total;
    }

    // 通过goodsInfoExt的信息 生成默认的商品规格 GoodsModelType  种类:普通
    private GoodsModel fetchDefaultModel(GoodsInfoExt goodsInfoExt) {
        GoodsModel defaultModel = new GoodsModel();
        defaultModel.setGoodsId(goodsInfoExt.getId());
        defaultModel.setModelCode(goodsInfoExt.getScanCode());
        defaultModel.setModelName("普通");
        defaultModel.setModel("30");
        defaultModel.setModelPrice(goodsInfoExt.getMarketPrice());
        defaultModel.setVirtualPrice(goodsInfoExt.getVirtualPrice());
        defaultModel.setCostPrice(goodsInfoExt.getMarketPrice());
        defaultModel.setModelStorage(goodsInfoExt.getStorage());
        defaultModel.setModelUnit(goodsInfoExt.getUnit());
        defaultModel.setModelUrl(goodsInfoExt.getThumbUrl());
        return defaultModel;
    }

    // 生成默认规格名称信息(GoodsModelTotal)
    private GoodsModelTotal fetchDefaultModelTotal(GoodsInfoExt goodsInfoExt){
        GoodsModelTotal goodsModelTotal = new GoodsModelTotal();
        goodsModelTotal.setGoodsId(goodsInfoExt.getId());
        goodsModelTotal.setModelId(IGoodsConstant.GoodsModelType.type.getCode());
        goodsModelTotal.setModelName(IGoodsConstant.GoodsModelType.type.getDesc());
        goodsModelTotal.setSubModelId("30");
        goodsModelTotal.setSubModelName("普通");
        return goodsModelTotal;
    }

    /**
     * 根据merchantId 和 多个商品sn 批量删除商品
     * @param goods  merchantId/多个商品sn 逗号连接
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteByGoodsSn(Goods goods) {
        return dao.deleteByGoodsSn(goods);
    }

    // 生成 goodsSn
    private String genGoodsSn() {
        // 缓存中初始值处理
        int startNum = IGoodsConstant.GOODS_ID_START;
        // 缓存中不存在时 查询数据库中的值
        if (null == JedisUtils.get(IGoodsConstant.GOODS_ID_INCR_KEY)) {
            try {
                Goods goods = new Goods();
                goods.setGoodsSn(IGoodsConstant.GOODS_ID_PREFIX);
                List<Goods> list = dao.findGoodsSnList(goods);
                if (!Collections3.isEmpty(list)) {
                    goods = list.get(0);
                    String number = StringUtils.substringAfter(goods.getGoodsSn(), IGoodsConstant.GOODS_ID_PREFIX);
                    if (StringUtils.isNotEmpty(number)) {
                        int num = StringUtils.toInteger(number);
                        startNum = (num > startNum) ? num : startNum;
                    }
                }
            } catch (Exception e) {
            }
        }
        Long num = JedisUtils.fetchIncrNum(IGoodsConstant.GOODS_ID_INCR_KEY, startNum);
        String result = IGoodsConstant.GOODS_ID_PREFIX + num;
        return result;
    }

    // 拼接 goodsModelSn
    private String genGoodsModelSn(String goodsSn, int num){
        return goodsSn + IGoodsConstant.GOODS_MODEL_INFIX + num;
    }

    // 获取  goodsModelSn后缀拼接的数字  错误时返回0
    private int fetchModelSnNum(String goodsModelSn){
        String num = StringUtils.substringAfterLast(goodsModelSn, IGoodsConstant.GOODS_MODEL_INFIX);
        return StringUtils.toInteger(num);
    }

    /**
     * 根据商品上下架状态 和 库存 改变 商品状态goodsStatus
     * @param goods
     * @return
     */
    private String changeGoodsStatus(Goods goods){
        // 默认 出售中
        String goodsStatus = IGoodsConstant.GoodsStatus.sale.getCode();
        // 上下架状态为0时,商品状态为仓库中
        if (StringUtils.equals(ICommonConstant.YesNoType.no.getCode(), StringUtils.toString(goods.getIsMarketable(), ICommonConstant.YesNoType.yes.getCode()))) {
            goodsStatus = IGoodsConstant.GoodsStatus.order.getCode();
        } else if (Integer.valueOf(0).equals(goods.getStorage())) {
            // 出售中 库存为0时 变为已售罄
            goodsStatus = IGoodsConstant.GoodsStatus.takeout.getCode();
        }
        return goodsStatus;
    }

    /**
     * 获取商品列表  带基础信息
     * 关联 xd_goods_price_rule  xd_goods_model  xd_goods_model_total
     * 查询必需带条件 (任一个,商品过多时,查询慢):
     * shopId / goodsName / goodsCategoryId / goodsSn / thirdGoodsSn
     * @param goods
     * @return
     */
    public List<GoodsInfoExt> findListWithBase(Goods goods) {
        return dao.findListWithBase(goods);
    }
}