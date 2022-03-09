package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.CommunityManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DistrictManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.CommunityManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DistrictManageService;
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
 * 片区管理Controller
 * @author yu.shen
 * @version 2018-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/districtManage")
public class DistrictManageController extends BaseController {

	@Autowired
	private DistrictManageService districtManageService;
	@Autowired
	private CommunityManageService communityManageService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public DistrictManage get(@RequestParam(required=false) String id) {
		DistrictManage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = districtManageService.get(id);
		}
		if (entity == null){
			entity = new DistrictManage();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:districtManage:view")
	@RequestMapping(value = {"list", ""})
	public String list(DistrictManage districtManage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DistrictManage> page = districtManageService.findPage(new Page<DistrictManage>(request, response), districtManage); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/districtManageList";
	}

	@RequiresPermissions("xiaodian:delivery:districtManage:view")
	@RequestMapping(value = "form")
	public String form(DistrictManage districtManage, Model model) {
		model.addAttribute("districtManage", districtManage);
		// 同一区下的所有小区
		List<CommunityManage> communityList;
		if (StringUtils.isNotEmpty(districtManage.getRegion())) {
			CommunityManage community = new CommunityManage();
//			community.setProvince(districtManage.getProvince());
//			community.setCity(districtManage.getCity());
//			community.setRegion(districtManage.getRegion());
			communityList = communityManageService.findList(community);
		}else {
			communityList = new ArrayList<>();
		}
		model.addAttribute("communityList",communityList);
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList", merchantList);
		return "modules/xiaodian/delivery/districtManageForm";
	}

	@RequiresPermissions("xiaodian:delivery:districtManage:edit")
	@RequestMapping(value = "save")
	public String save(DistrictManage districtManage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, districtManage)){
			return form(districtManage, model);
		}
		districtManageService.save(districtManage);
		addMessage(redirectAttributes, "保存片区管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/districtManage/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:districtManage:edit")
	@RequestMapping(value = "delete")
	public String delete(DistrictManage districtManage, RedirectAttributes redirectAttributes) {
		districtManageService.delete(districtManage);
		addMessage(redirectAttributes, "删除片区管理成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/districtManage/?repage";
	}

    /**
     * 查询 商家的片区
     * @param merchantId
     * @return
     */
    @RequiresPermissions(value = {"xiaodian:delivery:districtManage:edit", "xiaodian:manage:merchant:delivery:view"}, logical = Logical.OR)
    @RequestMapping(value = "query")
    @ResponseBody
    public List<Map<String, Object>> query(@RequestParam("merchantId") String merchantId) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        if (StringUtils.isEmpty(merchantId)) {
            return list;
        }
        DistrictManage districtManage = new DistrictManage();
        districtManage.setMerchantId(merchantId);
        List<DistrictManage> manList = districtManageService.findList(districtManage);
        Map<String, Object> obj;
        for (DistrictManage s : manList) {
            obj = new HashMap<>();
            obj.put("id", s.getId());
            obj.put("name", s.getName());
            list.add(obj);
        }
        return list;
    }
}