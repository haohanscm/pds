package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DistrictManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ShopServiceDistrict;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DistrictManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.ShopServiceDistrictService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺服务小区管理Controller
 * @author yu.shen
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/shopServiceDistrict")
public class ShopServiceDistrictController extends BaseController {

	@Autowired
	private ShopServiceDistrictService shopServiceDistrictService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private DistrictManageService districtManageService;
	
	@ModelAttribute
	public ShopServiceDistrict get(@RequestParam(required=false) String id) {
		ShopServiceDistrict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopServiceDistrictService.get(id);
		}
		if (entity == null){
			entity = new ShopServiceDistrict();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:shopServiceDistrict:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopServiceDistrict shopServiceDistrict, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopServiceDistrict> page = shopServiceDistrictService.findPage(new Page<ShopServiceDistrict>(request, response), shopServiceDistrict); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/shopServiceDistrictList";
	}

	@RequiresPermissions("xiaodian:delivery:shopServiceDistrict:view")
	@RequestMapping(value = "form")
	public String form(ShopServiceDistrict shopServiceDistrict, Model model) {
		model.addAttribute("shopServiceDistrict", shopServiceDistrict);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		return "modules/xiaodian/delivery/shopServiceDistrictForm";
	}

	@RequiresPermissions("xiaodian:delivery:shopServiceDistrict:edit")
	@RequestMapping(value = "save")
	public String save(ShopServiceDistrict shopServiceDistrict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopServiceDistrict)){
			return form(shopServiceDistrict, model);
		}
		// 获取片区的 省市区
		DistrictManage district = districtManageService.get(shopServiceDistrict.getDistrictAreaId());
		if( null!= district){
			shopServiceDistrict.setProvince(district.getProvince());
			shopServiceDistrict.setCity(district.getCity());
			shopServiceDistrict.setRegion(district.getRegion());
		}
		shopServiceDistrictService.save(shopServiceDistrict);
		addMessage(redirectAttributes, "保存店铺服务小区成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/shopServiceDistrict/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:shopServiceDistrict:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopServiceDistrict shopServiceDistrict, RedirectAttributes redirectAttributes) {
		shopServiceDistrictService.delete(shopServiceDistrict);
		addMessage(redirectAttributes, "删除店铺服务小区成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/shopServiceDistrict/?repage";
	}

    /**
     * 根据 商家/店铺id 查询服务小区
     * @param merchantId
     * @param shopId
     * @return
     */
    @RequiresPermissions(value = {"xiaodian:delivery:shopServiceDistrict:view", "xiaodian:manage:merchant:delivery:view"}, logical = Logical.OR)
    @RequestMapping(value = "query")
    @ResponseBody
    public List<Map<String, Object>> query(@RequestParam("merchantId") String merchantId, @RequestParam("shopId") String shopId) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        if (StringUtils.isEmpty(merchantId) && StringUtils.isEmpty(shopId)) {
            return list;
        }
        ShopServiceDistrict district = new ShopServiceDistrict();
        district.setMerchantId(merchantId);
        district.setShopId(shopId);
        List<ShopServiceDistrict> manList = shopServiceDistrictService.findList(district);
        Map<String, Object> obj;
        for (ShopServiceDistrict s : manList) {
            obj = new HashMap<>();
            obj.put("id", s.getCommunityId());
            obj.put("name", s.getCommunityName());
            list.add(obj);
        }
        return list;
    }

}