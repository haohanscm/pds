package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.goods;


import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.MerchantAuth;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.ReqGoods;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.RespPage;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.delivery.ReqGoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.merchant.goods.MerchantGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 商家 商品管理
 * Created by dy on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/v2.0/goods")
public class MerchantGoodsCtrl extends BaseController {

    @Autowired
    private MerchantGoodsService merchantGoodsService;

    /**
     * 商品列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchGoodsList")
    @ResponseBody
    public String fetchGoodsList(ReqGoods reqGoods, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
//        Map<String,Object> paramMap = getRequestParameters(request);
        String merchantId = merchantAuth.getMerchantId();
        // 请求参数
        Goods goods = new Goods();
        goods.setMerchantId(merchantId);
        goods.setShopId(reqGoods.getShopId());
        goods.setGoodsCategoryId(reqGoods.getCategoryId());
        goods.setGoodsStatus(reqGoods.getGoodsStatus());
        goods.setGoodsName(reqGoods.getGoodsName());
        goods.setGoodsSn(reqGoods.getGoodsSn());

        // 参数验证
//            if(StringUtils.isAnyEmpty(param.getMerchantId(), param.getCountType())){
//                resp.setMsg("缺少countType");
//                return resp.toJson();
//            }
        // 分页查询
        int pageNo = StringUtils.toInteger(reqGoods.getPageNo());
        int pageSize = StringUtils.toInteger(reqGoods.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 10 ? 10 : pageSize;

        RespPage result = merchantGoodsService.fetchGoodsList(goods, pageNo, pageSize);
        if (Collections3.isEmpty(result.getList())) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }

    /**
     * 商品详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchGoodsInfo")
    @ResponseBody
    public String fetchGoodsInfo(ReqGoods reqGoods, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
            resp.setMsg("缺少参数:goodsSn");
            return resp.toJson();
        }
        GoodsInfoExt goodsInfoExt = merchantGoodsService.getGoodsInfoExt(reqGoods.getGoodsSn());
        if (null == goodsInfoExt) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(goodsInfoExt);
        }
        return resp.toJson();
    }


    /**
     * 商品基础信息编辑   名称/价格/图片
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsBaseModify")
    @ResponseBody
    public String goodsBaseModify(@RequestBody ReqGoods reqGoods, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证  必需 名称/图片/价格/库存/描述
        if (StringUtils.isAnyEmpty(reqGoods.getShopId(), reqGoods.getGoodsName(), reqGoods.getThumbUrl(), reqGoods.getDetailDesc())) {
            resp.setMsg("缺少参数:shopId/goodsName/thumbUrl/detailDesc");
            return resp.toJson();
        }
        if (null == reqGoods.getMarketPrice() || reqGoods.getMarketPrice().compareTo(BigDecimal.ZERO) < 0) {
            resp.setMsg("价格错误");
        }
        // 库存 默认值 1
        Integer storage = (null == reqGoods.getStorage() || reqGoods.getStorage() < 1 )? 1:reqGoods.getStorage();
        reqGoods.setStorage(storage);
        // 排序  默认值 10
        reqGoods.setSort(StringUtils.defaultString(reqGoods.getSort(),"10"));
        // 传入参数处理
        GoodsInfoExt goods = new GoodsInfoExt();
        reqGoods.transfBaseGoods(goods);
        goods.setMerchantId(merchantAuth.getMerchantId());
        resp = merchantGoodsService.goodsBaseModify(goods);

        return resp.toJson();
    }


    /**
     * 商品扩展信息编辑  售卖规则/赠品规则/配送规则/服务选项
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsExtModify")
    @ResponseBody
    public String goodsExtModify(@RequestBody ReqGoods reqGoods, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
            resp.setMsg("缺少参数:goodsSn");
            return resp.toJson();
        }
        //  传入参数处理
        GoodsInfoExt goods = new GoodsInfoExt();
        reqGoods.transfExtGoods(goods);
//        goods.setMerchantId(merchantAuth.getMerchantId());
        resp = merchantGoodsService.goodsExtModify(goods);

        return resp.toJson();
    }


    /**
     * 商品删除  批量
     * 根据merchantId 和 多个商品sn 批量删除商品
     *
     * @return
     */
    @RequestMapping(value = "goodsDelete")
    @ResponseBody
    public String goodsDelete(ReqGoods reqGoods, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (Collections3.isEmpty(reqGoods.getGoodsSns())) {
            resp.setMsg("缺少参数:商品编号");
            return resp.toJson();
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setMerchantId(merchantAuth.getMerchantId());
        goods.setGoodsSn(StringUtils.join(reqGoods.getGoodsSns(), ","));
        int i = merchantGoodsService.goodsDelete(goods);
        resp.success();
        if (i == 0) {
            resp.setExt("删除失败");
        } else {
            resp.setExt("成功删除" + i + "条数据");
        }
        return resp.toJson();
    }


    /**
     * 商品上下架  批量
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsStatusModify")
    @ResponseBody
    public String goodsStatusModify(ReqGoods reqGoods, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
            resp.setMsg("缺少参数:goodsSn");
            return resp.toJson();
        }
        if(null == reqGoods.getIsMarketable()){
            resp.setMsg("缺少参数:isMarketable");
            return resp.toJson();
        }else if(!reqGoods.getIsMarketable().equals(Integer.valueOf(ICommonConstant.YesNoType.no.getCode()))){
            // 不是选下架 的都为 上架
            reqGoods.setIsMarketable(Integer.valueOf(ICommonConstant.YesNoType.yes.getCode()));
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setGoodsSn(reqGoods.getGoodsSn());
        goods.setIsMarketable(reqGoods.getIsMarketable());
        boolean flag = merchantGoodsService.goodsStatusModify(goods);
        resp.success();
        if (flag) {
            resp.setExt("修改成功");
        } else {
            resp.setExt("修改失败");
        }
        return resp.toJson();
    }


    /**
     * 商品权重  排序修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsSortModify")
    @ResponseBody
    public String goodsSortModify(ReqGoods reqGoods, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (StringUtils.isAnyEmpty(reqGoods.getGoodsSn(), reqGoods.getSort())) {
            resp.setMsg("缺少参数:goodsSn/sort");
            return resp.toJson();
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setGoodsSn(reqGoods.getGoodsSn());
        goods.setSort(reqGoods.getSort());
        boolean flag = merchantGoodsService.goodsSortModify(goods);
        resp.success();
        if (flag) {
            resp.setExt("修改成功");
        } else {
            resp.setExt("修改失败");
        }
        return resp.toJson();
    }

    /**
     * 商品库存  修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsStorageModify")
    @ResponseBody
    public String goodsStorageModify(ReqGoods reqGoods, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
//        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (StringUtils.isAnyEmpty(reqGoods.getGoodsSn(), StringUtils.toString(reqGoods.getStorage(),""))) {
            resp.setMsg("缺少参数:goodsSn/storage");
            return resp.toJson();
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setGoodsSn(reqGoods.getGoodsSn());
        goods.setStorage(reqGoods.getStorage());
        boolean flag = merchantGoodsService.goodsStorageModify(goods);
        resp.success();
        if (flag) {
            resp.setExt("修改成功");
        } else {
            resp.setExt("修改失败");
        }
        return resp.toJson();
    }

    /**
     * 商品分类列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fetchCategoryList")
    @ResponseBody
    public String fetchCategoryList(ReqGoodsCategory reqGoodsCategory, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
//        if(StringUtils.isEmpty(reqGoods.getGoodsSn())){
//            resp.setMsg("缺少参数:goodsSn");
//            return resp.toJson();
//        }
        // 传入参数处理
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setMerchantId(merchantAuth.getMerchantId());
        goodsCategory.setShopId(reqGoodsCategory.getShopId());
        goodsCategory.setParent(new GoodsCategory(reqGoodsCategory.getParentId()));
        ArrayList<GoodsCategory> result = merchantGoodsService.fetchCategoryList(goodsCategory);
        if (null == result) {
            resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
        } else {
            resp.success();
            resp.setExt(result);
        }
        return resp.toJson();
    }


    /**
     * 商品分类编辑
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsCategoryModify")
    @ResponseBody
    public String goodsCategoryModify(ReqGoodsCategory reqGoodsCategory, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (StringUtils.isAnyEmpty(reqGoodsCategory.getName(), reqGoodsCategory.getParentId(), reqGoodsCategory.getShopId())) {
            resp.setMsg("缺少参数:parentId/name/shopId");
            return resp.toJson();
        }
        //  传入参数处理
        GoodsCategory category= new GoodsCategory();
        reqGoodsCategory.transfGoodsCategory(category);
        category.setMerchantId(merchantAuth.getMerchantId());
        resp = merchantGoodsService.goodsCategoryModify(category);

        return resp.toJson();
    }

    /**
     * 商品分类删除  批量
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsCategoryDelete")
    @ResponseBody
    public String goodsCategoryDelete(ReqGoodsCategory reqGoodsCategory, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        // 参数验证
        if (Collections3.isEmpty(reqGoodsCategory.getCategoryIds())) {
            resp.setMsg("缺少参数:商品分类编号");
            return resp.toJson();
        }
        // 传入参数处理
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setMerchantId(merchantAuth.getMerchantId());
        goodsCategory.setId(StringUtils.join(reqGoodsCategory.getCategoryIds(), ","));
        int i = merchantGoodsService.categoryDelete(goodsCategory);
        resp.success();
        if (i == 0) {
            resp.setExt("删除失败");
        } else {
            resp.setExt("成功删除" + i + "条数据");
        }
        return resp.toJson();
    }

    /**
     * 图片上传
     * @param file  图片文件
     * @param request
     * @return
     */
    @RequestMapping("uploadPhoto")
    @ResponseBody
    public String uploadPhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        MerchantAuth merchantAuth = (MerchantAuth) request.getAttribute("merchantAuth");
        String merchantId = merchantAuth.getMerchantId();
        // 图片限制大小 2MB
        if(file.isEmpty() || file.getSize() > IMerchantConstant.LIMIT_FILE_SIZE ){
            resp.setMsg("图片最大支持2MB");
            return resp.toJson();
        }
        // 图片类型限制
        String name = file.getOriginalFilename();
        String suffix = "";
        if (StringUtils.isNotBlank(name) && name.contains(".")) {
            suffix = name.substring(name.lastIndexOf("."));
        }
        if (!StringUtils.equalsAny(suffix.toUpperCase(), ".JPEG", ".GIF", ".JPG", ".PNG", ".BMP")) {
            resp.setMsg("图片支持格式为:JPEG/GIF/JPG/PNG/BMP");
            return resp.toJson();
        }
        // 图片保存
        resp = merchantGoodsService.savePhoto(file, merchantId, IMerchantConstant.MerchantFilesType.productPhotos.getGroupNum());
        return resp.toJson();
    }


}