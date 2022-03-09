package com.haohan.platform.service.sys.modules.xiaodian.web.delivery;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.GoodsGifts;
import com.haohan.platform.service.sys.modules.xiaodian.service.delivery.GoodsGiftsService;
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
 * 赠品管理Controller
 * @author haohan
 * @version 2018-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/delivery/goodsGifts")
public class GoodsGiftsController extends BaseController {

	@Autowired
	private GoodsGiftsService goodsGiftsService;
	
	@ModelAttribute
	public GoodsGifts get(@RequestParam(required=false) String id) {
		GoodsGifts entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsGiftsService.get(id);
		}
		if (entity == null){
			entity = new GoodsGifts();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:delivery:goodsGifts:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsGifts goodsGifts, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsGifts> page = goodsGiftsService.findPage(new Page<GoodsGifts>(request, response), goodsGifts); 
		model.addAttribute("page", page);
		return "modules/xiaodian/delivery/goodsGiftsList";
	}

	@RequiresPermissions("xiaodian:delivery:goodsGifts:view")
	@RequestMapping(value = "form")
	public String form(GoodsGifts goodsGifts, Model model) {
		model.addAttribute("goodsGifts", goodsGifts);
		return "modules/xiaodian/delivery/goodsGiftsForm";
	}

	@RequiresPermissions("xiaodian:delivery:goodsGifts:edit")
	@RequestMapping(value = "save")
	public String save(GoodsGifts goodsGifts, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsGifts)){
			return form(goodsGifts, model);
		}
		goodsGiftsService.save(goodsGifts);
		addMessage(redirectAttributes, "保存赠品成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/goodsGifts/?repage";
	}
	
	@RequiresPermissions("xiaodian:delivery:goodsGifts:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsGifts goodsGifts, RedirectAttributes redirectAttributes) {
		goodsGiftsService.delete(goodsGifts);
		addMessage(redirectAttributes, "删除赠品成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/delivery/goodsGifts/?repage";
	}

}