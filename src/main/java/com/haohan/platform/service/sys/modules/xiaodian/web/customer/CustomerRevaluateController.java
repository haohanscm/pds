package com.haohan.platform.service.sys.modules.xiaodian.web.customer;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.customer.CustomerRevaluate;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.customer.CustomerRevaluateService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 客户服务评价Controller
 * @author haohan
 * @version 2018-04-22
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/customer/customerRevaluate")
public class CustomerRevaluateController extends BaseController {

	@Autowired
	private CustomerRevaluateService customerRevaluateService;
	@Autowired
	private MerchantAppManageService merchantAppManageService;
	
	@ModelAttribute
	public CustomerRevaluate get(@RequestParam(required=false) String id) {
		CustomerRevaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerRevaluateService.get(id);
		}
		if (entity == null){
			entity = new CustomerRevaluate();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:customer:customerRevaluate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerRevaluate customerRevaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerRevaluate> page = customerRevaluateService.findPage(new Page<CustomerRevaluate>(request, response), customerRevaluate); 
		model.addAttribute("page", page);
		return "modules/xiaodian/customer/customerRevaluateList";
	}

	@RequiresPermissions("xiaodian:customer:customerRevaluate:view")
	@RequestMapping(value = "form")
	public String form(CustomerRevaluate customerRevaluate, Model model) {
		List<MerchantAppManage> merchantAppManageList = merchantAppManageService.findAllList();
		List<CustomerRevaluate> merchantAppList = new ArrayList<CustomerRevaluate>();

        for (MerchantAppManage m :merchantAppManageList) {
            CustomerRevaluate temp = new CustomerRevaluate();
            temp.setAppId(m.getAppId());
            temp.setAppName(m.getAppName());
            temp.setMerchantId(m.getMerchantId());
            temp.setMerchantName(m.getMerchantName());
            merchantAppList.add(temp);
        }

		model.addAttribute("merchantApps", merchantAppList);
		model.addAttribute("customerRevaluate", customerRevaluate);
		return "modules/xiaodian/customer/customerRevaluateForm";
	}

	@RequiresPermissions("xiaodian:customer:customerRevaluate:edit")
	@RequestMapping(value = "save")
	public String save(CustomerRevaluate customerRevaluate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerRevaluate)){
			return form(customerRevaluate, model);
		}
		customerRevaluateService.save(customerRevaluate);
		addMessage(redirectAttributes, "保存服务评价成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerRevaluate/?repage";
	}
	
	@RequiresPermissions("xiaodian:customer:customerRevaluate:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerRevaluate customerRevaluate, RedirectAttributes redirectAttributes) {
		customerRevaluateService.delete(customerRevaluate);
		addMessage(redirectAttributes, "删除服务评价成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/customer/customerRevaluate/?repage";
	}

}