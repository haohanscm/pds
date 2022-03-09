package com.haohan.platform.service.sys.modules.xiaodian.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.Cart;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.CartService;
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

/**
 * 购物车Controller
 * @author haohan
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/order/cart")
public class CartController extends BaseController {

	@Autowired
	private CartService cartService;
	
	@ModelAttribute
	public Cart get(@RequestParam(required=false) String id) {
		Cart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cartService.get(id);
		}
		if (entity == null){
			entity = new Cart();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:order:cart:view")
	@RequestMapping(value = {"list", ""})
	public String list(Cart cart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Cart> page = cartService.findPage(new Page<Cart>(request, response), cart); 
		model.addAttribute("page", page);
		return "modules/xiaodian/order/cartList";
	}

	@RequiresPermissions("xiaodian:order:cart:view")
	@RequestMapping(value = "form")
	public String form(Cart cart, Model model) {
		model.addAttribute("cart", cart);
		return "modules/xiaodian/order/cartForm";
	}

	@RequiresPermissions("xiaodian:order:cart:edit")
	@RequestMapping(value = "save")
	public String save(Cart cart, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cart)){
			return form(cart, model);
		}
		cartService.save(cart);
		addMessage(redirectAttributes, "保存购物车成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/cart/?repage";
	}
	
	@RequiresPermissions("xiaodian:order:cart:edit")
	@RequestMapping(value = "delete")
	public String delete(Cart cart, RedirectAttributes redirectAttributes) {
		cartService.delete(cart);
		addMessage(redirectAttributes, "删除购物车成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/cart/?repage";
	}

}