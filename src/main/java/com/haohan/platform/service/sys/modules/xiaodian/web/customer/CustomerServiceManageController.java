package com.haohan.platform.service.sys.modules.xiaodian.web.customer;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerServiceManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.customer.CustomerServiceManageService;
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
 * 客户服务管理Controller
 * @author haohan
 * @version 2018-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/customer/customerServiceManage")
public class CustomerServiceManageController extends BaseController {

	@Autowired
	private CustomerServiceManageService customerServiceManageService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public CustomerServiceManage get(@RequestParam(required=false) String id) {
		CustomerServiceManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerServiceManageService.get(id);
		}
		if (entity == null){
			entity = new CustomerServiceManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:customer:customerServiceManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerServiceManage customerServiceManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerServiceManage> page = customerServiceManageService.findPage(new Page<CustomerServiceManage>(request, response), customerServiceManage); 
		model.addAttribute("page", page);
		return "modules/xiaodian/customer/customerServiceManageList";
	}

	@RequiresPermissions("xiaodian:customer:customerServiceManage:view")
	@RequestMapping(value = "form")
	public String form(CustomerServiceManage customerServiceManage, Model model) {
		List<Merchant> merchants = merchantService.findListEnabled(new Merchant());
		// 设置多选项
		if(customerServiceManage.getServiceContent()!=null){
			customerServiceManage.setServiceContents(customerServiceManage.getServiceContent().split(","));
		}
		model.addAttribute("merchants", merchants);
		model.addAttribute("customerServiceManage", customerServiceManage);
		return "modules/xiaodian/customer/customerServiceManageForm";
	}

	@RequiresPermissions("xiaodian:customer:customerServiceManage:edit")
	@RequestMapping(value = "save")
	public String save(CustomerServiceManage customerServiceManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerServiceManage)){
			return form(customerServiceManage, model);
		}
		// 设置多选项
		customerServiceManage.setServiceContent(StringUtils.join(customerServiceManage.getServiceContents(),","));
		customerServiceManageService.save(customerServiceManage);
		addMessage(redirectAttributes, "保存服务管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerServiceManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:customer:customerServiceManage:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerServiceManage customerServiceManage, RedirectAttributes redirectAttributes) {
		customerServiceManageService.delete(customerServiceManage);
		addMessage(redirectAttributes, "删除服务管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerServiceManage/?repage";
	}

}