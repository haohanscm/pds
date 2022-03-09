package com.haohan.platform.service.sys.modules.pss.web.procure;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.constant.IPssConstant;
import com.haohan.platform.service.sys.modules.pss.api.inf.IPssGoodsStorageService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.pss.entity.procure.Procurement;
import com.haohan.platform.service.sys.modules.pss.entity.procure.ProcurementDetail;
import com.haohan.platform.service.sys.modules.pss.entity.info.Supplier;
import com.haohan.platform.service.sys.modules.pss.entity.info.PssWarehouse;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.pss.service.procure.ProcurementDetailService;
import com.haohan.platform.service.sys.modules.pss.service.procure.ProcurementService;
import com.haohan.platform.service.sys.modules.pss.service.info.SupplierService;
import com.haohan.platform.service.sys.modules.pss.service.info.WarehouseService;
import org.apache.commons.collections.CollectionUtils;
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
 * 商品采购Controller
 * @author haohan
 * @version 2018-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/procure/procurement")
public class ProcurementController extends BaseController {

	@Autowired
	private ProcurementService procurementService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private WarehouseService warehouseService;
	@Resource
	private IPssGoodsStorageService mercGoodsStorageService;
	@Resource
	private ProcurementDetailService procurementDetailService;
	
	@ModelAttribute
	public Procurement get(@RequestParam(required=false) String id) {
		Procurement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = procurementService.get(id);
		}
		if (entity == null){
			entity = new Procurement();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:procure:procurement:view")
	@RequestMapping(value = {"list", ""})
	public String list(Procurement procurement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Procurement> page = procurementService.findPage(new Page<Procurement>(request, response), procurement);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		List<Supplier> pssSupplierList = supplierService.findList(new Supplier());
		model.addAttribute("pssSupplierList",pssSupplierList);
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("page", page);
		return "modules/pss/procure/procurementList";
	}

	@RequiresPermissions("pss:procure:procurement:view")
	@RequestMapping(value = "form")
	public String form(Procurement procurement, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		List<Supplier> pssSupplierList = supplierService.findList(new Supplier());
		List<PssWarehouse> warehouseList = warehouseService.findList(new PssWarehouse());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("pssSupplierList",pssSupplierList);
		model.addAttribute("warehouseList",warehouseList);
		model.addAttribute("procurement", procurement);
		return "modules/pss/procure/procurementForm";
	}

	@RequiresPermissions("pss:procure:procurement:edit")
	@RequestMapping(value = "save")
	public String save(Procurement procurement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, procurement)){
			return form(procurement, model);
		}
		procurementService.save(procurement);
		addMessage(redirectAttributes, "保存商品采购成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/procurement/?repage";
	}
	
	@RequiresPermissions("pss:procure:procurement:edit")
	@RequestMapping(value = "delete")
	public String delete(Procurement procurement, RedirectAttributes redirectAttributes) {
		procurementService.delete(procurement);
		addMessage(redirectAttributes, "删除商品采购成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/procurement/?repage";
	}

	@RequiresPermissions("pss:procure:procurement:edit")
	@RequestMapping(value = "enterStock")
	public String enterStock(Procurement procurement, RedirectAttributes redirectAttributes) {
		BaseResp baseResp = BaseResp.newError();
		String warehouseId = procurement.getWarehouseId();
		if (StringUtils.isEmpty(warehouseId)){
			redirectAttributes.addFlashAttribute("tip_message", "操作失败,请先选择仓库");
			return "redirect:"+Global.getAdminPath()+"/pss/procure/procurement/?repage";
		}
		List<ProcurementDetail> detailList = procurementDetailService.findByProcureNumWithStatus(procurement.getProcureNum(),IPssConstant.StockStatus.not_in);
		if (CollectionUtils.isEmpty(detailList)){
			baseResp.setMsg("未发现待入库的进货明细");
			redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
			return "redirect:"+Global.getAdminPath()+"/pss/procure/procurement/?repage";
		}
		for (ProcurementDetail detail : detailList){
			baseResp = mercGoodsStorageService.procureEnterStock(procurement,detail);
			procurementDetailService.save(detail);
			if (!baseResp.isSuccess()){
				redirectAttributes.addFlashAttribute("tip_message", baseResp.getMsg());
				return "redirect:"+Global.getAdminPath()+"/pss/procure/procurement/?repage";
			}else {
				procurement.setStockStatus(IPssConstant.StockStatus.entered.getCode());
			}
		}
		procurementService.save(procurement);
		redirectAttributes.addFlashAttribute("tip_message", "入库成功");
		return "redirect:"+Global.getAdminPath()+"/pss/procure/procurement/?repage";
	}

}