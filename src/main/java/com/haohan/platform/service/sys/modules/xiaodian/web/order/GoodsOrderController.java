package com.haohan.platform.service.sys.modules.xiaodian.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
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
 * 商品订单Controller
 * @author haohan
 * @version 2017-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/order/goodsOrder")
public class GoodsOrderController extends BaseController {

	@Autowired
	private GoodsOrderService goodsOrderService;
	
	@ModelAttribute
	public GoodsOrder get(@RequestParam(required=false) String id) {
		GoodsOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsOrderService.get(id);
		}
		if (entity == null){
			entity = new GoodsOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:order:goodsOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsOrder goodsOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsOrder> page = goodsOrderService.findPage(new Page<GoodsOrder>(request, response), goodsOrder);
		model.addAttribute("page", page);
		return "modules/xiaodian/order/goodsOrderList";
	}

	@RequiresPermissions("xiaodian:order:goodsOrder:view")
	@RequestMapping(value = "form")
	public String form(GoodsOrder goodsOrder, Model model) {
		model.addAttribute("goodsOrder", goodsOrder);
		return "modules/xiaodian/order/goodsOrderForm";
	}

	@RequiresPermissions("xiaodian:order:goodsOrder:edit")
	@RequestMapping(value = "save")
	public String save(GoodsOrder goodsOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsOrder)){
			return form(goodsOrder, model);
		}
		goodsOrderService.save(goodsOrder);
		addMessage(redirectAttributes, "保存商品订单成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/goodsOrder/?repage";
	}
	
	@RequiresPermissions("xiaodian:order:goodsOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsOrder goodsOrder, RedirectAttributes redirectAttributes) {
		goodsOrderService.delete(goodsOrder);
		addMessage(redirectAttributes, "删除商品订单成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/goodsOrder/?repage";
	}

}