package com.haohan.platform.service.sys.modules.xiaodian.web.retail;

import com.haohan.platform.service.sys.common.config.Global;
import com.haohan.platform.service.sys.common.persistence.Page;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;
import com.haohan.platform.service.sys.modules.xiaodian.service.retail.GoodsModelService;
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
 * 商品规格管理Controller
 * @author haohan
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/xiaodian/retail/goodsModel")
public class GoodsModelController extends BaseController {

	@Autowired
	private GoodsModelService goodsModelService;
	
	@ModelAttribute
	public GoodsModel get(@RequestParam(required=false) String id) {
		GoodsModel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsModelService.get(id);
		}
		if (entity == null){
			entity = new GoodsModel();
		}
		return entity;
	}
	
	@RequiresPermissions("xiaodian:retail:goodsModel:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsModel goodsModel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsModel> page = new Page<GoodsModel>(request, response);
		goodsModel.setPage(page);
        List<GoodsModel> list = goodsModelService.findJoinList(goodsModel);
        page.setList(list);
		model.addAttribute("page", page);
		return "modules/xiaodian/retail/goodsModelList";
	}

	@RequiresPermissions("xiaodian:retail:goodsModel:view")
	@RequestMapping(value = "form")
	public String form(GoodsModel goodsModel, Model model) {
		model.addAttribute("goodsModel", goodsModel);
		return "modules/xiaodian/retail/goodsModelForm";
	}

	@RequiresPermissions("xiaodian:retail:goodsModel:edit")
	@RequestMapping(value = "save")
	public String save(GoodsModel goodsModel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsModel)){
			return form(goodsModel, model);
		}
		goodsModelService.save(goodsModel);
		addMessage(redirectAttributes, "保存商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsModel/?repage";
	}
	
	@RequiresPermissions("xiaodian:retail:goodsModel:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsModel goodsModel, RedirectAttributes redirectAttributes) {
		goodsModelService.delete(goodsModel);
		addMessage(redirectAttributes, "删除商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/xiaodian/retail/goodsModel/?repage";
	}

}