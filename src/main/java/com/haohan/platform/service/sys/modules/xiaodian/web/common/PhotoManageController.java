package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.PhotoManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.PhotoManageService;
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

/**
 * 图片资源关系Controller
 * @author haohan
 * @version 2018-01-12
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/photoManage")
public class PhotoManageController extends BaseController {

	@Autowired
	private PhotoManageService photoManageService;
	
	@ModelAttribute
	public PhotoManage get(@RequestParam(required=false) String id) {
		PhotoManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = photoManageService.get(id);
		}
		if (entity == null){
			entity = new PhotoManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:photoManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(PhotoManage photoManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PhotoManage> page = photoManageService.findPage(new Page<PhotoManage>(request, response), photoManage); 
		model.addAttribute("page", page);
		return "modules/xiaodian/common/photoManageList";
	}

	@RequiresPermissions("xiaodian:common:photoManage:view")
	@RequestMapping(value = "form")
	public String form(PhotoManage photoManage, Model model) {
		model.addAttribute("photoManage", photoManage);
		return "modules/xiaodian/common/photoManageForm";
	}

	@RequiresPermissions("xiaodian:common:photoManage:edit")
	@RequestMapping(value = "save")
	public String save(PhotoManage photoManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, photoManage)){
			return form(photoManage, model);
		}
		photoManageService.save(photoManage);
		addMessage(redirectAttributes, "保存图片成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/photoManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:photoManage:edit")
	@RequestMapping(value = "delete")
	public String delete(PhotoManage photoManage, RedirectAttributes redirectAttributes) {
		photoManageService.delete(photoManage);
		addMessage(redirectAttributes, "删除图片成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/photoManage/?repage";
	}

	@RequiresPermissions("xiaodian:common:photoManage:edit")
	@RequestMapping(value = "deleteById")
	@ResponseBody
	public String deleteById(PhotoManage photoManage) {
		if(null == photoManage || StringUtils.isBlank(photoManage.getId())){
			return BaseResp.newError().toJson();
		}
		photoManageService.delete(photoManage);
		return BaseResp.newSuccess().toJson();
	};

}