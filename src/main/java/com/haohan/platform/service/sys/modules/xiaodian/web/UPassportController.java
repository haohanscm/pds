package com.haohan.platform.service.sys.modules.xiaodian.web;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
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
 * 通行证管理Controller
 * @author haohan
 * @version 2017-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/uPassport")
public class UPassportController extends BaseController {

	@Autowired
	private UPassportService uPassportService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public UPassport get(@RequestParam(required=false) String id) {
		UPassport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = uPassportService.get(id);
		}
		if (entity == null){
			entity = new UPassport();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:uPassport:view")
	@RequestMapping(value = {"list", ""})
	public String list(UPassport uPassport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UPassport> page = uPassportService.findPage(new Page<UPassport>(request, response), uPassport); 
		model.addAttribute("page", page);
		return "modules/xiaodian/uPassportList";
	}

	@RequiresPermissions("xiaodian:uPassport:view")
	@RequestMapping(value = "form")
	public String form(UPassport uPassport, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("uPassport", uPassport);
		return "modules/xiaodian/uPassportForm";
	}

	@RequiresPermissions("xiaodian:uPassport:edit")
	@RequestMapping(value = "save")
	public String save(UPassport uPassport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, uPassport)){
			return form(uPassport, model);
		}
		uPassportService.save(uPassport);
		addMessage(redirectAttributes, "保存通行证成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/uPassport/?repage";
	}
	
	@RequiresPermissions("xiaodian:uPassport:edit")
	@RequestMapping(value = "delete")
	public String delete(UPassport uPassport, RedirectAttributes redirectAttributes) {
		uPassportService.delete(uPassport);
		addMessage(redirectAttributes, "删除通行证成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/uPassport/?repage";
	}

}