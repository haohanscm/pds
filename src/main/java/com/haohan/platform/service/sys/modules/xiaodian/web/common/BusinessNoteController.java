package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.framework.utils.IpUtils;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.BusinessNote;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.BusinessNoteService;
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
import java.util.Date;

/**
 * 商务留言管理Controller
 * @author haohan
 * @version 2018-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/businessNote")
public class BusinessNoteController extends BaseController {

	@Autowired
	private BusinessNoteService businessNoteService;
	
	@ModelAttribute
	public BusinessNote get(@RequestParam(required=false) String id) {
		BusinessNote entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessNoteService.get(id);
		}
		if (entity == null){
			entity = new BusinessNote();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:businessNote:view")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessNote businessNote, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessNote> page = businessNoteService.findPage(new Page<BusinessNote>(request, response), businessNote); 
		model.addAttribute("page", page);
		return "modules/xiaodian/common/businessNoteList";
	}

	@RequiresPermissions("xiaodian:common:businessNote:view")
	@RequestMapping(value = "form")
	public String form(BusinessNote businessNote, Model model) {
		model.addAttribute("businessNote", businessNote);
		return "modules/xiaodian/common/businessNoteForm";
	}

	@RequiresPermissions("xiaodian:common:businessNote:edit")
	@RequestMapping(value = "save")
	public String save(BusinessNote businessNote, Model model, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, businessNote)){
			return form(businessNote, model);
		}
		businessNote.setRemarks(IpUtils.getRemoteIpAddr(request));
		businessNote.setCreateDate(new Date());
		businessNoteService.save(businessNote);
		addMessage(redirectAttributes, "保存商务留言成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/businessNote/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:businessNote:edit")
	@RequestMapping(value = "delete")
	public String delete(BusinessNote businessNote, RedirectAttributes redirectAttributes) {
		businessNoteService.delete(businessNote);
		addMessage(redirectAttributes, "删除商务留言成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/businessNote/?repage";
	}

}