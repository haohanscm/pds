package com.haohan.platform.service.sys.modules.pds.web.business;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.supplier.SupplierGoodsRelationReq;
import com.haohan.platform.service.sys.modules.pds.core.supplier.ISupplierGoodsCoreService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsSupplier;
import com.haohan.platform.service.sys.modules.pds.entity.business.SupplierGoods;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsSupplierService;
import com.haohan.platform.service.sys.modules.pds.service.business.SupplierGoodsService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.Goods;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商货物管理Controller
 *
 * @author haohan
 * @version 2018-11-14
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/business/supplierGoods")
public class SupplierGoodsController extends BaseController {

    @Autowired
    private SupplierGoodsService supplierGoodsService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
    @Autowired
    @Lazy(true)
    private ISupplierGoodsCoreService supplierGoodsCoreService;
    @Autowired
    @Lazy(true)
    private PdsSupplierService supplierService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;
    @Autowired
    @Lazy(true)
    private GoodsService goodsService;
    @Autowired
    @Lazy(true)
    private GoodsModelService goodsModelService;


    @ModelAttribute
    public SupplierGoods get(@RequestParam(required = false) String id) {
        SupplierGoods entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = supplierGoodsService.get(id);
        }
        if (entity == null) {
            entity = new SupplierGoods();
        }
        return entity;
    }

    @RequiresPermissions("pds:business:supplierGoods:view")
    @RequestMapping(value = {"list", ""})
    public String list(SupplierGoods supplierGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SupplierGoods> page = new Page<SupplierGoods>(request, response);
        supplierGoods.setPage(page);
        page.setList(supplierGoodsService.findJoinList(supplierGoods));
        model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        List<PdsSupplier> supplierList = supplierService.findUsableList();
        model.addAttribute("supplierList", supplierList);
        List<PdsSupplier> supplierMerchantList = supplierService.findMerchantList("");
        model.addAttribute("supplierMerchantList", supplierMerchantList);
        return "modules/pds/business/supplierGoodsList";
    }

    @RequiresPermissions("pds:business:supplierGoods:view")
    @RequestMapping(value = "form")
    public String form(SupplierGoods supplierGoods, Model model) {
        model.addAttribute("supplierGoods", supplierGoods);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/business/supplierGoodsForm";
    }

    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "save")
    public String save(SupplierGoods supplierGoods, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, supplierGoods)) {
            return form(supplierGoods, model);
        }
        supplierGoodsService.save(supplierGoods);
        addMessage(redirectAttributes, "保存供应商货物成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/supplierGoods/?repage";
    }

    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "delete")
    public String delete(SupplierGoods supplierGoods, RedirectAttributes redirectAttributes) {
        supplierGoodsService.delete(supplierGoods);
        addMessage(redirectAttributes, "删除供应商货物成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/supplierGoods/?repage";
    }

    @RequiresPermissions("pds:business:supplierGoods:view")
    @RequestMapping(value = "relation")
    public String relation(SupplierGoods supplierGoods, Model model) {
        model.addAttribute("supplierGoods", supplierGoods);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/business/supplierGoodsRelation";
    }

    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "relationSave")
    @ResponseBody
    public BaseResp relationSave(SupplierGoods supplierGoods) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = supplierGoods.getPmId();
        String supplierId = supplierGoods.getSupplierId();
        String supplierMerchantId = supplierGoods.getSupplierMerchantId();
        String goodsId = supplierGoods.getGoodsId();
        String goodsModelId = supplierGoods.getGoodsModelId();
        String supplierModelId = supplierGoods.getSupplierModelId();
        String status = supplierGoods.getStatus();
        if (StringUtils.isAnyEmpty(pmId, supplierId, supplierMerchantId, goodsId, goodsModelId, supplierModelId, status)) {
            baseResp.setMsg("信息未填写完整");
            return baseResp;
        }
        SupplierGoods query = new SupplierGoods();
        query.setPmId(pmId);
        query.setSupplierId(supplierId);
        query.setSupplierMerchantId(supplierMerchantId);
        query.setGoodsId(goodsId);
        query.setGoodsModelId(goodsModelId);
        List<SupplierGoods> goodsList = supplierGoodsService.findList(query);
        baseResp.success();
        if (!Collections3.isEmpty(goodsList)) {
            supplierGoods.setId(goodsList.get(0).getId());
            baseResp.setMsg("更新成功");
        } else {
            baseResp.setMsg("保存成功");
        }
        supplierGoodsService.save(supplierGoods);
        return baseResp;
    }

    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "relationQuery")
    @ResponseBody
    public BaseResp relationQuery(SupplierGoods supplierGoods) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = supplierGoods.getPmId();
        String supplierId = supplierGoods.getSupplierId();
        String goodsId = supplierGoods.getGoodsId();
        String goodsModelId = supplierGoods.getGoodsModelId();
        if (StringUtils.isAnyEmpty(pmId, supplierId, goodsId, goodsModelId)) {
            baseResp.setMsg("缺少参数");
            return baseResp;
        }
        SupplierGoods query = new SupplierGoods();
        query.setPmId(pmId);
        query.setSupplierId(supplierId);
        query.setGoodsId(goodsId);
        query.setGoodsModelId(goodsModelId);
        List<SupplierGoods> goodsList = supplierGoodsService.findJoinList(query);
        if (!Collections3.isEmpty(goodsList)) {
            baseResp.success();
            supplierGoods = goodsList.get(0);
            GoodsModel goodsModel = goodsModelService.get(supplierGoods.getSupplierModelId());
            if (null != goodsModel) {
                supplierGoods.setSupplierGoodsId(goodsModel.getGoodsId());
            }
            baseResp.setExt(supplierGoods);
        }
        return baseResp;
    }

    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "relationGoodsBatch")
    @ResponseBody
    public BaseResp relationGoodsBatch(SupplierGoodsRelationReq relationReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        baseResp = supplierGoodsCoreService.relationGoods(relationReq);
        return baseResp;
    }

    /**
     * 供应商列表
     *
     * @param supplierGoods
     * @return
     */
    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "supplierList")
    @ResponseBody
    public BaseResp supplierList(SupplierGoods supplierGoods) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = supplierGoods.getPmId();
        if (StringUtils.isEmpty(pmId)) {
            baseResp.setMsg("pmId为空");
            return baseResp;
        }
        PdsSupplier supplier = new PdsSupplier();
        supplier.setPmId(pmId);
        List<PdsSupplier> supplierList = supplierService.findUsableList(supplier);
        if (Collections3.isEmpty(supplierList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(new ArrayList<>(supplierList));
        return baseResp;
    }

    /**
     * 商品列表
     *
     * @param supplierGoods
     * @return
     */
    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "goodsList")
    @ResponseBody
    public BaseResp goodsList(SupplierGoods supplierGoods) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = supplierGoods.getPmId();
        if (StringUtils.isEmpty(pmId)) {
            baseResp.setMsg("pmId为空");
            return baseResp;
        }
        // 采购配送店铺
        String shopId = "";
        // 查询shopId  返回商家的采购配送店铺
        Shop shop = new Shop();
        shop.setMerchantId(pmId);
        shop.setShopLevel(IShopConstant.ShopLevelType.pds.getCode());
        List<Shop> shopList = shopService.fetchByMerchantIdEnable(shop);
        if (!Collections3.isEmpty(shopList)) {
            shopId = shopList.get(0).getId();
        }
        if (StringUtils.isEmpty(shopId)) {
            baseResp.setMsg("商家没有采购配送店铺");
            return baseResp;
        }
        Goods goods = new Goods();
        goods.setShopId(shopId);
        List<Goods> goodsList = goodsService.findList(goods);
        if (Collections3.isEmpty(goodsList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(new ArrayList<>(goodsList));
        return baseResp;
    }

    /**
     * 规格商品列表
     *
     * @param supplierGoods
     * @return
     */
    @RequiresPermissions("pds:business:supplierGoods:edit")
    @RequestMapping(value = "goodsModelList")
    @ResponseBody
    public BaseResp goodsModelList(SupplierGoods supplierGoods) {
        BaseResp baseResp = BaseResp.newError();
        String goodsId = supplierGoods.getGoodsId();
        if (StringUtils.isEmpty(goodsId)) {
            baseResp.setMsg("goodsId为空");
            return baseResp;
        }
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setGoodsId(goodsId);
        List<GoodsModel> goodsModelList = goodsModelService.findList(goodsModel);
        if (Collections3.isEmpty(goodsModelList)) {
            baseResp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
            return baseResp;
        }
        baseResp.success();
        baseResp.setExt(new ArrayList<>(goodsModelList));
        return baseResp;
    }

}