package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.database;


import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.ApiRespPage;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.database.*;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.database.GoodsDatebaseService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.ProductCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StandardProductUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.database.StockKeepingUnit;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共商品库 商品管理
 * Created by dy on 2018/10/17.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/database")
public class GoodsDatabaseCtrl extends BaseController {

    @Autowired
    private GoodsDatebaseService goodsDatabaseService;

    /**
     * 标准商品 spu 列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "spu/fetchGoodsList")
    @ResponseBody
    public String fetchStandardGoodsList(ReqStandardProduct reqStandardProduct, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        // 请求参数
        StandardProductUnit standardProductUnit = new StandardProductUnit();
        standardProductUnit.setGoodsCategoryId(reqStandardProduct.getGoodsCategoryId());
        standardProductUnit.setBrand(reqStandardProduct.getBrand());
        standardProductUnit.setIndustry(reqStandardProduct.getIndustry());
        standardProductUnit.setGoodsName(reqStandardProduct.getGoodsName());
        standardProductUnit.setGeneralSn(reqStandardProduct.getGeneralSn());

        // 参数验证
//            if(StringUtils.isAnyEmpty(param.getMerchantId(), param.getCountType())){
//                resp.setMsg("缺少countType");
//                return resp.toJson();
//            }
        // 分页查询
        int pageNo = StringUtils.toInteger(reqStandardProduct.getPageNo());
        int pageSize = StringUtils.toInteger(reqStandardProduct.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;

        ApiRespPage<StandardProductUnit> result = goodsDatabaseService.fetchStandardProductList(standardProductUnit, pageNo, pageSize);
        if (Collections3.isEmpty(result.getList())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 标准商品 spu 详情
     * 根据spuId查询
     * @param request
     * @return
     */
    @RequestMapping(value = "spu/fetchGoodsInfo")
    @ResponseBody
    public String fetchStandardGoods(ReqStandardProduct reqStandardProduct, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        // 参数验证
        if (StringUtils.isEmpty(reqStandardProduct.getGoodsId())) {
            resp.setMsg("缺少参数:goodsId");
            return resp.toJson();
        }
        RespSpu respSpu = goodsDatabaseService.getStandardGoods(reqStandardProduct.getGoodsId());
        if (null == respSpu) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(respSpu);
        }
        return resp.toJson();
    }

    /**
     * 库存商品 sku 列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "sku/fetchGoodsList")
    @ResponseBody
    public String fetchStockGoodsList(ReqStockProduct reqStockProduct, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        // 请求参数
        StockKeepingUnit stockKeepingUnit = new StockKeepingUnit();
        stockKeepingUnit.setSpuId(reqStockProduct.getSpuId());
        stockKeepingUnit.setStockGoodsSn(reqStockProduct.getStockGoodsSn());
        stockKeepingUnit.setGoodsName(reqStockProduct.getGoodsName());
        stockKeepingUnit.setScanCode(reqStockProduct.getScanCode());
        // 参数验证
//            if(StringUtils.isAnyEmpty(param.getMerchantId(), param.getCountType())){
//                resp.setMsg("缺少countType");
//                return resp.toJson();
//            }
        // 分页查询
        int pageNo = StringUtils.toInteger(reqStockProduct.getPageNo());
        int pageSize = StringUtils.toInteger(reqStockProduct.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;

        ApiRespPage<StockKeepingUnit> result = goodsDatabaseService.fetchStockGoodsList(stockKeepingUnit, pageNo, pageSize);
        if (Collections3.isEmpty(result.getList())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 标准商品 spu 详情
     * 根据spuId查询
     * @param request
     * @return
     */
    @RequestMapping(value = "sku/fetchGoodsInfo")
    @ResponseBody
    public String fetchStockGoods(ReqStockProduct reqStockProduct, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        // 参数验证
        if (StringUtils.isEmpty(reqStockProduct.getStockGoodsId())) {
            resp.setMsg("缺少参数:goodsId");
            return resp.toJson();
        }
        RespSku respSku = goodsDatabaseService.getStockGoods(reqStockProduct.getStockGoodsId());
        if (null == respSku) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(respSku);
        }
        return resp.toJson();
    }

    /**
     * 根据公共商品库 spu复制生成零售商品
     * @param reqStandardProduct  必需goodsId
     * @param request
     * @return
     */
    @RequestMapping(value = "method/copyToRetailGoods")
    @ResponseBody
    public String copyToRetailGoods(ReqStandardProduct reqStandardProduct, HttpServletRequest request){
        BaseResp resp = BaseResp.newError();
        // 参数验证
        if (StringUtils.isEmpty(reqStandardProduct.getGoodsId())) {
            resp.setMsg("缺少参数:goodsId");
            return resp.toJson();
        }
        GoodsInfoExt goodsInfoExt = goodsDatabaseService.copyToRetailGoods(reqStandardProduct.getGoodsId());
        if (null == goodsInfoExt) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(goodsInfoExt);
        }
        return resp.toJson();
    }

    /**
      * 商品分类列表
      *
      * @param request
      * @return
      */
    @RequestMapping(value = "category/fetchList")
    @ResponseBody
    public String fetchCategoryList(ReqProductCategory reqProductCategory, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        // 参数验证
//        if(StringUtils.isEmpty(reqGoods.getGoodsSn())){
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
        // 传入参数处理
        ProductCategory category = new ProductCategory();
        category.setName(reqProductCategory.getName());
        category.setParentId(reqProductCategory.getParentId());
        category.setParentIds(reqProductCategory.getForefatherId());
        ApiRespPage<ProductCategory> result = goodsDatabaseService.fetchCategoryList(category);
        if (null == result) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

//    /**
//     * 商品扩展信息编辑  售卖规则/赠品规则/配送规则/服务选项
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "goodsExtModify")
//    @ResponseBody
//    public String goodsExtModify(@RequestBody ReqGoods reqGoods, HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
////        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        // 参数验证
//        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
//        //  传入参数处理
//        GoodsInfoExt goods = new GoodsInfoExt();
//        reqGoods.transfExtGoods(goods);
////        goods.setMerchantId(merchantAuth.getMerchantId());
//        resp = merchantGoodsService.goodsExtModify(goods);
//
//        return resp.toJson();
//    }
//
//
//    /**
//     * 商品删除  批量
//     * 根据merchantId 和 多个商品sn 批量删除商品
//     *
//     * @return
//     */
//    @RequestMapping(value = "goodsDelete")
//    @ResponseBody
//    public String goodsDelete(ReqGoods reqGoods, HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        // 参数验证
//        if (Collections3.isEmpty(reqGoods.getGoodsSns())) {
//            resp.setMsg("缺少参数:商品编号");
//            return resp.toJson();
//        }
//        // 传入参数处理
//        Goods goods = new Goods();
//        goods.setMerchantId(merchantAuth.getMerchantId());
//        goods.setGoodsSn(StringUtils.join(reqGoods.getGoodsSns(), ","));
//        int i = merchantGoodsService.goodsDelete(goods);
//        resp.success();
//        if (i == 0) {
//            resp.setExt("删除失败");
//        } else {
//            resp.setExt("成功删除" + i + "条数据");
//        }
//        return resp.toJson();
//    }
//
//
//    /**
//     * 商品上下架  批量
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "goodsStatusModify")
//    @ResponseBody
//    public String goodsStatusModify(ReqGoods reqGoods, HttpServletRequest request) {
//        BaseResp resp = BaseResp.newError();
////        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        // 参数验证
//        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
//        if(null == reqGoods.getIsMarketable()){
//            resp.setMsg("缺少参数:isMarketable");
//            return resp.toJson();
//        }else if(!reqGoods.getIsMarketable().equals(Integer.valueOf(CommonConstant.YesNoType.no.getCode()))){
//            // 不是选下架 的都为 上架
//            reqGoods.setIsMarketable(Integer.valueOf(CommonConstant.YesNoType.yes.getCode()));
//        }
//        // 传入参数处理
//        Goods goods = new Goods();
//        goods.setGoodsSn(reqGoods.getGoodsSn());
//        goods.setIsMarketable(reqGoods.getIsMarketable());
//        boolean flag = merchantGoodsService.goodsStatusModify(goods);
//        resp.success();
//        if (flag) {
//            resp.setExt("修改成功");
//        } else {
//            resp.setExt("修改失败");
//        }
//        return resp.toJson();
//    }
//
//
//    /**
//     * 商品权重  排序修改
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "goodsSortModify")
//    @ResponseBody
//    public String goodsSortModify(ReqGoods reqGoods, HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
////        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        // 参数验证
//        if (StringUtils.isAnyEmpty(reqGoods.getGoodsSn(), reqGoods.getSort())) {
//            resp.setMsg("缺少参数:goodsSn/sort");
//            return resp.toJson();
//        }
//        // 传入参数处理
//        Goods goods = new Goods();
//        goods.setGoodsSn(reqGoods.getGoodsSn());
//        goods.setSort(reqGoods.getSort());
//        boolean flag = merchantGoodsService.goodsSortModify(goods);
//        resp.success();
//        if (flag) {
//            resp.setExt("修改成功");
//        } else {
//            resp.setExt("修改失败");
//        }
//        return resp.toJson();
//    }
//
//    /**
//     * 商品库存  修改
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "goodsStorageModify")
//    @ResponseBody
//    public String goodsStorageModify(ReqGoods reqGoods, HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
////        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        // 参数验证
//        if (StringUtils.isAnyEmpty(reqGoods.getGoodsSn(), StringUtils.toString(reqGoods.getStorage(),""))) {
//            resp.setMsg("缺少参数:goodsSn/storage");
//            return resp.toJson();
//        }
//        // 传入参数处理
//        Goods goods = new Goods();
//        goods.setGoodsSn(reqGoods.getGoodsSn());
//        goods.setStorage(reqGoods.getStorage());
//        boolean flag = merchantGoodsService.goodsStorageModify(goods);
//        resp.success();
//        if (flag) {
//            resp.setExt("修改成功");
//        } else {
//            resp.setExt("修改失败");
//        }
//        return resp.toJson();
//    }
//
//
//
//    /**
//     * 商品分类编辑
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "goodsCategoryModify")
//    @ResponseBody
//    public String goodsCategoryModify(ReqGoodsCategory reqGoodsCategory, HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        // 参数验证
//        if (StringUtils.isAnyEmpty(reqGoodsCategory.getName(), reqGoodsCategory.getParentId(), reqGoodsCategory.getShopId())) {
//            resp.setMsg("缺少参数:parentId/name/shopId");
//            return resp.toJson();
//        }
//        //  传入参数处理
//        GoodsCategory category= new GoodsCategory();
//        reqGoodsCategory.transfGoodsCategory(category);
//        category.setMerchantId(merchantAuth.getMerchantId());
//        resp = merchantGoodsService.goodsCategoryModify(category);
//
//        return resp.toJson();
//    }
//
//    /**
//     * 商品分类删除  批量
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "goodsCategoryDelete")
//    @ResponseBody
//    public String goodsCategoryDelete(ReqGoodsCategory reqGoodsCategory, HttpServletRequest request) {
//
//        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        // 参数验证
//        if (Collections3.isEmpty(reqGoodsCategory.getCategoryIds())) {
//            resp.setMsg("缺少参数:商品分类编号");
//            return resp.toJson();
//        }
//        // 传入参数处理
//        GoodsCategory goodsCategory = new GoodsCategory();
//        goodsCategory.setMerchantId(merchantAuth.getMerchantId());
//        goodsCategory.setId(StringUtils.join(reqGoodsCategory.getCategoryIds(), ","));
//        int i = merchantGoodsService.categoryDelete(goodsCategory);
//        resp.success();
//        if (i == 0) {
//            resp.setExt("删除失败");
//        } else {
//            resp.setExt("成功删除" + i + "条数据");
//        }
//        return resp.toJson();
//    }

}