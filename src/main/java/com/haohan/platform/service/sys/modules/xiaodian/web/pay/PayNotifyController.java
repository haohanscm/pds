package com.haohan.platform.service.sys.modules.xiaodian.web.pay;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.PayNotify;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.PayNotifyService;
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
 * 支付结果通知Controller
 * @author haohan
 * @version 2017-12-07
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/pay/payNotify")
public class PayNotifyController extends BaseController {

	@Autowired
	private PayNotifyService payNotifyService;
	
	@ModelAttribute
	public PayNotify get(@RequestParam(required=false) String id) {
		PayNotify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = payNotifyService.get(id);
		}
		if (entity == null){
			entity = new PayNotify();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:pay:payNotify:view")
	@RequestMapping(value = {"list", ""})
	public String list(PayNotify payNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayNotify> page = payNotifyService.findPage(new Page<PayNotify>(request, response), payNotify); 
		model.addAttribute("page", page);
		return "modules/xiaodian/pay/payNotifyList";
	}

	@RequiresPermissions("xiaodian:pay:payNotify:view")
	@RequestMapping(value = "form")
	public String form(PayNotify payNotify, Model model) {
		model.addAttribute("payNotify", payNotify);
		return "modules/xiaodian/pay/payNotifyForm";
	}

	@RequiresPermissions("xiaodian:pay:payNotify:edit")
	@RequestMapping(value = "save")
	public String save(PayNotify payNotify, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, payNotify)){
			return form(payNotify, model);
		}
		payNotifyService.save(payNotify);
		addMessage(redirectAttributes, "保存支付结果成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/payNotify/?repage";
	}
	
	@RequiresPermissions("xiaodian:pay:payNotify:edit")
	@RequestMapping(value = "delete")
	public String delete(PayNotify payNotify, RedirectAttributes redirectAttributes) {
		payNotifyService.delete(payNotify);
		addMessage(redirectAttributes, "删除支付结果成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/payNotify/?repage";
	}

}