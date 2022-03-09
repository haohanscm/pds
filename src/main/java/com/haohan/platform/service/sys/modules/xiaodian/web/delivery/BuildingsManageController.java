package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.BuildingsManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.BuildingsManageService;
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
 * 楼栋管理Controller
 * @author yu.shen
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/buildingsManage")
public class BuildingsManageController extends BaseController {

	@Autowired
	private BuildingsManageService buildingsManageService;
	
	@ModelAttribute
	public BuildingsManage get(@RequestParam(required=false) String id) {
		BuildingsManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = buildingsManageService.get(id);
		}
		if (entity == null){
			entity = new BuildingsManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:buildingsManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(BuildingsManage buildingsManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<BuildingsManage> list = buildingsManageService.findList(buildingsManage); 
		model.addAttribute("list", list);
		return "modules/xiaodian/delivery/buildingsManageList";
	}

	@RequiresPermissions("xiaodian:delivery:buildingsManage:view")
	@RequestMapping(value = "form")
	public String form(BuildingsManage buildingsManage, Model model) {
		if (buildingsManage.getParent()!=null && StringUtils.isNotBlank(buildingsManage.getParent().getId())){
			buildingsManage.setParent(buildingsManageService.get(buildingsManage.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(buildingsManage.getId())){
				BuildingsManage buildingsManageChild = new BuildingsManage();
				buildingsManageChild.setParent(new BuildingsManage(buildingsManage.getParent().getId()));
				List<BuildingsManage> list = buildingsManageService.findList(buildingsManage); 
				if (list.size() > 0){
					buildingsManage.setSort(list.get(list.size()-1).getSort());
					if (buildingsManage.getSort() != null){
						buildingsManage.setSort(buildingsManage.getSort() + 30);
					}
				}
			}
		}
		if (buildingsManage.getSort() == null){
			buildingsManage.setSort(30);
		}
		model.addAttribute("buildingsManage", buildingsManage);
		return "modules/xiaodian/delivery/buildingsManageForm";
	}

	@RequiresPermissions("xiaodian:delivery:buildingsManage:edit")
	@RequestMapping(value = "save")
	public String save(BuildingsManage buildingsManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, buildingsManage)){
			return form(buildingsManage, model);
		}
		buildingsManageService.save(buildingsManage);
		addMessage(redirectAttributes, "保存楼栋信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/buildingsManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:buildingsManage:edit")
	@RequestMapping(value = "delete")
	public String delete(BuildingsManage buildingsManage, RedirectAttributes redirectAttributes) {
		buildingsManageService.delete(buildingsManage);
		addMessage(redirectAttributes, "删除楼栋信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/buildingsManage/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BuildingsManage> list = buildingsManageService.findList(new BuildingsManage());
		for (int i=0; i<list.size(); i++){
			BuildingsManage e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}