package com.haohan.platform.service.sys.modules.xiaodian.web.customer;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerProjectManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.customer.CustomerProjectManageService;
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
 * 客户项目管理Controller
 * @author haohan
 * @version 2018-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/customer/customerProjectManage")
public class CustomerProjectManageController extends BaseController {

	@Autowired
	private CustomerProjectManageService customerProjectManageService;
	
	@ModelAttribute
	public CustomerProjectManage get(@RequestParam(required=false) String id) {
		CustomerProjectManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerProjectManageService.get(id);
		}
		if (entity == null){
			entity = new CustomerProjectManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:customer:customerProjectManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerProjectManage customerProjectManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerProjectManage> page = customerProjectManageService.findPage(new Page<CustomerProjectManage>(request, response), customerProjectManage); 
		model.addAttribute("page", page);
		return "modules/xiaodian/customer/customerProjectManageList";
	}

	@RequiresPermissions("xiaodian:customer:customerProjectManage:view")
	@RequestMapping(value = "form")
	public String form(CustomerProjectManage customerProjectManage, Model model) {
		if(customerProjectManage.getServiceList()!=null){
			customerProjectManage.setServiceLists(customerProjectManage.getServiceList().split(","));
		}
	    if(customerProjectManage.getProjectInfo()!=null){
			customerProjectManage.setProjectInfos(customerProjectManage.getProjectInfo().split(","));
		}
		model.addAttribute("customerProjectManage", customerProjectManage);
		return "modules/xiaodian/customer/customerProjectManageForm";
	}

	@RequiresPermissions("xiaodian:customer:customerProjectManage:edit")
	@RequestMapping(value = "save")
	public String save(CustomerProjectManage customerProjectManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerProjectManage)){
			return form(customerProjectManage, model);
		}
		// 设置多选
		customerProjectManage.setServiceList(StringUtils.join(customerProjectManage.getServiceLists(),","));
		customerProjectManage.setProjectInfo(StringUtils.join(customerProjectManage.getProjectInfos(),","));
		if(customerProjectManage.getSignTime()!=null) {
			customerProjectManage.setQuarter(DateUtils.getYearSeason(customerProjectManage.getSignTime()));
		}
		customerProjectManageService.save(customerProjectManage);
		addMessage(redirectAttributes, "保存项目管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerProjectManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:customer:customerProjectManage:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerProjectManage customerProjectManage, RedirectAttributes redirectAttributes) {
		customerProjectManageService.delete(customerProjectManage);
		addMessage(redirectAttributes, "删除项目管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerProjectManage/?repage";
	}

}