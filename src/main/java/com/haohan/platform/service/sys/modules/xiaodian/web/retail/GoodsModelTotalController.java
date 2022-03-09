package com.haohan.platform.service.sys.modules.xiaodian.web.retail;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModelTotal;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelTotalService;
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
 * 零售商品规格名称管理Controller
 * @author haohan
 * @version 2018-09-27
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/retail/goodsModelTotal")
public class GoodsModelTotalController extends BaseController {

	@Autowired
	private GoodsModelTotalService goodsModelTotalService;
	
	@ModelAttribute
	public GoodsModelTotal get(@RequestParam(required=false) String id) {
		GoodsModelTotal entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsModelTotalService.get(id);
		}
		if (entity == null){
			entity = new GoodsModelTotal();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:retail:goodsModelTotal:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsModelTotal goodsModelTotal, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsModelTotal> page = goodsModelTotalService.findPage(new Page<GoodsModelTotal>(request, response), goodsModelTotal); 
		model.addAttribute("page", page);
		return "modules/xiaodian/retail/goodsModelTotalList";
	}

	@RequiresPermissions("xiaodian:retail:goodsModelTotal:view")
	@RequestMapping(value = "form")
	public String form(GoodsModelTotal goodsModelTotal, Model model) {
		model.addAttribute("goodsModelTotal", goodsModelTotal);
		return "modules/xiaodian/retail/goodsModelTotalForm";
	}

	@RequiresPermissions("xiaodian:retail:goodsModelTotal:edit")
	@RequestMapping(value = "save")
	public String save(GoodsModelTotal goodsModelTotal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsModelTotal)){
			return form(goodsModelTotal, model);
		}
		goodsModelTotalService.save(goodsModelTotal);
		addMessage(redirectAttributes, "保存规格名称成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsModelTotal/?repage";
	}
	
	@RequiresPermissions("xiaodian:retail:goodsModelTotal:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsModelTotal goodsModelTotal, RedirectAttributes redirectAttributes) {
		goodsModelTotalService.delete(goodsModelTotal);
		addMessage(redirectAttributes, "删除规格名称成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsModelTotal/?repage";
	}

}