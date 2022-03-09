package com.haohan.platform.service.sys.modules.xiaodian.web.pay;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.pay.RefundManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.pay.RefundManageService;
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
 * 退款管理Controller
 * @author haohan
 * @version 2017-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/pay/refundManage")
public class RefundManageController extends BaseController {

	@Autowired
	private RefundManageService refundManageService;
	@Autowired
	private MerchantService merchantService;


	@ModelAttribute
	public RefundManage get(@RequestParam(required=false) String id) {
		RefundManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = refundManageService.get(id);
		}
		if (entity == null){
			entity = new RefundManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:pay:refundManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(RefundManage refundManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		String originPartnerId = refundManage.getPartnerId();
		Page<RefundManage> page = refundManageService.findPage(new Page<RefundManage>(request, response), refundManage);
		refundManage.setPartnerId(originPartnerId);
		model.addAttribute("page", page);
		return "modules/xiaodian/pay/refundManageList";
	}

	@RequiresPermissions("xiaodian:pay:refundManage:view")
	@RequestMapping(value = "form")
	public String form(RefundManage refundManage, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("refundManage", refundManage);
		model.addAttribute("merchantList",merchantList);
		return "modules/xiaodian/pay/refundManageForm";
	}

	@RequiresPermissions("xiaodian:pay:refundManage:edit")
	@RequestMapping(value = "save")
	public String save(RefundManage refundManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, refundManage)){
			return form(refundManage, model);
		}
		refundManageService.save(refundManage);
		addMessage(redirectAttributes, "保存退款成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/refundManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:pay:refundManage:edit")
	@RequestMapping(value = "delete")
	public String delete(RefundManage refundManage, RedirectAttributes redirectAttributes) {
		refundManageService.delete(refundManage);
		addMessage(redirectAttributes, "删除退款成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/pay/refundManage/?repage";
	}


}