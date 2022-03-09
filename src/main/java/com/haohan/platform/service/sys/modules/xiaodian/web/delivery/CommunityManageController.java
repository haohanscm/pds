package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.CommunityManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.CommunityManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小区信息管理Controller
 * @author yu.shen
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/communityManage")
public class CommunityManageController extends BaseController {

	@Autowired
	private CommunityManageService communityManageService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public CommunityManage get(@RequestParam(required=false) String id) {
		CommunityManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = communityManageService.get(id);
		}
		if (entity == null){
			entity = new CommunityManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:communityManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(CommunityManage communityManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CommunityManage> page = communityManageService.findPage(new Page<CommunityManage>(request, response), communityManage); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/communityManageList";
	}

	@RequiresPermissions("xiaodian:delivery:communityManage:view")
	@RequestMapping(value = "form")
	public String form(CommunityManage communityManage, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("communityManage", communityManage);
		return "modules/xiaodian/delivery/communityManageForm";
	}

	@RequiresPermissions("xiaodian:delivery:communityManage:edit")
	@RequestMapping(value = "save")
	public String save(CommunityManage communityManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, communityManage)){
			return form(communityManage, model);
		}
		communityManageService.save(communityManage);
		addMessage(redirectAttributes, "保存小区信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/communityManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:communityManage:edit")
	@RequestMapping(value = "delete")
	public String delete(CommunityManage communityManage, RedirectAttributes redirectAttributes) {
		communityManageService.delete(communityManage);
		addMessage(redirectAttributes, "删除小区信息成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/communityManage/?repage";
	}

    /**
     * 查询片区下关联的小区 或 根据 省市区查询小区
     * @param districtId
     * @return
     */
    @RequiresPermissions(value = {"xiaodian:delivery:communityManage:edit", "xiaodian:manage:merchant:delivery:view"}, logical = Logical.OR)
    @RequestMapping(value = "query")
    @ResponseBody
    public List<Map<String, Object>> query(@RequestParam(value = "districtId", required = false) String districtId,
                                           @RequestParam(value = "province", required = false) String province,
                                           @RequestParam(value = "city", required = false) String city,
                                           @RequestParam(value = "region", required = false) String region) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        CommunityManage communityManage = new CommunityManage();
        if (StringUtils.isNotEmpty(districtId)) {
            communityManage.setDistrictId(districtId);
        } else {
            if (StringUtils.isEmpty(province) && StringUtils.isEmpty(city) && StringUtils.isEmpty(region)) {
                return list;
            }
            communityManage.setProvince(province);
            communityManage.setCity(city);
            communityManage.setRegion(region);
        }
        List<CommunityManage> manList = communityManageService.findList(communityManage);
        Map<String, Object> obj;
        for (CommunityManage s : manList) {
            obj = new HashMap<>();
            obj.put("id", s.getId());
            obj.put("name", s.getName());
            list.add(obj);
        }
        return list;
    }

}