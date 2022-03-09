package com.haohan.platform.service.sys.modules.pss.web.goods;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.goods.PssGoodsCategory;
import com.haohan.platform.service.sys.modules.pss.service.goods.PssGoodsCategoryService;
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
 * 商品总分类Controller
 * @author haohan
 * @version 2018-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/goods/pssGoodsCategory")
public class PssGoodsCategoryController extends BaseController {

	@Autowired
	private PssGoodsCategoryService pssGoodsCategoryService;
	
	@ModelAttribute
	public PssGoodsCategory get(@RequestParam(required=false) String id) {
		PssGoodsCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pssGoodsCategoryService.get(id);
		}
		if (entity == null){
			entity = new PssGoodsCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:goods:pssGoodsCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(PssGoodsCategory pssGoodsCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<PssGoodsCategory> list = pssGoodsCategoryService.findList(pssGoodsCategory);
		model.addAttribute("list", list);
		return "modules/pss/goods/pssGoodsCategoryList";
	}

	@RequiresPermissions("pss:goods:pssGoodsCategory:view")
	@RequestMapping(value = "form")
	public String form(PssGoodsCategory pssGoodsCategory, Model model) {
		if (pssGoodsCategory.getParent()!=null && StringUtils.isNotBlank(pssGoodsCategory.getParent().getId())){
			pssGoodsCategory.setParent(pssGoodsCategoryService.get(pssGoodsCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(pssGoodsCategory.getId())){
				PssGoodsCategory pssGoodsCategoryChild = new PssGoodsCategory();
				pssGoodsCategoryChild.setParent(new PssGoodsCategory(pssGoodsCategory.getParent().getId()));
				List<PssGoodsCategory> list = pssGoodsCategoryService.findList(pssGoodsCategory);
				if (list.size() > 0){
					pssGoodsCategory.setSort(list.get(list.size()-1).getSort());
					if (pssGoodsCategory.getSort() != null){
						pssGoodsCategory.setSort(pssGoodsCategory.getSort() + 30);
					}
				}
			}
		}
		if (pssGoodsCategory.getSort() == null){
			pssGoodsCategory.setSort(30);
		}
		model.addAttribute("pssGoodsCategory", pssGoodsCategory);
		return "modules/pss/goods/pssGoodsCategoryForm";
	}

	@RequiresPermissions("pss:goods:pssGoodsCategory:edit")
	@RequestMapping(value = "save")
	public String save(PssGoodsCategory pssGoodsCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, pssGoodsCategory)){
			return form(pssGoodsCategory, model);
		}
		pssGoodsCategoryService.save(pssGoodsCategory);
		addMessage(redirectAttributes, "保存商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/pss/goods/pssGoodsCategory/?repage";
	}
	
	@RequiresPermissions("pss:goods:pssGoodsCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(PssGoodsCategory pssGoodsCategory, RedirectAttributes redirectAttributes) {
		pssGoodsCategoryService.delete(pssGoodsCategory);
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/pss/goods/pssGoodsCategory/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<PssGoodsCategory> list = pssGoodsCategoryService.findList(new PssGoodsCategory());
		for (int i=0; i<list.size(); i++){
			PssGoodsCategory e = list.get(i);
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