package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.LogApiRecord;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.LogApiRecordService;
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
 * 接口日志记录Controller
 * @author haohan
 * @version 2018-05-29
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/logApiRecord")
public class LogApiRecordController extends BaseController {

	@Autowired
	private LogApiRecordService logApiRecordService;
	
	@ModelAttribute
	public LogApiRecord get(@RequestParam(required=false) String id) {
		LogApiRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logApiRecordService.get(id);
		}
		if (entity == null){
			entity = new LogApiRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:logApiRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(LogApiRecord logApiRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LogApiRecord> page = logApiRecordService.findPage(new Page<LogApiRecord>(request, response), logApiRecord); 
		model.addAttribute("page", page);
		return "modules/xiaodian/common/logApiRecordList";
	}

	@RequiresPermissions("xiaodian:common:logApiRecord:view")
	@RequestMapping(value = "form")
	public String form(LogApiRecord logApiRecord, Model model) {
		model.addAttribute("logApiRecord", logApiRecord);
		return "modules/xiaodian/common/logApiRecordForm";
	}

	@RequiresPermissions("xiaodian:common:logApiRecord:edit")
	@RequestMapping(value = "save")
	public String save(LogApiRecord logApiRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logApiRecord)){
			return form(logApiRecord, model);
		}
		logApiRecordService.save(logApiRecord);
		addMessage(redirectAttributes, "保存接口日志管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/logApiRecord/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:logApiRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(LogApiRecord logApiRecord, RedirectAttributes redirectAttributes) {
		logApiRecordService.delete(logApiRecord);
		addMessage(redirectAttributes, "删除接口日志管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/logApiRecord/?repage";
	}

}