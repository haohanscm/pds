package com.haohan.platform.service.sys.modules.pss.web.procure;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturnDetail;
import com.haohan.platform.service.sys.modules.pss.service.procure.PurchaseReturnDetailService;
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
 * 采购退货明细Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/procure/purchaseReturnDetail")
public class PurchaseReturnDetailController extends BaseController {

	@Autowired
	private PurchaseReturnDetailService purchaseReturnDetailService;
	
	@ModelAttribute
	public PurchaseReturnDetail get(@RequestParam(required=false) String id) {
		PurchaseReturnDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaseReturnDetailService.get(id);
		}
		if (entity == null){
			entity = new PurchaseReturnDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:procure:purchaseReturnDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(PurchaseReturnDetail purchaseReturnDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaseReturnDetail> page = purchaseReturnDetailService.findPage(new Page<PurchaseReturnDetail>(request, response), purchaseReturnDetail); 
		model.addAttribute("page", page);
		return "modules/pss/procure/purchaseReturnDetailList";
	}

	@RequiresPermissions("pss:procure:purchaseReturnDetail:view")
	@RequestMapping(value = "form")
	public String form(PurchaseReturnDetail purchaseReturnDetail, Model model) {
		model.addAttribute("purchaseReturnDetail", purchaseReturnDetail);
		return "modules/pss/procure/purchaseReturnDetailForm";
	}

	@RequiresPermissions("pss:procure:purchaseReturnDetail:edit")
	@RequestMapping(value = "save")
	public String save(PurchaseReturnDetail purchaseReturnDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, purchaseReturnDetail)){
			return form(purchaseReturnDetail, model);
		}
		purchaseReturnDetailService.save(purchaseReturnDetail);
		addMessage(redirectAttributes, "保存采购退货明细成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/purchaseReturnDetail/?repage";
	}
	
	@RequiresPermissions("pss:procure:purchaseReturnDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(PurchaseReturnDetail purchaseReturnDetail, RedirectAttributes redirectAttributes) {
		purchaseReturnDetailService.delete(purchaseReturnDetail);
		addMessage(redirectAttributes, "删除采购退货明细成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/purchaseReturnDetail/?repage";
	}

}