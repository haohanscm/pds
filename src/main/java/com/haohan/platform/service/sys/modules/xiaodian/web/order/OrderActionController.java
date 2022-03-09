package com.haohan.platform.service.sys.modules.xiaodian.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderAction;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.OrderActionService;
import com.haohan.platform.service.sys.modules.xiaodian.util.CommonUtils;
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

/**
 * 订单操作Controller
 * @author haohan
 * @version 2017-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/order/orderAction")
public class OrderActionController extends BaseController {

	@Autowired
	private OrderActionService orderActionService;
	
	@ModelAttribute
	public OrderAction get(@RequestParam(required=false) String id) {
		OrderAction entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderActionService.get(id);
		}
		if (entity == null){
			entity = new OrderAction();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:order:orderAction:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderAction orderAction, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderAction> page = orderActionService.findPage(new Page<OrderAction>(request, response), orderAction); 
		model.addAttribute("page", page);
		return "modules/xiaodian/order/orderActionList";
	}

	@RequiresPermissions("xiaodian:order:orderAction:view")
	@RequestMapping(value = "form")
	public String form(OrderAction orderAction, Model model) {
		model.addAttribute("orderAction", orderAction);
		return "modules/xiaodian/order/orderActionForm";
	}

	@RequiresPermissions("xiaodian:order:orderAction:edit")
	@RequestMapping(value = "save")
	public String save(OrderAction orderAction, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderAction)){
			return form(orderAction, model);
		}
		orderActionService.save(orderAction);
		addMessage(redirectAttributes, "保存订单操作成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderAction/?repage";
	}
	
	@RequiresPermissions("xiaodian:order:orderAction:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderAction orderAction, RedirectAttributes redirectAttributes) {
		orderActionService.delete(orderAction);
		addMessage(redirectAttributes, "删除订单操作成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderAction/?repage";
	}


	@RequiresPermissions("xiaodian:order:orderAction:view")
	@RequestMapping(value = "genOrderId")
	@ResponseBody
	public String genOrderId(String opId) {

		if (StringUtils.isNotBlank(opId)) {
			return new ApiResp().success(CommonUtils.genId(opId));
		}
		return new ApiResp().success(CommonUtils.genId("000"));
	}

}