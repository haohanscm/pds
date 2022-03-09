package com.haohan.platform.service.sys.modules.xiaodian.web.merchant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.mapper.JsonMapper;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.ApiResp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppExt;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppExtService;
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
 * 商家应用扩展信息管理Controller
 * @author haohan
 * @version 2018-02-05
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/merchant/merchantAppExt")
public class MerchantAppExtController extends BaseController {

	@Autowired
	private MerchantAppExtService merchantAppExtService;
	
	@ModelAttribute
	public MerchantAppExt get(@RequestParam(required=false) String id) {
		MerchantAppExt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantAppExtService.get(id);
		}
		if (entity == null){
			entity = new MerchantAppExt();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:merchant:merchantAppExt:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantAppExt merchantAppExt, HttpServletRequest request, HttpServletResponse response, Model model) {
	    // 默认节点数5 即不同的appId
	    int defaultNodeNum = 5;
	    Page<MerchantAppExt> page = new Page<>(request,response);
        List<MerchantAppExt> resultList = new ArrayList<>();
        int num = StringUtils.toInteger(merchantAppExt.getRootNodeNum());
		page.setPageSize(num == 0 ? defaultNodeNum : num);
		// 不带appId查询时 按appId分页
		if(StringUtils.isEmpty(merchantAppExt.getAppId())){
            // 根据不同的appId分页查询
            page = merchantAppExtService.fetchAppIdPage(page, merchantAppExt);
            MerchantAppExt appExt = new MerchantAppExt();
            appExt.setName(merchantAppExt.getName());
            appExt.setTemplateId(merchantAppExt.getTemplateId());
            appExt.setMerchantId(merchantAppExt.getMerchantId());
            appExt.setFieldName(merchantAppExt.getFieldName());
            // 同一appId下的MerchantAppExt记录
            for (MerchantAppExt temp : page.getList()){
                appExt.setAppId(temp.getAppId());
                resultList.addAll(merchantAppExtService.findList(appExt));
            }
        }else {
		    resultList = merchantAppExtService.findList(merchantAppExt);
		    page.setCount(1);
        }
        page.setList(resultList);
		model.addAttribute("page",page);
		return "modules/xiaodian/merchant/merchantAppExtList";
	}

	@RequiresPermissions("xiaodian:merchant:merchantAppExt:view")
	@RequestMapping(value = "form")
	public String form(MerchantAppExt merchantAppExt, Model model) {
		if (merchantAppExt.getParent()!=null && StringUtils.isNotBlank(merchantAppExt.getParent().getId())){
			merchantAppExt.setParent(merchantAppExtService.get(merchantAppExt.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(merchantAppExt.getId())){
				MerchantAppExt merchantAppExtChild = new MerchantAppExt();
				merchantAppExtChild.setParent(new MerchantAppExt(merchantAppExt.getParent().getId()));
				List<MerchantAppExt> list = merchantAppExtService.findList(merchantAppExt); 
				if (list.size() > 0){
					merchantAppExt.setSort(list.get(list.size()-1).getSort());
					if (merchantAppExt.getSort() != null){
						merchantAppExt.setSort(merchantAppExt.getSort() + 30);
					}
				}
			}
		}
		if (merchantAppExt.getSort() == null){
			merchantAppExt.setSort(30);
		}
		model.addAttribute("merchantAppExt", merchantAppExt);
		return "modules/xiaodian/merchant/merchantAppExtForm";
	}

	@RequiresPermissions("xiaodian:merchant:merchantAppExt:edit")
	@RequestMapping(value = "save")
	public String save(MerchantAppExt merchantAppExt, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantAppExt)){
			return form(merchantAppExt, model);
		}
		merchantAppExtService.save(merchantAppExt);
		addMessage(redirectAttributes, "保存商家应用扩展信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/merchantAppExt/?repage";
	}
	
	@RequiresPermissions("xiaodian:merchant:merchantAppExt:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantAppExt merchantAppExt, RedirectAttributes redirectAttributes) {
		merchantAppExtService.deleteWithChildren(merchantAppExt);
		addMessage(redirectAttributes, "删除商家应用扩展信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/merchant/merchantAppExt/?appId="+merchantAppExt.getAppId();
	}

	@RequiresPermissions("xiaodian:merchant:merchantAppExt:view")
	@RequestMapping(value = "fetchChildrens")
	@ResponseBody
	public String fetchChildrens(MerchantAppExt merchantAppExt) {

		List<MerchantAppExt> list = merchantAppExtService.findList(merchantAppExt);

		return new ApiResp().success(JsonMapper.toJsonString(list));
	}

	@RequiresPermissions("xiaodian:merchant:merchantAppExt:edit")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<MerchantAppExt> list = merchantAppExtService.findList(new MerchantAppExt());
		for (int i=0; i<list.size(); i++){
			MerchantAppExt e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				map.put("fieldName", e.getFieldName());
				map.put("fieldCode", e.getFieldCode());
				map.put("fieldValue",e.getFieldValue());
				mapList.add(map);
			}
		}

		return mapList;
	}
	
}