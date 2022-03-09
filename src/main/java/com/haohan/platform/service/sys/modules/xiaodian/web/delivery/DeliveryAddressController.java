package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryAddress;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryAddressService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 配送信息Controller
 * @author shenyu
 * @version 2018-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/deliveryAddress")
public class DeliveryAddressController extends BaseController {

	@Autowired
	private DeliveryAddressService deliveryAddressService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public DeliveryAddress get(@RequestParam(required=false) String id) {
		DeliveryAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deliveryAddressService.get(id);
		}
		if (entity == null){
			entity = new DeliveryAddress();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:deliveryAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeliveryAddress deliveryAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeliveryAddress> page = deliveryAddressService.findPage(new Page<DeliveryAddress>(request, response), deliveryAddress); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/deliveryAddressList";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryAddress:view")
	@RequestMapping(value = "form")
	public String form(DeliveryAddress deliveryAddress, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList", merchantList);
		model.addAttribute("deliveryAddress", deliveryAddress);
		return "modules/xiaodian/delivery/deliveryAddressForm";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryAddress:edit")
	@RequestMapping(value = "save")
	public String save(DeliveryAddress deliveryAddress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deliveryAddress)){
			return form(deliveryAddress, model);
		}
		deliveryAddressService.save(deliveryAddress);
		addMessage(redirectAttributes, "保存配送信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryAddress/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:deliveryAddress:edit")
	@RequestMapping(value = "delete")
	public String delete(DeliveryAddress deliveryAddress, RedirectAttributes redirectAttributes) {
		deliveryAddressService.delete(deliveryAddress);
		addMessage(redirectAttributes, "删除配送信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryAddress/?repage";
	}

}