package com.haohan.platform.service.sys.modules.pds.api.common;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerGoodsReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.PdsBuyerTopNGoodsApiReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.common.ReqGoods;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.core.common.IPdsCommonService;
import com.haohan.platform.service.sys.modules.pds.service.PdsMerchantGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.database.ReqGoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsCategory;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公共接口 平台商品
 *
 * @author dy
 * @date 2019/1/3
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/common/goods")
public class PdsCommonGoodsCtrl extends BaseController implements IPdsConstant {

    @Autowired
    private IPdsCommonService pdsCommonService;
    @Autowired
    private PdsMerchantGoodsService pdsMerchantGoodsService;

    /**
     * 商品列表
     *
     * @return
     */
    @RequestMapping(value = "fetchGoodsList")
    @ResponseBody
    public BaseResp fetchGoodsList(PdsBuyerGoodsReq goodsReq) {

        BaseResp baseResp = BaseResp.newError();
        String pmId = goodsReq.getPmId();
        String shopId = goodsReq.getShopId();
        String buyerId = goodsReq.getBuyerId();
        // 参数验证
        if (StringUtils.isAnyEmpty(pmId, shopId)) {
            baseResp.setMsg("缺少参数pmId/shopId");
            return baseResp;
        }
        // 分页查询
        int pageNo = StringUtils.toInteger(goodsReq.getPageNo());
        int pageSize = StringUtils.toInteger(goodsReq.getPageSize());
        pageNo = pageNo <= 1 ? 1 : pageNo;
        pageSize = pageSize <= 5 ? 5 : pageSize;
        goodsReq.setPageNo(pageNo);
        goodsReq.setPageSize(pageSize);
        // 配送时间设置  用于确定周期报价
        Date date = goodsReq.getDeliveryDate();
        date = (null == date) ? new Date() : date;

        // 采购商/供应商/管理后台
        if (StringUtils.isEmpty(buyerId)) {
            // 请求参数
            String goodsStatus = goodsReq.getGoodsStatus();
            Goods goods = new Goods();
            goods.setMerchantId(pmId);
            goods.setShopId(shopId);
            goods.setGoodsCategoryId(goodsReq.getCategoryId());
            if (StringUtils.isEmpty(goodsStatus)) {
                // 上下架状态 0:下架,1:上架,2:全部;
                String status = goodsReq.getStatus();
                status = (null == status) ? "" : status;
                // 默认 只要上架商品
                switch (status) {
                    case "2":
                        break;
                    case "0":
                        goods.setIsMarketable(0);
                        break;
                    default:
                        goods.setIsMarketable(1);
                }
            } else {
                goods.setGoodsStatus(goodsStatus);
            }
            goods.setGoodsName(goodsReq.getGoodsName());
            goods.setGoodsModel(goodsReq.getModelStatus());
            goods.setDeliveryDate(goodsReq.getDeliveryDate());
            goods.setGoodsSn(goodsReq.getGoodsSn());
            baseResp = pdsCommonService.fetchGoodsList(goods, pageNo, pageSize);
        } else {
            // 小程序 采购商使用
            // 用户收藏
            if (StringUtils.isEmpty(goodsReq.getUid())) {
                goodsReq.setUid("");
            }
            baseResp = pdsCommonService.fetchGoodsList(goodsReq);
        }
        return baseResp;
    }

    /**
     * 商品详情
     *
     * @return
     */
    @RequestMapping(value = "fetchGoodsInfo")
    @ResponseBody
    public BaseResp fetchGoodsInfo(ReqGoods reqGoods) {
        BaseResp baseResp = new BaseResp();
        // 参数验证
        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
            baseResp.setMsg("缺少参数:goodsSn");
            return baseResp;
        }
        baseResp = pdsCommonService.fetchGoodsInfo(reqGoods.getGoodsSn());
        return baseResp;
    }

    /**
     * 商品分类列表
     *
     * @return
     */
    @RequestMapping(value = "fetchCategoryList")
    @ResponseBody
    public BaseResp fetchCategoryList(ReqGoodsCategory reqGoodsCategory) {
        BaseResp baseResp = BaseResp.newError();
        // 参数验证
        if (StringUtils.isEmpty(reqGoodsCategory.getMerchantId()) && StringUtils.isEmpty(reqGoodsCategory.getShopId())) {
            baseResp.setMsg("缺少参数merchantId或shopId");
            return baseResp;
        }
        // 传入参数处理
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setMerchantId(reqGoodsCategory.getMerchantId());
        goodsCategory.setShopId(reqGoodsCategory.getShopId());
        goodsCategory.setParent(new GoodsCategory(reqGoodsCategory.getParentId()));
        baseResp = pdsCommonService.fetchCategoryList(goodsCategory);
        return baseResp;
    }

    @RequestMapping(value = "topList")
    @ResponseBody
    public BaseResp selectTopN(@Validated PdsBuyerTopNGoodsApiReq pdsApiBuyerTopNGoodsReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }

        baseResp = pdsCommonService.selectTopNGoodsList(pdsApiBuyerTopNGoodsReq);
        return baseResp;
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
        // 参数验证  必需 名称/图片/价格/库存/描述
        if (StringUtils.isAnyEmpty(reqGoods.getShopId(), reqGoods.getGoodsName(), reqGoods.getThumbUrl())) {
            resp.setMsg("缺少参数:shopId/goodsName/thumbUrl");
            return resp.toJson();
        }
        if (null == reqGoods.getMarketPrice() || reqGoods.getMarketPrice().compareTo(BigDecimal.ZERO) < 0) {
            resp.setMsg("价格错误");
        }
        // 库存 默认值 1000
        Integer storage = (null == reqGoods.getStorage() || reqGoods.getStorage() < 1) ? 1000 : reqGoods.getStorage();
        reqGoods.setStorage(storage);
        // 排序  默认值 10
        reqGoods.setSort(StringUtils.defaultString(reqGoods.getSort(), "10"));
        // 传入参数处理
        GoodsInfoExt goods = new GoodsInfoExt();
        reqGoods.transfBaseGoods(goods);
        resp = pdsMerchantGoodsService.goodsBaseModify(goods);
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
        // 参数验证
        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
            resp.setMsg("缺少参数:goodsSn");
            return resp.toJson();
        }
        //  传入参数处理
        GoodsInfoExt goods = new GoodsInfoExt();
        reqGoods.transfExtGoods(goods);
        resp = pdsMerchantGoodsService.goodsExtModify(goods);
        return resp.toJson();
    }

    /**
     * 商品上下架  单个
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "goodsStatusModify")
    @ResponseBody
    public String goodsStatusModify(ReqGoods reqGoods, HttpServletRequest request) {
        BaseResp resp = BaseResp.newError();
        // 参数验证
        if (StringUtils.isEmpty(reqGoods.getGoodsSn())) {
            resp.setMsg("缺少参数:goodsSn");
            return resp.toJson();
        }
        if (null == reqGoods.getIsMarketable()) {
            resp.setMsg("缺少参数:isMarketable");
            return resp.toJson();
        } else if (!reqGoods.getIsMarketable().equals(Integer.valueOf(ICommonConstant.YesNoType.no.getCode()))) {
            // 不是选下架 的都为 上架
            reqGoods.setIsMarketable(Integer.valueOf(ICommonConstant.YesNoType.yes.getCode()));
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setGoodsSn(reqGoods.getGoodsSn());
        goods.setIsMarketable(reqGoods.getIsMarketable());
        boolean flag = pdsMerchantGoodsService.goodsStatusModify(goods);
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
        // 参数验证
        if (StringUtils.isAnyEmpty(reqGoods.getGoodsSn(), reqGoods.getSort())) {
            resp.setMsg("缺少参数:goodsSn/sort");
            return resp.toJson();
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setGoodsSn(reqGoods.getGoodsSn());
        goods.setSort(reqGoods.getSort());
        boolean flag = pdsMerchantGoodsService.goodsSortModify(goods);
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
        // 参数验证
        if (StringUtils.isAnyEmpty(reqGoods.getGoodsSn(), StringUtils.toString(reqGoods.getStorage(), ""))) {
            resp.setMsg("缺少参数:goodsSn/storage");
            return resp.toJson();
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setGoodsSn(reqGoods.getGoodsSn());
        goods.setStorage(reqGoods.getStorage());
        boolean flag = pdsMerchantGoodsService.goodsStorageModify(goods);
        resp.success();
        if (flag) {
            resp.setExt("修改成功");
        } else {
            resp.setExt("修改失败");
        }
        return resp.toJson();
    }

    /**
     * 商品删除  批量
     * 根据多个商品sn 批量删除商品
     *
     * @return
     */
    @RequestMapping(value = "goodsDelete")
    @ResponseBody
    public String goodsDelete(ReqGoods reqGoods, HttpServletRequest request) {

        BaseResp resp = BaseResp.newError();
        // 参数验证
        if (Collections3.isEmpty(reqGoods.getGoodsSns())) {
            resp.setMsg("缺少参数:商品编号");
            return resp.toJson();
        }
        // 传入参数处理
        Goods goods = new Goods();
        goods.setGoodsSn(StringUtils.join(reqGoods.getGoodsSns(), ","));
        int i = pdsMerchantGoodsService.goodsDelete(goods);
        resp.success();
        if (i == 0) {
            resp.setExt("删除失败");
        } else {
            resp.setExt("成功删除" + i + "条数据");
        }
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
        // 参数验证
        if (Collections3.isEmpty(reqGoodsCategory.getCategoryIds())) {
            resp.setMsg("缺少参数:商品分类编号");
            return resp.toJson();
        }
        // 传入参数处理
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setId(StringUtils.join(reqGoodsCategory.getCategoryIds(), ","));
        int i = pdsMerchantGoodsService.categoryDelete(goodsCategory);
        resp.success();
        if (i == 0) {
            resp.setExt("删除失败");
        } else {
            resp.setExt("成功删除" + i + "条数据");
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
        // 参数验证
        if (StringUtils.isAnyEmpty(reqGoodsCategory.getName(), reqGoodsCategory.getParentId(), reqGoodsCategory.getShopId())) {
            resp.setMsg("缺少参数:parentId/name/shopId");
            return resp.toJson();
        }
        //  传入参数处理
        GoodsCategory category = new GoodsCategory();
        reqGoodsCategory.transfGoodsCategory(category);
        resp = pdsMerchantGoodsService.goodsCategoryModify(category);

        return resp.toJson();
    }

    /**
     * 图片上传
     *
     * @param file 图片文件
     * @return
     */
    @RequestMapping("uploadPhoto")
    @ResponseBody
    public BaseResp uploadPhoto(MultipartFile file, String pmId, String type) {
        BaseResp resp = BaseResp.newError();
        String merchantId = pmId;
        if (null == file || StringUtils.isEmpty(pmId)) {
            resp.setMsg("参数有误");
            return resp;
        }
        // 图片限制大小 2MB
        if (file.isEmpty() || file.getSize() > IMerchantConstant.LIMIT_FILE_SIZE) {
            resp.setMsg("图片最大支持2MB");
            return resp;
        }
        // 图片类型限制
        String name = file.getOriginalFilename();
        String suffix = "";
        if (StringUtils.isNotBlank(name) && name.contains(".")) {
            suffix = name.substring(name.lastIndexOf("."));
        }
        if (!StringUtils.equalsAny(suffix.toUpperCase(), ".JPEG", ".GIF", ".JPG", ".PNG", ".BMP")) {
            resp.setMsg("图片支持格式为:JPEG/GIF/JPG/PNG/BMP");
            return resp;
        }
        // 图片保存 此接口默认保存商品图片
        type = (StringUtils.isBlank(type)) ? IMerchantConstant.MerchantFilesType.productPhotos.getGroupNum() : type;
        resp = pdsMerchantGoodsService.savePhoto(file, merchantId, type);
        return resp;
    }


}
