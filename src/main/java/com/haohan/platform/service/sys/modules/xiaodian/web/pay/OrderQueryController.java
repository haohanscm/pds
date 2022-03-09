package com.haohan.platform.service.sys.modules.xiaodian.web.pay;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.OrderQuery;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.OrderQueryService;
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
 * 交易状态查询Controller
 * @author haohan
 * @version 2017-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/pay/orderQuery")
public class OrderQueryController extends BaseController {

	@Autowired
	private OrderQueryService orderQueryService;
	
	@ModelAttribute
	public OrderQuery get(@RequestParam(required=false) String id) {
		OrderQuery entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderQueryService.get(id);
		}
		if (entity == null){
			entity = new OrderQuery();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:pay:orderQuery:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderQuery orderQuery, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderQuery> page = orderQueryService.findPage(new Page<OrderQuery>(request, response), orderQuery); 
		model.addAttribute("page", page);
		return "modules/xiaodian/pay/orderQueryList";
	}

	@RequiresPermissions("xiaodian:pay:orderQuery:view")
	@RequestMapping(value = "form")
	public String form(OrderQuery orderQuery, Model model) {
		model.addAttribute("orderQuery", orderQuery);
		return "modules/xiaodian/pay/orderQueryForm";
	}

	@RequiresPermissions("xiaodian:pay:orderQuery:edit")
	@RequestMapping(value = "save")
	public String save(OrderQuery orderQuery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderQuery)){
			return form(orderQuery, model);
		}
		orderQueryService.save(orderQuery);
		addMessage(redirectAttributes, "保存交易信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/orderQuery/?repage";
	}
	
	@RequiresPermissions("xiaodian:pay:orderQuery:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderQuery orderQuery, RedirectAttributes redirectAttributes) {
		orderQueryService.delete(orderQuery);
		addMessage(redirectAttributes, "删除交易信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/orderQuery/?repage";
	}


}