package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliverServiceArea;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.DeliverServiceAreaService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 配送员服务区域Controller
 * @author yu.shen
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/deliverServiceArea")
public class DeliverServiceAreaController extends BaseController {

	@Autowired
	private DeliverServiceAreaService deliverServiceAreaService;
	@Autowired
	private MerchantService merchantService;
	
	@ModelAttribute
	public DeliverServiceArea get(@RequestParam(required=false) String id) {
		DeliverServiceArea entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deliverServiceAreaService.get(id);
		}
		if (entity == null){
			entity = new DeliverServiceArea();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:deliverServiceArea:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeliverServiceArea deliverServiceArea, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeliverServiceArea> page = deliverServiceAreaService.findPage(new Page<DeliverServiceArea>(request, response), deliverServiceArea); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/deliverServiceAreaList";
	}

	@RequiresPermissions("xiaodian:delivery:deliverServiceArea:view")
	@RequestMapping(value = "form")
	public String form(DeliverServiceArea deliverServiceArea, Model model) {
		List<Merchant> merchantList = merchantService.findListEnabled(new Merchant());
		model.addAttribute("merchantList",merchantList);
		model.addAttribute("deliverServiceArea", deliverServiceArea);
		return "modules/xiaodian/delivery/deliverServiceAreaForm";
	}

	@RequiresPermissions("xiaodian:delivery:deliverServiceArea:edit")
	@RequestMapping(value = "save")
	public String save(DeliverServiceArea deliverServiceArea, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, deliverServiceArea)){
//			return form(deliverServiceArea, model);
//		}
//		deliverServiceAreaService.save(deliverServiceArea);
        // 小区多选时
        String[] communityIdList = deliverServiceArea.getCommunityId().split(",");
        if (communityIdList.length > 1) {
            DeliverServiceArea area;
            for (int i = 0; i < communityIdList.length; i++) {
                area = new DeliverServiceArea();
                area.setMerchantId(deliverServiceArea.getMerchantId());
                area.setMerchantName(deliverServiceArea.getMerchantName());
                area.setShopId(deliverServiceArea.getShopId());
                area.setShopName(deliverServiceArea.getShopName());
                area.setDeliverManId(deliverServiceArea.getDeliverManId());
                area.setCommunityId(communityIdList[i]);
                deliverServiceAreaService.save(area);
            }
        } else {
            deliverServiceAreaService.save(deliverServiceArea);
        }
		addMessage(redirectAttributes, "保存配送服务区域成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliverServiceArea/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:deliverServiceArea:edit")
	@RequestMapping(value = "delete")
	public String delete(DeliverServiceArea deliverServiceArea, RedirectAttributes redirectAttributes) {
		deliverServiceAreaService.delete(deliverServiceArea);
		addMessage(redirectAttributes, "删除配送服务区域成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/deliverServiceArea/?repage";
	}

}