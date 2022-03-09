package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverManManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliverManManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送员管理Controller
 * @author yu.shen
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/deliverManManage")
public class DeliverManManageController extends BaseController {

	@Autowired
	private DeliverManManageService deliverManManageService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public DeliverManManage get(@RequestParam(required=false) String id) {
		DeliverManManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deliverManManageService.get(id);
		}
		if (entity == null){
			entity = new DeliverManManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:deliverManManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeliverManManage deliverManManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeliverManManage> page = deliverManManageService.findPage(new Page<DeliverManManage>(request, response), deliverManManage); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/deliverManManageList";
	}

	@RequiresPermissions("xiaodian:delivery:deliverManManage:view")
	@RequestMapping(value = "form")
	public String form(DeliverManManage deliverManManage, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("deliverManManage", deliverManManage);
		return "modules/xiaodian/delivery/deliverManManageForm";
	}

	@RequiresPermissions("xiaodian:delivery:deliverManManage:edit")
	@RequestMapping(value = "save")
	public String save(DeliverManManage deliverManManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deliverManManage)){
			return form(deliverManManage, model);
		}
		deliverManManageService.save(deliverManManage);
		addMessage(redirectAttributes, "保存配送员成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliverManManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:deliverManManage:edit")
	@RequestMapping(value = "delete")
	public String delete(DeliverManManage deliverManManage, RedirectAttributes redirectAttributes) {
		deliverManManageService.delete(deliverManManage);
		addMessage(redirectAttributes, "删除配送员成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliverManManage/?repage";
	}

	@RequiresPermissions(value = {"xiaodian:delivery:deliverManManage:view","xiaodian:manage:merchant:delivery:view"}, logical = Logical.OR)
	@RequestMapping(value = "query")
	@ResponseBody
	public List<Map<String, Object>> query(@RequestParam("merchantId") String merchantId, @RequestParam("shopId")String shopId) {
		DeliverManManage man = new DeliverManManage();
		man.setMerchantId(merchantId);
		man.setShopId(shopId);
		List<DeliverManManage> manList = deliverManManageService.findList(man);
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> obj;
		for(DeliverManManage m:manList){
			obj = new HashMap<>();
			obj.put("id",m.getId());
			obj.put("name",m.getRealName());
			list.add(obj);
		}
		return list;
	}

	@RequiresPermissions("xiaodian:delivery:deliverManManage:edit")
	@RequestMapping(value = "fetchDeliverManList")
	@ResponseBody
	public List<DeliverManManage> fetchDeliverManList(HttpServletRequest request){
		DeliverManManage deliverManManage = new DeliverManManage();
		List<DeliverManManage> list = deliverManManageService.findList(deliverManManage);
		return list;
	}

}