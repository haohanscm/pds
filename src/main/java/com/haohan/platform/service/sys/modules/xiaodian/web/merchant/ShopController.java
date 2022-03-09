package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IShopConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.*;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopCategoryService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店铺管理Controller
 * @author haohan
 * @version 2017-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant/shop")
public class ShopController extends BaseController {

	@Autowired
	private ShopService shopService;

	@Autowired
	private MerchantService merchantService;
    @Autowired
    @Lazy(true)
    private ShopCategoryService shopCategoryService;

	@Autowired
	private PhotoGroupManageService photoGroupManageService;
	
	@ModelAttribute
	public Shop get(@RequestParam(required=false) String id) {
		Shop entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopService.get(id);
		}
		if (entity == null){
			entity = new Shop();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:merchant:shop:view")
	@RequestMapping(value = {"list", ""})
	public String list(Shop shop, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Shop> page = new Page<Shop>(request, response);
		shop.setPage(page);
        page.setList(shopService.findJoinList(shop));
		model.addAttribute("page", page);
		List<Merchant> merchantList = merchantService.findList(new Merchant());
		model.addAttribute("merchantList", merchantList);
		return "modules/xiaodian/merchant/shopList";
	}

	@RequiresPermissions("xiaodian:merchant:shop:view")
	@RequestMapping(value = "form")
	public String form(Shop shop, Model model) {
		List<Merchant> merchantList = merchantService.findList(new Merchant());
		model.addAttribute("merchantList", merchantList);
		String shopCategoryId = shop.getShopCategory();
		if(StringUtils.isNotEmpty(shopCategoryId)){
            ShopCategory category = shopCategoryService.get(shopCategoryId);
            if(null != category){
                shop.setShopCategoryName(category.getName());
            }
        }
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.setShop(shop);
		shopInfo.setShopLocation(shop.fetchShopLocation());
		model.addAttribute("shopInfo", shopInfo);
		model.addAttribute("shopLocation", shop.fetchShopLocation());

//		//设置店铺Logo
//		if(StringUtils.isNotEmpty(shop.getShopLogo())){
//			PhotoGroupManage groupManage = 	photoGroupManageService.fetchByGroupNum(shop.getShopLogo());
//			shopInfo.setShopLogos(groupManage);
//			model.addAttribute("shopLogos", groupManage);
//		}
//
//		//设置店铺支付二维码
//		if(StringUtils.isNotEmpty(shop.getPayCode())){
//			PhotoGroupManage groupManage = 	photoGroupManageService.fetchByGroupNum(shop.getPayCode());
//			shopInfo.setShopPayCodes(groupManage);
//			model.addAttribute("shopPayCodes", groupManage);
//		}
//
//		//设置店铺二维码
//		if(StringUtils.isNotEmpty(shop.getQrcode())){
//			PhotoGroupManage groupManage = 	photoGroupManageService.fetchByGroupNum(shop.getQrcode());
//			shopInfo.setShopQrCodes(groupManage);
//			model.addAttribute("shopQrCodes", groupManage);
//		}



		return "modules/xiaodian/merchant/shopForm";
	}

	@RequiresPermissions("xiaodian:merchant:shop:edit")
	@RequestMapping(value = "save")
	public String save(ShopInfo shopInfo, Model model, RedirectAttributes redirectAttributes) {
		Shop shop = shopInfo.getShop();
		ShopLocation shopLocation = shopInfo.getShopLocation();
		if (!beanValidator(model, shop)){
			return form(shop, model);
		}
		String merchantId = shopInfo.getShop().getMerchantId();
		shop.setShopLocation(shopLocation.toJson());
		// 设置店铺id 用于生成 groupNum
        if(StringUtils.isEmpty(shop.getId())){
            shop.setId(IdGen.genByT(Shop.class));
            shop.setIsNewRecord(true);
            // 重设图片组编号
//            shop.setShopLogo(shop.getId() + "-" + IShopConstant.ShopPhotoType.logo.getCode());
//            shop.setPayCode(shop.getId() + "-" + IShopConstant.ShopPhotoType.payCode.getCode());
//            shop.setQrcode(shop.getId() + "-" + IShopConstant.ShopPhotoType.qrcode.getCode());
        }
//		//设置logo
//		PhotoGroupManage shopLogos = shopInfo.getShopLogos();
//		shopLogos.setMerchantId(shop.getMerchantId());
//		shopLogos.setGroupNum(shop.getShopLogo());
//		photoGroupManageService.save(shopLogos);
//
//		//设置店铺支付二维码
//		PhotoGroupManage  shopPayCodes = shopInfo.getShopPayCodes();
//		shopPayCodes.setMerchantId(shop.getMerchantId());
//		shopPayCodes.setGroupNum(shop.getPayCode());
//		photoGroupManageService.save(shopPayCodes);
//
//		//设置店铺二维码
//		PhotoGroupManage shopQrCodes = shopInfo.getShopQrCodes();
//		shopQrCodes.setMerchantId(shop.getMerchantId());
//		shopQrCodes.setGroupNum(shop.getQrcode());
//		photoGroupManageService.save(shopQrCodes);

		shopService.save(shop);

		addMessage(redirectAttributes, "保存店铺成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shop/?repage";
	}
	
	@RequiresPermissions("xiaodian:merchant:shop:edit")
	@RequestMapping(value = "delete")
	public String delete(Shop shop, RedirectAttributes redirectAttributes) {
		shopService.delete(shop);
		addMessage(redirectAttributes, "删除店铺成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shop/?repage";
	}

	@RequiresPermissions(value = {"xiaodian:merchant:shop:view","xiaodian:manage:merchant:delivery:view"}, logical = Logical.OR)
	@RequestMapping(value = "fetchShops")
	@ResponseBody
	public String fetchShops(Shop shop, HttpServletRequest request, HttpServletResponse response) {
		List<Shop> list = shopService.findList(shop);
		return new ApiResp().success(JsonMapper.toJsonString(list));
	}

}