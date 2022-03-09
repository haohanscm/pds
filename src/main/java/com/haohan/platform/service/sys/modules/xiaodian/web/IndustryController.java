package com.haohan.platform.service.sys.modules.xiaodian.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.Industry;
import com.haohan.platform.service.sys.modules.xiaodian.service.IndustryService;
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
 * 行业分类管理Controller
 * @author haohan
 * @version 2017-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/industry")
public class IndustryController extends BaseController {

	@Autowired
	private IndustryService industryService;
	
	@ModelAttribute
	public Industry get(@RequestParam(required=false) String id) {
		Industry entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = industryService.get(id);
		}
		if (entity == null){
			entity = new Industry();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:industry:view")
	@RequestMapping(value = {"list", ""})
	public String list(Industry industry, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Industry> list = industryService.findList(industry);
		model.addAttribute("list", list);
		return "modules/xiaodian/industryList";
	}

	@RequiresPermissions("xiaodian:industry:view")
	@RequestMapping(value = "form")
	public String form(Industry industry, Model model) {
		if (industry.getParent()!=null && StringUtils.isNotBlank(industry.getParent().getId())){
			industry.setParent(industryService.get(industry.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(industry.getId())){
				Industry industryChild = new Industry();
				industryChild.setParent(new Industry(industry.getParent().getId()));
				List<Industry> list = industryService.findList(industry);
				if (list.size() > 0){
					industry.setSort(list.get(list.size()-1).getSort());
					if (industry.getSort() != null){
						industry.setSort(industry.getSort() + 30);
					}
				}
			}
		}
		if (industry.getSort() == null){
			industry.setSort(30);
		}
		model.addAttribute("industry", industry);
		return "modules/xiaodian/industryForm";
	}

	@RequiresPermissions("xiaodian:industry:edit")
	@RequestMapping(value = "save")
	public String save(Industry industry, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, industry)){
			return form(industry, model);
		}
		industryService.save(industry);
		addMessage(redirectAttributes, "保存行业分类成功");
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/industry/?repage";
	}
	
	@RequiresPermissions("xiaodian:industry:edit")
	@RequestMapping(value = "delete")
	public String delete(Industry industry, RedirectAttributes redirectAttributes) {
		industryService.delete(industry);
		addMessage(redirectAttributes, "删除行业分类成功");
		return "redirect:"+ Global.getAdminPath()+"/xiaodian/industry/?repage";
	}

	@RequiresPermissions("xiaodian:industry:view")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Industry> list = industryService.findList(new Industry());
		for (int i=0; i<list.size(); i++){
			Industry e = list.get(i);
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