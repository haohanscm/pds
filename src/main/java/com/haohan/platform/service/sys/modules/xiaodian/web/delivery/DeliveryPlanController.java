package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IDeliveryConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverManManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryPlan;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params.DeliveryReplaceReq;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliverManManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliveryPlanService;
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
 * 配送计划Controller
 * @author yu.shen
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/deliveryPlan")
public class DeliveryPlanController extends BaseController {

	@Autowired
	private DeliveryPlanService deliveryPlanService;
	@Autowired
	private DeliverManManageService deliverManManageService;
	
	@ModelAttribute
	public DeliveryPlan get(@RequestParam(required=false) String id) {
		DeliveryPlan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deliveryPlanService.get(id);
		}
		if (entity == null){
			entity = new DeliveryPlan();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:deliveryPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeliveryPlan deliveryPlan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeliveryPlan> page = deliveryPlanService.findPage(new Page<DeliveryPlan>(request, response), deliveryPlan); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/deliveryPlanList";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryPlan:view")
	@RequestMapping(value = "form")
	public String form(DeliveryPlan deliveryPlan, Model model) {
		model.addAttribute("deliveryPlan", deliveryPlan);
		return "modules/xiaodian/delivery/deliveryPlanForm";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryPlan:edit")
	@RequestMapping(value = "save")
	public String save(DeliveryPlan deliveryPlan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deliveryPlan)){
			return form(deliveryPlan, model);
		}
		deliveryPlanService.save(deliveryPlan);
		addMessage(redirectAttributes, "保存配送计划成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryPlan/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:deliveryPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(DeliveryPlan deliveryPlan, RedirectAttributes redirectAttributes) {
		deliveryPlanService.delete(deliveryPlan);
		addMessage(redirectAttributes, "删除配送计划成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryPlan/?repage";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryPlan:edit")
	@RequestMapping(value = "copy")
	public String copy(DeliveryPlan deliveryPlan, RedirectAttributes redirectAttributes) {
        if(!StringUtils.isEmpty(deliveryPlan.getId())){
            deliveryPlan.setId(null);
            deliveryPlanService.save(deliveryPlan);
        }
		addMessage(redirectAttributes, "复制配送计划成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryPlan/?repage";
	}

	@RequiresPermissions("xiaodian:delivery:deliveryPlan:edit")
	@RequestMapping(value = "replaceDeliveryMan")
	public String replaceDeliveryMan(DeliveryReplaceReq replaceReq,Model model,RedirectAttributes redirectAttributes){
		String newDeliverManId = replaceReq.getNewDeliveryManId();
		DeliverManManage newDeliverManManage = deliverManManageService.get(newDeliverManId);
		if (null != newDeliverManManage){
			DeliveryPlan deliveryPlan = new DeliveryPlan();
			deliveryPlan.setDeliveryManId(replaceReq.getOrgDeliveryManId());
			deliveryPlan.setBeginDate(replaceReq.getStartDate());
			deliveryPlan.setEndDate(replaceReq.getEndDate());
			deliveryPlan.setStatus(IDeliveryConstant.DeliveryStatus.wait.getCode());
			List<DeliveryPlan> list = deliveryPlanService.findList(deliveryPlan);
			if (CollectionUtils.isEmpty(list)){
				//提示该配送员没有要配送的订单
				addMessage(redirectAttributes,"该配送员没有配送计划");
				return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryPlan/?repage";
			}
			for (DeliveryPlan plan : list){
				deliveryPlanService.replaceDeliveryMan(plan,newDeliverManManage);
			}
			addMessage(redirectAttributes,"变更配送员成功!");
		}
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliveryPlan/?repage";
	}

}