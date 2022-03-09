package com.haohan.platform.service.sys.modules.xiaodian.web.customer;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerEvaluateDetail;
import com.haohan.platform.service.sys.modules.xiaodian.service.customer.CustomerEvaluateDetailService;
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
 * 客户服务评价明细Controller
 * @author haohan
 * @version 2018-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/customer/customerEvaluateDetail")
public class CustomerEvaluateDetailController extends BaseController {

	@Autowired
	private CustomerEvaluateDetailService customerEvaluateDetailService;
	
	@ModelAttribute
	public CustomerEvaluateDetail get(@RequestParam(required=false) String id) {
		CustomerEvaluateDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerEvaluateDetailService.get(id);
		}
		if (entity == null){
			entity = new CustomerEvaluateDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:customer:customerEvaluateDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerEvaluateDetail customerEvaluateDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerEvaluateDetail> page = customerEvaluateDetailService.findPage(new Page<CustomerEvaluateDetail>(request, response), customerEvaluateDetail); 
		model.addAttribute("page", page);
		return "modules/xiaodian/customer/customerEvaluateDetailList";
	}

	@RequiresPermissions("xiaodian:customer:customerEvaluateDetail:view")
	@RequestMapping(value = "form")
	public String form(CustomerEvaluateDetail customerEvaluateDetail, Model model) {
		model.addAttribute("customerEvaluateDetail", customerEvaluateDetail);
		return "modules/xiaodian/customer/customerEvaluateDetailForm";
	}

	@RequiresPermissions("xiaodian:customer:customerEvaluateDetail:edit")
	@RequestMapping(value = "save")
	public String save(CustomerEvaluateDetail customerEvaluateDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerEvaluateDetail)){
			return form(customerEvaluateDetail, model);
		}
		customerEvaluateDetailService.save(customerEvaluateDetail);
		addMessage(redirectAttributes, "保存服务评价明细成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerEvaluateDetail/?repage";
	}
	
	@RequiresPermissions("xiaodian:customer:customerEvaluateDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerEvaluateDetail customerEvaluateDetail, RedirectAttributes redirectAttributes) {
		customerEvaluateDetailService.delete(customerEvaluateDetail);
		addMessage(redirectAttributes, "删除服务评价明细成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerEvaluateDetail/?repage";
	}

}