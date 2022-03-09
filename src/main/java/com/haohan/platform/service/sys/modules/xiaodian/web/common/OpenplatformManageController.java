package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.OpenplatformManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.OpenplatformManageService;
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
import java.util.List;
import java.util.Map;

/**
 * 开放平台应用资料管理Controller
 * @author haohan
 * @version 2018-02-01
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/openplatformManage")
public class OpenplatformManageController extends BaseController {

	@Autowired
	private OpenplatformManageService openplatformManageService;

	@ModelAttribute
	public OpenplatformManage get(@RequestParam(required=false) String id) {
		OpenplatformManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = openplatformManageService.get(id);
		}
		if (entity == null){
			entity = new OpenplatformManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:openplatformManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpenplatformManage openplatformManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<OpenplatformManage> list = openplatformManageService.findList(openplatformManage); 
		model.addAttribute("list", list);
		return "modules/xiaodian/common/openplatformManageList";
	}

	@RequiresPermissions("xiaodian:common:openplatformManage:view")
	@RequestMapping(value = "form")
	public String form(OpenplatformManage openplatformManage, Model model) {
		if (openplatformManage.getParent()!=null && StringUtils.isNotBlank(openplatformManage.getParent().getId())){
			openplatformManage.setParent(openplatformManageService.get(openplatformManage.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(openplatformManage.getId())){
				OpenplatformManage openplatformManageChild = new OpenplatformManage();
				openplatformManageChild.setParent(new OpenplatformManage(openplatformManage.getParent().getId()));
				List<OpenplatformManage> list = openplatformManageService.findList(openplatformManage); 
				if (list.size() > 0){
					openplatformManage.setSort(list.get(list.size()-1).getSort());
					if (openplatformManage.getSort() != null){
						openplatformManage.setSort(openplatformManage.getSort() + 30);
					}
				}
			}
		}
		if (openplatformManage.getSort() == null){
			openplatformManage.setSort(30);
		}
		model.addAttribute("openplatformManage", openplatformManage);
		return "modules/xiaodian/common/openplatformManageForm";
	}



	@RequiresPermissions("xiaodian:common:openplatformManage:edit")
	@RequestMapping(value = "save")
	public String save(OpenplatformManage openplatformManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, openplatformManage)){
			return form(openplatformManage, model);
		}

		openplatformManageService.save(openplatformManage);
		addMessage(redirectAttributes, "保存应用信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/openplatformManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:openplatformManage:edit")
	@RequestMapping(value = "delete")
	public String delete(OpenplatformManage openplatformManage, RedirectAttributes redirectAttributes) {
		openplatformManageService.delete(openplatformManage);
		addMessage(redirectAttributes, "删除应用信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/openplatformManage/?repage";
	}

	@RequiresPermissions("xiaodian:common:openplatformManage:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<OpenplatformManage> list = openplatformManageService.findList(new OpenplatformManage());
		for (int i=0; i<list.size(); i++){
			OpenplatformManage e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getAppName());
				map.put("appName", e.getAppName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}