package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pds.service.business.PdsBuyerService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.MerchantEmployeeService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 员工管理Controller
 * @author haohan
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/merchantEmployee")
public class MerchantEmployeeController extends BaseController {

	@Autowired
	private MerchantEmployeeService merchantEmployeeService;
    @Autowired
    private PdsBuyerService pdsBuyerService;
	@Resource
	private MerchantService merchantService;
	
	@ModelAttribute
	public MerchantEmployee get(@RequestParam(required=false) String id) {
		MerchantEmployee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantEmployeeService.get(id);
		}
		if (entity == null){
			entity = new MerchantEmployee();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:merchantEmployee:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantEmployee merchantEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantEmployee> page = new Page<MerchantEmployee>(request, response);
		merchantEmployee.setPage(page);
		page.setList(merchantEmployeeService.findJoinList(merchantEmployee));
		model.addAttribute("page", page);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
		return "modules/xiaodian/common/merchantEmployeeList";
	}

	@RequiresPermissions("xiaodian:common:merchantEmployee:view")
	@RequestMapping(value = "form")
	public String form(MerchantEmployee merchantEmployee, Model model) {
		Merchant merchant = new Merchant();
		merchant.setStatus(StringUtils.toInteger(IMerchantConstant.available));
		List<Merchant> list = merchantService.findListEnabled(merchant);
		model.addAttribute("merchantList", list);
        List<Merchant> pmList = pdsBuyerService.findPlatformMerchantList();
        model.addAttribute("pmList", pmList);
		model.addAttribute("merchantEmployee", merchantEmployee);
		return "modules/xiaodian/common/merchantEmployeeForm";
	}

	@RequiresPermissions("xiaodian:common:merchantEmployee:edit")
	@RequestMapping(value = "save")
	public String save(MerchantEmployee merchantEmployee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantEmployee)){
			return form(merchantEmployee, model);
		}
		merchantEmployeeService.save(merchantEmployee);
		addMessage(redirectAttributes, "保存员工管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/merchantEmployee/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:merchantEmployee:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantEmployee merchantEmployee, RedirectAttributes redirectAttributes) {
		merchantEmployeeService.delete(merchantEmployee);
		addMessage(redirectAttributes, "删除员工管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/merchantEmployee/?repage";
	}

}