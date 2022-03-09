package com.haohan.platform.service.sys.modules.pss.web.procure;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import com.haohan.platform.service.sys.modules.pss.service.procure.ProcurementDetailService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品采购明细Controller
 * @author haohan
 * @version 2018-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/procure/procurementDetail")
public class ProcurementDetailController extends BaseController {

	@Autowired
	private ProcurementDetailService procurementDetailService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public ProcurementDetail get(@RequestParam(required=false) String id) {
		ProcurementDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = procurementDetailService.get(id);
		}
		if (entity == null){
			entity = new ProcurementDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:procure:procurementDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProcurementDetail procurementDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProcurementDetail> page = procurementDetailService.findPage(new Page<ProcurementDetail>(request, response), procurementDetail);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("page", page);
		return "modules/pss/procure/procurementDetailList";
	}

	@RequiresPermissions("pss:procure:procurementDetail:view")
	@RequestMapping(value = "form")
	public String form(ProcurementDetail procurementDetail, Model model) {
		model.addAttribute("procurementDetail", procurementDetail);
		return "modules/pss/procure/procurementDetailForm";
	}

	@RequiresPermissions("pss:procure:procurementDetail:edit")
	@RequestMapping(value = "save")
	public String save(ProcurementDetail procurementDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, procurementDetail)){
			return form(procurementDetail, model);
		}
		procurementDetailService.save(procurementDetail);
		addMessage(redirectAttributes, "保存商品采购明细成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/procurementDetail/?repage";
	}
	
	@RequiresPermissions("pss:procure:procurementDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(ProcurementDetail procurementDetail, RedirectAttributes redirectAttributes) {
		procurementDetailService.delete(procurementDetail);
		addMessage(redirectAttributes, "删除商品采购明细成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/procurementDetail/?repage";
	}

}