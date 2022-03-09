package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopCategory;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopCategoryService;
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
 * 店铺分类管理Controller
 * @author haohan
 * @version 2019-01-15
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant/shopCategory")
public class ShopCategoryController extends BaseController {

	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@ModelAttribute
	public ShopCategory get(@RequestParam(required=false) String id) {
		ShopCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopCategoryService.get(id);
		}
		if (entity == null){
			entity = new ShopCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:merchant:shopCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopCategory shopCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ShopCategory> list = shopCategoryService.findList(shopCategory); 
		model.addAttribute("list", list);
		return "modules/xiaodian/merchant/shopCategoryList";
	}

	@RequiresPermissions("xiaodian:merchant:shopCategory:view")
	@RequestMapping(value = "form")
	public String form(ShopCategory shopCategory, Model model) {
		if (shopCategory.getParent()!=null && StringUtils.isNotBlank(shopCategory.getParent().getId())){
			shopCategory.setParent(shopCategoryService.get(shopCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(shopCategory.getId())){
				ShopCategory shopCategoryChild = new ShopCategory();
				shopCategoryChild.setParent(new ShopCategory(shopCategory.getParent().getId()));
				List<ShopCategory> list = shopCategoryService.findList(shopCategory); 
				if (list.size() > 0){
					shopCategory.setSort(list.get(list.size()-1).getSort());
					if (shopCategory.getSort() != null){
						shopCategory.setSort(shopCategory.getSort() + 30);
					}
				}
			}
		}
		if (shopCategory.getSort() == null){
			shopCategory.setSort(30);
		}
		model.addAttribute("shopCategory", shopCategory);
		return "modules/xiaodian/merchant/shopCategoryForm";
	}

	@RequiresPermissions("xiaodian:merchant:shopCategory:edit")
	@RequestMapping(value = "save")
	public String save(ShopCategory shopCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopCategory)){
			return form(shopCategory, model);
		}
		shopCategoryService.save(shopCategory);
		addMessage(redirectAttributes, "保存店铺分类成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shopCategory/?repage";
	}
	
	@RequiresPermissions("xiaodian:merchant:shopCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopCategory shopCategory, RedirectAttributes redirectAttributes) {
		shopCategoryService.delete(shopCategory);
		addMessage(redirectAttributes, "删除店铺分类成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shopCategory/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false)String limitType, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
        // 限制查询条件
        ShopCategory category = new ShopCategory();
		List<ShopCategory> list;
		if(StringUtils.isNotEmpty(limitType)){
		    category.setAggregationType(limitType);
        }
        list = shopCategoryService.findList(category);
		for (int i=0; i<list.size(); i++){
			ShopCategory e = list.get(i);
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