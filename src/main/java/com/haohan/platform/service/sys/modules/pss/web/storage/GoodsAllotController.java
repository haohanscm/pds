package com.haohan.platform.service.sys.modules.pss.web.storage;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.pss.entity.storage.GoodsAllot;
import com.haohan.platform.service.sys.modules.pss.service.storage.GoodsAllotService;
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
 * 商品调拨Controller
 * @author haohan
 * @version 2018-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/pss/storage/goodsAllot")
public class GoodsAllotController extends BaseController {

	@Autowired
	private GoodsAllotService goodsAllotService;
	
	@ModelAttribute
	public GoodsAllot get(@RequestParam(required=false) String id) {
		GoodsAllot entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsAllotService.get(id);
		}
		if (entity == null){
			entity = new GoodsAllot();
		}
		return entity;
	}
	
	@RequiresPermissions("pss:storage:goodsAllot:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsAllot goodsAllot, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsAllot> page = goodsAllotService.findPage(new Page<GoodsAllot>(request, response), goodsAllot); 
		model.addAttribute("page", page);
		return "modules/pss/storage/goodsAllotList";
	}

	@RequiresPermissions("pss:storage:goodsAllot:view")
	@RequestMapping(value = "form")
	public String form(GoodsAllot goodsAllot, Model model) {
		model.addAttribute("goodsAllot", goodsAllot);
		return "modules/pss/storage/goodsAllotForm";
	}

	@RequiresPermissions("pss:storage:goodsAllot:edit")
	@RequestMapping(value = "save")
	public String save(GoodsAllot goodsAllot, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsAllot)){
			return form(goodsAllot, model);
		}
		goodsAllotService.save(goodsAllot);
		addMessage(redirectAttributes, "保存商品调拨成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/goodsAllot/?repage";
	}
	
	@RequiresPermissions("pss:storage:goodsAllot:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsAllot goodsAllot, RedirectAttributes redirectAttributes) {
		goodsAllotService.delete(goodsAllot);
		addMessage(redirectAttributes, "删除商品调拨成功");
		return "redirect:"+Global.getAdminPath()+"/pss/storage/goodsAllot/?repage";
	}

}