package com.haohan.platform.service.sys.modules.xiaodian.web.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.TreeDict;
import com.haohan.platform.service.sys.modules.xiaodian.service.common.TreeDictService;
import org.apache.shiro.authz.annotation.Logical;
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
 * 树形字典Controller
 * @author haohan
 * @version 2017-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/common/treeDict")
public class TreeDictController extends BaseController {

	@Autowired
	private TreeDictService treeDictService;
	
	@ModelAttribute
	public TreeDict get(@RequestParam(required=false) String id) {
		TreeDict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = treeDictService.get(id);
		}
		if (entity == null){
			entity = new TreeDict();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:common:treeDict:view")
	@RequestMapping(value = {"list", ""})
	public String list(TreeDict treeDict, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 当查询条件为空时，按type为00查询
        if (StringUtils.isEmpty(treeDict.getName()) && StringUtils.isEmpty(treeDict.getType()) && StringUtils.isEmpty(treeDict.getAttr())) {
            treeDict.setType("00");
        }
		List<TreeDict> list = treeDictService.findList(treeDict); 
		model.addAttribute("list", list);
		return "modules/xiaodian/common/treeDictList";
	}


	@RequiresPermissions(value = {"xiaodian:common:treeDict:view", "xiaodian:manage:merchant:delivery:view"}, logical = Logical.OR)
	@RequestMapping(value = "fetchChildrens")
	@ResponseBody
	public String fetchChildrens(TreeDict treeDict, HttpServletRequest request, HttpServletResponse response) {

		List<TreeDict> list = treeDictService.findList(treeDict);

		return new ApiResp().success(JsonMapper.toJsonString(list));
	}

	@RequiresPermissions("xiaodian:common:treeDict:view")
	@RequestMapping(value = "form")
	public String form(TreeDict treeDict, Model model) {
		if (treeDict.getParent()!=null && StringUtils.isNotBlank(treeDict.getParent().getId())){
			treeDict.setParent(treeDictService.get(treeDict.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(treeDict.getId())){
				TreeDict treeDictChild = new TreeDict();
				treeDictChild.setParent(new TreeDict(treeDict.getParent().getId()));
				List<TreeDict> list = treeDictService.findList(treeDict); 
				if (list.size() > 0){
					treeDict.setSort(list.get(list.size()-1).getSort());
					if (treeDict.getSort() != null){
						treeDict.setSort(treeDict.getSort() + 30);
					}
				}
			}
		}
		if (treeDict.getSort() == null){
			treeDict.setSort(30);
		}
		model.addAttribute("treeDict", treeDict);
		return "modules/xiaodian/common/treeDictForm";
	}

	@RequiresPermissions("xiaodian:common:treeDict:edit")
	@RequestMapping(value = "save")
	public String save(TreeDict treeDict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, treeDict)){
			return form(treeDict, model);
		}
		treeDictService.save(treeDict);
		addMessage(redirectAttributes, "保存字典信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/treeDict/?repage";
	}
	
	@RequiresPermissions("xiaodian:common:treeDict:edit")
	@RequestMapping(value = "delete")
	public String delete(TreeDict treeDict, RedirectAttributes redirectAttributes) {
		treeDictService.delete(treeDict);
		addMessage(redirectAttributes, "删除字典信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/common/treeDict/?repage";
	}




	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,String type,String attr, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		TreeDict treeDict =	new TreeDict();
		treeDict.setType(type);
		treeDict.setAttr(attr);
		List<TreeDict> list = treeDictService.findList(treeDict);
		for (int i=0; i<list.size(); i++){
			TreeDict e = list.get(i);
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