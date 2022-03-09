package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoGroupManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoGroupManageService;
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
 * 图片组管理Controller
 * @author haohan
 * @version 2018-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/photoGroupManage")
public class PhotoGroupManageController extends BaseController {

	@Autowired
	private PhotoGroupManageService photoGroupManageService;
	
	@ModelAttribute
	public PhotoGroupManage get(@RequestParam(required=false) String id) {
		PhotoGroupManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = photoGroupManageService.get(id);
		}
		if (entity == null){
			entity = new PhotoGroupManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:photoGroupManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(PhotoGroupManage photoGroupManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PhotoGroupManage> page = photoGroupManageService.findPage(new Page<PhotoGroupManage>(request, response), photoGroupManage); 
		model.addAttribute("page", page);
		return "modules/xiaodian/common/photoGroupManageList";
	}

	@RequiresPermissions("xiaodian:common:photoGroupManage:view")
	@RequestMapping(value = "form")
	public String form(PhotoGroupManage photoGroupManage, Model model) {
		model.addAttribute("photoGroupManage", photoGroupManage);
		return "modules/xiaodian/common/photoGroupManageForm";
	}

	@RequiresPermissions("xiaodian:common:photoGroupManage:edit")
	@RequestMapping(value = "save")
	public String save(PhotoGroupManage photoGroupManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, photoGroupManage)){
			return form(photoGroupManage, model);
		}
		photoGroupManageService.save(photoGroupManage);
		addMessage(redirectAttributes, "保存图片组成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/photoGroupManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:photoGroupManage:edit")
	@RequestMapping(value = "delete")
	public String delete(PhotoGroupManage photoGroupManage, RedirectAttributes redirectAttributes) {
		photoGroupManageService.delete(photoGroupManage);
		addMessage(redirectAttributes, "删除图片组成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/photoGroupManage/?repage";
	}

}