package com.haohan.platform.service.sys.modules.pds.api.supplier;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.supplier.SupplierGoodsRelationReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.supplier.SupplyPriceApiReq;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierGoodsCoreService;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierPaymentService;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoodsExt;
import com.haohan.platform.service.sys.modules.pds.entity.cost.SupplierPayment;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 供应商API接口   信息 货款
 *
 * @author dy
 * @create 2018/11/11
 */
@Controller
@RequestMapping(value = "${frontPath}/pds/api/supplier/info")
public class PdsSupplierInfoCtrl extends BaseController {

    @Autowired
    private ISupplierPaymentService supplierPaymentService;
    @Autowired
    private ISupplierGoodsCoreService supplierGoodsCoreService;

    /**
     * 供应商 交易单 按日 生成货款记录
     *
     * @return
     */
    @RequestMapping(value = "paymentRecord")
    @ResponseBody
    public BaseResp paymentRecord(SupplierPayment supplierPayment) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isEmpty(supplierPayment.getSupplierId()) || null == supplierPayment.getSupplyDate()) {
            baseResp.setMsg("缺少参数supplierId/supplyDate");
            return baseResp;
        }
        // 请求参数
        baseResp = supplierPaymentService.paymentRecord(supplierPayment);
        return baseResp;
    }

    /**
     * 供应商 商品分类列表查询 带关联状态
     *
     * @return
     */
    @RequestMapping(value = "goods/categoryStatusList")
    @ResponseBody
    public BaseResp categoryStatusList(SupplierGoods supplierGoods) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isAnyEmpty(supplierGoods.getSupplierId(), supplierGoods.getShopId())) {
            baseResp.setMsg("缺少参数supplierId/shopId");
            return baseResp;
        }
        // 请求参数
        SupplierGoods goods = new SupplierGoods();
        goods.setSupplierId(supplierGoods.getSupplierId());
        goods.setShopId(supplierGoods.getShopId());
        if (StringUtils.isNotEmpty(supplierGoods.getCategoryId())) {
            goods.setCategoryId(supplierGoods.getCategoryId());
        }
        baseResp = supplierGoodsCoreService.queryCategoryStatusList(goods);
        return baseResp;
    }

    /**
     * 供应商 商品列表查询
     *
     * @return
     */
    @RequestMapping(value = "goods/fetchList")
    @ResponseBody
    public BaseResp fetchGoodsList(SupplierGoodsExt supplierGoodsExt, Page page) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (StringUtils.isAnyEmpty(supplierGoodsExt.getSupplierId(), supplierGoodsExt.getShopId())) {
            baseResp.setMsg("缺少参数supplierId/shopId");
            return baseResp;
        }
        // 请求参数
        SupplierGoodsExt goodsExt = new SupplierGoodsExt();
        goodsExt.setSupplierId(supplierGoodsExt.getSupplierId());
        if (StringUtils.isNotEmpty(supplierGoodsExt.getStatus())) {
            goodsExt.setStatus(supplierGoodsExt.getStatus());
        }
        Goods goods = new Goods();
        goods.setShopId(supplierGoodsExt.getShopId());
        if (StringUtils.isNotEmpty(supplierGoodsExt.getCategoryId())) {
            goods.setGoodsCategoryId(supplierGoodsExt.getCategoryId());
        }
        goodsExt.setGoods(goods);
        int pageNo = page.getPageNo() <= 1 ? 1 : page.getPageNo();
        int pageSize = page.getPageSize() <= 5 ? 5 : page.getPageSize();
        page = new Page(pageNo, pageSize);
        baseResp = supplierGoodsCoreService.queryGoodsList(goodsExt, page);
        return baseResp;
    }

    /**
     * 供应商 商品关联  新增/取消
     *
     * @return
     */
    @RequestMapping(value = "goods/modify")
    @ResponseBody
    public BaseResp modifyGoods(SupplierGoods supplierGoods) {

        BaseResp baseResp = BaseResp.newError();
        String status = supplierGoods.getStatus();
        //验证参数
        if (StringUtils.isAnyEmpty(supplierGoods.getSupplierId(), status)) {
            baseResp.setMsg("缺少参数supplierId/status");
            return baseResp;
        }
        // 可只按goodsId修改  也可按分类categoryId批量修改
        if ((StringUtils.isAnyEmpty(supplierGoods.getShopId(), supplierGoods.getCategoryId()) || StringUtils.isEmpty(supplierGoods.getGoodsId()))) {
            baseResp.setMsg("缺少参数goodsId/categoryId");
        }
        // 默认状态为 关联
        if (!StringUtils.equals(status, ICommonConstant.YesNoType.no.getCode())) {
            supplierGoods.setStatus(ICommonConstant.YesNoType.yes.getCode());
        }
        baseResp = supplierGoodsCoreService.modifyGoods(supplierGoods);
        return baseResp;
    }

    /**
     * 供应商 商品供应价格查询
     *
     * @return
     */
    @RequestMapping(value = "goods/querySupplyPriceList")
    @ResponseBody
    public BaseResp querySupplyPriceList(@Validated SupplyPriceApiReq supplyPriceReq, BindingResult bindingResult) {

        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        SupplierGoods supplierGoods = new SupplierGoods();
        supplierGoods.setPmId(supplyPriceReq.getPmId());
        supplierGoods.setGoodsModelId(supplyPriceReq.getGoodsModelId());
        baseResp = supplierGoodsCoreService.querySupplyPriceList(supplierGoods);
        return baseResp;
    }

    /**
     * 批量生成供应商和平台商家 商品的映射关系
     *
     * @return
     */
    @RequestMapping(value = "goods/relationGoods")
    @ResponseBody
    public BaseResp relationGoods(@Validated SupplierGoodsRelationReq relationReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        //验证参数
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = supplierGoodsCoreService.relationGoods(relationReq);
        return baseResp;
    }

}
