package com.haohan.platform.service.sys.modules.pss.web.procure;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;
import com.haohan.platform.service.sys.modules.pss.service.procure.PurchaseReturnService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
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
 * 采购退货Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/procure/purchaseReturn")
public class PurchaseReturnController extends BaseController {

	@Autowired
	private PurchaseReturnService purchaseReturnService;
	@Resource
	private MerchantService merchantService;
	
	@ModelAttribute
	public PurchaseReturn get(@RequestParam(required=false) String id) {
		PurchaseReturn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaseReturnService.get(id);
		}
		if (entity == null){
			entity = new PurchaseReturn();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:procure:purchaseReturn:view")
	@RequestMapping(value = {"list", ""})
	public String list(PurchaseReturn purchaseReturn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseReturn> page = purchaseReturnService.findPage(new Page<PurchaseReturn>(request, response), purchaseReturn);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("page", page);
		return "modules/pss/procure/purchaseReturnList";
	}

	@RequiresPermissions("pss:procure:purchaseReturn:view")
	@RequestMapping(value = "form")
	public String form(PurchaseReturn purchaseReturn, Model model) {
		model.addAttribute("purchaseReturn", purchaseReturn);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		return "modules/pss/procure/purchaseReturnForm";
	}

	@RequiresPermissions("pss:procure:purchaseReturn:edit")
	@RequestMapping(value = "save")
	public String save(PurchaseReturn purchaseReturn, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, purchaseReturn)){
			return form(purchaseReturn, model);
		}
		purchaseReturnService.save(purchaseReturn);
		addMessage(redirectAttributes, "保存采购退货成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/purchaseReturn/?repage";
	}
	
	@RequiresPermissions("pss:procure:purchaseReturn:edit")
	@RequestMapping(value = "delete")
	public String delete(PurchaseReturn purchaseReturn, RedirectAttributes redirectAttributes) {
		purchaseReturnService.delete(purchaseReturn);
		addMessage(redirectAttributes, "删除采购退货成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/purchaseReturn/?repage";
	}

}