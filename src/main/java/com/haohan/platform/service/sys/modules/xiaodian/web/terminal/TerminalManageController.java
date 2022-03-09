package com.haohan.platform.service.sys.modules.xiaodian.web.terminal;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.teiminal.TerminalManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopService;
import com.haohan.platform.service.sys.modules.xiaodian.service.terminal.TerminalManageService;
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
 *
 * 终端设备Controller
 * @author shenyu
 * @version 2018-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/terminal/terminalManage")
public class TerminalManageController extends BaseController {

	@Autowired
	private TerminalManageService terminalManageService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ShopService shopService;
	
	@ModelAttribute
	public TerminalManage get(@RequestParam(required=false) String id) {
		TerminalManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = terminalManageService.get(id);
		}
		if (entity == null){
			entity = new TerminalManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:terminal:terminalManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(TerminalManage terminalManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TerminalManage> page = terminalManageService.findPage(new Page<TerminalManage>(request, response), terminalManage);
		model.addAttribute("page", page);
		return "modules/xiaodian/terminal/terminalManageList";
	}

	@RequiresPermissions("xiaodian:terminal:terminalManage:view")
	@RequestMapping(value = "form")
	public String form(TerminalManage terminalManage, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("terminalManage", terminalManage);
		return "modules/xiaodian/terminal/terminalManageForm";
	}

	@RequiresPermissions("xiaodian:terminal:terminalManage:edit")
	@RequestMapping(value = "save")
	public String save(TerminalManage terminalManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, terminalManage)){
			return form(terminalManage, model);
		}
		terminalManageService.save(terminalManage);
		addMessage(redirectAttributes, "保存设备成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/terminal/terminalManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:terminal:terminalManage:edit")
	@RequestMapping(value = "delete")
	public String delete(TerminalManage terminalManage, RedirectAttributes redirectAttributes) {
		terminalManageService.delete(terminalManage);
		addMessage(redirectAttributes, "删除设备成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/terminal/terminalManage/?repage";
	}

}