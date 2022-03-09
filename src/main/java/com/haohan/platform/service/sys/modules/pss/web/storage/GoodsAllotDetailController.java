package com.haohan.platform.service.sys.modules.pss.web.storage;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllotDetail;
import com.haohan.platform.service.sys.modules.pss.service.storage.GoodsAllotDetailService;
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

/**
 * 商品调拨明细Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/storage/goodsAllotDetail")
public class GoodsAllotDetailController extends BaseController {

	@Autowired
	private GoodsAllotDetailService goodsAllotDetailService;
	
	@ModelAttribute
	public GoodsAllotDetail get(@RequestParam(required=false) String id) {
		GoodsAllotDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsAllotDetailService.get(id);
		}
		if (entity == null){
			entity = new GoodsAllotDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:storage:goodsAllotDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsAllotDetail goodsAllotDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsAllotDetail> page = goodsAllotDetailService.findPage(new Page<GoodsAllotDetail>(request, response), goodsAllotDetail); 
		model.addAttribute("page", page);
		return "modules/pss/storage/goodsAllotDetailList";
	}

	@RequiresPermissions("pss:storage:goodsAllotDetail:view")
	@RequestMapping(value = "form")
	public String form(GoodsAllotDetail goodsAllotDetail, Model model) {
		model.addAttribute("goodsAllotDetail", goodsAllotDetail);
		return "modules/pss/storage/goodsAllotDetailForm";
	}

	@RequiresPermissions("pss:storage:goodsAllotDetail:edit")
	@RequestMapping(value = "save")
	public String save(GoodsAllotDetail goodsAllotDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsAllotDetail)){
			return form(goodsAllotDetail, model);
		}
		goodsAllotDetailService.save(goodsAllotDetail);
		addMessage(redirectAttributes, "保存商品调拨明细成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/goodsAllotDetail/?repage";
	}
	
	@RequiresPermissions("pss:storage:goodsAllotDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsAllotDetail goodsAllotDetail, RedirectAttributes redirectAttributes) {
		goodsAllotDetailService.delete(goodsAllotDetail);
		addMessage(redirectAttributes, "删除商品调拨明细成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/goodsAllotDetail/?repage";
	}

}