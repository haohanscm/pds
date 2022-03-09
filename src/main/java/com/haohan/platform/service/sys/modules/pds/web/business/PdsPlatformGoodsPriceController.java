package com.haohan.platform.service.sys.modules.pds.web.business;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.PdsBuyerPriceReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer.PdsDeletePriceBatchReq;
import com.haohan.platform.service.sys.modules.pds.api.entity.req.buyer.PdsUpdateShopPriceReq;
import com.haohan.platform.service.sys.modules.pds.core.buyer.IBuyerGoodsService;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsBuyer;
import com.haohan.platform.service.sys.modules.pds.entity.business.PdsPlatformGoodsPrice;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsPlatformGoodsPriceService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
 * 平台商品定价管理Controller
 *
 * @author haohan
 * @version 2018-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/pds/business/pdsPlatformGoodsPrice")
public class PdsPlatformGoodsPriceController extends BaseController {

    @Autowired
    private PdsPlatformGoodsPriceService pdsPlatformGoodsPriceService;
    @Autowired
    private PdsBuyerService buyerService;
    @Autowired
    private IBuyerGoodsService buyerGoodsService;
    @Autowired
    @Lazy(true)
    private ShopService shopService;


    @ModelAttribute
    public PdsPlatformGoodsPrice get(@RequestParam(required = false) String id) {
        PdsPlatformGoodsPrice entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = pdsPlatformGoodsPriceService.get(id);
        }
        if (entity == null) {
            entity = new PdsPlatformGoodsPrice();
        }
        return entity;
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:view")
    @RequestMapping(value = {"list", ""})
    public String list(PdsPlatformGoodsPrice pdsPlatformGoodsPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<PdsPlatformGoodsPrice> page = new Page<PdsPlatformGoodsPrice>(request, response);
        pdsPlatformGoodsPrice.setPage(page);
        page.setList(pdsPlatformGoodsPriceService.findJoinList(pdsPlatformGoodsPrice));
        model.addAttribute("page", page);
        List<PdsBuyer> merchantList = buyerService.findMerchantList();
        model.addAttribute("merchantList", merchantList);
        List<Merchant> pmList = buyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/business/pdsPlatformGoodsPriceList";
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:view")
    @RequestMapping(value = "form")
    public String form(PdsPlatformGoodsPrice pdsPlatformGoodsPrice, Model model) {
        model.addAttribute("pdsPlatformGoodsPrice", pdsPlatformGoodsPrice);
        List<PdsBuyer> merchantList = buyerService.findMerchantList();
        model.addAttribute("merchantList", merchantList);
        List<Merchant> pmList = buyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
        return "modules/pds/business/pdsPlatformGoodsPriceForm";
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "save")
    public String save(PdsPlatformGoodsPrice pdsPlatformGoodsPrice, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, pdsPlatformGoodsPrice)) {
            return form(pdsPlatformGoodsPrice, model);
        }
        pdsPlatformGoodsPriceService.save(pdsPlatformGoodsPrice);
        addMessage(redirectAttributes, "保存平台商品定价成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/pdsPlatformGoodsPrice/?repage";
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "delete")
    public String delete(PdsPlatformGoodsPrice pdsPlatformGoodsPrice, RedirectAttributes redirectAttributes) {
        pdsPlatformGoodsPriceService.delete(pdsPlatformGoodsPrice);
        addMessage(redirectAttributes, "删除平台商品定价成功");
        return "redirect:" + Global.getAdminPath() + "/pds/business/pdsPlatformGoodsPrice/?repage";
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "initPrice")
    @ResponseBody
    public BaseResp initPrice(PdsBuyerPriceReq buyerPrice) {
        BaseResp baseResp = buyerGoodsService.copyGoodsToBuyerGoods(buyerPrice);
        return baseResp;
    }

    /**
     * 复制商家的 商品定价
     * 必需 pmId/merchantId/newMerchantId/beginStartDate/beginEndDate/startDate/endDate
     *
     * @param pdsPlatformGoodsPrice
     * @return
     */
    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "copyPrice")
    @ResponseBody
    public BaseResp copyPrice(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        return buyerGoodsService.copyGoodsByMerchant(pdsPlatformGoodsPrice);
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "deletePrice")
    @ResponseBody
    public BaseResp deletePrice(@Validated PdsDeletePriceBatchReq deleteReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        return buyerGoodsService.deletePriceBatch(deleteReq);
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "updateShopPrice")
    @ResponseBody
    public BaseResp updateShopPrice(@Validated PdsUpdateShopPriceReq updateReq, BindingResult bindingResult) {
        BaseResp baseResp = BaseResp.newError();
        if (!validBindingResult(bindingResult, baseResp)) {
            return baseResp;
        }
        return buyerGoodsService.updatePriceToPmShop(updateReq);
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "buyerMerchantList")
    @ResponseBody
    public BaseResp buyerMerchantList(PdsPlatformGoodsPrice pdsPlatformGoodsPrice) {
        BaseResp baseResp = BaseResp.newError();
        String pmId = pdsPlatformGoodsPrice.getPmId();
        if (StringUtils.isEmpty(pmId)) {
            baseResp.setMsg("pmId为空");
            return baseResp;
        }
        // 返回采购商所属的商家
        List<PdsBuyer> list = buyerService.findMerchantList(pmId);
        if (!Collections3.isEmpty(list)) {
            baseResp.success();
            baseResp.setExt(new ArrayList<>(list));
        }
        return baseResp;
    }

    @RequiresPermissions("pds:business:pdsPlatformGoodsPrice:edit")
    @RequestMapping(value = "shopList")
    @ResponseBody
    public BaseResp shopList(Shop shop) {
        BaseResp baseResp = BaseResp.newError();
        // 返回商家的采购配送店铺
        shop.setShopLevel(IShopConstant.ShopLevelType.pds.getCode());
        List<Shop> list = shopService.fetchByMerchantIdEnable(shop);
        if (!Collections3.isEmpty(list)) {
            baseResp.success();
            baseResp.setExt(new ArrayList<>(list));
        }
        return baseResp;
    }

}