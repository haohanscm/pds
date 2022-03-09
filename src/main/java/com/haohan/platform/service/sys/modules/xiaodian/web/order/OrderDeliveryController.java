package com.haohan.platform.service.sys.modules.xiaodian.web.order;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.service.order.OrderDeliveryApiService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IDeliveryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryPlan;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrder;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.GoodsOrderDetail;
import com.haohan.platform.service.sys.modules.xiaodian.entity.order.OrderDelivery;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryPlanService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderDetailService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.GoodsOrderService;
import com.haohan.platform.service.sys.modules.xiaodian.service.order.OrderDeliveryService;
import org.apache.commons.collections.CollectionUtils;
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
 * 订单配送信息管理Controller
 * @author haohan
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/order/orderDelivery")
public class OrderDeliveryController extends BaseController {

	@Autowired
	private OrderDeliveryService orderDeliveryService;
	@Autowired
	private OrderDeliveryApiService orderDeliveryApiService;
	@Autowired
	private GoodsOrderService goodsOrderService;
	@Autowired
	private GoodsOrderDetailService goodsOrderDetailService;
	@Autowired
	private DeliveryPlanService deliveryPlanService;

	@ModelAttribute
	public OrderDelivery get(@RequestParam(required=false) String id) {
		OrderDelivery entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderDeliveryService.get(id);
		}
		if (entity == null){
			entity = new OrderDelivery();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:order:orderDelivery:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderDelivery orderDelivery, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderDelivery> page = orderDeliveryService.findPage(new Page<OrderDelivery>(request, response), orderDelivery); 
		model.addAttribute("page", page);
		return "modules/xiaodian/order/orderDeliveryList";
	}

	@RequiresPermissions("xiaodian:order:orderDelivery:view")
	@RequestMapping(value = "form")
	public String form(OrderDelivery orderDelivery, Model model) {
		model.addAttribute("orderDelivery", orderDelivery);
		return "modules/xiaodian/order/orderDeliveryForm";
	}

	@RequiresPermissions("xiaodian:order:orderDelivery:edit")
	@RequestMapping(value = "save")
	public String save(OrderDelivery orderDelivery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderDelivery)){
			return form(orderDelivery, model);
		}
		orderDeliveryService.save(orderDelivery);
		addMessage(redirectAttributes, "保存订单配送信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderDelivery/?repage";
	}
	
	@RequiresPermissions("xiaodian:order:orderDelivery:edit")
	@RequestMapping(value = "delete")
	public String delete(OrderDelivery orderDelivery, RedirectAttributes redirectAttributes) {
		orderDeliveryService.delete(orderDelivery);
		addMessage(redirectAttributes, "删除订单配送信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderDelivery/?repage";
	}

	@RequiresPermissions("xiaodian:order:orderDelivery:edit")
	@RequestMapping(value = "genDeliveryPlan")
	public String genDeliveryPlan(String orderId,RedirectAttributes redirectAttributes){
		if (StringUtils.isNotEmpty(orderId)){
			GoodsOrder goodsOrder = goodsOrderService.fetchByOrderId(orderId);
			List<GoodsOrderDetail> details = goodsOrderDetailService.findByOrderId(orderId);
			OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
			//验证是否已生成计划
			DeliveryPlan deliveryPlan = new DeliveryPlan();
			deliveryPlan.setOrderId(orderId);
			List<DeliveryPlan> deliveryPlanList = deliveryPlanService.findList(deliveryPlan);
			if (CollectionUtils.isNotEmpty(deliveryPlanList)){
				//已有配送计划,先删除
				deliveryPlanService.deleteBatch(deliveryPlan);
			}

			//验证是否已有配送完成的计划
			deliveryPlan.setStatus(IDeliveryConstant.DeliveryStatus.success.getCode());
			Boolean isGenGifts = false;
			List<DeliveryPlan> finishPlanList = deliveryPlanService.findList(deliveryPlan);
			if (CollectionUtils.isEmpty(finishPlanList)){
				isGenGifts = true;
			}

			boolean genFlag = orderDeliveryApiService.formDeliveryPlan(goodsOrder,details,orderDelivery,isGenGifts);
			if (genFlag){
				redirectAttributes.addFlashAttribute("message","操作成功");
			}else {
				addMessage(redirectAttributes,"操作失败,请查看具体失败原因");
			}
			return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderDelivery/?repage";
		} else {
			addMessage(redirectAttributes,"操作失败,缺少请求参数");
			return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderDelivery/?repage";
		}

	}

	@RequiresPermissions("xiaodian:order:orderDelivery:edit")
	@RequestMapping(value = "emptyDeliveryPlan")
	public String emptyDeliveryPlan(String orderId,RedirectAttributes redirectAttributes){
		if (StringUtils.isNotEmpty(orderId)){
			OrderDelivery orderDelivery = orderDeliveryService.fetchByOrderId(orderId);
			//验证是否已生成计划
			DeliveryPlan deliveryPlan = new DeliveryPlan();
			deliveryPlan.setOrderId(orderId);
			List<DeliveryPlan> deliveryPlanList = deliveryPlanService.findList(deliveryPlan);
			if (CollectionUtils.isNotEmpty(deliveryPlanList)){
				//删除计划
				deliveryPlanService.deleteBatch(deliveryPlan);
				orderDelivery.setPlanGenStatus(IDeliveryConstant.PlanGenStatus.not_gen.getCode());
				orderDelivery.setPlanGenDesc("计划已删除");
				orderDeliveryService.save(orderDelivery);
			}
			return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderDelivery/?repage";
		} else {
			addMessage(redirectAttributes,"操作失败,缺少请求参数");
			return "redirect:"+Global.getAdminPath()+"/xiaodian/order/orderDelivery/?repage";
		}

	}

}