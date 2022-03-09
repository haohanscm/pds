package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopTemplate;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.ShopTemplateExtInfo;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopTemplateExtInfoService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.ShopTemplateService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 店铺模板扩展信息管理Controller
 * @author haohan
 * @version 2018-02-05
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant/shopTemplateExtInfo")
public class ShopTemplateExtInfoController extends BaseController {

	@Autowired
	private ShopTemplateExtInfoService shopTemplateExtInfoService;

	@Autowired
	private ShopTemplateService shopTemplateService;
	
	@ModelAttribute
	public ShopTemplateExtInfo get(@RequestParam(required=false) String id) {
		ShopTemplateExtInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopTemplateExtInfoService.get(id);
		}
		if (entity == null){
			entity = new ShopTemplateExtInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:merchant:shopTemplateExtInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopTemplateExtInfo shopTemplateExtInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String originNodeId = "0";
		int defaultNodeNum = 5;
		Page<ShopTemplateExtInfo> rootNodePage = new Page<ShopTemplateExtInfo>(request,response);
		ShopTemplateExtInfo originNode = new ShopTemplateExtInfo();
		List<ShopTemplateExtInfo> resultList = new ArrayList<ShopTemplateExtInfo>();
		try {
			rootNodePage.setPageSize(shopTemplateExtInfo.getRootNodeNum()!=null&&!"".equals(shopTemplateExtInfo.getRootNodeNum())?Integer.parseInt(shopTemplateExtInfo.getRootNodeNum()):defaultNodeNum); //TODO
		} catch (NumberFormatException e) {
			rootNodePage.setPageSize(defaultNodeNum);
		}
		originNode.setId(originNodeId);
		shopTemplateExtInfo.setParent(originNode);
		rootNodePage = shopTemplateExtInfoService.findPage(rootNodePage,shopTemplateExtInfo);

		shopTemplateExtInfo.setPage(null);
		shopTemplateExtInfo.setParent(null);
		for (ShopTemplateExtInfo temp : rootNodePage.getList()){
			shopTemplateExtInfo.setParentIds(temp.getId());
			resultList.addAll(shopTemplateExtInfoService.findList(shopTemplateExtInfo));
		}
		resultList.addAll(rootNodePage.getList());
		rootNodePage.setList(resultList);
		model.addAttribute("page",rootNodePage);
		model.addAttribute("list",resultList);
		return "modules/xiaodian/merchant/shopTemplateExtInfoList";
	}

	@RequiresPermissions("xiaodian:merchant:shopTemplateExtInfo:view")
	@RequestMapping(value = "form")
	public String form(ShopTemplateExtInfo shopTemplateExtInfo, Model model) {
		if (shopTemplateExtInfo.getParent()!=null && StringUtils.isNotBlank(shopTemplateExtInfo.getParent().getId())){
			shopTemplateExtInfo.setParent(shopTemplateExtInfoService.get(shopTemplateExtInfo.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(shopTemplateExtInfo.getId())){
				ShopTemplateExtInfo shopTemplateExtInfoChild = new ShopTemplateExtInfo();
				shopTemplateExtInfoChild.setParent(new ShopTemplateExtInfo(shopTemplateExtInfo.getParent().getId()));
				List<ShopTemplateExtInfo> list = shopTemplateExtInfoService.findList(shopTemplateExtInfo); 
				if (list.size() > 0){
					shopTemplateExtInfo.setSort(list.get(list.size()-1).getSort());
					if (shopTemplateExtInfo.getSort() != null){
						shopTemplateExtInfo.setSort(shopTemplateExtInfo.getSort() + 30);
					}
				}
			}
		}
		if (shopTemplateExtInfo.getSort() == null){
			shopTemplateExtInfo.setSort(30);
		}
		List<ShopTemplate> shopTemplates = shopTemplateService.findList(new ShopTemplate());
		model.addAttribute("shopTemplates", shopTemplates);
		model.addAttribute("shopTemplateExtInfo", shopTemplateExtInfo);
		return "modules/xiaodian/merchant/shopTemplateExtInfoForm";
	}

	@RequiresPermissions("xiaodian:merchant:shopTemplateExtInfo:edit")
	@RequestMapping(value = "save")
	public String save(ShopTemplateExtInfo shopTemplateExtInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopTemplateExtInfo)){
			return form(shopTemplateExtInfo, model);
		}
		shopTemplateExtInfoService.save(shopTemplateExtInfo);
		addMessage(redirectAttributes, "保存模板扩展信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shopTemplateExtInfo/?repage";
	}
	
	@RequiresPermissions("xiaodian:merchant:shopTemplateExtInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopTemplateExtInfo shopTemplateExtInfo, RedirectAttributes redirectAttributes) {
		shopTemplateExtInfoService.delete(shopTemplateExtInfo);
		addMessage(redirectAttributes, "删除模板扩展信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/shopTemplateExtInfo/?repage";
	}

	@RequiresPermissions("xiaodian:merchant:shopTemplateExtInfo:edit")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ShopTemplateExtInfo> list = shopTemplateExtInfoService.findList(new ShopTemplateExtInfo());
		for (int i=0; i<list.size(); i++){
			ShopTemplateExtInfo e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				map.put("fieldCode", e.getFieldCode());
				mapList.add(map);
			}
		}

		return mapList;
	}
	
}