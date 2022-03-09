package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.UnionBankNo;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.UnionBankNoService;
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
 * 银行行号管理Controller
 * @author haohan
 * @version 2017-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/unionBankNo")
public class UnionBankNoController extends BaseController {

	@Autowired
	private UnionBankNoService unionBankNoService;
	
	@ModelAttribute
	public UnionBankNo get(@RequestParam(required=false) String id) {
		UnionBankNo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = unionBankNoService.get(id);
		}
		if (entity == null){
			entity = new UnionBankNo();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:unionBankNo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UnionBankNo unionBankNo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UnionBankNo> page = unionBankNoService.findPage(new Page<UnionBankNo>(request, response), unionBankNo); 
		model.addAttribute("page", page);
		return "modules/xiaodian/common/unionBankNoList";
	}

	@RequiresPermissions("xiaodian:common:unionBankNo:view")
	@RequestMapping(value = "form")
	public String form(UnionBankNo unionBankNo, Model model) {
		model.addAttribute("unionBankNo", unionBankNo);
		return "modules/xiaodian/common/unionBankNoForm";
	}

	@RequiresPermissions("xiaodian:common:unionBankNo:edit")
	@RequestMapping(value = "save")
	public String save(UnionBankNo unionBankNo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, unionBankNo)){
			return form(unionBankNo, model);
		}
		unionBankNoService.save(unionBankNo);
		addMessage(redirectAttributes, "保存联银行号成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/unionBankNo/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:unionBankNo:edit")
	@RequestMapping(value = "delete")
	public String delete(UnionBankNo unionBankNo, RedirectAttributes redirectAttributes) {
		unionBankNoService.delete(unionBankNo);
		addMessage(redirectAttributes, "删除联银行号成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/unionBankNo/?repage";
	}

}