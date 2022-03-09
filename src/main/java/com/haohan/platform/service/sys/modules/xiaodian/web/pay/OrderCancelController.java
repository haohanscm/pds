package com.haohan.platform.service.sys.modules.xiaodian.web.pay;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderCancel;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderCancelService;
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
 * 订单撤销Controller
 * @author haohan
 * @version 2017-12-30
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/pay/orderCancel")
public class OrderCancelController extends BaseController {

	@Autowired
	private OrderCancelService orderCancelService;
	
	@ModelAttribute
	public OrderCancel get(@RequestParam(required=false) String id) {
		OrderCancel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderCancelService.get(id);
		}
		if (entity == null){
			entity = new OrderCancel();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:pay:orderCancel:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderCancel orderCancel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderCancel> page = orderCancelService.findPage(new Page<OrderCancel>(request, response), orderCancel); 
		model.addAttribute("page", page);
		return "modules/xiaodian/pay/orderCancelList";
	}

	@RequiresPermissions("xiaodian:pay:orderCancel:view")
	@RequestMapping(value = "form")
	public String form(OrderCancel orderCancel, Model model) {
		model.addAttribute("orderCancel", orderCancel);
		return "modules/xiaodian/pay/orderCancelForm";
	}

	@RequiresPermissions("xiaodian:pay:orderCancel:edit")
	@RequestMapping(value = "save")
	public String save(OrderCancel orderCancel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderCancel)){
			return form(orderCancel, model);
		}
		orderCancelService.save(orderCancel);
		addMessage(redirectAttributes, "保存订单撤销成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/orderCancel/?repage";
	}
	
	@RequiresPermissions("xiaodian:pay:orderCancel:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderCancel orderCancel, RedirectAttributes redirectAttributes) {
		orderCancelService.delete(orderCancel);
		addMessage(redirectAttributes, "删除订单撤销成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/orderCancel/?repage";
	}

}